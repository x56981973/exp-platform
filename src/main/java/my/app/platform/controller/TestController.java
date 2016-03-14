package my.app.platform.controller;

import my.app.framework.web.Result;
import my.app.framework.web.ResultHelper;
import my.app.platform.domain.Teacher;
import my.app.platform.repository.mapper.teacher.ITeacherInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 夏之阳
 * 创建时间：2016-03-14 20:46
 * 创建说明：接口控制测试
 */

@RestController
public class TestController {
    @Autowired
    ITeacherInfoDao teacherInfoDao;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public Result testHandler(@RequestParam String request){
//        Teacher teacher = new Teacher();
//        teacher.setT_login_name("yiping");
//        teacher.setT_name("yiping");
//        teacher.setT_password("123");
//        teacher.setT_school("SJTU");
//        int result = teacherInfoDao.updateTeacherInfo(teacher);
        List<Teacher> teacherList = teacherInfoDao.queryTeacherInfo("yiping");
        return ResultHelper.newSuccessResult(teacherList);
    }
}
