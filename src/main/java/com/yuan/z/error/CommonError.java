package com.yuan.z.error;

/**
 * @创建人 YDL
 * @创建时间 2020/6/7 22:47
 * @描述
 */
public interface CommonError {
    int getErrCode();
    String getErrMsg();
    CommonError setErrMsg(String errMsg);
}
