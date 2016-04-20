package my.app.platform.controller.home;

import my.app.platform.domain.Student;
import my.app.platform.repository.mapper.student.IStudentInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

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
    IStudentInfoDao studentInfoDao;

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
//        //获取教师id
//        String t_id = httpSession.getAttribute("t_id").toString();
//
//        Student student = new Student();
//        student.setS_name(s_name);
//        student.setS_login_name(s_login_name);
//        student.setS_password(s_password);
//        student.setS_grade(s_grade);
//        student.setTeacher(t_id);
//        if(studentInfoDao.insertStudentInfo(student) > 0){
//            return "{\"error\":\"0\",\"msg\":\"插入成功\",\"to\":\"/setting\"}";
//        }else{
//            return "{\"error\":\"1\",\"msg\":\"插入失败\",\"to\":\"/setting\"}";
//        }

        return "{\"error\":\"0\",\"msg\":\"插入成功\",\"to\":\"/setting\"}";
    }
}
