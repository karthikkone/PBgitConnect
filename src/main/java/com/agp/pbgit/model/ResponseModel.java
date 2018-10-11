package com.agp.pbgit.model;


public class ResponseModel {
    private Integer httpStatus;
    private String message;
    private String authCode;

    public ResponseModel() {
    }

    public ResponseModel(Integer httpStatus, String message, String authCode) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.authCode = authCode;
    }

    public Integer getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(Integer httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    @Override
    public String toString() {
        String authtoken = (this.authCode == null ? "-" : this.authCode);
        return "ResponseModel{" +
                "httpStatus=" + httpStatus +
                ", message='" + message + '\'' +
                ", authCode='" + authtoken + '\'' +
                '}';
    }
}

