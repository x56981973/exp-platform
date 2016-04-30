package my.app.platform.controller.admin;

import my.app.platform.domain.ExpClass;
import my.app.platform.domain.Student;
import my.app.platform.domain.Teacher;
import my.app.platform.domain.model.MExpType;
import my.app.platform.domain.model.MExperiment;
import my.app.platform.service.ExpService;
import my.app.platform.service.StudentService;
import my.app.platform.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author 夏之阳
 * 创建时间：2016-04-29 21:11
 * 创建说明：管理员界面
 */

@Controller
public class adminController {
    @Autowired
    private HttpSession session;

    @Autowired
    private ExpService expService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @RequestMapping(value = "/admin")
    public String admin(Model model){
        String t_name = session.getAttribute("t_name").toString();
        model.addAttribute("t_name",t_name);
        return "/admin/home";
    }

    @RequestMapping(value = "/admin/exp")
    public String experiment(Model model){
        //Get t_name
        String t_name = session.getAttribute("t_name").toString();
        model.addAttribute("t_name",t_name);

        //Get Exp info
        List<MExperiment> experimentList = expService.getExp();
        model.addAttribute("exp",experimentList);

        List<MExpType> expTypeList = expService.getExpType();
        model.addAttribute("exp_type",expTypeList);

        List<ExpClass> expClassList = expService.getExpClass();
        model.addAttribute("exp_class",expClassList);

        return "/admin/experiment";
    }

    @RequestMapping(value = "/admin/student")
    public String student(Model model){
        String t_name = session.getAttribute("t_name").toString();
        model.addAttribute("t_name",t_name);

        List<Student> studentList = studentService.getStudentList();
        model.addAttribute("student",studentList);

        return "/admin/student";
    }

    @RequestMapping(value = "/admin/teacher")
    public String teacher(Model model){
        String t_name = session.getAttribute("t_name").toString();
        model.addAttribute("t_name", t_name);

        List<Teacher> teacherList = teacherService.getTeacherList();
        model.addAttribute("teacher",teacherList);

        return "/admin/student";
    }
}
