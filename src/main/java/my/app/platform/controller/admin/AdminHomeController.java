package my.app.platform.controller.admin;

import my.app.platform.domain.LoginRecord;
import my.app.platform.domain.OptionRecord;
import my.app.platform.repository.mapper.experiment.IExpInfoDao;
import my.app.platform.repository.mapper.log.ILogInfoDao;
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
@RequestMapping(value = "/admin")
public class AdminHomeController {
    @Autowired
    private HttpSession session;

    @Autowired
    private ILogInfoDao logInfoDao;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private IExpInfoDao expInfoDao;

    //首页
    @RequestMapping(value = "/home")
    public String admin(Model model){
        String t_id = session.getAttribute("t_id").toString();
        String t_name = session.getAttribute("t_name").toString();
        model.addAttribute("t_name", t_name);

        int t_num = teacherService.getTeacherList().size();
        model.addAttribute("t_num",t_num);

        int s_num = studentService.getStudentList().size();
        model.addAttribute("s_num",s_num);

        int e_num = expInfoDao.queryAllMExp().size();
        model.addAttribute("e_num", e_num);

        List<LoginRecord> loginRecordList = logInfoDao.queryLoginRecord(t_id);
        model.addAttribute("login_record",loginRecordList);

        List<OptionRecord> optionRecordList = logInfoDao.queryOptionRecord(t_id);
        model.addAttribute("option_record",optionRecordList);

        return "/admin/home";
    }

}
