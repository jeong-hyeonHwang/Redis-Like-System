package com.jhh.api.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@Getter
@JsonPropertyOrder({"isSuccess", "code", "message", "data"})
public class BaseResponse<T> {

    private final Boolean isSuccess;
    private final String code;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    // 요청 성공 Default
    private BaseResponse(T data) {
        this.isSuccess = BaseResponseSuccessStatus.SUCCESS.isSuccess();
        this.message = BaseResponseSuccessStatus.SUCCESS.getMessage();
        this.code = BaseResponseSuccessStatus.SUCCESS.getCode();
        this.data = data;
    }

    // 요청 성공 Custom
    private BaseResponse(BaseResponseSuccessStatus status, T data) {
        this.isSuccess = status.isSuccess();
        this.code = status.getCode();
        this.message = status.getCode();
        this.data = data;
    }

    // 요청 실패 Custom
    private BaseResponse(BaseResponseErrorStatus status, T data) {
        this.isSuccess = status.isSuccess();
        this.code = status.getCode();
        this.message = status.getCode();
        this.data = data;
    }

    // 요청 성공 Default - data X
    public static <T> BaseResponse<T> ok() {
        return new BaseResponse<>(null);
    }

    // 요청 성공 Default - data O
    public static <T> BaseResponse<T> ok(T data) {
        return new BaseResponse<>(data);
    }

    // 요청 성공 Custom - data X
    public static <T> BaseResponse<T> status(BaseResponseSuccessStatus status) {
        return new BaseResponse<>(status, null);
    }

    // 요청 실패 Custom - data X
    public static <T> BaseResponse<T> status(BaseResponseErrorStatus status) {
        return new BaseResponse<>(status, null);
    }
}
