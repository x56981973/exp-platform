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
     * 查询学生基本信息
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
     * 删除学生信息
     * @param s_login_name 学生登录名
     * @return 删除条数
     */
    int deleteStudentInfo(String s_login_name);
}
