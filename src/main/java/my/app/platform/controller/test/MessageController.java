package my.app.platform.controller.test;

import my.app.framework.web.Result;
import my.app.framework.web.ResultHelper;
import my.app.platform.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 夏之阳
 * 创建时间：2016-05-17 01:33
 * 创建说明：消息接口
 */

@RestController
@RequestMapping(value = "/test")
public class MessageController {
    @Autowired
    MessageService messageService;

    /**
     * 消息插入
     * @param s_id 学生id
     * @param text 消息正文
     * @return
     */
    @RequestMapping(value = "/message", method = RequestMethod.POST)
    public Result messageInsertHandler(String s_id, String text) {
        int result = messageService.insertMessage(s_id, text);
        if(result != 0){
            return ResultHelper.newSuccessResult("");
        }else {
            return ResultHelper.newFailureResult("");
        }
    }
}
