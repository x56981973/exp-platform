package my.app.platform.controller.admin;

import my.app.platform.domain.ExpClass;
import my.app.platform.domain.ExpType;
import my.app.platform.domain.Experiment;
import my.app.platform.domain.OptionRecord;
import my.app.platform.domain.model.MExpType;
import my.app.platform.domain.model.MExperiment;
import my.app.platform.repository.mapper.experiment.IExpInfoDao;
import my.app.platform.repository.mapper.log.ILogInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author 夏之阳
 * 创建时间：2016-05-02 14:12
 * 创建说明：
 */

@Controller
public class adminExpController {
    @Autowired
    HttpSession session;

    @Autowired
    ILogInfoDao logInfoDao;

    @Autowired
    IExpInfoDao expInfoDao;

    OptionRecord optionRecord = new OptionRecord();

    @RequestMapping(value = "/admin/exp")
    public String experiment(Model model){
        //Get t_name
        String t_name = session.getAttribute("t_name").toString();
        model.addAttribute("t_name",t_name);

        //Get Exp info
        List<MExperiment> experimentList = expInfoDao.queryAllExp();
        model.addAttribute("exp",experimentList);

        List<MExpType> expTypeList = expInfoDao.queryAllExpType();
        model.addAttribute("exp_type",expTypeList);

        List<ExpClass> expClassList = expInfoDao.queryAllExpClass();
        model.addAttribute("exp_class",expClassList);

        return "/admin/experiment";
    }

    @RequestMapping(value = "/admin/exp/delete")
    @ResponseBody
    public String expDeleteHandler(String id){
        if(expInfoDao.deleteExperiment(id) != 0) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String date = df.format(new Date());
            optionRecord.setDate(date);
            optionRecord.setUid(session.getAttribute("t_id").toString());
            optionRecord.setOption_class("exp");
            optionRecord.setOption_detail("删除实验：" + id);
            logInfoDao.insertOptionRecord(optionRecord);

            return "{\"error\":\"0\",\"msg\":\"删除成功\"}";
        }else{
            return "{\"error\":\"1\",\"msg\":\"删除失败\"}";
        }
    }

    @RequestMapping(value = "/admin/expType/delete")
    @ResponseBody
    public String typeDeleteHandler(String id){
        if(expInfoDao.deleteExpType(id) != 0) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String date = df.format(new Date());
            optionRecord.setDate(date);
            optionRecord.setUid(session.getAttribute("t_id").toString());
            optionRecord.setOption_class("exp");
            optionRecord.setOption_detail("删除实验小类：" + id);
            logInfoDao.insertOptionRecord(optionRecord);

            return "{\"error\":\"0\",\"msg\":\"删除成功\"}";
        }else{
            return "{\"error\":\"1\",\"msg\":\"删除失败\"}";
        }
    }

    @RequestMapping(value = "/admin/expClass/delete")
    @ResponseBody
    public String classDeleteHandler(String id){
        if(expInfoDao.deleteExpClass(id) != 0) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String date = df.format(new Date());
            optionRecord.setDate(date);
            optionRecord.setUid(session.getAttribute("t_id").toString());
            optionRecord.setOption_class("exp");
            optionRecord.setOption_detail("删除实验大类：" + id);
            logInfoDao.insertOptionRecord(optionRecord);

            return "{\"error\":\"0\",\"msg\":\"删除成功\"}";
        }else{
            return "{\"error\":\"1\",\"msg\":\"删除失败\"}";
        }
    }

    @RequestMapping(value = "/admin/expClass/add")
    @ResponseBody
    public String classAddHandler(ExpClass expClass){
        String class_id = String.valueOf(expClass.getClass_id());
        List<ExpClass> expClasses = expInfoDao.queryExpClass(class_id);
        if(expClasses.size() != 0){
            return "{\"error\":\"1\",\"msg\":\"编号已存在\"}";
        }

        if(expInfoDao.insertExpClass(expClass) != 0) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String date = df.format(new Date());
            optionRecord.setDate(date);
            optionRecord.setUid(session.getAttribute("t_id").toString());
            optionRecord.setOption_class("exp");
            optionRecord.setOption_detail("添加实验大类：" + class_id);
            logInfoDao.insertOptionRecord(optionRecord);

            return "{\"error\":\"0\",\"msg\":\"添加成功\"}";
        }else{
            return "{\"error\":\"1\",\"msg\":\"添加失败\"}";
        }
    }

    @RequestMapping(value = "/admin/expType/add")
    @ResponseBody
    public String typeAddHandler(ExpType expType,String class_info){
        String class_id = class_info.split(" ")[0];
        expType.setClass_id(class_id);

        String type_id = String.valueOf(expType.getType_id());
        List<ExpType> expTypes = expInfoDao.queryExpType(type_id);
        if(expTypes.size() != 0){
            return "{\"error\":\"1\",\"msg\":\"编号已存在\"}";
        }

        if(expInfoDao.insertExpType(expType) != 0) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String date = df.format(new Date());
            optionRecord.setDate(date);
            optionRecord.setUid(session.getAttribute("t_id").toString());
            optionRecord.setOption_class("exp");
            optionRecord.setOption_detail("添加实验小类：" + type_id);
            logInfoDao.insertOptionRecord(optionRecord);

            return "{\"error\":\"0\",\"msg\":\"添加成功\"}";
        }else{
            return "{\"error\":\"1\",\"msg\":\"添加失败\"}";
        }
    }

    @RequestMapping(value = "/admin/exp/insert")
    @ResponseBody
    public String expAddHandler(Experiment experiment,String class_info,String type_info){
        String class_id = class_info.split(" ")[0];
        experiment.setClass_id(class_id);

        String type_id = type_info.split(" ")[0];
        experiment.setType_id(type_id);

        String exp_id = experiment.getE_id();
        List<Experiment> experiments = expInfoDao.queryExperiment(exp_id);
        if(experiments.size() != 0){
            return "{\"error\":\"1\",\"msg\":\"编号已存在\"}";
        }

        if(expInfoDao.insertExperiment(experiment) != 0) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String date = df.format(new Date());
            optionRecord.setDate(date);
            optionRecord.setUid(session.getAttribute("t_id").toString());
            optionRecord.setOption_class("exp");
            optionRecord.setOption_detail("添加实验："+ exp_id);
            logInfoDao.insertOptionRecord(optionRecord);

            return "{\"error\":\"0\",\"msg\":\"添加成功\"}";
        }else{
            return "{\"error\":\"1\",\"msg\":\"添加失败\"}";
        }
    }

    @RequestMapping(value = "/admin/exp/add")
    public String expAddHandler(Model model){
        //Get t_name
        String t_name = session.getAttribute("t_name").toString();
        model.addAttribute("t_name", t_name);

        //Get Exp info
        List<MExperiment> experimentList = expInfoDao.queryAllExp();
        model.addAttribute("exp", experimentList);

        List<MExpType> expTypeList = expInfoDao.queryAllExpType();
        model.addAttribute("exp_type",expTypeList);

        List<ExpClass> expClassList = expInfoDao.queryAllExpClass();
        model.addAttribute("exp_class",expClassList);

        return "/admin/newExp";
    }
}
