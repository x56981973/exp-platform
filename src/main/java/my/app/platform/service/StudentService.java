package my.app.platform.service;

/**
 * @author 夏之阳
 * 创建时间：2016-03-19 21:33
 * 创建说明：学生信息服务
 */

import my.app.platform.domain.Student;
import my.app.platform.repository.mapper.student.IStudentInfoDao;
import my.app.platform.tool.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    IStudentInfoDao studentInfoDao;

    /**
     * 插入单个学生
     * @param student 学生信息
     * @return 插入条数
     */
    public int insertStudent(Student student){
        return studentInfoDao.insertStudentInfo(student);
    }


    /**
     * 从Excel中导出学生名单，插入到数据库中
     * @param path excel文件路径
     * @return 插入条数
     * @throws IOException
     */
    public int insertStudentList(String path,String t_id) throws IOException {
        List<List<String>> nameList;

        int index = path.lastIndexOf(".");
        if("xlsx".equals(path.substring(index+1))) {
            nameList = ExcelUtil.readXlsx(path);
        } else if ("xls".equals(path.substring(index+1))) {
            nameList = ExcelUtil.readXls(path);
        } else {
            return 0;
        }

        int count = 0;
        for(List<String> person : nameList){
            if (person.size() != 3) return 0;

            List<Student> students = studentInfoDao.queryStudentInfo(person.get(0));
            if(students.size() != 0) continue;

            Student student = new Student();
            student.setS_login_name(person.get(0));
            student.setS_password(person.get(0)); //密码默认与登陆名一致
            student.setS_name(person.get(1));
            student.setS_grade(person.get(2));
            student.setS_score("0"); //成绩默认为0
            student.setTeacher(t_id);

            count += studentInfoDao.insertStudentInfo(student);
        }
        return count;
    }

    /**
     * 获取所有学生列表
     * @return 学生列表
     */
    public List<Student> getStudentList(){
        return studentInfoDao.queryAllStudent();
    }

    /**
     * 删除学生
     * @param s_login_name 登录名
     * @return 删除条数
     */
    public int deleteStudent(String s_login_name){
        return studentInfoDao.deleteStudentInfo(s_login_name);
    }

    /**
     * 更新学生成绩
     * @param s_login_name 学生登录名
     * @return 更新条数
     */
    public int updateInfo(String s_login_name,String s_name, String s_grade, String s_score){
        return studentInfoDao.updateInfo(s_login_name,s_name,s_grade,s_score);
    }

    /**
     * 更新学生信息
     * @param s_login_name 学生登录名
     * @return 更新条数
     */
    public int updateStudentPwd(String s_login_name,String s_password){
        return studentInfoDao.updatePwd(s_login_name,s_password);
    }
}
