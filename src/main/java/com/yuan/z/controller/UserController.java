package com.yuan.z.controller;
import com.alibaba.druid.util.StringUtils;
import com.yuan.z.controller.viewobject.UserVO;
import com.yuan.z.dao.UserDOMapper;
import com.yuan.z.error.BusinessException;
import com.yuan.z.error.EmBusinessError;
import com.yuan.z.response.CommonReturnType;
import com.yuan.z.service.UserService;
import com.yuan.z.service.model.UserModel;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;


/**
 * @创建人 YDL
 * @创建时间 2020/6/7 21:15
 * @描述
 */
@Controller("user")
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class UserController extends BaseController{
    @Autowired(required = false)
    private UserService userService;
    @Autowired
    private HttpServletRequest httpServletRequest;
    @RequestMapping(value = "/register",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType register(@RequestParam(name="telphone") String telphone,
                                     @RequestParam(name="otpCode")String otpCode,
                                     @RequestParam(name="name")String name,
                                     @RequestParam(name="gender")Integer gender,
                                     @RequestParam(name="age")Integer age,
                                     @RequestParam(name="password")String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        //验证手机号和对应的otpcode
        String inSessionOtpCode = (String) this.httpServletRequest.getSession().getAttribute(telphone);
        if(!StringUtils.equals(otpCode,inSessionOtpCode)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"短信验证码不符合");
        }
        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setGender(gender.byteValue());
        userModel.setAge(age);
        userModel.setTelphone(telphone);
        userModel.setRegisterMode("byphone");
        userModel.setEncrptPassword(this.EncodeByMd5(password));

        userService.register(userModel);
        return CommonReturnType.create(null);
    }

    public String EncodeByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        Base64.Encoder encoder = Base64.getEncoder();

        String newstr = encoder.encodeToString(md5.digest(str.getBytes("utf-8")));
        return newstr;
    }
    /**
     * 功能描述:
     * 调用service服务获取对应的id的用户对象并返回给前端
     *
     * @param id 1
     * @return : void
     * @author : YDL
     * @date : 2020/6/7 21:17
     **/
    @RequestMapping("/get")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam(name = "id")Integer id) throws BusinessException {
       UserModel userModel = userService.getUserById(id);
       if(userModel==null){
           throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
       }
       UserVO userVO = convertFromModel(userModel);
       return CommonReturnType.create(userVO);
    }

    private UserVO convertFromModel(UserModel userModel){
        if(userModel==null){
            throw new NullPointerException("UserModel为空");
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel,userVO);
        return userVO;
    }
    @RequestMapping(value = "/getotp",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType getOyp(@RequestParam(name="telphone") String telphone){
        //需要按照一定的规则生成OTP验证码
        Random random = new Random();
        int randomInt = random.nextInt(99999);
        randomInt += 10000;
        String otpCode = String.valueOf(randomInt);

        //将OTP验证码同对应用户的手机号关联,使用httpssion
        httpServletRequest.getSession().setAttribute(telphone,otpCode);
        //将OTP验证码通过短信通道发送给用户
        System.out.println("telphone = "+telphone+"&otpCode="+otpCode);
        return CommonReturnType.create(null);
    }
    @RequestMapping(value = "/login",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType login(@RequestParam("telphone") String telphone, @RequestParam(name="password")String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        //入参校验

        if(StringUtils.isEmpty(telphone)||StringUtils.isEmpty(password)){
            throw  new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        UserModel userModel = userService.validateLogin(telphone,this.EncodeByMd5(password));
        //登陆凭证加入到用户登录成功的Session内
        this.httpServletRequest.getSession().setAttribute("IS_LOGIN",true);
        this.httpServletRequest.getSession().setAttribute("Login_User",userModel);

        return CommonReturnType.create(null);
    }
}
