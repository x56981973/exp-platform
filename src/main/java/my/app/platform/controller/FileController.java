package my.app.platform.controller;

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
public class FileController {
    @Autowired
    UploadFileService uploadFileService;

    @Autowired
    DownLoadFileService downLoadFileService;

    @RequestMapping(value="/upload", method= RequestMethod.POST)
    public Result handleFileUpload(MultipartFile file, String userId){
        return ResultHelper.newSuccessResult(uploadFileService.uploadStudentListService(file, userId));
    }

    @RequestMapping(value="/download", method= RequestMethod.POST)
    public void downloadFile(String fileName,HttpServletResponse response){
        downLoadFileService.downloadFile(fileName, response);
    }
}
