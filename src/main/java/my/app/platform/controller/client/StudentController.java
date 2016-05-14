package my.app.platform.controller.client;

import my.app.framework.web.Result;
import my.app.framework.web.ResultHelper;
import my.app.platform.domain.Student;
import my.app.platform.repository.mapper.student.IStudentInfoDao;
import my.app.platform.service.LoginService;
import my.app.platform.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 夏之阳
 * 创建时间：2016-03-27 15:48
 * 创建说明：学生接口
 */

@RestController
@RequestMapping(value = "/client")
public class StudentController {
    @Autowired
    LoginService loginService;

    @Autowired
    IStudentInfoDao studentInfoDao;

    @Autowired
    StudentService studentService;

    @RequestMapping(value = "/student/login", method = RequestMethod.POST)
    public Result studentLoginHandler(String usr, String pwd) {
        Student student = loginService.studentLoginCheck(usr, pwd);
        if(student != null) {
            return ResultHelper.newSuccessResult(student);
        } else {
            return ResultHelper.newFailureResult("Login Failed");
        }
    }

    @RequestMapping(value = "/student/score", method = RequestMethod.POST)
    public Result studentScoreHandler(String s_login_name) {
        String score = studentInfoDao.queryScore(s_login_name);
        return ResultHelper.newSuccessResult(score);
    }

    @RequestMapping(value = "/student/changePwd", method = RequestMethod.POST)
    public Result studentChangePwdHandler(String s_login_name,String pwd) {
        int result = studentInfoDao.updatePwd(s_login_name, pwd);
        return ResultHelper.newSuccessResult(result);
    }

    @RequestMapping(value = "/student/newExp", method = RequestMethod.POST)
    public Result studentNewExpHandler(String s_login_name,String e_id) {
        int result = studentService.updateTask(s_login_name, e_id);
        return ResultHelper.newSuccessResult(result);
    }
}
