package my.app.platform.service;

import my.app.platform.domain.Student;
import my.app.platform.domain.Teacher;
import my.app.platform.repository.mapper.student.IStudentInfoDao;
import my.app.platform.repository.mapper.teacher.ITeacherInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 夏之阳
 * 创建时间：2016-03-27 15:25
 * 创建说明：用户登录服务
 */

@Service
public class LoginService {
    @Autowired
    IStudentInfoDao studentInfoDao;

    @Autowired
    ITeacherInfoDao teacherInfoDao;

    public Student studentLoginCheck(String userName, String password){
        List<Student> students = studentInfoDao.checkStudentInfo(userName, password);
        if(students.size() != 0){
            return students.get(0);
        } else {
            return null;
        }
    }

    public Teacher teacherLoginCheck(String userName, String password){
        List<Teacher> teachers = teacherInfoDao.checkLogin(userName, password);
        if(teachers.size() != 0){
            return teachers.get(0);
        } else {
            return null;
        }
    }
}
