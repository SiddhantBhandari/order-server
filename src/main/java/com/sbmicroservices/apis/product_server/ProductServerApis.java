package com.sbmicroservices.apis.product_server;

import com.sbmicroservices.apis.product_server.response.GetProductResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProductServerApis {

    @PUT("reduceQuantity/{id}/quantity/{qtty}")
    Call<Void> reduceQuantity(@Path("id") String productId, @Path("qtty") Double qtty);

    @GET("{id}/{code}")
    Call<GetProductResponse> getProductDetails(@Path("id") String id, @Path("code") String code);
}
