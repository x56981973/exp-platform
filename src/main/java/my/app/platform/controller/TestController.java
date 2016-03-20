package my.app.platform.controller;

import my.app.framework.web.Result;
import my.app.framework.web.ResultHelper;
import my.app.platform.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author 夏之阳
 * 创建时间：2016-03-14 20:46
 * 创建说明：接口控制测试
 */

@RestController
public class TestController {
    @Autowired
    StudentService studentService;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public Result testHandler(@RequestParam String path) throws IOException {
        return ResultHelper.newSuccessResult(studentService.insertStudentList(path));
    }
}
