package my.app.platform.controller;

import my.app.framework.web.Result;
import my.app.framework.web.ResultHelper;
import my.app.platform.repository.mapper.student.IStudentInfoDao;
import my.app.platform.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author 夏之阳
 * 创建时间：2016-03-20 00:18
 * 创建说明：学生信息接口
 */

@RestController
public class StudentController {
    @Autowired
    HttpSession httpSession;

    @Autowired
    IStudentInfoDao studentInfoDao;

    @Autowired
    StudentService studentService;

    /**
     * 删除单个学生
     * @param s_login_name 学生登录名
     * @return 是否成功
     */
    @RequestMapping(value = "/student/delete", method = RequestMethod.GET)
    public Result deleteStudentHandler(@RequestParam String s_login_name) {
        return ResultHelper.newSuccessResult(studentInfoDao.deleteStudentInfo(s_login_name));
    }

    @RequestMapping(value = "/student/score", method = RequestMethod.GET)
    public Result studentScoreHandler(@RequestParam String studentName) {
        return ResultHelper.newSuccessResult(studentInfoDao.queryScore(studentName));
    }
}
