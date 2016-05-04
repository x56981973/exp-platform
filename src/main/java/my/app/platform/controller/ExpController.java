package my.app.platform.controller;

import my.app.framework.web.Result;
import my.app.framework.web.ResultHelper;
import my.app.platform.domain.ExpClass;
import my.app.platform.domain.ExpType;
import my.app.platform.domain.Experiment;
import my.app.platform.repository.mapper.experiment.IExpInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 夏之阳
 * 创建时间：2016-03-25 11:33
 * 创建说明：实验接口
 */

@RestController
@RequestMapping(value = "/client")
public class ExpController {
    @Autowired
    IExpInfoDao expInfoDao;

    @RequestMapping(value = "/exp/getList", method = RequestMethod.GET)
    public Result queryExperiment(){
        return ResultHelper.newSuccessResult(expInfoDao.queryAllExp());
    }

    /**
     * 新增实验
     * @param experiment 实验信息
     * @return 插入成功条数
     */
    @RequestMapping(value = "/exp/insert", method = RequestMethod.POST)
    public Result insertExperiment(@RequestBody Experiment experiment){
        return ResultHelper.newSuccessResult(expInfoDao.insertExperiment(experiment));
    }

    /**
     * 更新实验
     * @param experiment 实验信息
     * @return 更新成功条数
     */
    @RequestMapping(value = "/exp/update", method = RequestMethod.POST)
    public Result updateExperiment(@RequestBody Experiment experiment){
        return ResultHelper.newSuccessResult(expInfoDao.updateExperiment(experiment));
    }

    /**
     * 新增实验类型
     * @param expType 实验类型
     * @return 插入成功条数
     */
    @RequestMapping(value = "/expType/insert", method = RequestMethod.POST)
    public Result insertExpType(@RequestBody ExpType expType){
        return ResultHelper.newSuccessResult(expInfoDao.insertExpType(expType));
    }

    /**
     * 新增实验类别
     * @param expClass 实验类别
     * @return 插入成功条数
     */
    @RequestMapping(value = "/expClass/insert", method = RequestMethod.POST)
    public Result insertExpClass(@RequestBody ExpClass expClass){
        return ResultHelper.newSuccessResult(expInfoDao.insertExpClass(expClass));
    }

    /**
     * 插入实验列表
     * @param experimentList 实验列表
     * @return 插入成功条数
     */
    @RequestMapping(value = "/exp/insertList", method = RequestMethod.POST)
    public Result insertExperimentList(@RequestBody List<Experiment> experimentList){
        int count = 0;
        for(Experiment e : experimentList) {
            count += expInfoDao.insertExperiment(e);
        }
        return ResultHelper.newSuccessResult(count);
    }

    /**
     * 更新实验列表
     * @param experimentList 实验列表
     * @return 更新成功条数
     */
    @RequestMapping(value = "/exp/updateList", method = RequestMethod.POST)
    public Result updateExperimentList(@RequestBody List<Experiment> experimentList){
        int count = 0;
        for(Experiment e : experimentList) {
            count += expInfoDao.updateExperiment(e);
        }
        return ResultHelper.newSuccessResult(count);
    }

    /**
     * 插入实验类型列表
     * @param expTypeList 实验类型列表
     * @return 插入成功条数
     */
    @RequestMapping(value = "/expType/insertList", method = RequestMethod.POST)
    public Result insertExpTypeList(@RequestBody List<ExpType> expTypeList){
        int count = 0;
        for(ExpType e : expTypeList) {
            count += expInfoDao.insertExpType(e);
        }
        return ResultHelper.newSuccessResult(count);
    }

    /**
     * 插入实验类别列表
     * @param expClassList 实验类别列表
     * @return 插入成功条数
     */
    @RequestMapping(value = "/expClass/insertList", method = RequestMethod.POST)
    public Result insertExpClassList(@RequestBody List<ExpClass> expClassList){
        int count = 0;
        for(ExpClass e : expClassList) {
            count += expInfoDao.insertExpClass(e);
        }
        return ResultHelper.newSuccessResult(count);
    }

}
