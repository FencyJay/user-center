package com.fei.common;

import lombok.Data;

import java.io.Serializable;


@Data
public class BaseResponse<T> implements Serializable {

    private int code;

    private T data;

    private String message;

    private String description;

    public BaseResponse(int code, T data, String message, String description) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = description;
    }


    /**
     * 失败
     * @param code
     * @param message
     * @return
     */
    public BaseResponse(int code, T data, String message) {
        this(code, data, message, "");
    }
    /**
     * 失败
     * @param code
     * @param message
     * @return
     */
    public BaseResponse(int code, String message,String description) {
        this(code, null,message, description);
    }
    /**
     * 失败
     * @param code
     * @param data
     */
    public BaseResponse(int code, T data) {
        this(code, data, "","");
    }

    /**
     * 失败
     * @param errorCode
     */
    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(),null,errorCode.getMessage(),errorCode.getDescription());
    }


}
