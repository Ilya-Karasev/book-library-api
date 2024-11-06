package com.example.musiclibrary.dtos;
public class ActionDto {
    private String href;
    private String method;
    private String accept;
    public ActionDto(String href, String method, String accept) {
        this.href = href;
        this.method = method;
        this.accept = accept;
    }
    public ActionDto(String href, String method) {
        this.href = href;
        this.method = method;
    }
    public String getHref() {
        return href;
    }
    public void setHref(String href) {
        this.href = href;
    }
    public String getMethod() {
        return method;
    }
    public void setMethod(String method) {
        this.method = method;
    }
    public String getAccept() {
        return accept;
    }
    public void setAccept(String accept) {
        this.accept = accept;
    }
}