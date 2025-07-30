package com.sbmicroservices.models.request;


import lombok.Data;

@Data
public class SignUpUserBody {

    private String userName;

    private String userEmail;

    private String contactNo;

    private String password;

}
