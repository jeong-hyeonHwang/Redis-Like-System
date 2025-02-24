package com.jhh.api.common.exception;

import com.jhh.api.common.response.BaseResponseErrorStatus;

public class ForbiddenException extends BaseException {
    public ForbiddenException(BaseResponseErrorStatus status) {
        super(status);
    }
}
