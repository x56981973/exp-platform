package my.app.platform.controller.home;

import my.app.platform.domain.ExpClass;
import my.app.platform.domain.LoginRecord;
import my.app.platform.domain.Teacher;
import my.app.platform.domain.model.ActiveExperiment;
import my.app.platform.domain.model.MExpType;
import my.app.platform.domain.model.MExperiment;
import my.app.platform.repository.mapper.experiment.IExpInfoDao;
import my.app.platform.repository.mapper.log.ILogInfoDao;
import my.app.platform.service.LoginService;
import my.app.platform.service.StudentService;
import my.app.platform.service.TeacherService;
import my.app.platform.tool.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private ILogInfoDao logInfoDao;

    @Autowired
    private LoginService loginService;

    @Autowired
    private IExpInfoDao expInfoDao;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/")
    public String home(Model model){
        String t_id = session.getAttribute("t_id").toString();
        String t_name = session.getAttribute("t_name").toString();
        model.addAttribute("t_name", t_name);

        //Get Active Exp info
        List<ActiveExperiment> activeExperiments = teacherService.getActiveExpList(t_id);
        model.addAttribute("active_exp",activeExperiments);

        //Get Exp info
        List<MExperiment> experiments = expInfoDao.queryAllExp();
        model.addAttribute("exp",experiments);

        int s_num = studentService.getStudentListByTeacher(t_id).size();
        model.addAttribute("s_num",s_num);

        int e_num = experiments.size();
        model.addAttribute("e_num", e_num);

        return "/user/home";
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    @ResponseBody
    public String teacherLoginHandler(HttpServletRequest request,String username,String password) {
        if(!username.matches("^([A-Za-z]|[0-9]){0,}$")){
            return "{\"error\":\"1\",\"msg\":\"用户名仅能使用数字字母组合\",\"to\":\"/login\"}";
        }
        if(!password.matches("^([A-Za-z]|[0-9]){0,}$")){
            return "{\"error\":\"1\",\"msg\":\"密码仅能使用数字字母组合\",\"to\":\"/login\"}";
        }

        Teacher teacher = loginService.teacherLoginCheck(username, password);
        if(teacher != null) {
            session.setAttribute("t_id",teacher.getT_login_name());
            session.setAttribute("t_name",teacher.getT_name());

            //插入登陆记录
            LoginRecord loginRecord = new LoginRecord();
            loginRecord.setUid(username);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String date = df.format(new Date());
            loginRecord.setDate(date);
            loginRecord.setIp_address(IpUtil.getIpAddr(request));
            logInfoDao.insertLoginRecord(loginRecord);

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
        List<MExperiment> experimentList = expInfoDao.queryAllExp();
        model.addAttribute("exp",experimentList);

        List<MExpType> expTypeList = expInfoDao.queryAllExpType();
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
