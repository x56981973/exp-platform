package my.app.platform.domain.model;

/**
 * @author 夏之阳
 * 创建时间：2016-05-08 15:12
 * 创建说明：
 */

public class ActiveExperiment {
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
     * 参考代码开放状态
     */
    private String status;

    /**
     * 参考代码路径
     */
    private String ref_path;

    public String getE_id() {
        return e_id;
    }

    public void setE_id(String e_id) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRef_path() {
        return ref_path;
    }

    public void setRef_path(String ref_path) {
        this.ref_path = ref_path;
    }
}
