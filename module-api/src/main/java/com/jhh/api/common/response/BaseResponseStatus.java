package com.jhh.api.common.response;

public interface BaseResponseStatus {
    boolean isSuccess();
    String getCode();
    String getMessage();
}
