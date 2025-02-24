package com.jhh.api.common.exception;

import com.jhh.api.common.response.BaseResponseErrorStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseException extends RuntimeException {
    private BaseResponseErrorStatus status;

    public BaseException(BaseResponseErrorStatus status) {
        super(status.getMessage());
        this.status = status;
    }
}
