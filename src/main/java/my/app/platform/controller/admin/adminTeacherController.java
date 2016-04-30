package my.app.platform.controller.admin;

import my.app.platform.domain.Teacher;
import my.app.platform.service.StudentService;
import my.app.platform.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author 夏之阳
 * 创建时间：2016-05-01 00:44
 * 创建说明：
 */

@Controller
public class adminTeacherController {
    @Autowired
    private HttpSession session;

    @Autowired
    private TeacherService teacherService;

    @RequestMapping(value = "/admin/teacher")
    public String teacher(Model model){
        String t_name = session.getAttribute("t_name").toString();
        model.addAttribute("t_name", t_name);

        List<Teacher> teacherList = teacherService.getTeacherList();
        model.addAttribute("teacher",teacherList);

        return "/admin/teacher";
    }

    @RequestMapping(value = "/admin/teacher/delete")
    @ResponseBody
    public String teacherDeleteHandler(String t_login_name){
        if(teacherService.deleteTeacher(t_login_name) != 0) {
            return "{\"error\":\"0\",\"msg\":\"删除成功\"}";
        }else{
            return "{\"error\":\"1\",\"msg\":\"删除失败\"}";
        }
    }

    @RequestMapping(value = "/admin/teacher/insert")
    @ResponseBody
    public String teacherInsertHandler(Teacher teacher){
        Teacher t = teacherService.getTeacher(teacher.getT_login_name());
        if(t != null){
            return "{\"error\":\"1\",\"msg\":\"已存在该教师\"}";
        }

        if(teacherService.insertTeacher(teacher) != 0) {
            return "{\"error\":\"0\",\"msg\":\"添加成功\"}";
        }else{
            return "{\"error\":\"1\",\"msg\":\"添加失败\"}";
        }
    }

    @RequestMapping(value = "/admin/teacher/update")
    @ResponseBody
    public String teacherUpdateHandler(Teacher teacher){
        if(teacherService.updateTeacher(teacher) != 0) {
            return "{\"error\":\"0\",\"msg\":\"添加成功\"}";
        }else{
            return "{\"error\":\"1\",\"msg\":\"添加失败\"}";
        }
    }
}
