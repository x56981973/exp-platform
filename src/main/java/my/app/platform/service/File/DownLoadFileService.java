package my.app.platform.service.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author 夏之阳
 * 创建时间：2016-03-27 16:35
 * 创建说明：下载文件服务
 */

@Service
public class DownLoadFileService {
    @Value("${download.file.path}")
    private String folderPath;

    public void downloadFile(String fileName,HttpServletResponse response){
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition","attachment;fileName="+fileName);

        try {
            File file=new File(folderPath + fileName);
            InputStream inputStream=new FileInputStream(file);
            OutputStream os=response.getOutputStream();
            byte[] b=new byte[1024];
            int length;
            while((length=inputStream.read(b))>0){
                os.write(b,0,length);
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void downloadRefCode(String fileName,HttpServletResponse response){
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition","attachment;fileName="+fileName);

        try {
            File file=new File(folderPath + "ref/" + fileName);
            InputStream inputStream=new FileInputStream(file);
            OutputStream os=response.getOutputStream();
            byte[] b=new byte[1024];
            int length;
            while((length=inputStream.read(b))>0){
                os.write(b,0,length);
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
