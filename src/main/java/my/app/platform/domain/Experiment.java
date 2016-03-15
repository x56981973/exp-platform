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
     * 实验分类id
     */
    private int c_id;

    public int getE_id() {
        return e_id;
    }

    public void setE_id(int e_id) {
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

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }
}
