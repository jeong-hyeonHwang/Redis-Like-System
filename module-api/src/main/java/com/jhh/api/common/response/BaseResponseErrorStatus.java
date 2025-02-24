package com.jhh.api.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BaseResponseErrorStatus implements BaseResponseStatus {

    /**
     * User Error
     */
    NOT_EXIST_USER("US" + HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),
    /**
     * Post Error
     */
    NOT_EXIST_POST("PO" + HttpStatus.NOT_FOUND, "존재하지 않는 포스트입니다."),


    /**
     * Comment Error
     */
    NOT_EXIST_COMMENT("CM" + HttpStatus.NOT_FOUND, "존재하지 않는 댓글입니다."),
    /**
     * Server Error
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "서버 에러");

    private final boolean isSuccess = false;
    private final String code;
    private final String message;
}
