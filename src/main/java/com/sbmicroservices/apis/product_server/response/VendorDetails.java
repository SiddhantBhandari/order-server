package com.sbmicroservices.apis.product_server.response;


import lombok.Data;

@Data
public class VendorDetails {

    private Long id;

    private String vendorId;

    private String vendorName;

    private String vendorEmail;

    private String contact;

    private String tradeName;

    private String category;

    private String pan;

    private String gstIn;
}
