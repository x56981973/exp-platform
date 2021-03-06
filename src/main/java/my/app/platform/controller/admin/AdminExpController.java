package my.app.platform.controller.admin;

import my.app.platform.domain.ExpClass;
import my.app.platform.domain.ExpType;
import my.app.platform.domain.Experiment;
import my.app.platform.domain.OptionRecord;
import my.app.platform.domain.model.MExpType;
import my.app.platform.domain.model.MExperiment;
import my.app.platform.repository.mapper.experiment.IExpInfoDao;
import my.app.platform.repository.mapper.log.ILogInfoDao;
import my.app.platform.service.File.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 夏之阳
 * 创建时间：2016-05-02 14:12
 * 创建说明：管理员实验管理
 */

@Controller
@RequestMapping(value = "/admin")
public class AdminExpController {
    @Autowired
    HttpSession session;

    @Autowired
    ILogInfoDao logInfoDao;

    @Autowired
    IExpInfoDao expInfoDao;

    @Autowired
    UploadFileService uploadFileService;

    //实验页面
    @RequestMapping(value = "/exp")
    public String experiment(Model model){
        String name = session.getAttribute("name").toString();
        model.addAttribute("name",name);

        //Get Exp info
        List<MExperiment> experimentList = expInfoDao.queryAllMExp();
        model.addAttribute("exp",experimentList);

        List<MExpType> expTypeList = expInfoDao.queryAllMExpType();
        model.addAttribute("exp_type",expTypeList);

        List<ExpClass> expClassList = expInfoDao.queryAllExpClass();
        model.addAttribute("exp_class",expClassList);

        return "/admin/experiment";
    }

    //删除实验
    @RequestMapping(value = "/exp/delete")
    @ResponseBody
    public String expDeleteHandler(String id){
        if(expInfoDao.deleteExperiment(id) != 0) {
            String record = "删除实验：" + id;
            setOptionRecord(record);

            return "{\"error\":\"0\",\"msg\":\"删除成功\"}";
        }else{
            return "{\"error\":\"1\",\"msg\":\"删除失败\"}";
        }
    }

    //删除实验类型
    @RequestMapping(value = "/expType/delete")
    @ResponseBody
    public String typeDeleteHandler(String id){
        if(expInfoDao.deleteExpType(id) != 0) {
            String record = "删除实验小类：" + id;
            setOptionRecord(record);

            return "{\"error\":\"0\",\"msg\":\"删除成功\"}";
        }else{
            return "{\"error\":\"1\",\"msg\":\"删除失败\"}";
        }
    }

    //删除实验类别
    @RequestMapping(value = "/expClass/delete")
    @ResponseBody
    public String classDeleteHandler(String id){
        if(expInfoDao.deleteExpClass(id) != 0) {
            String record = "删除实验大类：" + id;
            setOptionRecord(record);

            return "{\"error\":\"0\",\"msg\":\"删除成功\"}";
        }else{
            return "{\"error\":\"1\",\"msg\":\"删除失败\"}";
        }
    }

    //新增实验类别
    @RequestMapping(value = "/expClass/add")
    @ResponseBody
    public String classAddHandler(ExpClass expClass){
        String class_id = String.valueOf(expClass.getClass_id());
        List<ExpClass> expClasses = expInfoDao.queryExpClass(class_id);
        if(expClasses.size() != 0){
            return "{\"error\":\"1\",\"msg\":\"编号已存在\"}";
        }

        if(expInfoDao.insertExpClass(expClass) != 0) {
            String record = "添加实验大类：" + class_id;
            setOptionRecord(record);

            return "{\"error\":\"0\",\"msg\":\"添加成功\"}";
        }else{
            return "{\"error\":\"1\",\"msg\":\"添加失败\"}";
        }
    }

