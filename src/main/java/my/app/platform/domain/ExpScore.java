package my.app.platform.domain;

/**
 * @author 夏之阳
 * 创建时间：2016-03-15 19:06
 * 创建说明：单个实验成绩类
 */

public class ExpScore {
    /**
     * 实验id
     */
    private int e_id;

    /**
     * 学生登录名
     */
    private String s_login_name;

    /**
     * 学生成绩
     */
    private int score;

    public int getE_id() {
        return e_id;
    }

    public void setE_id(int e_id) {
        this.e_id = e_id;
    }

    public String getS_login_name() {
        return s_login_name;
    }

    public void setS_login_name(String s_login_name) {
        this.s_login_name = s_login_name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
