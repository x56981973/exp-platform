package my.app.platform.controller.home;

import my.app.platform.domain.ExpClass;
import my.app.platform.domain.Student;
import my.app.platform.domain.Teacher;
import my.app.platform.domain.model.MExpType;
import my.app.platform.domain.model.MExperiment;
import my.app.platform.service.ExpService;
import my.app.platform.service.LoginService;
import my.app.platform.service.StudentService;
import my.app.platform.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * @author 夏之阳
 * 创建时间：2016-03-31 21:25
 * 创建说明：
 */

@Controller
public class homeController {
    @Autowired
    private HttpSession session;

    @Autowired
    private LoginService loginService;

    @Autowired
    private ExpService expService;

    @Autowired
    private TeacherService teacherService;

    @RequestMapping(value = "/")
    public String home(Model model){
        String t_name = session.getAttribute("t_name").toString();
        model.addAttribute("t_name", t_name);
        return "/user/home";
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    @ResponseBody
    public String teacherLoginHandler(String username,String password) {
        Teacher teacher = loginService.teacherLoginCheck(username, password);
        if(teacher != null) {
            session.setAttribute("t_id",teacher.getT_login_name());
            session.setAttribute("t_name",teacher.getT_name());
            if("user".equals(teacher.getRole())) {
                return "{\"error\":\"0\",\"msg\":\"登陆成功\",\"to\":\"/\"}";
            } else {
                return "{\"error\":\"0\",\"msg\":\"登陆成功\",\"to\":\"/admin\"}";
            }
        } else{
            return "{\"error\":\"1\",\"msg\":\"用户名或密码错误\",\"to\":\"/login\"}";
        }
    }

    @RequestMapping(value = "/login")
    public String login(){
        return "/user/login";
    }

    @RequestMapping(value = "/logout")
    public String logout(){
        session.removeAttribute("t_id");
        session.removeAttribute("t_name");
        return "redirect:/login";
    }

    @RequestMapping(value = "/exp")
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

        return "/user/experiment";
    }

    @RequestMapping(value = "/setting")
    public String setting(Model model){
        String t_id = session.getAttribute("t_id").toString();
        Teacher teacher = teacherService.getTeacher(t_id);
        model.addAttribute("teacher", teacher);

        return "/user/setting";
    }
}
