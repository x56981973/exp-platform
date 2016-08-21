package my.app.platform.controller.student;

import my.app.platform.service.File.UploadFileService;
import my.app.platform.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

/**
 * @author 夏之阳
 * 创建时间：2016-08-21 15:00
 * 创建说明：学生信息修改
 */

@RestController
@RequestMapping(value = "/student")
public class StudentInfoController {
    @Autowired
    HttpSession httpSession;

    @Autowired
    StudentService studentService;

    @Autowired
    UploadFileService uploadFileService;

    /**
     * 更新学生信息接口
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String updateTeacher(String s_login_name, String password){
        if(!password.matches("^([A-Za-z]|[0-9]){0,}$")){
            return "{\"error\":\"1\",\"msg\":\"密码仅能使用数字字母组合\"}";
        }
        int result = studentService.updateStudentPwd(s_login_name, password);
        if (result != 0) {
            httpSession.removeAttribute("role");
            httpSession.removeAttribute("name");
            httpSession.removeAttribute("uid");
            return "{\"error\":\"0\",\"msg\":\"更新成功,请重新登录\"}";
        } else {
            return "{\"error\":\"1\",\"msg\":\"更新失败\"}";
        }
    }

    /**
     * 实验报告上传
     * @param report 实验报告
     * @return 上传结果
     */
    @RequestMapping(value="/upload/report", method= RequestMethod.POST)
    public String uploadReportHandler(MultipartFile report){
        String s_id = httpSession.getAttribute("uid").toString();
        String fileName = report.getOriginalFilename();
        int index = fileName.lastIndexOf(".");
        if(!("doc".equals(fileName.substring(index+1)) ||
                "docx".equals(fileName.substring(index+1)) ||
                "pdf".equals(fileName.substring(index+1)))){
            return "{\"error\":\"1\",\"msg\":\"请上传文件名后缀为doc/docx/pdf的文件!\"}";
        }

        String result = uploadFileService.uploadStudentReport(report, s_id);
        if("".equals(result)){
            return "{\"error\":\"1\",\"msg\":\"上传失败，请联系管理员!\"}";
        } else {
            return "{\"error\":\"0\",\"msg\":\"上传成功!\"}";
        }
    }

}
