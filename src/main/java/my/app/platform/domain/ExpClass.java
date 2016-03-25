package my.app.platform.domain;

/**
 * @author 夏之阳
 * 创建时间：2016-03-15 19:10
 * 创建说明：实验分类
 */

public class ExpClass {
    /**
     * 分类id
     */
    private int class_id;

    /**
     * 分类名
     */
    private String class_name;

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }
}
