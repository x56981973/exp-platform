package my.app.platform.repository.mapper.experiment;

import my.app.platform.domain.ExpClass;
import my.app.platform.domain.ExpType;
import my.app.platform.domain.Experiment;

import java.util.List;

/**
 * @author 夏之阳
 * 创建时间：2016-03-21 23:10
 * 创建说明：实验查询接口
 */

public interface IExpInfoDao {
    /**
     * 获取所有实验列表
     * @return 实验列表
     */
    List<Experiment> queryAllExp();

    /**
     * 插入实验信息
     * @param experiment 实验信息
     * @return 插入成功条数
     */
    int insertExperiment(Experiment experiment);

    /**
     * 插入实验类别
     * @param expClass 实验类别
     * @return 插入成功条数
     */
    int insertExpClass(ExpClass expClass);

    /**
     * 插入实验类型
     * @param expType 实验类型
     * @return 插入成功条数
     */
    int insertExpType(ExpType expType);

    /**
     * 删除实验信息
     * @param e_id 实验id
     * @return 删除成功条数
     */
    int deleteExperiment(String e_id);

    /**
     * 删除实验类别
     * @param class_id 实验类别id
     * @return 删除成功条数
     */
    int deleteExpClass(String class_id);

    /**
     * 删除实验类型
     * @param type_id 实验类型id
     * @return 删除成功条数
     */
    int deleteExpType(String type_id);

    /**
     * 查询实验信息
     * @param e_id 实验id
     * @return 实验信息
     */
    List<Experiment> queryExperiment(String e_id);

    /**
     * 查询实验类别
     * @param class_id 实验类别id
     * @return 实验类别
     */
    List<ExpClass> queryExpClass(String class_id);

    /**
     * 查询实验类型
     * @param type_id 实验类型id
     * @return 实验类型
     */
    List<ExpType> queryExpType(String type_id);

    /**
     * 修改实验信息
     * @param experiment 实验信息
     * @return 更新成功条数
     */
    int updateExperiment(Experiment experiment);

    /**
     * 修改实验类别
     * @param expClass 实验类别
     * @return 更新成功条数
     */
    int updateExpClass(ExpClass expClass);

    /**
     * 修改实验类型
     * @param expType 实验类型
     * @return 更新成功条数
     */
    int updateExpType(ExpType expType);
}
