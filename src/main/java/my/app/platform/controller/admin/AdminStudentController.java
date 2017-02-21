package my.app.platform.controller.admin;

import my.app.platform.domain.OptionRecord;
import my.app.platform.domain.Student;
import my.app.platform.domain.Teacher;
import my.app.platform.repository.mapper.log.ILogInfoDao;
import my.app.platform.service.StudentService;
import my.app.platform.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author 夏之阳
 * 创建时间：2016-04-30 21:19
 * 创建说明：管理员学生管理
 */

@Controller
@RequestMapping(value = "/admin")
public class AdminStudentController {
    @Autowired
    private HttpSession session;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    ILogInfoDao logInfoDao;

    @RequestMapping(value = "/student")
    public String student(Model model){
        String name = session.getAttribute("name").toString();
        model.addAttribute("name",name);

        List<Student> studentList = studentService.getStudentList();
        model.addAttribute("student",studentList);

        return "/admin/student";
    }

    @RequestMapping(value = "/student/delete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteStudentHandler(String s_login_name) {
        if(studentService.deleteStudent(s_login_name) != 0) {
            String record = "删除学生：" + s_login_name;
            setOptionRecord(record);

            return "{\"error\":\"0\",\"msg\":\"删除成功\"}";
        }else{
            return "{\"error\":\"1\",\"msg\":\"删除失败\"}";
        }
    }

    /**
     * 插入单个学生
     * @param s_login_name 学生登录名
     * @param s_name 学生名
     * @param s_password 学生密码
     * @return 是否成功
     */
    @RequestMapping(value = "/student/insert", method = RequestMethod.POST)
    @ResponseBody
    public String insertStudentHandler(String s_login_name,String s_name,String s_password,String s_grade,String t_login_name) {
        Student student = studentService.getStudent(s_login_name);
        if(student != null){
            return "{\"error\":\"1\",\"msg\":\"已存在此学生\"}";
        }

        Teacher teacher = teacherService.getTeacher(t_login_name);
        if(teacher == null){
            return "{\"error\":\"1\",\"msg\":\"查无此教师\"}";
        }

        if(!s_password.matches("^([A-Za-z]|[0-9]){0,}$")){
            return "{\"error\":\"1\",\"msg\":\"密码仅能使用数字字母组合\"}";
        }

        Student new_student = new Student();
        new_student.setS_name(s_name);
        new_student.setS_login_name(s_login_name);
        new_student.setS_password(s_password);
        new_student.setS_grade(s_grade);
        new_student.setTeacher(t_login_name);
        if(studentService.insertStudent(new_student) > 0){
            String record = "添加学生：" + s_login_name;
            setOptionRecord(record);

            return "{\"error\":\"0\",\"msg\":\"添加成功\"}";
        }else{
            return "{\"error\":\"1\",\"msg\":\"添加失败\"}";
        }
    }

    @RequestMapping(value = "/student/update", method = RequestMethod.POST)
    @ResponseBody
    public String updateStudentInfoHandler(Student student) {
        int result = studentService.updateStudent(student);
        if(result != 0){
            String record = "修改学生：" + student.getS_login_name();
            setOptionRecord(record);

            return "{\"error\":\"0\",\"msg\":\"修改成功\"}";
        } else {
            return "{\"error\":\"1\",\"msg\":\"修改失败\"}";
        }
    }

    private int setOptionRecord(String record){
        OptionRecord optionRecord = new OptionRecord();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String date = df.format(new Date());
        optionRecord.setDate(date);
        optionRecord.setUid(session.getAttribute("uid").toString());
        optionRecord.setOption_class("student");
        optionRecord.setOption_detail(record);

        return logInfoDao.insertOptionRecord(optionRecord);
    }
}
