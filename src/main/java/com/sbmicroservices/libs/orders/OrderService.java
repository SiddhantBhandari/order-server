package com.sbmicroservices.libs.orders;


import com.sbmicroservices.apis.payment_server.PaymentServerApis;
import com.sbmicroservices.apis.payment_server.request.PaymentRequest;
import com.sbmicroservices.apis.product_server.response.GetProductResponse;
import com.sbmicroservices.apis.product_serverV2.ProductServerV2Apis;
import com.sbmicroservices.exceptions.InvalidAttributesException;
import com.sbmicroservices.exceptions.OrderNotFoundException;
import com.sbmicroservices.exceptions.UserNotFoundException;
import com.sbmicroservices.libs.cart_items.CartItems;
import com.sbmicroservices.libs.cart_items.CartItemsService;
import com.sbmicroservices.libs.counter.CounterService;
import com.sbmicroservices.libs.users.UserService;
import com.sbmicroservices.libs.users.Users;
import com.sbmicroservices.models.request.Items;
import com.sbmicroservices.models.request.PlaceOrderRequest;
import com.sbmicroservices.models.response.OrderDetails;
import com.sbmicroservices.models.response.SuccessResponse;
import com.sbmicroservices.utils.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderService {

    private static final String ORDER_SEQUENCE = "ORDER_SEQUENCE";

    private static final String ITEM_SEQUENCE = "ITEM_SEQUENCE";

    private final OrderRepository orderRepository;

    private final UserService userService;

    private final CounterService counterService;

    private final CartItemsService cartItemsService;

    @Autowired
    private ProductServerV2Apis productServerV2Apis;

    @Autowired
    private PaymentServerApis paymentServerApis;

    public OrderService(OrderRepository orderRepository, UserService userService, CounterService counterService, CartItemsService cartItemsService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.counterService = counterService;
        this.cartItemsService = cartItemsService;
    }

    private void save(Orders orders) {
        orderRepository.save(orders);
    }

    public void update(Orders orders) {
        orderRepository.saveAndFlush(orders);
    }

    public void delete(Orders orders) {
        orderRepository.delete(orders);
    }


    private final Set<String> paymentModes = new HashSet<>();

    {
        paymentModes.add("UPI");
        paymentModes.add("CREDIT_CARD");
        paymentModes.add("DEBIT_CARD");
        paymentModes.add("NET_BANKING");
        paymentModes.add("CASH_ON_DELIVERY");
    }

    public Orders placeOrder(PlaceOrderRequest request) throws UserNotFoundException, InvalidAttributesException {
        try {
            String paymentMode = request.getPaymentMode().toUpperCase();
            if (!paymentModes.contains(paymentMode)) {
                throw new RuntimeException("Invalid Payment Mode selected.");
            }

            Users user = getUser(request.getUserId());
            log.info("Placing order for user :: {}", user.getUserName());

            Orders orders = setBasicOrderDetails(user, request.getCurrency());

            OrderStatus orderStatus = initializeCartItems(request, orders);
            log.info("Order status is :: {}", orderStatus);

//        CompletableFuture.supplyAsync(() -> {
            log.info("Payment triggered for order :: {}", orders.getOrderId());
            initializePayment(orders, paymentMode);
//            return null;
//        });

            orders.setStatus(orderStatus);
            return orders;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    private Users getUser(Long userId) throws UserNotFoundException, InvalidAttributesException {
        if (userId == null || userId.equals(0L)) {
            throw new InvalidAttributesException("User id passed is invalid");
        }
        return userService.getUserById(userId);
    }

    private Orders setBasicOrderDetails(Users user, String currency) {
        Orders orders = new Orders();
        orders.setUser(user);
        Long counter = counterService.counter(ORDER_SEQUENCE);
        orders.setOrderId(String.format("%s-%05d", "CART-ORDER", counter));
        orders.setOrderDate(new Date());
        orders.setDeliveryDate(DateUtils.addDays(orders.getOrderDate(), 5));
        orders.setCurrency(currency);
        orders.setTotalAmount(0D); // Temporary
        orders.setStatus(OrderStatus.INITIATED);

        log.info("Order saved with basic details..");
        save(orders);
        return orders;
    }

    public OrderStatus initializeCartItems(PlaceOrderRequest request, Orders orders) {
        OrderStatus orderStatus = null;
        Double totalAmount = 0D;
        for (Items items : request.getItems()) {
            try {
                ResponseEntity<Void> reduceQuantity = productServerV2Apis.reduceQuantity(items.getProductId().toString(), items.getQuantity());
                HttpStatusCode statusCode = reduceQuantity.getStatusCode();
                if (!statusCode.is2xxSuccessful() || statusCode.is3xxRedirection()
                        || statusCode.is4xxClientError() || statusCode.is5xxServerError()) {
                    log.error("Error while updating product qtty for product :: {}", items.getProductId());
                    orderStatus = OrderStatus.FAILED;
                } else if (statusCode.is2xxSuccessful()) {
                    log.info("Quantity updated in product server for item :: {}", items.getProductId());
                    orderStatus = OrderStatus.PENDING;
                }
                ResponseEntity<GetProductResponse> getProductResponse = productServerV2Apis.getProductDetails(items.getProductId().toString(), "0");
                HttpStatusCode getProductResponseStatusCode = getProductResponse.getStatusCode();
                if (!getProductResponseStatusCode.is2xxSuccessful() || getProductResponseStatusCode.is5xxServerError() || getProductResponseStatusCode.is4xxClientError()
                        || getProductResponseStatusCode.is3xxRedirection()) {
                    log.error("Error while fetching product details for product :: {}", items.getProductId());
                    continue;
                }
                GetProductResponse productDetails = getProductResponse.getBody();

                if (productDetails != null && productDetails.getDetails() != null) {
                    CartItems cartItems = new CartItems();
                    cartItems.setOrder(orders);
                    cartItems.setProductId(productDetails.getDetails().getId());
                    cartItems.setQuantity(items.getQuantity());
                    cartItems.setProductName(productDetails.getDetails().getProductName());
                    cartItems.setUnitPrice(productDetails.getDetails().getPrice());
                    cartItems.setTotalPrice(items.getQuantity() * cartItems.getUnitPrice());
                    cartItems.setUsers(orders.getUser());
                    cartItems.setVendorId(productDetails.getDetails().getVendorId());
                    Long itemCounter = counterService.counter(ITEM_SEQUENCE);
                    cartItems.setItemId(String.format("%s-%05d", "CART-ITEM", itemCounter));

                    cartItemsService.save(cartItems);
                    totalAmount += cartItems.getTotalPrice();
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("Error while saving cart item :: {}", e.getLocalizedMessage());
                orderStatus = OrderStatus.FAILED;
            }
        }
        orders.setTotalAmount(totalAmount);
        orders.setStatus(orderStatus);
        update(orders);
        return orderStatus;
    }

    private void initializePayment(Orders orders, String paymentMode) {
        PaymentRequest paymentRequest = new PaymentRequest(orders);
        paymentRequest.setPaymentMethod(paymentMode);
        String json = Mapper.toJson(paymentRequest);
        log.info("Payment request :: {}", json);
        ResponseEntity<SuccessResponse> response = paymentServerApis.doPayment(paymentRequest);
        HttpStatusCode statusCode = response.getStatusCode();
        if (!statusCode.is2xxSuccessful() || statusCode.is3xxRedirection()
                || statusCode.is4xxClientError() || statusCode.is5xxServerError()) {
            log.error("Error while calling payment api for order :: {} ", orders.getOrderId());
            orders.setStatus(OrderStatus.PENDING);
        } else if (statusCode.is2xxSuccessful() && response.hasBody() && response.getBody() != null && response.getBody().isSuccess()) {
            log.info("Payment completed successfully for order :: {}", orders.getOrderId());
            orders.setStatus(OrderStatus.COMPLETED);
        }
        update(orders);
    }


    public Orders getOrderById(Long id) throws OrderNotFoundException {
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Order not found with provided id"));
    }


    public OrderDetails getOrderDetails(Long id) throws OrderNotFoundException {
        Orders orders = getOrderById(id);
        OrderDetails orderDetails = new OrderDetails(orders);
        List<CartItems> orderedItems = cartItemsService.getOrderedItems(id);
        orderDetails.setItems(orderedItems
                .stream()
                .map(items -> {
                    GetProductResponse getProductResponse = null;
                    ResponseEntity<GetProductResponse> productDetails = productServerV2Apis.getProductDetails(items.getProductId().toString(), "0");
                    if(productDetails.getStatusCode().is2xxSuccessful() && productDetails.hasBody()){
                        getProductResponse = productDetails.getBody();
                    }
                    return new com.sbmicroservices.models.response.Items(items, getProductResponse);
                })
                .collect(Collectors.toList()));
        return orderDetails;
    }

}
