package my.app.platform.domain.model;

/**
 * @author 夏之阳
 * 创建时间：2016-04-10 15:21
 * 创建说明：实验类别（传递给Model的格式）
 */

public class MExpType {
    /**
     * 类型id
     */
    private int type_id;

    /**
     * 类型名
     */
    private String type_name;

    /**
     * 类别id
     */
    private String class_id;

    /**
     * 类别名称
     */
    private String class_name;

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }
}
