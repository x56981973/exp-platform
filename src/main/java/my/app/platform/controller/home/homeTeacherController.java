package my.app.platform.controller.home;

import my.app.platform.domain.Teacher;
import my.app.platform.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author 夏之阳
 * 创建时间：2016-04-21 13:24
 * 创建说明：
 */

@Controller
public class homeTeacherController {
    @Autowired
    HttpSession httpSession;

    @Autowired
    TeacherService teacherService;


    /**
     * 更新教师信息接口
     */
    @RequestMapping(value = "/teacher/update", method = RequestMethod.POST)
    @ResponseBody
    public String updateTeacher(String t_login_name, String t_name,String school,String password){
        Teacher teacher = new Teacher();
        teacher.setT_login_name(t_login_name);
        teacher.setT_name(t_name);
        teacher.setT_school(school);
        teacher.setT_password(password);

        int result = teacherService.updateTeacher(teacher);
        if (result != 0) {
            return "{\"error\":\"0\",\"msg\":\"插入成功\"}";
        } else {
            return "{\"error\":\"1\",\"msg\":\"插入失败\"}";
        }
    }
}
