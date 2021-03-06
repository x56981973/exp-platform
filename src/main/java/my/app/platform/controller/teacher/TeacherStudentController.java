package my.app.platform.controller.teacher;

import my.app.platform.domain.Student;
import my.app.platform.service.File.UploadFileService;
import my.app.platform.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @author 夏之阳
 * 创建时间：2016-04-17 17:19
 * 创建说明：用户学生管理接口
 */

@Controller
@RequestMapping(value = "/teacher")
public class TeacherStudentController {
    @Autowired
    HttpSession httpSession;

    @Autowired
    StudentService studentService;

    @Autowired
    UploadFileService uploadFileService;

    //学生页面
    @RequestMapping(value = "/student")
    public String student(Model model){
        String name = httpSession.getAttribute("name").toString();
        model.addAttribute("name",name);

        String t_login_name = httpSession.getAttribute("uid").toString();
        List<Student> studentList = studentService.getStudentListByTeacher(t_login_name);
        model.addAttribute("student",studentList);

        return "/teacher/student";
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
    public String insertStudentHandler(String s_login_name,String s_name,String s_password,String s_grade) {
        //获取教师id
        String t_id = httpSession.getAttribute("uid").toString();

        Student student = studentService.getStudent(s_login_name);
        if(student != null){
            return "{\"error\":\"1\",\"msg\":\"已存在此学生\"}";
        }

        if(!s_password.matches("^([A-Za-z]|[0-9]){0,}$")){
            return "{\"error\":\"1\",\"msg\":\"密码仅能使用数字字母组合\"}";
        }

        Student new_student = new Student();
        new_student.setS_name(s_name);
        new_student.setS_login_name(s_login_name);
        new_student.setS_password(s_password);
        new_student.setS_grade(s_grade);
        new_student.setTeacher(t_id);
        if(studentService.insertStudent(new_student) > 0){
            return "{\"error\":\"0\",\"msg\":\"添加成功\"}";
        }else{
            return "{\"error\":\"1\",\"msg\":\"添加失败\"}";
        }
    }

    /**
     * 插入学生列表
     * @param studentList 学生列表
     * @return 插入结果
     */
    @RequestMapping(value = "/student/insertList", method = RequestMethod.POST)
    @ResponseBody
    public String insertStudentListHandler(MultipartFile studentList) {
        //获取教师id
        String t_id = httpSession.getAttribute("uid").toString();

        String fileName = studentList.getOriginalFilename();
        int index = fileName.lastIndexOf(".");
        if(!("xlsx".equals(fileName.substring(index+1)) || "xls".equals(fileName.substring(index+1)))){
            return "{\"error\":\"1\",\"msg\":\"文件格式不正确\"}";
        }

        String result = uploadFileService.uploadStudentListService(studentList, t_id);
        if("".equals(result)){
            return "{\"error\":\"1\",\"msg\":\"文件上传失败\"}";
        }else{
            int count;
            try {
                count = studentService.insertStudentList(result,t_id);
            } catch (IOException e) {
                e.printStackTrace();
                return "{\"error\":\"1\",\"msg\":\"插入数据库失败\"}";
            }
            if(count != 0){
                String format = "{\"error\":\"0\",\"msg\":\"成功插入%d条学生记录\"}";
                return String.format(format,count);
            }else{
                return "{\"error\":\"1\",\"msg\":\"插入数据库失败\"}";
            }
        }
    }

    /**
     * 删除学生
     * @param s_login_name 学生登录名
     * @return 删除结果
     */
    @RequestMapping(value = "/student/delete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteStudentHandler(String s_login_name) {
        if(studentService.deleteStudent(s_login_name) != 0) {
            return "{\"error\":\"0\",\"msg\":\"删除成功\"}";
        }else{
            return "{\"error\":\"1\",\"msg\":\"删除失败\"}";
        }
    }

    /**
     * 更新学生信息
     * @param s_login_name 登录名
     * @param s_name 学生姓名
     * @param s_grade 学生年级
     * @param s_score 学生成绩
     * @return 修改结果
     */
    @RequestMapping(value = "/student/update/info", method = RequestMethod.POST)
    @ResponseBody
    public String updateStudentInfoHandler(String s_login_name,String s_name, String s_grade, String s_score) {
        int result = studentService.updateInfo(s_login_name,s_name,s_grade,s_score);
        if(result != 0){
            return "{\"error\":\"0\",\"msg\":\"修改成功\"}";
        } else {
            return "{\"error\":\"1\",\"msg\":\"修改失败\"}";
        }
    }

    /**
     * 修改学生密码
     * @param s_login_name 学生登录名
     * @param s_password 学生密码
     * @return 修改结果
     */
    @RequestMapping(value = "/student/update/pwd", method = RequestMethod.POST)
    @ResponseBody
    public String updateStudentPwdHandler(String s_login_name,String s_password) {
        if(!s_password.matches("^([A-Za-z]|[0-9]){0,}$")){
            return "{\"error\":\"1\",\"msg\":\"密码仅能使用数字字母组合\"}";
        }

        int result = studentService.updateStudentPwd(s_login_name,s_password);
        if(result != 0){
            return "{\"error\":\"0\",\"msg\":\"修改成功\"}";
        } else {
            return "{\"error\":\"1\",\"msg\":\"修改失败\"}";
        }
    }
}
