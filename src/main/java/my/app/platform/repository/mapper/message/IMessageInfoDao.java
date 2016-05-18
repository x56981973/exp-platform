package my.app.platform.repository.mapper.message;

import my.app.platform.domain.Message;

import java.util.List;

/**
 * @author 夏之阳
 * 创建时间：2016-05-17 01:39
 * 创建说明：消息查询
 */

public interface IMessageInfoDao {
    /**
     * 根据教师查询消息
     * @param t_id 教师id
     * @return 查询结果
     */
    List<Message> queryMessage(String t_id);

    /**
     * 根据教师查询未读消息
     * @param t_id 教师id
     * @return 查询结果
     */
    List<Message> queryNotReadMessage(String t_id);

    /**
     * 插入消息
     * @param message 消息
     * @return 插入条数
     */
    int insertMessage(Message message);

    /**
     * 更新消息已读
     * @param id 消息id
     * @return 更新条数
     */
    int updateIsRead(String id);
}
