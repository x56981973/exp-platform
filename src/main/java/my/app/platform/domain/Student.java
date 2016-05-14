package my.app.platform.domain;

/**
 * @author 夏之阳
 * 创建时间：2016-03-15 18:09
 * 创建说明：学生类
 */

public class Student {
    /**
     * 学生姓名
     */
    private String s_name;

    /**
     * 学生登陆名
     */
    private String s_login_name;

    /**
     * 学生登录密码
     */
    private String s_password;

    /**
     * 学生年级
     */
    private String s_grade;

    /**
     * 学生最终成绩
     */
    private String s_score;

    /**
     * 教师
     */
    private String teacher;

    /**
     * 报告状态
     */
    private String report_status;

    /**
     * 报告路径
     */
    private String report_path;

    /**
     * 已完成的实验
     */
    private String task_done;

    /**
     * 实验进度
     */
    private String progress;

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public String getS_login_name() {
        return s_login_name;
    }

    public void setS_login_name(String s_login_name) {
        this.s_login_name = s_login_name;
    }

    public String getS_password() {
        return s_password;
    }

    public void setS_password(String s_password) {
        this.s_password = s_password;
    }

    public String getS_grade() {
        return s_grade;
    }

    public void setS_grade(String s_grade) {
        this.s_grade = s_grade;
    }

    public String getS_score() {
        return s_score;
    }

    public void setS_score(String s_score) {
        this.s_score = s_score;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getReport_status() {
        return report_status;
    }

    public void setReport_status(String report_status) {
        this.report_status = report_status;
    }

    public String getReport_path() {
        return report_path;
    }

    public void setReport_path(String report_path) {
        this.report_path = report_path;
    }

    public String getTask_done() {
        return task_done;
    }

    public void setTask_done(String task_done) {
        this.task_done = task_done;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }
}
