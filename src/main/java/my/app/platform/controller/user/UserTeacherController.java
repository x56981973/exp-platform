package my.app.platform.controller.user;

import my.app.platform.domain.Teacher;
import my.app.platform.repository.mapper.message.IMessageInfoDao;
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
 * 创建说明：用户教师信息接口
 */

@Controller
@RequestMapping(value = "/user")
public class UserTeacherController {
    @Autowired
    HttpSession httpSession;

    @Autowired
    TeacherService teacherService;

    @Autowired
    IMessageInfoDao messageInfoDao;


    /**
     * 更新教师信息接口
     */
    @RequestMapping(value = "/teacher/update", method = RequestMethod.POST)
    @ResponseBody
    public String updateTeacher(String t_login_name, String password){
        int result = teacherService.updateTeacherPwd(t_login_name, password);
        if (result != 0) {
            return "{\"error\":\"0\",\"msg\":\"更新成功\"}";
        } else {
            return "{\"error\":\"1\",\"msg\":\"更新失败\"}";
        }
    }

    /**
     * 修改正在进行中实验的状态
     * @param e_id 实验id
     * @param status 状态
     * @return 处理结果
     */
    @RequestMapping(value = "/teacher/changeExpStatus", method = RequestMethod.POST)
    @ResponseBody
    public String changeExpStatus(String e_id,String status){
        String t_id = httpSession.getAttribute("t_id").toString();

        Teacher teacher = teacherService.getTeacher(t_id);
        String exp_status = teacher.getActive_exp();
        StringBuilder new_status = new StringBuilder(exp_status);
        int index = exp_status.indexOf(e_id) + e_id.length();
        new_status.replace(index, index + 1, status);

        if (teacherService.updateActiveExpList(t_id, new_status.toString()) != 0) {
            return "{\"error\":\"0\",\"msg\":\"修改成功\"}";
        } else {
            return "{\"error\":\"1\",\"msg\":\"修改失败\"}";
        }
    }

    /**
     * 修改正在进行中的实验
     * @param list 新实验列表
     * @return 处理结果
     */
    @RequestMapping(value = "/teacher/changeActiveExp", method = RequestMethod.POST)
    @ResponseBody
    public String changeActiveExp(String list){
        String t_id = httpSession.getAttribute("t_id").toString();
        Teacher teacher = teacherService.getTeacher(t_id);
        String exp_status = teacher.getActive_exp();
        String[] new_exp = list.split(",");

        String new_exp_status = "";
        for(String e : new_exp){
            if(exp_status.contains(e)){
                int index = exp_status.indexOf(e);
                new_exp_status += e + exp_status.charAt(index+e.length()) + ",";
            } else {
                new_exp_status += e + "-" + ",";
            }
        }
        String active_exp = new_exp_status.substring(0, new_exp_status.length() - 1);

        if (teacherService.updateActiveExpList(t_id,active_exp) != 0) {
            return "{\"error\":\"0\",\"msg\":\"修改成功\"}";
        } else {
            return "{\"error\":\"1\",\"msg\":\"修改失败\"}";
        }
    }

    /**
     * 更新教师阅读消息
     * @param id 消息id
     * @return 处理结果
     */
    @RequestMapping(value = "/teacher/readMessage", method = RequestMethod.POST)
    @ResponseBody
    public String readMessage(String id){
        if (messageInfoDao.updateIsRead(id) != 0) {
            return "{\"error\":\"0\",\"msg\":\"修改成功\"}";
        } else {
            return "{\"error\":\"1\",\"msg\":\"修改失败\"}";
        }
    }
}
