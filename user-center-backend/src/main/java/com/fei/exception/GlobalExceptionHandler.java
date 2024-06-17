package com.fei.exception;

import com.fei.common.BaseResponse;
import com.fei.common.ErrorCode;
import com.fei.common.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author ziyu
 */

//@RestControllerAdvice 定义全局异常处理器类，拦截应用程序中所有控制器抛出的异常。
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    //如果抛出BusinessException异常，就调用这个方法处理
    @ExceptionHandler(BusinessException.class)
    public BaseResponse businessExceptionHandler(BusinessException e) {
        log.error("businessException" + e.getMessage(),e);
        return ResultUtil.error(e.getCode(),e.getMessage(),e.getDescription());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse runtimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException", e);
        return ResultUtil.error(ErrorCode.SYSTEM_ERROR,e.getMessage(),"");
    }

}
