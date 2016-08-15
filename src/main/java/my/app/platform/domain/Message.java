package my.app.platform.domain;

/**
 * @author 夏之阳
 * 创建时间：2016-05-17 01:36
 * 创建说明：消息类
 */


public class Message {
    /**
     * 消息id
     */
    private String id;

    /**
     * 教师id
     */
    private String t_id;

    /**
     * 学生id
     */
    private String s_id;

    /**
     * 消息正文
     */
    private String text;

    /**
     * 消息时间
     */
    private String date;

    /**
     * 已读
     */
    private String is_read;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getT_id() {
        return t_id;
    }

    public void setT_id(String t_id) {
        this.t_id = t_id;
    }

    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIs_read() {
        return is_read;
    }

    public void setIs_read(String is_read) {
        this.is_read = is_read;
    }
}
