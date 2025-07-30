package com.sbmicroservices.models.response;

import com.sbmicroservices.apis.product_server.response.VendorDetails;
import lombok.Data;


@Data
public class Vendor {

    private Long id;

    private String vendorId;

    private String vendorName;

    private String vendorEmail;

    private String contact;

    private String tradeName;

    private String category;

    private String pan;

    private String gstIn;

    public Vendor(VendorDetails details) {
        this.id = details.getId();
        this.vendorId = details.getVendorId();
        this.vendorName = details.getVendorName();
        this.vendorEmail = details.getVendorEmail();
        this.contact = details.getContact();
        this.tradeName = details.getTradeName();
        this.category = details.getCategory();
        this.pan = details.getPan();
        this.gstIn = details.getPan();
    }
}
