package my.app.platform.service.File;

import my.app.platform.domain.Student;
import my.app.platform.repository.mapper.student.IStudentInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author 夏之阳
 * 创建时间：2016-03-27 16:34
 * 创建说明：上传文件服务
 */

@Service
public class UploadFileService {
    @Value("${upload.file.path}")
    private String path;

    @Autowired
    IStudentInfoDao studentInfoDao;

    /**
     * 教师上传学生列表
     * @param file 学生名单
     * @param userId 教师id
     * @return 是否上传成功
     */
    public String uploadStudentListService(MultipartFile file, String userId){
        String prefix = "temp/";
        if (!file.isEmpty()) {
            try {
                String folderPath = path + prefix;

                //如果路径不存在，则创建
                File newFile = new File(folderPath);
                if(!newFile.isDirectory()){
                    newFile.mkdirs();
                }

                String fileName = file.getOriginalFilename();
                Date date = new Date();
                DateFormat format = new SimpleDateFormat("yyyyMMdd");
                String time = format.format(date);
                int index = fileName.lastIndexOf(".");
                String subString = fileName.substring(0, index);
                String newFileName = subString + "_" + time + fileName.substring(index);

                String filePath = folderPath + newFileName;
                file.transferTo(new File(filePath));
                return filePath;
            } catch (Exception e) {
                return "";
            }
        } else {
            return "";
        }
    }

    /**
     * 管理员上传实验指导书
     * @param file 实验指导书文件
     * @return 是否上传成功
     */
    public String uploadExpGuideService(MultipartFile file){
        if (!file.isEmpty()) {
            try {
                String folderPath = path + "exp/";

                //如果路径不存在，则创建
                File newFile = new File(folderPath);
                if(!newFile.isDirectory()){
                    newFile.mkdirs();
                }

                String fileName = file.getOriginalFilename();
                String filePath = folderPath + fileName;
                file.transferTo(new File(filePath));
                return filePath;
            } catch (Exception e) {
                return "";
            }
        } else {
            return "";
        }
    }

    /**
     * 管理员上传实验参考代码
     * @param file 参考代码
     * @return 是否上传成功
     */
    public String uploadExpRefService(MultipartFile file){
        if (!file.isEmpty()) {
            try {
                String folderPath = path + "ref/";

                //如果路径不存在，则创建
                File newFile = new File(folderPath);
                if(!newFile.isDirectory()){
                    newFile.mkdirs();
                }

                String fileName = file.getOriginalFilename();
                String filePath = folderPath + fileName;
                file.transferTo(new File(filePath));
                return filePath;
            } catch (Exception e) {
                return "";
            }
        } else {
            return "";
        }
    }

    public String uploadStudentReport(MultipartFile file,String s_id){
        if (!file.isEmpty()) {
            try {
                List<Student> studentList = studentInfoDao.queryStudentInfo(s_id);
                if(studentList.size() == 0){
                    return "";
                }
                Student student = studentList.get(0);
                String folderPath = path + student.getTeacher() + "/" + student.getS_login_name() + "/";

                //如果路径不存在，则创建
                File newFile = new File(folderPath);
                if(!newFile.isDirectory()){
                    newFile.mkdirs();
                }

                String fileName = file.getOriginalFilename();
                String filePath = folderPath + fileName;
                file.transferTo(new File(filePath));

                student.setReport_path(fileName);
                student.setReport_status("已提交");
                studentInfoDao.updateStudent(student);

                return filePath;
            } catch (Exception e) {
                return "";
            }
        } else {
            return "";
        }
    }
}
