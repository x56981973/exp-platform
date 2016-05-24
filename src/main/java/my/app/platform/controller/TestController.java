package my.app.platform.controller;

import my.app.framework.web.Result;
import my.app.framework.web.ResultHelper;
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
    /**
     * 客户端连接测试结果
     * @param hello 测试语句
     * @return 原句返回
     */
    @RequestMapping(value = "/client/interface/test", method = RequestMethod.POST)
    public Result interfaceTestHandler(String hello) {
        return ResultHelper.newSuccessResult(hello);
    }
}
