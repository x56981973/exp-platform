package my.app.platform.service;

/**
 * @author 夏之阳
 * 创建时间：2016-03-19 21:33
 * 创建说明：学生信息服务
 */

import my.app.platform.domain.Student;
import my.app.platform.domain.Teacher;
import my.app.platform.repository.mapper.student.IStudentInfoDao;
import my.app.platform.repository.mapper.user.IUserListDao;
import my.app.platform.tool.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    IStudentInfoDao studentInfoDao;

    @Autowired
    IUserListDao userListDao;

    @Autowired
    TeacherService teacherService;

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
     * 查询学生信息
     * @param s_login_name 学生登录名
     * @return 学生信息
     */
    public Student getStudent(String s_login_name){
        List<Student> students = studentInfoDao.queryStudentInfo(s_login_name);
        if(students.size() != 0) {
            return students.get(0);
        }else{
            return null;
        }
    }

    /**
     * 获取所有学生列表
     * @return 学生列表
     */
    public List<Student> getStudentList(){
        return studentInfoDao.queryAllStudent();
    }

    /**
     * 获取教师学生列表
     * @return 学生列表
     */
    public List<Student> getStudentListByTeacher(String t_login_name){
        return studentInfoDao.queryStudentByTeacher(t_login_name);
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
     * 更新学生信息（面向管理员）
     * @param student 学生信息
     * @return 更新条数
     */
    public int updateStudent(Student student){
        return studentInfoDao.updateStudent(student);
    }

    /**
     * 更新学生信息（面向教师）
     * @param s_login_name 学生登录名
     * @return 更新条数
     */
    public int updateInfo(String s_login_name,String s_name, String s_grade, String s_score){
        return studentInfoDao.updateInfo(s_login_name, s_name, s_grade, s_score);
    }

    /**
     * 更新学生密码
     * @param s_login_name 学生登录名
     * @return 更新条数
     */
    public int updateStudentPwd(String s_login_name,String s_password){
        int i = userListDao.updatePwd(s_login_name,s_password);
        int j = studentInfoDao.updatePwd(s_login_name, s_password);
        return i * j;
    }

    /**
     * 更新学生已完成实验
     * @param s_login_name 学生登录名
     * @return 更新条数
     */
    public int updateTask(String s_login_name,String e_id){
        Student student = getStudent(s_login_name);
        String task = student.getTask_done();
        if(task == null) task = "";

        String t_login_name = student.getTeacher();
        Teacher teacher = teacherService.getTeacher(t_login_name);
        String taskList = teacher.getActive_exp();

        if(!taskList.contains(e_id)){
            return 0;
        }
        if(task.contains(e_id)){
            return 0;
        }

        if("".equals(task)) task += e_id;
        else task += "," + e_id;
        student.setTask_done(task);
        student.setProgress(getStudentProgress(task, t_login_name));
        return studentInfoDao.updateStudent(student);
    }

    /**
     * 获取学生实验进度
     * @param task 学生完成实验列表
     * @param t_login_name 教师id
     * @return 进度百分比
     */
    public String getStudentProgress(String task,String t_login_name){
        int done = task.split(",").length;

        Teacher teacher = teacherService.getTeacher(t_login_name);
        String taskList = teacher.getActive_exp();
        int todoList = taskList.split(",").length;

        float num= (float)done/todoList*100;
        DecimalFormat df = new DecimalFormat("0.00");

        return df.format(num);
    }
}
