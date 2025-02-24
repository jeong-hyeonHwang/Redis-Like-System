package com.jhh.api.common.exception;

import com.jhh.api.common.response.BaseResponseErrorStatus;

public class BadRequestException extends BaseException {
    public BadRequestException(BaseResponseErrorStatus status) {
        super(status);
    }
}
