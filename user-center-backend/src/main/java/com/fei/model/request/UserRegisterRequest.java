package com.fei.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegisterRequest implements Serializable {
    //做序列化用的ID
    public static final long serialVersionUID = 7896903996916926758L;

    private String userAccount;
    private String userPassword;
    private  String checkPassword;
    private  String planetCode;


}
