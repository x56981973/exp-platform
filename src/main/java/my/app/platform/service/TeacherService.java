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
     * 更新实验列表
     * @param t_id 教师id
     * @param activeExp 新实验列表
     * @return 更新条数
     */
    public int updateActiveExpList(String t_id, String activeExp){
        return teacherInfoDao.updateActiveExp(t_id, activeExp);
    }

    /**
     * 更新教师密码
     * @param t_id 教师id
     * @param password 新密码
     * @return 更新条数
     */
    public int updateTeacherPwd(String t_id, String password){
        return teacherInfoDao.updateTeacherPwd(t_id, password);
    }

    /**
     * 更新教师信息
     * @param teacher 教师信息
     * @return 更新条数
     */
    public int updateTeacherInfo(Teacher teacher){
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
        if(list == null){
            return null;
        }
        String[] expList = list.split(",");

        List<ActiveExperiment> result = new ArrayList<>();
        for(String exp : expList){
            String exp_id = exp.substring(0,exp.length()-1);
            String status = exp.substring(exp.length() - 1);

            List<Experiment> experiments = expInfoDao.queryExperiment(exp_id);
            if(experiments.size() != 0) {
                Experiment experiment = experiments.get(0);

                ActiveExperiment activeExperiment = new ActiveExperiment();
                activeExperiment.setStatus(status);
                activeExperiment.setE_id(exp_id);
                activeExperiment.setE_name(experiment.getE_name());
                activeExperiment.setRef_path(experiment.getRef_path());
                activeExperiment.setE_description(experiment.getE_description());

                result.add(activeExperiment);
            }
        }
        return result;
    }

    /**
     * 更新正在进行中的实验
     * @return 更新条数
     */
    public int updateActiveExp(){
        List<Teacher> teacherList = teacherInfoDao.queryAllTeacher();

        int count = 0;
        for(Teacher teacher : teacherList){
            String list = teacher.getActive_exp();
            String[] expList = list.split(",");

            String newList = "";
            for(String exp : expList){
                String exp_id = exp.substring(0,exp.length()-1);
                String status = exp.substring(exp.length() - 1);

                List<Experiment> experiments = expInfoDao.queryExperiment(exp_id);
                if(experiments.size() != 0) {
                    newList += exp_id + status + ",";
                }
            }
            newList = newList.substring(0,newList.length()-1);
            if(!newList.equals(list)){
                count += teacherInfoDao.updateActiveExp(teacher.getT_login_name(),newList);
            }
        }

        return count;
    }
}
