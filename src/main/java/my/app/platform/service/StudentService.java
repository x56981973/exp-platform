package my.app.platform.service;

/**
 * @author 夏之阳
 * 创建时间：2016-03-19 21:33
 * 创建说明：学生信息服务
 */

import my.app.platform.domain.Student;
import my.app.platform.repository.mapper.student.IStudentInfoDao;
import my.app.platform.tool.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    IStudentInfoDao studentInfoDao;

    /**
     * 从Excel中导出学生名单，插入到数据库中
     * @param path excel文件路径
     * @return 插入条数
     * @throws IOException
     */
    public int insertStudentList(String path) throws IOException {
        List<List<String>> nameList;

        int index = path.lastIndexOf(".");
        if("xlsx".equals(path.substring(index+1))) {
            nameList = ExcelUtil.readXlsx(path);
        } else if ("xls".equals(path.substring(index+1))) {
            nameList = ExcelUtil.readXls(path);
        } else {
            return 0;
        }

        int count = 0;
        for(List<String> person : nameList){
            if (person.size() != 3) return 0;
            Student student = new Student();
            student.setS_login_name(person.get(0));
            student.setS_password(person.get(0)); //密码默认与登陆名一致
            student.setS_name(person.get(1));
            student.setS_grade(person.get(2));
            student.setS_score("0"); //成绩默认为0
            student.setTeacher("yiping"); //教师由参数传递，此处为暂用

            count += studentInfoDao.insertStudentInfo(student);
        }
        return count;
    }
}