    //编辑实验类别
    @RequestMapping(value = "/expClass/edit")
    @ResponseBody
    public String classEditHandler(ExpClass expClass){
        String class_id = String.valueOf(expClass.getClass_id());
        List<ExpClass> expClasses = expInfoDao.queryExpClass(class_id);
        if(expClasses.size() == 0){
            return "{\"error\":\"1\",\"msg\":\"此编号不存在\"}";
        }

        if(expInfoDao.updateExpClass(expClass) != 0) {
            String record = "编辑实验大类：" + class_id;
            setOptionRecord(record);

            return "{\"error\":\"0\",\"msg\":\"编辑成功\"}";
        }else{
            return "{\"error\":\"1\",\"msg\":\"编辑失败\"}";
        }
    }

    //新增实验类型
    @RequestMapping(value = "/expType/add")
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
            String record = "添加实验小类：" + type_id;
            setOptionRecord(record);

            return "{\"error\":\"0\",\"msg\":\"添加成功\"}";
        }else{
            return "{\"error\":\"1\",\"msg\":\"添加失败\"}";
        }
    }

    //编辑实验类型
    @RequestMapping(value = "/expType/edit")
    @ResponseBody
    public String typeEditHandler(ExpType expType,String class_info){
        String class_id = class_info.split(" ")[0];
        expType.setClass_id(class_id);

        String type_id = String.valueOf(expType.getType_id());
        List<ExpType> expTypes = expInfoDao.queryExpType(type_id);
        if(expTypes.size() == 0){
            return "{\"error\":\"1\",\"msg\":\"此编号不存在\"}";
        }

        if(expInfoDao.updateExpType(expType) != 0) {
            String record = "编辑实验小类：" + type_id;
            setOptionRecord(record);

            return "{\"error\":\"0\",\"msg\":\"编辑成功\"}";
        }else{
            return "{\"error\":\"1\",\"msg\":\"编辑失败\"}";
        }
    }

    //新增实验
    @RequestMapping(value = "/exp/insert")
    @ResponseBody
    public String expAddHandler(Experiment experiment,String class_info,String type_info,MultipartFile doc,MultipartFile ref){
        String class_id = class_info.split(" ")[0];
        experiment.setClass_id(class_id);

        String type_id = type_info.split(" ")[0];
        experiment.setType_id(type_id);

        String exp_id = experiment.getE_id();
        Pattern pattern = Pattern.compile("^[0-9]+$");
        Matcher isNum = pattern.matcher(exp_id);
        if( !isNum.matches() ){
            return "{\"error\":\"1\",\"msg\":\"编号格式错误，必须为数字\"}";
        }

        experiment.setRef_path("");
        experiment.setE_srcPath("");

        List<Experiment> experiments = expInfoDao.queryExperiment(exp_id);
        if(experiments.size() != 0){
            return "{\"error\":\"1\",\"msg\":\"编号已存在\"}";
        }

        if(expInfoDao.insertExperiment(experiment) != 0) {
            String record = "添加实验：" + exp_id;
            setOptionRecord(record);
        }else{
            return "{\"error\":\"1\",\"msg\":\"添加失败\"}";
        }

        if(doc != null){
            insertDoc(doc, exp_id);
        }
        if(ref != null){
            insertRef(ref, exp_id);
        }
        return "{\"error\":\"0\",\"msg\":\"添加成功\"}";
    }

    //新增页面
    @RequestMapping(value = "/exp/add")
    public String expAddHandler(Model model){

        //Get Exp info
        List<MExperiment> experimentList = expInfoDao.queryAllMExp();
        model.addAttribute("exp", experimentList);

        List<MExpType> expTypeList = expInfoDao.queryAllMExpType();
        model.addAttribute("exp_type",expTypeList);

        List<ExpClass> expClassList = expInfoDao.queryAllExpClass();
        model.addAttribute("exp_class",expClassList);

        return "/admin/newExp";
    }

    //编辑实验页面
    @RequestMapping(value = "/exp/edit/{e_id}")
    public String expEditHandler(@PathVariable String e_id,Model model){

        List<Experiment> experiments = expInfoDao.queryExperiment(e_id);
        if(experiments == null){
            return "redirect:/admin/newExp";
        }
        Experiment experiment = experiments.get(0);
        model.addAttribute("experiment", experiment);

        List<MExpType> expTypeList = expInfoDao.queryAllMExpType();
        model.addAttribute("exp_type",expTypeList);

        List<ExpClass> expClassList = expInfoDao.queryAllExpClass();
        model.addAttribute("exp_class",expClassList);

        return "/admin/editExp";
    }

    //编辑实验
    @RequestMapping(value = "/exp/edit")
    @ResponseBody
    public String editExp(Experiment experiment,String class_info,String type_info){
        String class_id = class_info.split(" ")[0];
        experiment.setClass_id(class_id);

        String type_id = type_info.split(" ")[0];
        experiment.setType_id(type_id);

        String exp_id = experiment.getE_id();
        List<Experiment> experiments = expInfoDao.queryExperiment(exp_id);
        if(experiments.size() == 0){
            return "{\"error\":\"1\",\"msg\":\"编号不存在\"}";
        }

        if(expInfoDao.updateExperiment(experiment) != 0) {
            String record = "修改实验："+ exp_id;
            setOptionRecord(record);
            return "{\"error\":\"0\",\"msg\":\"修改成功\"}";
        }else{
            return "{\"error\":\"1\",\"msg\":\"修改失败\"}";
        }
    }

    //导入实验指南
    @RequestMapping(value = "/exp/insertGuide")
    @ResponseBody
    public String insertGuide(MultipartFile guide,String e_id){
        List<Experiment> experiments = expInfoDao.queryExperiment(e_id);
        if(experiments.size() == 0){
            return "{\"error\":\"1\",\"msg\":\"查无此实验编号\"}";
        }

        if(insertDoc(guide, e_id) == 1){
            return "{\"error\":\"0\",\"msg\":\"文档路径修改成功\"}";
        } else {
            return "{\"error\":\"1\",\"msg\":\"上传文件失败\"}";
        }
    }

    //导入实验参考代码
    @RequestMapping(value = "/exp/insertRef")
    @ResponseBody
    public String insertReference(MultipartFile ref,String e_id){
        List<Experiment> experiments = expInfoDao.queryExperiment(e_id);
        if(experiments.size() == 0){
            return "{\"error\":\"1\",\"msg\":\"查无此实验编号\"}";
        }

        if(insertRef(ref, e_id) == 1){
            return "{\"error\":\"0\",\"msg\":\"文档路径修改成功\"}";
        } else {
            return "{\"error\":\"1\",\"msg\":\"上传文件失败\"}";
        }
    }

    //导入实验文档
    public int insertDoc(MultipartFile doc, String e_id){
        String result = uploadFileService.uploadExpGuideService(doc);
        if("".equals(result)){
            return 0;
        }

        String filename = doc.getOriginalFilename();
        if(expInfoDao.updateExpSrc(filename, e_id) > 0){
            return 1;
        }else{
            return 0;
        }
    }

    //导入实验参考代码
    public int insertRef(MultipartFile ref,String e_id){
        String result = uploadFileService.uploadExpRefService(ref);
        if("".equals(result)){
            return 0;
        }

        String filename = ref.getOriginalFilename();
        if(expInfoDao.updateRefPath(filename, e_id) > 0){
            return 1;
        }else{
            return 0;
        }
    }

    private int setOptionRecord(String record){
        OptionRecord optionRecord = new OptionRecord();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String date = df.format(new Date());
        optionRecord.setDate(date);
        optionRecord.setUid(session.getAttribute("uid").toString());
        optionRecord.setOption_class("exp");
        optionRecord.setOption_detail(record);

        return logInfoDao.insertOptionRecord(optionRecord);
    }
}
