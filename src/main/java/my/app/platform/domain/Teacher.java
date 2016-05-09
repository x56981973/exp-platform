package my.app.platform.domain;

/**
 * @author 夏之阳
 * 创建时间：2016-03-14 21:00
 * 创建说明：教师类
 */

public class Teacher {
    /**
     * 教师姓名
     */
    private String t_name;

    /**
     * 教师登录名
     */
    private String t_login_name;

    /**
     * 教师登陆密码
     */
    private String t_password;

    /**
     * 学校
     */
    private String t_school;

    /**
     * 角色
     */
    private String role;

    /**
     * 正在进行中的实验
     */
    private String active_exp;

    public String getT_name() {
        return t_name;
    }

    public void setT_name(String t_name) {
        this.t_name = t_name;
    }

    public String getT_login_name() {
        return t_login_name;
    }

    public void setT_login_name(String t_login_name) {
        this.t_login_name = t_login_name;
    }

    public String getT_password() {
        return t_password;
    }

    public void setT_password(String t_password) {
        this.t_password = t_password;
    }

    public String getT_school() {
        return t_school;
    }

    public void setT_school(String t_school) {
        this.t_school = t_school;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getActive_exp() {
        return active_exp;
    }

    public void setActive_exp(String active_exp) {
        this.active_exp = active_exp;
    }
}
