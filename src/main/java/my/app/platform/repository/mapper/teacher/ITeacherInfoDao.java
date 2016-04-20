package my.app.platform.repository.mapper.teacher;

import my.app.platform.domain.Teacher;

import java.util.List;

/**
 * @author 夏之阳
 * 创建时间：2016-03-14 21:05
 * 创建说明：教师查询接口
 */

public interface ITeacherInfoDao {
    /**
     * 查询所有教师基本信息
     * @return 教师信息
     */
    List<Teacher> queryAllTeacher();

    /**
     * 查询教师基本信息
     * @param t_login_name 教师登录名
     * @return 教师信息
     */
    List<Teacher> querySingleTeacher(String t_login_name);

    /**
     * 查询教师基本信息(登录时调用)
     * @param t_login_name 教师登录名
     * @param t_password 教师登录密码
     * @return 教师信息
     */
    List<Teacher> checkLogin(String t_login_name, String t_password);

    /**
     * 插入教师基本信息
     * @param teacher 教师基本信息
     * @return 成功条数
     */
    int insertTeacherInfo(Teacher teacher);

    /**
     * 更新教师基本信息
     * @param teacher 教师基本信息
     * @return 成功条数
     */
    int updateTeacherInfo(Teacher teacher);

    /**
     * 删除教师信息
     * @param t_login_name 教师登录名
     * @return 删除条数
     */
    int deleteTeacherInfo(String t_login_name);
}
