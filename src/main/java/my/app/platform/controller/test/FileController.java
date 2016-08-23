package my.app.platform.controller.test;

import my.app.framework.web.Result;
import my.app.framework.web.ResultHelper;
import my.app.platform.domain.Experiment;
import my.app.platform.domain.Student;
import my.app.platform.domain.Teacher;
import my.app.platform.repository.mapper.experiment.IExpInfoDao;
import my.app.platform.service.File.DownLoadFileService;
import my.app.platform.service.File.UploadFileService;
import my.app.platform.service.StudentService;
import my.app.platform.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author 夏之阳
 * 创建时间：2016-03-28 15:05
 * 创建说明：文件上传接口
 */

@RestController
@RequestMapping(value="/test")
public class FileController {
    @Autowired
    UploadFileService uploadFileService;

    @Autowired
    DownLoadFileService downLoadFileService;

    @Autowired
    StudentService studentService;

    @Autowired
    TeacherService teacherService;

    @Autowired
    IExpInfoDao expInfoDao;

    /**
     * 实验报告上传
     * @param file 实验报告
     * @param s_id 学生id
     * @return 上传结果
     */
    @RequestMapping(value="/upload/report", method= RequestMethod.POST)
    public Result uploadReportHandler(MultipartFile file, String s_id){
        String fileName = file.getOriginalFilename();
        int index = fileName.lastIndexOf(".");
        if(!("doc".equals(fileName.substring(index+1)) ||
            "docx".equals(fileName.substring(index+1)) ||
            "pdf".equals(fileName.substring(index+1)))){
            return ResultHelper.newFailureResult("请上传文件名后缀为doc/docx/pdf的文件!");
        }

        String result = uploadFileService.uploadStudentReport(file, s_id);
        if("".equals(result)){
            return ResultHelper.newFailureResult("请联系管理员");
        } else {
            return ResultHelper.newSuccessResult(result);
        }
    }

    /**
     * 文件下载接口
     * @param fileName 文件名（含路径）
     * @param response http响应（返回文件流格式）
     */
    @RequestMapping(value="/download", method= RequestMethod.POST)
    public void downloadFile(String fileName,HttpServletResponse response){
        downLoadFileService.downloadFile(fileName, response);
    }

    /**
     * 参考代码下载
     * @param s_id 学生id
     * @param e_id 实验id
     * @param response http响应（返回文件流格式）
     */
    @RequestMapping(value="/download/referenceCode", method= RequestMethod.GET)
    public void downloadCodeFile(String s_id,String e_id,HttpServletResponse response){

//        Student student = studentService.getStudent(s_id);
//        Teacher teacher = teacherService.getTeacher(student.getTeacher());
//        String activeExp = teacher.getActive_exp();
//        if(!activeExp.contains(e_id)){
//            return;
//        }
//        int index = activeExp.lastIndexOf(e_id);
//        if(activeExp.charAt(index+e_id.length())=='-'){
//            return;
//        }

        Experiment experiment = expInfoDao.queryExperiment(e_id).get(0);
        String refPath = experiment.getRef_path();
        downLoadFileService.downloadRefCode(refPath, response);
    }

    /**
     * 参考代码下载验证
     * @param s_id 学生id
     * @param e_id 实验id
     * @return 验证结果
     */
    @RequestMapping(value="/download/referenceCode/check", method= RequestMethod.GET)
    public Result downloadRefFile(String s_id,String e_id){
        Student student = studentService.getStudent(s_id);
        Teacher teacher = teacherService.getTeacher(student.getTeacher());
        String activeExp = teacher.getActive_exp();
        if(!activeExp.contains(e_id)){
            return ResultHelper.newFailureResult("");
        }
        int index = activeExp.lastIndexOf(e_id);
        if(activeExp.charAt(index+e_id.length())=='-') {
            return ResultHelper.newFailureResult("");
        }

        return ResultHelper.newSuccessResult("");
    }


}
