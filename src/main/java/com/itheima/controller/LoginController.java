package com.itheima.controller;


import com.itheima.pojo.Emp;
import com.itheima.pojo.LoginInfo;
import com.itheima.pojo.Result;
import com.itheima.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录请求
 */
@Slf4j
@RestController
public class LoginController {
    @Autowired
    private EmpService empService;

    /**
     * 登录
     * @return
     */
//    @PostMapping("/login")
//    public Result login(@RequestBody Emp emp){
//        log.info("登录：{}",emp);
//        LoginInfo info =empService.login(emp);
//        if(info != null){
//            return Result.success(info);
//        }
//        return Result.error("用户名或密码错误");
//    }
    @PostMapping("/login")
    public Result login(@RequestBody Emp emp){
        log.info("登录：{}",emp);
        LoginInfo info =empService.login(emp);
        Result result;
        if(info != null){
            result = Result.success(info);
        } else {
            result = Result.error("用户名或密码错误");
        }
        // 添加返回值日志打印
        log.info("登录返回结果：{}", result);
        return result;
    }

}
