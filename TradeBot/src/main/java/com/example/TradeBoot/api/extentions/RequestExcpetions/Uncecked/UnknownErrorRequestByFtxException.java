package com.example.TradeBoot.api.extentions.RequestExcpetions.Uncecked;

public class UnknownErrorRequestByFtxException extends BadRequestByFtxException{

    public UnknownErrorRequestByFtxException(String s, Exception e) {
        super(s, e);
    }

    public UnknownErrorRequestByFtxException(String s) {
        super(s);
    }
}
