package my.app.platform.controller;

import my.app.framework.web.Result;
import my.app.framework.web.ResultHelper;
import my.app.platform.domain.OptionRecord;
import my.app.platform.repository.mapper.log.ILogInfoDao;
import my.app.platform.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 夏之阳
 * 创建时间：2016-03-14 20:46
 * 创建说明：接口控制测试
 */

@RestController
public class TestController {
    @Autowired
//    StudentService studentService;
    ILogInfoDao logInfoDao;

    @RequestMapping(value = "/client/test", method = RequestMethod.GET)
//    public Result testHandler(@RequestParam String path) throws IOException {
//        return ResultHelper.newSuccessResult(studentService.insertStudentList(path,"yiping"));
//    }

    public Result testHandler() {
        OptionRecord optionRecord = new OptionRecord();
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//        String date = df.format(new Date());
//        optionRecord.setDate(date);
        optionRecord.setDate("123");
        optionRecord.setUid("admin");
        optionRecord.setOption_class("student");
        optionRecord.setOption_detail("删除学生：");
        logInfoDao.insertOptionRecord(optionRecord);
        return ResultHelper.newSuccessResult(logInfoDao.insertOptionRecord(optionRecord));
    }
}
