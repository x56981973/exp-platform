package my.app.platform.controller.admin;

import my.app.platform.domain.OptionRecord;
import my.app.platform.domain.Teacher;
import my.app.platform.repository.mapper.log.ILogInfoDao;
import my.app.platform.service.StudentService;
import my.app.platform.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author 夏之阳
 * 创建时间：2016-05-01 00:44
 * 创建说明：管理员教师管理
 */

@Controller
@RequestMapping(value = "/admin")
public class AdminTeacherController {
    @Autowired
    private HttpSession session;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    ILogInfoDao logInfoDao;

    @RequestMapping(value = "/teacher")
    public String teacher(Model model){
        String name = session.getAttribute("name").toString();
        model.addAttribute("name", name);

        List<Teacher> teacherList = teacherService.getTeacherList();
        model.addAttribute("teacher",teacherList);

        return "/admin/teacher";
    }

    @RequestMapping(value = "/teacher/delete")
    @ResponseBody
    public String teacherDeleteHandler(String t_login_name){
        if(teacherService.deleteTeacher(t_login_name) != 0) {
            String record = "删除教师：" + t_login_name;
            setOptionRecord(record);
            return "{\"error\":\"0\",\"msg\":\"删除成功\"}";
        }else{
            return "{\"error\":\"1\",\"msg\":\"删除失败\"}";
        }
    }

    @RequestMapping(value = "/teacher/insert")
    @ResponseBody
    public String teacherInsertHandler(Teacher teacher){
        Teacher t = teacherService.getTeacher(teacher.getT_login_name());
        if(t != null){
            return "{\"error\":\"1\",\"msg\":\"已存在该教师\"}";
        }

        if(teacherService.insertTeacher(teacher) != 0) {
            String record = "新增教师：" + teacher.getT_login_name();
            setOptionRecord(record);
            return "{\"error\":\"0\",\"msg\":\"添加成功\"}";
        }else{
            return "{\"error\":\"1\",\"msg\":\"添加失败\"}";
        }
    }

    @RequestMapping(value = "/teacher/update")
    @ResponseBody
    public String teacherUpdateHandler(Teacher teacher){
        if(teacherService.updateTeacherInfo(teacher) != 0) {
            String record = "更新教师：" + teacher.getT_login_name();
            setOptionRecord(record);
            return "{\"error\":\"0\",\"msg\":\"添加成功\"}";
        }else{
            return "{\"error\":\"1\",\"msg\":\"添加失败\"}";
        }
    }

    private int setOptionRecord(String record){
        OptionRecord optionRecord = new OptionRecord();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String date = df.format(new Date());
        optionRecord.setDate(date);
        optionRecord.setUid(session.getAttribute("uid").toString());
        optionRecord.setOption_class("teacher");
        optionRecord.setOption_detail(record);

        return logInfoDao.insertOptionRecord(optionRecord);
    }
}
