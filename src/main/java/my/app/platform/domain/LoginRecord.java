package my.app.platform.domain;

/**
 * @author 夏之阳
 * 创建时间：2016-05-04 21:37
 * 创建说明：登录记录
 */

public class LoginRecord {
    /**
     * 用户id
     */
    private String uid;

    /**
     * 登陆时间
     */
    private String date;

    /**
     * 登陆ip地址
     */
    private String ip_address;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }
}
