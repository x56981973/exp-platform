package my.app.platform.controller.client;

import my.app.framework.web.Result;
import my.app.framework.web.ResultHelper;
import my.app.platform.service.File.DownLoadFileService;
import my.app.platform.service.File.UploadFileService;
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
@RequestMapping(value="/client")
public class FileController {
    @Autowired
    UploadFileService uploadFileService;

    @Autowired
    DownLoadFileService downLoadFileService;

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

    @RequestMapping(value="/download", method= RequestMethod.POST)
    public void downloadFile(String fileName,HttpServletResponse response){
        downLoadFileService.downloadFile(fileName, response);
    }
}
