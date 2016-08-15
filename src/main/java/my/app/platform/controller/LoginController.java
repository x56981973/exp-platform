package my.app.platform.controller;

import my.app.platform.domain.LoginRecord;
import my.app.platform.domain.Teacher;
import my.app.platform.repository.mapper.log.ILogInfoDao;
import my.app.platform.service.LoginService;
import my.app.platform.tool.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 夏之阳
 * 创建时间：2016-05-10 21:52
 * 创建说明：登陆接口
 */

@Controller
public class LoginController {
    @Autowired
    private HttpSession session;

    @Autowired
    private ILogInfoDao logInfoDao;

    @Autowired
    private LoginService loginService;

    /**
     * 默认接口
     * @return 跳转到主页面
     */
    @RequestMapping(value = "/")
    public String home(){
        if("teacher".equals(session.getAttribute("role").toString())){
            return "redirect:/teacher/home";
        } else {
            return "redirect:/admin/home";
        }
    }

    /**
     * 登陆接口
     * @return 跳转到登陆页面
     */
    @RequestMapping(value = "/login")
    public String login(){
        return "/login";
    }

    /**
     * 登出接口
     * @return 跳转到登录页面
     */
    @RequestMapping(value = "/logout")
    public String logout(){
        session.removeAttribute("t_id");
        session.removeAttribute("t_name");
        return "redirect:/login";
    }

    /**
     * 登陆验证接口
     * @param request http请求
     * @param username 用户名
     * @param password 密码
     * @return 登陆结果
     */
    @RequestMapping(value = "/loginCheck", method = RequestMethod.POST)
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
            session.setMaxInactiveInterval(900);
            session.setAttribute("t_id", teacher.getT_login_name());
            session.setAttribute("t_name", teacher.getT_name());
            session.setAttribute("role", teacher.getRole());

            if("teacher".equals(teacher.getRole())) {
                return "{\"error\":\"0\",\"msg\":\"登陆成功\",\"to\":\"/teacher/home\"}";
            } else {
                //插入登陆记录
                LoginRecord loginRecord = new LoginRecord();
                loginRecord.setUid(username);
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                String date = df.format(new Date());
                loginRecord.setDate(date);
                loginRecord.setIp_address(IpUtil.getIpAddr(request));
                logInfoDao.insertLoginRecord(loginRecord);

                return "{\"error\":\"0\",\"msg\":\"登陆成功\",\"to\":\"/admin/home\"}";
            }
        } else{
            return "{\"error\":\"1\",\"msg\":\"用户名或密码错误\",\"to\":\"/login\"}";
        }
    }
}
