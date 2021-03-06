package my.app.platform.controller.student;

import my.app.platform.domain.*;
import my.app.platform.domain.model.ActiveExperiment;
import my.app.platform.domain.model.MExperiment;
import my.app.platform.repository.mapper.experiment.IExpInfoDao;
import my.app.platform.service.StudentService;
import my.app.platform.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Value("${cloud.url}")
    String cloudUrl;

    //主页
    @RequestMapping(value = "/home")
    public String home(Model model){
        String s_login_name = httpSession.getAttribute("uid").toString();
        Student student = studentService.getStudent(s_login_name);
        model.addAttribute("student",student);

        String t_login_name = student.getTeacher();
        Teacher teacher = teacherService.getTeacher(t_login_name);
        model.addAttribute("teacher", teacher);

        List<ActiveExperiment> activeExperimentList = teacherService.getActiveExpList(t_login_name);
        model.addAttribute("activeExp", activeExperimentList);
        model.addAttribute("cloud_url",cloudUrl);

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

        String s_login_name = httpSession.getAttribute("uid").toString();
        Student student = studentService.getStudent(s_login_name);
        model.addAttribute("student",student);

        return "/student/experiment";
    }

    //实验详情页面
    @RequestMapping(value = "/exp/{e_id}")
    public String experimentDetail(@PathVariable String e_id, Model model){
        List<Experiment> experiments = expInfoDao.queryExperiment(e_id);
        Experiment experiment = experiments.get(0);
        model.addAttribute("experiment", experiment);

        return "/student/experiment_detail";
    }

    //设置页面
    @RequestMapping(value = "/settings")
    public String setting(Model model){
        String s_login_name = httpSession.getAttribute("uid").toString();
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
