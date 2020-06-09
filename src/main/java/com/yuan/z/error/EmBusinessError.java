package com.yuan.z.error;

/**
 * @创建人 YDL
 * @创建时间 2020/6/7 22:49
 * @描述
 */
public enum EmBusinessError implements CommonError {
    //通用错误类型
    PARAMETER_VALIDATION_ERROR(100001,"参数不合法"),
    UNKNOWN_ERROR(100002,"未知错误"),
    USER_NOT_EXIST(200001,"用户信息不存在"),
    USER_LOGIN_FAIL(200002,"用户手机号或密码不正确")
    ;
    private int errCode;
    private String errMsg;

    EmBusinessError(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }
}
