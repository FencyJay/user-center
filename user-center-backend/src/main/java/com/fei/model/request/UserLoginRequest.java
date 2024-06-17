package com.fei.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginRequest implements Serializable {
    //做序列化用的ID
    private static final long serialVersionUID = 7896903996916926758L;

    private String userAccount;
    private String userPassword;
}
