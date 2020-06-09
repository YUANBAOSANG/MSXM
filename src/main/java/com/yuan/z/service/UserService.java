package com.yuan.z.service;

import com.yuan.z.error.BusinessException;
import com.yuan.z.service.model.UserModel;

/**
 * @创建人 YDL
 * @创建时间 2020/6/7 21:18
 * @描述
 */
public interface UserService {
    UserModel getUserById(Integer id);
    void register(UserModel userModel) throws BusinessException;
    UserModel validateLogin(String telphone, String encrptpassword) throws BusinessException;
}
