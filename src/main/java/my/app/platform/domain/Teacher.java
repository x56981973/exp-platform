package my.app.platform.domain;

/**
 * @author 夏之阳
 * 创建时间：2016-03-14 21:00
 * 创建说明：教师类
 */

public class Teacher {
    private String t_name;

    private String t_login_name;

    private String t_password;

    private String t_school;

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
}