package my.app.platform.controller.client;

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
    public boolean uploadReportHandler(MultipartFile file, String s_id){
        uploadFileService.uploadStudentReport(file, s_id);
        return true;
    }

    @RequestMapping(value="/download", method= RequestMethod.POST)
    public void downloadFile(String fileName,HttpServletResponse response){
        downLoadFileService.downloadFile(fileName, response);
    }
}
