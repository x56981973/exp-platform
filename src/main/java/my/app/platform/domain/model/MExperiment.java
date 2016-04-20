package my.app.platform.domain.model;

/**
 * @author 夏之阳
 * 创建时间：2016-04-10 15:21
 * 创建说明：实验信息（传递给Model的格式）
 */

public class MExperiment {
    /**
     * 实验id
     */
    private int e_id;

    /**
     * 实验名
     */
    private String e_name;

    /**
     * 实验描述
     */
    private String e_description;

    /**
     * 实验分类
     */
    private String class_name;

    /**
     * 实验类型
     */
    private String type_name;

    /**
     * 实验文件路径
     */
    private String e_srcPath;

    public int getE_id() {
        return e_id;
    }

    public void setE_id(int e_id) {
        this.e_id = e_id;
    }

    public String getE_name() {
        return e_name;
    }

    public void setE_name(String e_name) {
        this.e_name = e_name;
    }

    public String getE_description() {
        return e_description;
    }

    public void setE_description(String e_description) {
        this.e_description = e_description;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getE_srcPath() {
        return e_srcPath;
    }

    public void setE_srcPath(String e_srcPath) {
        this.e_srcPath = e_srcPath;
    }
}
