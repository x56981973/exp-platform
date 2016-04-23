package my.app.platform.service;

import my.app.platform.domain.Teacher;
import my.app.platform.repository.mapper.teacher.ITeacherInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 夏之阳
 * 创建时间：2016-04-11 22:56
 * 创建说明：教师信息服务
 */

@Service
public class TeacherService {
    @Autowired
    ITeacherInfoDao teacherInfoDao;

    public Teacher getTeacher(String s_login_name){
        List<Teacher> teachers = teacherInfoDao.querySingleTeacher(s_login_name);
        if(teachers.size() != 0){
            return teachers.get(0);
        } else {
            return null;
        }
    }

    /**
     * 更新教师信息
     * @param teacher
     * @return 更新条数
     */
    public int updateTeacher(Teacher teacher){
        return teacherInfoDao.updateTeacherInfo(teacher);
    }
}
