package com.erzhiqianyi.questionnaire.web.vo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseResult<T> {
    private String msg;
    private T result;
    private int code;


    public static <T> ResponseResult<T> badRequest(String msg) {
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.setCode(ResponseStatusEnum.BAD_REQUEST.getCode());
        responseResult.setMsg(msg);
        return responseResult;
    }

    public static <T> ResponseResult<T> success(String msg) {
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.setCode(ResponseStatusEnum.SUCCESS.getCode());
        responseResult.setMsg(msg);
        return responseResult;
    }

    public static <T> ResponseResult<T> success(String msg, T data) {
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.setCode(ResponseStatusEnum.SUCCESS.getCode());
        responseResult.setMsg(msg);
        responseResult.setResult(data);
        return responseResult;
    }


    public static <T> ResponseResult<T> error(String msg) {
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.setCode(ResponseStatusEnum.ERROR.getCode());
        responseResult.setMsg(msg);
        return responseResult;
    }


    public static <T> ResponseResult<T> error(String msg, int code) {
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.setCode(code);
        responseResult.setMsg(msg);
        return responseResult;
    }


    public boolean isSuccess() {
        return code == ResponseStatusEnum.SUCCESS.getCode();
    }
}
