package com.example.apidemo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


//@RestController
//@RequestMapping("/api")
//@Component
public class Mainthread {
/*
    @Autowired
    getJson getJson;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Scheduled(fixedRate = 1800000)
    //@RequestMapping("/comobj")
    public String loginBusiness() {
        String jsonText = getJson.getJson("method=loginBusiness&businessUsername=cmadr&businessPassword=123456");
        JSONObject jsonObject = JSONObject.parseObject(jsonText);
        if(jsonObject == null){
            System.out.println("json返回为空");
            return "json返回为空";
        }
        System.out.println("json获取成功!");
        String comobj = jsonObject.getString("comobj");
        System.out.println("comobj获取成功" + comobj);
        JSONObject comobjson = JSON.parseObject(comobj);

        System.out.println("combojson.companyName=" + comobjson.getString("companyName"));
        
        String sql = "insert into comobj(address,  adminName,  areaId,  companyName,  companyType,  contact,  email,  farmArea,  geom,  id,  internalId,  internalName,  mobile,  postcode,  projectInfo,  province,  remark,  state,  telephone,  ysAppkey,  ysPassword,  ysSecket,  ysToken,  ysUsername)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update("truncate comobj");
        jdbcTemplate.update(sql, comobjson.getString("address"), comobjson.getString("adminName"), comobjson.getString("areaId"), comobjson.getString("companyName"), comobjson.getInteger("companyType"), comobjson.getString("contact"), comobjson.getString("email"), comobjson.getDouble("farmArea"), comobjson.getString("geom"), comobjson.getInteger("id"), comobjson.getInteger("internalId"), comobjson.getString("internalName"), comobjson.getString("mobile"), comobjson.getString("postcode"), comobjson.getString("projectInfo"), comobjson.getString("province"), comobjson.getString("remark"), comobjson.getInteger("state"), comobjson.getString("telephone"), comobjson.getString("ysAppkey"), comobjson.getString("ysPassword"), comobjson.getString("ysSecket"), comobjson.getString("ysToken"), comobjson.getString("ysUsername"));

        return "前台用户登陆接口更新成功！";
    }

    @Scheduled(fixedRate = 1800000)
    //@RequestMapping("/detaildata")
    public String getRealTimeDeviceDetailData() {
        String jsonText = getJson.getJson("username=cmadr&method=getRealTimeDeviceDetailData&addr=44041330&sessionkey=82BA0712C75800201C177AD425107EA2");
        JSONObject jsonObject = JSONObject.parseObject(jsonText);
        System.out.println("json获取成功-gatherTime=" + jsonObject.getTimestamp("gatherTime"));
        //
        String sql = "insert into detaildata(custid,Addr,custname,gatherTime,workUnitName) values(?,?,?,?,?)";
        jdbcTemplate.update("truncate detaildata");
        jdbcTemplate.update(sql, jsonObject.getInteger("custid"), jsonObject.getString("Addr"), jsonObject.getString("custname"), jsonObject.getTimestamp("gatherTime"), jsonObject.getString("workUnitName"));
        String hikvalue_sql2 = "insert into detaildata_hikvalue(custid,videosn,isdome,ysAppkey,accessToken) values(?,?,?,?,?)";
        JSONObject hikvalue = JSONObject.parseObject(jsonObject.getString("hikValue"));
        jdbcTemplate.update("truncate detaildata_hikvalue");
        jdbcTemplate.update(hikvalue_sql2, jsonObject.getInteger("custid"), hikvalue.getString("videosn"), hikvalue.getInteger("isdome"), hikvalue.getString("ysAppkey"), hikvalue.getString("accessToken"));
        System.out.println("hikValue数据更新成功！");

        String imageValue_sql = "insert into detaildata_imagevalue(custid,gatherTime,imagePath,imagePathWhole) values (?,?,?,?)";
        JSONArray imageValue_array = JSONObject.parseArray(jsonObject.getString("imageValue"));
        for (int i = 0; i < imageValue_array.size(); i++) {
            JSONObject imageValue_json = imageValue_array.getJSONObject(i);
            jdbcTemplate.update("truncate detaildata_imagevalue");
            jdbcTemplate.update(imageValue_sql, jsonObject.getInteger("custid"), imageValue_json.getTimestamp("gatherTime"), imageValue_json.getString("imagePath"), imageValue_json.getString("imagePathWhole"));

        }

        System.out.println("imageValue数据更新成功！");
        String sensorvalue_sql = "insert into detaildata_sensorvalue(custid,Chx,DisplayName,workUnitName,channelType,chValue,Unit,Flag,man) values (?,?,?,?,?,?,?,?,?)";
        JSONArray sensorvalue_array = JSONObject.parseArray(jsonObject.getString("sensorValue"));
        for (int j = 0; j < sensorvalue_array.size(); j++) {
            JSONObject sensorvalue_json = sensorvalue_array.getJSONObject(j);
            jdbcTemplate.update("truncate detaildata_sensorvalue");
            jdbcTemplate.update(sensorvalue_sql, jsonObject.getInteger("custid"), sensorvalue_json.getString("Chx"), sensorvalue_json.getString("DisplayName"), sensorvalue_json.getString("workUnitName"), sensorvalue_json.getInteger("channelType"), sensorvalue_json.getString("chValue"), sensorvalue_json.getString("Unit"), sensorvalue_json.getString("Flag"), sensorvalue_json.getString("man"));
        }

        System.out.println("sensorValue数据更新成功！");
        return "设备详细数据获取成功！";
    }

    @Scheduled(fixedRate = 1800000)
    //获取设备历史数据
    //@RequestMapping("/datafromtime")
    public String getDeviceSensorDataFromTime() {
        String jsonText = getJson.getJson("addr=44041331&method=getDeviceSensorDataFromTime&sessionkey=82BA0712C75800201C177AD425107EA2&startTime=2016-04-09%2004:48:33&channelNumber=4");
        JSONObject jsonObject = JSONObject.parseObject(jsonText);

        String sql = "insert into datafromtime(unitId,addr,unitName,upperLimit,lowerLimit,startTime,endTime)values(?,?,?,?,?,?,?)";
        System.out.println("json获取成功！unitName:" + jsonObject.getString("unitName"));
        jdbcTemplate.update("truncate datafromtime");
        jdbcTemplate.update(sql, jsonObject.getString("unitId"), jsonObject.getString("addr"), jsonObject.getString("unitName"), jsonObject.getInteger("upperLimit"), jsonObject.getInteger("lowerLimit"), jsonObject.getTimestamp("startTime"), jsonObject.getTimestamp("endTime"));
        System.out.println("datafromtime数据更新成功！");
        String dataarr = "insert into datafromtime_dataarr(unitId,dataid,gatherTime,column_4)values(?,?,?,?)";
        JSONArray array = jsonObject.getJSONArray("dataarr");
        for (int i = 0; i < array.size(); i++) {
            JSONObject arrjson = array.getJSONObject(i);
            jdbcTemplate.update("truncate datafromtime_dataarr");
            jdbcTemplate.update(dataarr, jsonObject.getString("unitId"), arrjson.getString("dataid"), arrjson.getTimestamp("gatherTime"), arrjson.getString("column_4"));
        }
        return "设备历史数据更新成功！";
    }

    @Scheduled(fixedRate = 1800000)
    //获取设备实时数据
    //@RequestMapping("/realtimedata")
    public String getRealTimeDeviceData() {
        String jsonText = getJson.getJson("addr=44041331&method=getRealTimeDeviceData&sessionkey=82BA0712C75800201C177AD425107EA2");
        JSONObject jsonObject = JSONObject.parseObject(jsonText);
        String sql = "insert into realtimedata(id,addr,devname,workunitid,workunitname,gathertime)values(?,?,?,?,?,?)";
        jdbcTemplate.update("truncate realtimedata");
        jdbcTemplate.update(sql, jsonObject.getString("id"), jsonObject.getString("addr"), jsonObject.getString("devname"), jsonObject.getInteger("workunitid"), jsonObject.getString("workunitname"), jsonObject.getTimestamp("gathertime"));
        System.out.println("realtime数据更新成功！");
        JSONArray array = jsonObject.getJSONArray("devValue");
        String sqljson = "insert into realtimedata_devvalue(id,addr,columnn,name,value,unit) values(?,?,?,?,?,?)";
        jdbcTemplate.update("truncate dev2cplus");
        for (int i = 0; i < array.size(); i++) {
            JSONObject json = array.getJSONObject(i);

            jdbcTemplate.update(sqljson, jsonObject.getString("id"), jsonObject.getString("addr"), json.getString("columnn"), json.getString("name"), json.getString("value"), json.getString("unit"));

        }
        System.out.println("devValue数据更新成功！");
        return "设备实时数据更新成功！";
    }

    //获取用户设备信息
    @Scheduled(fixedRate = 1800000)
    public String getDeviceChannel() {
        String jsonText = getJson.getJson("username=cmadr&method=getDeviceChannel&sessionkey=82BA0712C75800201C177AD425107EA2");

        JSONObject jsonObject = JSONObject.parseObject(jsonText);
        String sql = "insert into dev2cplus(Addr,Chx,DisplayName,chValue,chError,chMan,Unit,Flag)values(?,?,?,?,?,?,?,?)";
        jdbcTemplate.update("truncate dev2cplus");
        JSONArray array = jsonObject.getJSONArray("dev2cplus");
        for (int i = 0; i < array.size(); i++) {
            JSONObject object = array.getJSONObject(i);
            jdbcTemplate.update(sql, object.getString("Addr"), object.getString("Chx"), object.getString("DisplayName"),
                    object.getString("chValue"), object.getString("chError"), object.getString("chMan"),
                    object.getString("Unit"), object.getString("Flag"));
        }
        JSONArray array1 = jsonObject.getJSONArray("devcam");
        String sql1 = "insert into devcam(camip,camname,camwanip,camFlag)values(?,?,?,?)";
        jdbcTemplate.update("truncate devcam");

        JSONArray array2 = jsonObject.getJSONArray("devcam");
        for (int i = 0; i < array2.size(); i++) {
            JSONObject object2 = array2.getJSONObject(i);
            jdbcTemplate.update(sql1, object2.getString("camip"), object2.getString("camname"), object2.getString("camwanip"), object2.getString("camFlag"));
        }
        System.out.println("用户设备更新成功！");
        return "用户设备更新成功！";
    }

    //获取设备历史图片
    @Scheduled(fixedRate = 1800000)
    public String getCamImageFromTime() {
        JSONArray array = JSONArray.parseArray(getJson.getJson("method=getCamImageFromTime&addr=44041330&startTime=2016-10-25%2016:00:00&endTime=2019-04-09%2022:00:00&sessionkey=82BA0712C75800201C177AD425107EA2&pageSize=10&pageNumber=1").toString());
        String sql = "insert into camimagefromtime(addr,gatherTime,imgPath,imgPathWhole)values(?,?,?,?)";
        jdbcTemplate.update("truncate camimagefromtime");
        for (int i = 0; i < array.size(); i++) {
            JSONObject object = array.getJSONObject(i);
            jdbcTemplate.update(sql, object.getString("addr"), object.getTimestamp("gatherTime"), object.getString("imgPath"), object.getString("imgPathWhole"));
        }
        System.out.println("设备历史图片更新成功！");
        return "设备历史图片更新成功！";
    }

    //获取实时图片
    @Scheduled(fixedRate = 1800000)
    public String getCamRealTimeImage() {
        String jsonText = getJson.getJson("method=getCamRealTimeImage&addr=42082851");
        JSONObject jsonObject = JSONObject.parseObject(jsonText);
        String sql = "insert into camrealtimeimage(addr,imgPath)values(?,?)";
        jdbcTemplate.update("truncate camrealtimeimage");
        jdbcTemplate.update(sql, jsonObject.getString("addr"), jsonObject.getString("imgPath"));
        System.out.println("实时图片获取成功！");
        return "实时图片获取成功！";
    }

    //获取单个用户下所有设备和数据
    @Scheduled(fixedRate = 1800000)
    public String getRealTimeDeviceChannelData() {
            JSONArray array = JSONArray.parseArray(getJson.getJson("method=getRealTimeDeviceChannelData&username=cmadr&sessionkey=82BA0712C75800201C177AD425107EA2&pageSize=500&pageNumber=1"));


            String sql = "insert into realtimedevicechanneldata(gatherTime,Addr,havehkcam,havecam,workUnitName,latestImagePath,latestImagePathWhole)values(?,?,?,?,?,?,?)";
            String sql2 = "insert into devvalue(ChannelType,Chx,DisplayName,chValue,Unit,Flag)values(?,?,?,?,?,?)";
            jdbcTemplate.update("truncate realtimedevicechanneldata");
            jdbcTemplate.update("truncate devvalue");
            for (int i = 0; i < array.size(); i++) {
                JSONObject object = array.getJSONObject(i);
                JSONArray array1 = object.getJSONArray("devValue");
                jdbcTemplate.update(sql, object.getTimestamp("gatherTime"), object.getString("Addr"), object.getString("havehkcam"), object.getString("havecam"), object.getString("workUnitName"), object.getString("latestImagePath"), object.getString("latestImagePathWhole"));
                for (int j = 0; j < array1.size(); j++) {
                    JSONObject object1 = array1.getJSONObject(j);
                    jdbcTemplate.update(sql2, object1.getInteger("ChannelType"), object1.getString("Chx"), object1.getString("DisplayName"), object1.getString("chValue"), object1.getString("Unit"), object1.getString("Flag"));
                }

            }
        System.out.println("获取单个用户下所有设备和数据");
        return "获取单个用户下所有设备和数据";
    }

    //根据农场id获取折备
    @Scheduled(fixedRate = 1800000)
    public String getDeviceByFarmId() {
        JSONArray array = JSONArray.parseArray(getJson.getJson("custId=3&method=getDeviceByFarmId&sessionkey=82BA0712C75800201C177AD425107EA2"));
        String sql = "insert into devicebyfarmid(orgid,orgname)values(?,?)";
        String sql2 = "insert into devarr(unitid,unitname,unittype,address,addr,devtype)values(?,?,?,?,?,?)";
        jdbcTemplate.update("truncate devicebyfarmid");
        jdbcTemplate.update("truncate devarr");
        for (int i = 0; i < array.size(); i++) {
            JSONObject object = array.getJSONObject(i);
            JSONArray array1 = object.getJSONArray("devarr");
            jdbcTemplate.update(sql, object.getInteger("orgid"), object.getString("orgname"));
            for (int j = 0; j < array1.size(); j++) {
                JSONObject object1 = array1.getJSONObject(j);
                jdbcTemplate.update(sql2, object1.getInteger("unitid"), object1.getString("unitname"), object1.getString("unittype"), object1.getString("address"), object1.getString("addr"), object1.getInteger("devtype"));
            }
        }
        System.out.println("根据农场id获取设备");
        return "根据农场id获取设备";
    }

    //获取设备历史数据
    @Scheduled(fixedRate = 1800000)
    public String getHistoryData(){
        int pageNumber = 0;
        jdbcTemplate.update("truncate historydata");
        for(int t=0;t<1000;t++) {
            pageNumber++;
            JSONArray jsonArray = JSONArray.parseArray(getJson.getJson("method=getHistoryData&sessionkey=82BA0712C75800201C177AD425107EA2&addr=44041331&startTime=2016-06-08%2020:00:00&endTime=2019-04-09%2000:00:00&orderby=desc&pageSize=500&pageNumber="+pageNumber));
            if(jsonArray == null)break;
            String sql = "insert into historydata(id,设备编号,设备名称,工作单元ID,工作单元名称,采集数据时间,插入数据库时间,光照,气温,湿度,流量,土湿,土温)values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                if(object == null)break;
                jdbcTemplate.update(sql, object.getString("id"), object.getString("设备编号"), object.getString("设备名称"), object.getString("工作单元ID"), object.getString("工作单元名称"), object.getString("采集数据时间"), object.getString("插入数据库时间"), object.getString("光照"), object.getString("气温"), object.getString("湿度"), object.getString("流量"), object.getString("土湿"), object.getString("土温"));
            }
        }
        System.out.println("获取设备历史数据");
        return "获取设备历史数据";
    }


    //获取设备报警信息列表
    @Scheduled(fixedRate = 1800000)
    public String getSystemNoticeList(){
        String jsonText = getJson.getJson("method=getSystemNoticeList&operatorid=11&pageSize=10&pageNumber=1&sessionkey=82BA0712C75800201C177AD425107EA2");
        JSONObject jsonObject = JSONObject.parseObject(jsonText);

        String sql = "insert into noticelist(timetype,pageSize,pageNumber,unreadcount)values(?,?,?,?)";
        String sql2 = "insert into notice(msgid,title,msgtype,content,time,state)values(?,?,?,?,?,?)";
        jdbcTemplate.update("truncate noticelist");
        jdbcTemplate.update("truncate notice");
        JSONArray array = jsonObject.getJSONArray("notice");
        jdbcTemplate.update(sql,jsonObject.getString("timetype"),jsonObject.getString("pageSize"),jsonObject.getString("pageNumber"),jsonObject.getString("unreadcount"));
        for (int i=0;i<array.size();i++){
            JSONObject object = array.getJSONObject(i);
            jdbcTemplate.update(sql2,object.getInteger("msgid"),object.getString("title"),object.getString("msgtype"),object.getString("content"),object.getTimestamp("time"),object.getInteger("state"));
        }
        System.out.println("获取设备报警信息列表");
        return "获取设备报警信息列表";
    }

    //获取设备健康状态实时数据
    @Scheduled(fixedRate = 1800000)
    public String  getHealthState(){
        String jsonText = getJson.getJson("method=getHealthState&addr=44042087&sessionkey=82BA0712C75800201C177AD425107EA2");
        JSONObject jsonObject = JSONObject.parseObject(jsonText);
        String sql = "insert into healthstate(addr,devname,voltage,signall,worktemp,gathertime,pwrstate,custId)values(?,?,?,?,?,?,?,?)";
        jdbcTemplate.update("truncate healthstate");
        jdbcTemplate.update(sql,jsonObject.getString("addr"),jsonObject.getString("devname"),jsonObject.getString("voltage"),jsonObject.getString("signall"),jsonObject.getFloat("worktemp"),jsonObject.getTimestamp("gathertime"),jsonObject.getInteger("pwrstate"),jsonObject.getInteger("custId"));
        System.out.println("获取设备健康状态实时数据");
        return "获取设备健康状态实时数据";
    }

    //获取设备健康状态历史数据
    @Scheduled(fixedRate = 1800000)
    public String getDevHealthList(){
        JSONArray jsonArray = JSONArray.parseArray(getJson.getJson("method=getDevHealthList&addr=44041331&startTime=2017-05-10%2000:00:00&endTime=2019-04-09%2023:59:59&pageSize=10&pageNumber=1&sessionkey=82BA0712C75800201C177AD425107EA2"));
        String sql = "insert into devhealthlist(msgid,addr,devname,voltage,signall,worktemp,gathertime,pwrstate)values(?,?,?,?,?,?,?,?)";
        jdbcTemplate.update("truncate devhealthlist");
        for(int i=0;i<jsonArray.size();i++){
            JSONObject object = jsonArray.getJSONObject(i);
            jdbcTemplate.update(sql,object.getInteger("msgid"),object.getString("addr"),object.getString("devname"),object.getFloat("voltage"),object.getString("signall"),object.getFloat("worktemp"),object.getTimestamp("gathertime"),object.getInteger("pwrstate"));
        }
        System.out.println("获取设备健康状态历史数据");
       return "获取设备健康状态历史数据";
    }

       //根据用户id获取设备操作日志
    @Scheduled(fixedRate = 1800000)
    public String deviceControlLog(){
        String jsonText = getJson.getBusinessJson("method=deviceControlLog&operatorid=440");
        JSONObject jsonObject = JSONObject.parseObject(jsonText);
        String sql = "insert into devicecontrollog(msg,code)values(?,?)";
        String sql2="insert into datas(pageNumber,pageSize,timetype,unreadcount)values(?,?,?,?)";
        String sql3 = "insert into controllognotice(msgid,title,time,content)values(?,?,?,?)";
        JSONObject object = jsonObject.getJSONObject("datas");
        JSONArray array = object.getJSONArray("notice");
        jdbcTemplate.update("truncate devicecontrollog");
        jdbcTemplate.update("truncate datas");
        jdbcTemplate.update("truncate controllognotice");
        jdbcTemplate.update(sql,jsonObject.getString("msg"),jsonObject.getInteger("code"));
        jdbcTemplate.update(sql2,object.getString("pageNumber"),object.getString("pageSize"),object.getString("timetype"),object.getInteger("unreadcount"));
        for (int i=0;i<array.size();i++){
            JSONObject jsonObject1 = array.getJSONObject(i);
            jdbcTemplate.update(sql3,jsonObject1.getInteger("msgid"),jsonObject1.getString("title"),jsonObject1.getTimestamp("time"),jsonObject1.getString("content"));
        }
        System.out.println("根据用户id获取设备操作日志");
        return "根据用户id获取设备操作日志";
    }

    //根据用户id获取设备分组消息
    @Scheduled(fixedRate = 1800000)
    public String getOrgByOperatorId (){
        String jsonText = getJson.getBusinessJson("method=getOrgByOperatorId&operatorid=579");
        JSONObject jsonObject = JSONObject.parseObject(jsonText);
        System.out.println("jsonText:"+jsonText);
        JSONObject jsonObject1 = jsonObject.getJSONObject("datas");
        System.out.println("ORGLIST-datas:"+jsonObject1);
        JSONArray ORGLIST = jsonObject1.getJSONArray("ORGLIST");
        String orgdatas = "insert into orgdatas(id,registerTime,parentId)values(?,?,?)";
        String orglist = "insert into orglist(node,nodeName,id,registerTime,parentId)values(?,?,?,?,?)";
        jdbcTemplate.update("truncate orgdatas");
        jdbcTemplate.update("truncate orglist");
        jdbcTemplate.update(orgdatas,jsonObject1.getInteger("id"),jsonObject1.getTimestamp("registerTime"),jsonObject1.getInteger("parentId"));
        for(int i=0;i<ORGLIST.size();i++){
            JSONObject object = ORGLIST.getJSONObject(i);
            JSONArray orglistarray = object.getJSONArray("orglist");
            for(int j=0;j<orglistarray.size();j++) {
                JSONObject orglistobject = orglistarray.getJSONObject(j);
                jdbcTemplate.update(orglist, object.getString("nodeName"), orglistobject.getString("nodeName"), orglistobject.getInteger("id"), orglistobject.getTimestamp("registerTime"), orglistobject.getInteger("parentId"));
            }
        }
        System.out.println("根据用户id获取设备分组消息");
        return "根据用户id获取设备分组消息";
    }


    //根据农场id获取摄像头
    @Scheduled(fixedRate = 1800000)
    public String getCamListByCustId(){
        String jsonText = getJson.getBusinessJson("method=getCamListByCustId&custId=1");
        JSONObject jsonObject = JSONObject.parseObject(jsonText);
        if(jsonObject == null){
            System.out.println("json为空");
            return "json为空";
        }
        JSONObject datas = jsonObject.getJSONObject("datas");
        if(datas == null){
            System.out.println("datas为空");
            return "";
        }
        JSONArray camList = datas.getJSONArray("camList");
        if(camList == null){
            System.out.println("camList为空");
            return "列表为空";
        }
        String sql = "insert into camlistbycustid(id,deviceusername,devicepassword,devicewanip,addr,registerTime,verifycode,videosn,displayname,videoType,wanPort,isDome)values(?,?,?,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update("truncate camlistbycustid");
        for(int i=0;i<camList.size();i++){
           JSONObject object = camList.getJSONObject(i);
           jdbcTemplate.update(sql,object.getInteger("id"),object.getString("deviceusername"),object.getString("devicepassword"),object.getString("devicewanip"),object.getString("addr"),object.getTimestamp("registerTime"),object.getString("verifycode"),object.getString("videosn"),object.getString("displayname"),object.getInteger("videoType"),object.getInteger("wanPort"),object.getInteger("isDome"));
        }
        System.out.println("根据农场id获取摄像头");
        return "根据农场id获取摄像头";
    }

    //获取设备某通道的配置信息
    @Scheduled(fixedRate = 1800000)
    public String getControlInfo(){
        String jsonText = getJson.getJson("addr=44040491&method=getControlInfo&channelNumber=7&sessionkey=82BA0712C75800201C177AD425107EA2");
        JSONObject jsonObject = JSONObject.parseObject(jsonText);
        if(jsonText == null){
            System.out.println("json为空");
            return "json为空";
        }
        JSONObject object = jsonObject.getJSONObject("devinfojson");
        if(object == null){
            System.out.println("devinfojson列表为空");
            return "空值";
        }
        String sql = "insert into controlinfo(devtype,channel,IsUse,N,U,Bus,BusAddr,T,k,b,Min,Max,StoreValue,InstOrSum)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update("truncate controlinfo"); //更新前清除之前的数据
        jdbcTemplate.update(sql,jsonObject.getString("devtype"),object.getString("channel"),object.getString("IsUse"),object.getString("N"),object.getString("U"),object.getString("Bus"),object.getString("BusAddr"),object.getString("T"),object.getString("k"),object.getString("b"),object.getString("Min"),object.getString("Max"),object.getString("StoreValue"),object.getString("InstOrSum"));
        
        System.out.println("获取设备某通道的配置信息");
        return "获取设备某通道的配置信息";
    }
*/
}