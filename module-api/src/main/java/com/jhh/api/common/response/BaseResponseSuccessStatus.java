package com.jhh.api.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BaseResponseSuccessStatus implements BaseResponseStatus {
    SUCCESS(HttpStatus.OK.toString(), "요청에 성공하였습니다.");

    private final boolean isSuccess = true;
    private final String code;
    private final String message;
}
