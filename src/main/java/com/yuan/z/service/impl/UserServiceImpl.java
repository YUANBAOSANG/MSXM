package com.yuan.z.service.impl;

import com.yuan.z.dao.UserDOMapper;
import com.yuan.z.dao.UserPasswordDOMapper;
import com.yuan.z.dataobject.UserDO;
import com.yuan.z.dataobject.UserPasswordDO;
import com.yuan.z.error.BusinessException;
import com.yuan.z.error.EmBusinessError;
import com.yuan.z.service.UserService;
import com.yuan.z.service.model.UserModel;
import com.yuan.z.validator.ValidationResult;
import com.yuan.z.validator.ValidatorImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @创建人 YDL
 * @创建时间 2020/6/7 21:19
 * @描述
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired(required = false)
    private UserDOMapper userDOMapper;
    @Autowired(required = false)
    private UserPasswordDOMapper userPasswordDOMapper;
    @Autowired(required = false)
    private ValidatorImpl validator;
    @Override
    public UserModel getUserById(Integer id) {
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);
        if(userDO==null){
            return null;
        }
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(id);
        return convertFromDataObject(userDO,userPasswordDO);
    }

    @Override
    @Transactional
    public void register(UserModel userModel) throws BusinessException {
        if(userModel==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
//        if(StringUtils.isEmpty(userModel.getName())||
//                userModel.getAge()==null||
//                userModel.getGender()==null||StringUtils.isEmpty(userModel.getTelphone())){
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
//        }
        ValidationResult result = validator.validate(userModel);
        if(result.isHasErrors()){
            throw  new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }
        //model->dataobj
        UserDO userDO = convertFromModel(userModel);
        try {
            userDOMapper.insertSelective(userDO);
        }catch (DuplicateKeyException ex){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"该手机号已注册过");
        }
        userModel.setId(userDO.getId());
        UserPasswordDO userPasswordDO = convertPassWordFromModel(userModel);
        userPasswordDOMapper.insertSelective(userPasswordDO);

    }

    @Override
    public UserModel validateLogin(String telphone, String encrptPassword) throws BusinessException {
        //通过用户的手机获取用户信息
        UserDO userDO = userDOMapper.selectByTelphone(telphone);
        if(userDO==null){
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());
        UserModel userModel = convertFromDataObject(userDO,userPasswordDO);
        //比对密码是否一致
        if(!StringUtils.equals(encrptPassword,userModel.getEncrptPassword())){
            throw  new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        return userModel;
    }

    private UserPasswordDO convertPassWordFromModel(UserModel userModel){
        if(userModel==null){
            return null;
        }
        UserPasswordDO userPasswordDO = new UserPasswordDO();
        userPasswordDO.setEncrptPassword(userModel.getEncrptPassword());
        userPasswordDO.setUserId(userModel.getId());
        return userPasswordDO;
    }
    private UserDO convertFromModel(UserModel userModel){
        if(userModel==null){
            return null;
        }
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userModel,userDO);
        userDO.setGender(Integer.valueOf(userModel.getGender()));
        return userDO;
    }


    private UserModel convertFromDataObject(UserDO userDO, UserPasswordDO userPasswordDO){
        if(userDO==null){
            throw new NullPointerException("UserDao为空");
        }
        UserModel userModel =  new UserModel();
        BeanUtils.copyProperties(userDO,userModel);
        userModel.setGender(userDO.getGender().byteValue());
        //userModel.setGender((userDO.getGender().byteValue()));
        if(userPasswordDO!=null){
            userModel.setEncrptPassword(userPasswordDO.getEncrptPassword());
        }

        return userModel;
    }
}
