package com.ciyun.renshe.controller.exception;

import com.ciyun.renshe.common.Result;
import com.ciyun.renshe.common.StatusCode;
import com.ciyun.renshe.common.validation.ValidationErrorResponse;
import com.ciyun.renshe.common.validation.Violation;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * 全局异常处理类
 *
 * @Date 2019/8/20 8:48
 * @Author Admin
 * @Version 1.0
 */
@RestControllerAdvice
public class BaseExceptionHandle {

    /**
     * 唯一索引异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public Result uniqueError(DuplicateKeyException e) {
        e.printStackTrace();
        return new Result(false, StatusCode.ERROR, "存在重复数据，添加失败");
    }

    /**
     * 拦截运行时异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public Result baseError(RuntimeException e) {
        e.printStackTrace();
        return new Result(false, StatusCode.ERROR, e.getMessage());
    }

    /**
     * 拦截参数异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public Result illegalArgumentException(IllegalArgumentException e) {
        e.printStackTrace();
        return new Result(false, StatusCode.ERROR, e.getMessage());
    }

    /**
     * 拦截参数异常时返回信息
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    //@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    Result onConstraintValidationException(
            ConstraintViolationException e) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        for (ConstraintViolation violation : e.getConstraintViolations()) {
            error.getViolations().add(
                    new Violation(violation.getPropertyPath().toString(), violation.getMessage()));
        }
        return new Result(false, HttpStatus.BAD_REQUEST.value(), error.getViolations().get(0).getMessage(), error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    //@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    Result onMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            error.getViolations().add(
                    new Violation(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        List<String> errorList = new ArrayList<>(error.getViolations().size());
        error.getViolations().forEach(violation -> errorList.add(violation.getMessage()));
        return new Result(false, HttpStatus.BAD_REQUEST.value(), errorList.toString(), error);
    }
}
