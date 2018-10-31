package com.agp.pbgit.model;


public class ResponseModel {
    private Integer httpStatus;
    private String message;
    private String authCode;
    private String login;

    public ResponseModel() {
    }

    public ResponseModel(Integer httpStatus, String message, String authCode, String login) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.authCode = authCode;
        this.login = login;
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

    public void setLogin(String login) { this.login = login; }
    public String getLogin() { return  this.login; }

    @Override
    public String toString() {
        String authtoken = (this.authCode == null ? "-" : this.authCode);
        return "ResponseModel{" +
                "httpStatus=" + httpStatus +
                ", message='" + message + '\'' +
                ", authCode='" + authtoken + '\'' +
                ", login='" + login + '\''+
                '}';
    }
}

