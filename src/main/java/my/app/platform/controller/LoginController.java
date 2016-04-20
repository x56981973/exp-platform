package my.app.platform.controller;

import my.app.framework.web.Result;
import my.app.framework.web.ResultHelper;
import my.app.platform.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 夏之阳
 * 创建时间：2016-03-27 15:48
 * 创建说明：登陆接口
 */

@RestController
public class LoginController {
    @Autowired
    LoginService loginService;

    @RequestMapping(value = "/student/login", method = RequestMethod.POST)
    public Result studentLoginHandler(@RequestParam String userName,String password) {
        String result = loginService.studentLoginCheck(userName, password);
        if("".equals(result)){
            return ResultHelper.newSuccessResult("Login Failed");
        } else {
            return ResultHelper.newSuccessResult(result);
        }
    }
//
//    @RequestMapping(value = "/teacher/login", method = RequestMethod.POST)
//    public Result teacherLoginHandler(@RequestParam String username,String password) {
//        return ResultHelper.newSuccessResult(loginService.teacherLoginCheck(username,password));
//    }
}
