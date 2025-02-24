package com.jhh.api.common.exception;

import com.jhh.api.common.response.BaseResponseErrorStatus;

public class UnauthorizedException extends BaseException {
    public UnauthorizedException(BaseResponseErrorStatus status) {
        super(status);
    }
}
