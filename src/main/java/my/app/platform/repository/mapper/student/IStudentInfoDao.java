package my.app.platform.repository.mapper.student;

import my.app.platform.domain.Student;

import java.util.List;

/**
 * @author 夏之阳
 * 创建时间：2016-03-15 19:17
 * 创建说明：学生查询接口
 */

public interface IStudentInfoDao {
    /**
     * 查询所有学生信息
     * @return 学生列表
     */
    List<Student> queryAllStudent();

    /**
     * 查询所有学生信息
     * @param t_login_name 教师id
     * @return 学生列表
     */
    List<Student> queryStudentByTeacher(String t_login_name);

    /**
     * 查询学生基本信息(登陆时调用)
     * @param s_login_name 学生登录名
     * @param s_password 学生登录密码
     * @return 学生信息
     */
    List<Student> checkStudentInfo(String s_login_name, String s_password);

    /**
     * 查询学生基本信息(登陆时调用)
     * @param s_login_name 学生登录名
     * @return 学生信息
     */
    List<Student> queryStudentInfo(String s_login_name);

    /**
     * 插入学生基本信息
     * @param student 学生基本信息
     * @return 成功条数
     */
    int insertStudentInfo(Student student);

    /**
     * 更新学生基本信息
     * @param student 学生基本信息
     * @return 成功条数
     */
    int updateStudentInfo(Student student);

    /**
     * 更新学生成绩
     * @param s_login_name 学生登录名
     * @return 更新条数
     */
    int updateInfo(String s_login_name,String s_name, String s_grade, String s_score);

    /**
     * 更新学生密码
     * @param s_login_name 学生登录名
     * @return 更新条数
     */
    int updatePwd(String s_login_name,String s_password);

    /**
     * 删除学生信息
     * @param s_login_name 学生登录名
     * @return 删除条数
     */
    int deleteStudentInfo(String s_login_name);

    /**
     * 学生成绩查询
     * @param s_login_name 学生登陆名
     * @return 成绩
     */
    String queryScore(String s_login_name);
}
