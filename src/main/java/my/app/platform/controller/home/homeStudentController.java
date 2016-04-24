package my.app.platform.controller.home;

import my.app.platform.domain.Student;
import my.app.platform.service.File.UploadFileService;
import my.app.platform.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @author 夏之阳
 * 创建时间：2016-04-17 17:19
 * 创建说明：
 */

@Controller
public class homeStudentController {
    @Autowired
    HttpSession httpSession;

    @Autowired
    StudentService studentService;

    @Autowired
    UploadFileService uploadFileService;


    @RequestMapping(value = "/student")
    public String student(Model model){
        String t_name = httpSession.getAttribute("t_name").toString();
        model.addAttribute("t_name",t_name);

        List<Student> studentList = studentService.getStudentList();
        model.addAttribute("student",studentList);

        return "/user/student";
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
        String t_id = httpSession.getAttribute("t_id").toString();

        Student student = new Student();
        student.setS_name(s_name);
        student.setS_login_name(s_login_name);
        student.setS_password(s_password);
        student.setS_grade(s_grade);
        student.setTeacher(t_id);
        if(studentService.insertStudent(student) > 0){
            return "{\"error\":\"0\",\"msg\":\"插入成功\"}";
        }else{
            return "{\"error\":\"1\",\"msg\":\"插入失败\"}";
        }
    }

    @RequestMapping(value = "/student/insertList", method = RequestMethod.POST)
    @ResponseBody
    public String insertStudentListHandler(MultipartFile studentList) {
        //获取教师id
        String t_id = httpSession.getAttribute("t_id").toString();

        String fileName = studentList.getOriginalFilename();
        int index = fileName.lastIndexOf(".");
        if(!("xlsx".equals(fileName.substring(index+1)) || "xls".equals(fileName.substring(index+1)))){
            return "{\"error\":\"1\",\"msg\":\"文件格式不正确\"}";
        }

        String result = uploadFileService.uploadService(studentList,t_id);
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

    @RequestMapping(value = "/student/delete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteStudentHandler(String s_login_name) {
        if(studentService.deleteStudent(s_login_name) != 0) {
            return "{\"error\":\"0\",\"msg\":\"删除成功\"}";
        }else{
            return "{\"error\":\"1\",\"msg\":\"删除失败\"}";
        }
    }
}
