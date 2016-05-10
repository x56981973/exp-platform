package my.app.platform.controller.home;

import my.app.platform.domain.ExpClass;
import my.app.platform.domain.Teacher;
import my.app.platform.domain.model.ActiveExperiment;
import my.app.platform.domain.model.MExpType;
import my.app.platform.domain.model.MExperiment;
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
 * 创建时间：2016-03-31 21:25
 * 创建说明：用户接口
 */

@Controller
@RequestMapping(value = "/user")
public class homeController {
    @Autowired
    private HttpSession session;

    @Autowired
    private IExpInfoDao expInfoDao;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/home")
    public String home(Model model){
        String t_id = session.getAttribute("t_id").toString();
        String t_name = session.getAttribute("t_name").toString();
        model.addAttribute("t_name", t_name);

        //Get Active Exp info
        List<ActiveExperiment> activeExperiments = teacherService.getActiveExpList(t_id);
        model.addAttribute("active_exp",activeExperiments);

        //Get Exp info
        List<MExperiment> experiments = expInfoDao.queryAllMExp();
        model.addAttribute("exp",experiments);

        int s_num = studentService.getStudentListByTeacher(t_id).size();
        model.addAttribute("s_num",s_num);

        int e_num = experiments.size();
        model.addAttribute("e_num", e_num);

        return "/user/home";
    }

    @RequestMapping(value = "/exp")
    public String experiment(Model model){
        //Get t_name
        String t_name = session.getAttribute("t_name").toString();
        model.addAttribute("t_name",t_name);

        //Get Exp info
        List<MExperiment> experimentList = expInfoDao.queryAllMExp();
        model.addAttribute("exp",experimentList);

        List<MExpType> expTypeList = expInfoDao.queryAllMExpType();
        model.addAttribute("exp_type",expTypeList);

        List<ExpClass> expClassList = expInfoDao.queryAllExpClass();
        model.addAttribute("exp_class",expClassList);

        return "/user/experiment";
    }

    @RequestMapping(value = "/settings")
    public String setting(Model model){
        String t_id = session.getAttribute("t_id").toString();
        Teacher teacher = teacherService.getTeacher(t_id);
        model.addAttribute("teacher", teacher);

        return "/user/settings";
    }
}
