package my.app.platform.controller;

import my.app.framework.web.Result;
import my.app.framework.web.ResultHelper;
import my.app.platform.domain.Teacher;
import my.app.platform.repository.mapper.experiment.IExpInfoDao;
import my.app.platform.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value = "/client/test", method = RequestMethod.POST)
    public Result testHandler() {
        String e_id = "1";
        String status = "+";
        Teacher teacher = teacherService.getTeacher("yiping");
        String exp_status = teacher.getActive_exp();
        StringBuilder new_status = new StringBuilder(exp_status);
        int index = exp_status.indexOf(e_id) + e_id.length();
        new_status.replace(index,index+1,status);
        teacher.setActive_exp(new_status.toString());

        return ResultHelper.newSuccessResult(teacherService.updateTeacher(teacher));
    }
}
