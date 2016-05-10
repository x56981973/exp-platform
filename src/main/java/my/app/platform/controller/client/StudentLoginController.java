package my.app.platform.controller.client;

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
@RequestMapping(value = "/client")
public class StudentLoginController {
    @Autowired
    LoginService loginService;

    @RequestMapping(value = "/student/login", method = RequestMethod.POST)
    public String studentLoginHandler(@RequestParam String userName,String password) {
        String result = loginService.studentLoginCheck(userName, password);
        if("".equals(result)){
            return "Login Failed";
        } else {
            return result;
        }
    }
}
