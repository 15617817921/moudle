package com.liu.anew.bean.data;

public class GoodMsg {

    /**
     * msg : 商品ID不能为空
     * code : 400
     * success : false
     */

    private String msg;
    private String code;
    private boolean success;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
