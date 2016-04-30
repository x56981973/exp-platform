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

    public List<Teacher> getTeacherList(){
        return teacherInfoDao.queryAllTeacher();
    }

    public Teacher getTeacher(String t_login_name){
        List<Teacher> teachers = teacherInfoDao.querySingleTeacher(t_login_name);
        if(teachers.size() != 0){
            return teachers.get(0);
        } else {
            return null;
        }
    }

    /**
     * 更新教师信息
     * @param teacher 教师信息
     * @return 更新条数
     */
    public int updateTeacher(Teacher teacher){
        return teacherInfoDao.updateTeacherInfo(teacher);
    }

    /**
     * 删除教师
     * @param t_login_name 教师登录名
     * @return 删除条数
     */
    public int deleteTeacher(String t_login_name){
        return teacherInfoDao.deleteTeacherInfo(t_login_name);
    }

    public int insertTeacher(Teacher teacher){
        return teacherInfoDao.insertTeacherInfo(teacher);
    }
}
