package com.jhh.api.common.exception;

import com.jhh.api.common.response.BaseResponseErrorStatus;

public class NotFoundException extends BaseException {
    public NotFoundException(BaseResponseErrorStatus status) {
        super(status);
    }
}
