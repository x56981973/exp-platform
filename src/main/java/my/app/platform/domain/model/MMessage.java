package my.app.platform.domain.model;

/**
 * @author 夏之阳
 * 创建时间：2016-05-18 10:49
 * 创建说明：Message(Model)
 */

public class MMessage {
    /**
     * 消息id
     */
    private String id;

    /**
     * 学生id
     */
    private String s_id;

    /**
     * 学生姓名
     */
    private String s_name;

    /**
     * 实验id
     */
    private String e_name;

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

    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public String getE_name() {
        return e_name;
    }

    public void setE_name(String e_name) {
        this.e_name = e_name;
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
