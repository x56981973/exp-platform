package my.app.platform.service;

import my.app.platform.domain.User;
import my.app.platform.repository.mapper.user.IUserListDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 夏之阳
 * 创建时间：2016-03-27 15:25
 * 创建说明：用户登录服务
 */

@Service
public class LoginService {
    @Autowired
    IUserListDao loginCheckDao;

    /**
     * 用户登录验证
     * @param userName 用户名
     * @param password 密码
     * @return 用户信息
     */
    public User loginCheck(String userName, String password){
        List<User> userList = loginCheckDao.checkLogin(userName, password);
        if(userList.size() != 0){
            return userList.get(0);
        } else {
            return null;
        }
    }
}
