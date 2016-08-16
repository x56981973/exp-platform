package my.app.platform.controller.student;

import my.app.platform.domain.ExpClass;
import my.app.platform.domain.ExpType;
import my.app.platform.domain.Experiment;
import my.app.platform.domain.Student;
import my.app.platform.domain.model.ActiveExperiment;
import my.app.platform.repository.mapper.experiment.IExpInfoDao;
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
 * 创建时间：2016-03-25 11:33
 * 创建说明：实验接口
 */

@Controller
@RequestMapping(value = "/student")
public class StudentHomeController {
    @Autowired
    HttpSession httpSession;

    @Autowired
    IExpInfoDao expInfoDao;

    @Autowired
    StudentService studentService;

    @Autowired
    TeacherService teacherService;

    //主页
    @RequestMapping(value = "/home")
    public String home(Model model){
//        String s_login_name = httpSession.getAttribute("s_id").toString();
//        Student student = studentService.getStudent(s_login_name);
//        model.addAttribute("student",student);
//
//        String t_login_name = student.getTeacher();
//        List<ActiveExperiment> activeExperimentList = teacherService.getActiveExpList(t_login_name);
//        model.addAttribute("activeExp", activeExperimentList);

        return "/student/home";
    }

    //实验页面
    @RequestMapping(value = "/exp")
    public String experiment(Model model){
        List<Experiment> experimentList = expInfoDao.queryAllExp();
        List<ExpClass> expClassList = expInfoDao.queryAllExpClass();
        List<ExpType> expTypeList = expInfoDao.queryAllExpType();
        model.addAttribute("experimentList",experimentList);
        model.addAttribute("expClassList",expClassList);
        model.addAttribute("expTypeList",expTypeList);

        String s_login_name = httpSession.getAttribute("s_id").toString();
        Student student = studentService.getStudent(s_login_name);
        model.addAttribute("student",student);

        return "/student/experiment";
    }

    //设置页面
    @RequestMapping(value = "/settings")
    public String setting(Model model){
        String s_login_name = httpSession.getAttribute("s_id").toString();
        Student student = studentService.getStudent(s_login_name);
        model.addAttribute("student",student);

        return "/student/settings";
    }

    //消息页面
    @RequestMapping(value = "/message")
    public String message(Model model){

        return "/student/message";
    }

}
