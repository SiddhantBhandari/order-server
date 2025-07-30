package com.sbmicroservices.models.response;


import com.sbmicroservices.libs.users.Users;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "builder")
public class UserDetails {

    private String userName;

    private String userEmail;

    private String contactNo;

    public UserDetails(Users users) {
        this.userName = users.getUserName();
        this.userEmail = users.getUserEmail();
        this.contactNo = users.getContactNo();
    }
}
