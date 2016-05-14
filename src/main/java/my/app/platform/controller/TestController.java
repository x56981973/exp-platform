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
        int count = 0;
        List<Student> studentList = studentService.getStudentListByTeacher("yiping");
        for(Student student : studentList){
            studentService.updateTask(student.getS_login_name(),"3");
            count ++;
        }

        return ResultHelper.newSuccessResult(count);
    }

    @RequestMapping(value = "/client/interface/test", method = RequestMethod.POST)
    public Result interfaceTestHandler(String hello) {
        return ResultHelper.newSuccessResult(hello);
    }
}
