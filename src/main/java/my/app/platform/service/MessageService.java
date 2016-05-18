package my.app.platform.service;

import my.app.platform.domain.Message;
import my.app.platform.domain.Student;
import my.app.platform.repository.mapper.message.IMessageInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author 夏之阳
 * 创建时间：2016-05-17 01:53
 * 创建说明：消息服务
 */

@Service
public class MessageService {
    @Autowired
    IMessageInfoDao messageDao;

    @Autowired
    StudentService studentService;

    @Autowired
    TeacherService teacherService;

    public int insertMessage(String s_id, String e_id, String text){
        Student student = studentService.getStudent(s_id);
        String t_id = student.getTeacher();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
        String date = df.format(new Date());

        Message message = new Message();
        message.setS_id(s_id);
        message.setDate(date);
        message.setE_id(e_id);
        message.setT_id(t_id);
        message.setText(text);
        message.setIs_read("0"); //默认未读

        return messageDao.insertMessage(message);
    }

    public Message queryMessage(String t_id){
        List<Message> messageList = messageDao.queryMessage(t_id);
        if(messageList.size() != 0){
            return messageList.get(0);
        } else {
            return null;
        }
    }

}
