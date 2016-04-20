package my.app.platform.service;

import my.app.platform.domain.ExpClass;
import my.app.platform.domain.model.MExpType;
import my.app.platform.domain.model.MExperiment;
import my.app.platform.repository.mapper.experiment.IExpInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 夏之阳
 * 创建时间：2016-04-10 14:59
 * 创建说明：实验信息服务
 */

@Service
public class ExpService {
    @Autowired
    private IExpInfoDao expInfoDao;

    public List<ExpClass> getExpClass(){
        return  expInfoDao.queryAllExpClass();
    }

    public List<MExpType> getExpType(){
        return  expInfoDao.queryAllExpType();
    }

    public List<MExperiment> getExp(){
        return  expInfoDao.queryAllExp();
    }
}
