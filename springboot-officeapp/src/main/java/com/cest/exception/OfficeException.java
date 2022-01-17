package com.cest.exception;

import lombok.Data;

@Data
public class OfficeException extends RuntimeException{

    private String msg;
    private int code = 500;

    public OfficeException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public OfficeException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public OfficeException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public OfficeException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }
}
