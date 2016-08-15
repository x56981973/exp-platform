package my.app.platform.controller.student;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 夏之阳
 * 创建时间：2016-03-25 11:33
 * 创建说明：实验接口
 */

@Controller
@RequestMapping(value = "/student")
public class StudentHomeController {
    //主页
    @RequestMapping(value = "/home")
    public String home(Model model){

        return "/student/home";
    }

    //实验页面
    @RequestMapping(value = "/exp")
    public String experiment(Model model){


        return "/student/experiment";
    }

    //设置页面
    @RequestMapping(value = "/settings")
    public String setting(Model model){

        return "/student/settings";
    }

    //消息页面
    @RequestMapping(value = "/message")
    public String message(Model model){

        return "/student/message";
    }

}
