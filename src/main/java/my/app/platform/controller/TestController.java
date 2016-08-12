package my.app.platform.controller;

import my.app.framework.web.Result;
import my.app.framework.web.ResultHelper;
import my.app.platform.repository.mapper.experiment.IExpInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 夏之阳
 * 创建时间：2016-03-14 20:46
 * 创建说明：接口控制测试
 */

@RestController
public class TestController {
    @Autowired
    IExpInfoDao expInfoDao;

    /**
     * 客户端连接测试结果
     * @return 原句返回
     */
    @RequestMapping(value = "/client/interface/test", method = RequestMethod.GET)
    public Result interfaceTestHandler() {
        return ResultHelper.newSuccessResult(expInfoDao.queryAllExp());
    }
}
