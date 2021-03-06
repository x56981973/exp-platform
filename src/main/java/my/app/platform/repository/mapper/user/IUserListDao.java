package my.app.platform.repository.mapper.user;

import my.app.platform.domain.User;

import java.util.List;

/**
 * @author 夏之阳
 * 创建时间：2016-03-15 19:17
 * 创建说明：学生查询接口
 */

public interface IUserListDao {

    /**
     * 登陆验证
     * @param username 用户名
     * @param password 密码
     * @return 用户列表
     */
    List<User> checkLogin(String username, String password);

    /**
     * 更新密码
     * @param username 用户名
     * @param password 密码
     * @return 更新条数
     */
    int updatePwd(String username, String password);

}
