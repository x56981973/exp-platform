package my.app.platform.domain;

/**
 * @author 夏之阳
 * 创建时间：2016-03-15 18:51
 * 创建说明：实验类
 */

public class Experiment {
    /**
     * 实验id
     */
    private String e_id;

    /**
     * 实验名
     */
    private String e_name;

    /**
     * 实验描述
     */
    private String e_description;

    /**
     * 实验分类id
     */
    private String class_id;

    /**
     * 实验类型分类
     */
    private String type_id;

    /**
     * 实验文件路径
     */
    private String e_srcPath;

    public String getE_id() {
        return e_id;
    }

    public void setE_id(String e_id) {
        this.e_id = e_id;
    }

    public String getE_description() {
        return e_description;
    }

    public void setE_description(String e_description) {
        this.e_description = e_description;
    }

    public String getE_name() {
        return e_name;
    }

    public void setE_name(String e_name) {
        this.e_name = e_name;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getE_srcPath() {
        return e_srcPath;
    }

    public void setE_srcPath(String e_srcPath) {
        this.e_srcPath = e_srcPath;
    }
}
