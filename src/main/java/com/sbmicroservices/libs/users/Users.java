package com.sbmicroservices.libs.users;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sbmicroservices.libs.BaseEntity;
import jakarta.persistence.*;


@Entity
@Table(name = "cart_users")
public class Users extends BaseEntity {

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "email", nullable = false)
    private String userEmail;

    @Column(name = "contact_no", length = 12)
    private String contactNo;

    @JsonIgnore
    private String password;



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
