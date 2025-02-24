package com.jhh.api.common.exception;

import com.jhh.api.common.response.BaseResponseErrorStatus;

public class InternalServerException extends BaseException {
    public InternalServerException(BaseResponseErrorStatus status) {
        super(status);
    }
}
