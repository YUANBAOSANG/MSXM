package com.yuan.z;

import com.yuan.z.dao.UserDOMapper;
import com.yuan.z.dataobject.UserDO;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 *
 */
//自动装载，会自动配置一个Tomcat，它会在资源目录扫描是否存在配置文件
// 如果存在则读里面的配置
//@EnableAutoConfiguration
@RestController
@SpringBootApplication(scanBasePackages = {"com.yuan.z"})
@MapperScan("com.yuan.z.dao")
public class App {
    @Autowired(required = false)
    private UserDOMapper userDOMapper;

    @RequestMapping("/")
    public String home(){
        UserDO userDO =  userDOMapper.selectByPrimaryKey(1);
        if(userDO==null){
            return "不存在";
        }else{
            return userDO.getName();
        }

    }
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        SpringApplication.run(App.class,args);
    }
}
