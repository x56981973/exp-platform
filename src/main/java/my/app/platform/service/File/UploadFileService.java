package my.app.platform.service.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author 夏之阳
 * 创建时间：2016-03-27 16:34
 * 创建说明：上传文件服务
 */

@Service
public class UploadFileService {
    @Value("${upload.file.path}")
    private String folderPath;

    public Boolean uploadService(MultipartFile file){
        if (!file.isEmpty()) {
            try {
                String filePath = folderPath + file.getOriginalFilename(); //File Path.
                file.transferTo(new File(filePath));
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }
}
