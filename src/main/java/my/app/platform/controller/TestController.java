package my.app.platform.controller;

import my.app.framework.web.Result;
import my.app.framework.web.ResultHelper;
import my.app.platform.domain.Student;
import my.app.platform.domain.Teacher;
import my.app.platform.repository.mapper.experiment.IExpInfoDao;
import my.app.platform.service.StudentService;
import my.app.platform.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    IExpInfoDao expInfoDao;

    @Autowired
    TeacherService teacherService;

    @Autowired
    StudentService studentService;

    @RequestMapping(value = "/client/test", method = RequestMethod.POST)
     public Result testHandler() {
        Student student = studentService.getStudent("1");
        Teacher teacher = teacherService.getTeacher(student.getTeacher());
        String activeExp = teacher.getActive_exp();
        String e_id = "6";
        if(!activeExp.contains(e_id)){
            return ResultHelper.newFailureResult("");
        }
        int index = activeExp.lastIndexOf(e_id);
        if(activeExp.charAt(index+e_id.length())=='-'){
            return ResultHelper.newFailureResult("");
        }
        return ResultHelper.newSuccessResult("");
    }

    @RequestMapping(value = "/client/interface/test", method = RequestMethod.POST)
    public Result interfaceTestHandler(String hello) {
        return ResultHelper.newSuccessResult(hello);
    }
}
