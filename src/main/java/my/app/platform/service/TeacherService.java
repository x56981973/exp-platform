package my.app.platform.service;

import my.app.platform.domain.Experiment;
import my.app.platform.domain.Teacher;
import my.app.platform.domain.model.ActiveExperiment;
import my.app.platform.repository.mapper.experiment.IExpInfoDao;
import my.app.platform.repository.mapper.teacher.ITeacherInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Autowired
    IExpInfoDao expInfoDao;

    /**
     * 获取管理员列表
     * @return 管理员列表
     */
    public List<Teacher> getAdminList(){
        return teacherInfoDao.queryAllAdmin();
    }

    /**
     * 获取教师列表
     * @return 教师列表
     */
    public List<Teacher> getTeacherList(){
        return teacherInfoDao.queryAllTeacher();
    }

    /**
     * 根据登录名查教师信息
     * @param t_login_name 登录名
     * @return 教师信息
     */
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

    /**
     * 插入教师
     * @param teacher 教师信息
     * @return 插入条数
     */
    public int insertTeacher(Teacher teacher){
        return teacherInfoDao.insertTeacherInfo(teacher);
    }

    /**
     * 根据用户名查询正在进行中的实验
     * @param t_login_name 教师登录名
     * @return 实验列表
     */
    public List<ActiveExperiment> getActiveExpList(String t_login_name){
        Teacher teacher = teacherInfoDao.querySingleTeacher(t_login_name).get(0);
        String list = teacher.getActive_exp();
        String[] expList = list.split(",");

        List<ActiveExperiment> result = new ArrayList<>();
        for(String exp : expList){
            String exp_id = exp.substring(0,exp.length()-1);
            String status = exp.substring(exp.length()-1);

            Experiment experiment = expInfoDao.queryExperiment(exp_id).get(0);
            ActiveExperiment activeExperiment = new ActiveExperiment();
            activeExperiment.setStatus(status);
            activeExperiment.setE_id(exp_id);
            activeExperiment.setE_name(experiment.getE_name());
            activeExperiment.setE_description(experiment.getE_description());

            result.add(activeExperiment);
        }
        return result;
    }
}
