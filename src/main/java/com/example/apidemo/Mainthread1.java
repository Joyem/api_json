package com.example.apidemo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


//@RestController
//@RequestMapping("/api")
@Component
public class Mainthread1 {
    @Autowired
    getJson getJson;
    @Autowired
    JdbcTemplate jdbcTemplate;

    //@Scheduled(fixedRate = 500000)//测试
    //@Scheduled(cron = "0 0 0 * * ?")//每天0点执行一次
    //获取用户设备信息，静态
    //数据库表 dev2cplus  devcam
    @Scheduled(fixedRate = 36000000)
    public String getDeviceChannel() {
        String jsonText = getJson.getJson("username=cmadr&method=getDeviceChannel&sessionkey=82BA0712C75800201C177AD425107EA2");
//用户cdhbzcs
        String jsonText2 = getJson.getJson("username=cdhbzcs&method=getDeviceChannel&sessionkey=82BA0712C75800201C177AD425107EA2");
        JSONObject jsonObject2 = JSONObject.parseObject(jsonText2);
        JSONObject jsonObject = JSONObject.parseObject(jsonText);
        String sql = "insert into dev2cplus(username,Addr,Chx,DisplayName,chValue,chError,chMan,Unit,Flag)values(?,?,?,?,?,?,?,?,?)";
       // String sql1 = "insert into devcam(camip,camname,camwanip,camFlag)values(?,?,?,?)";
        jdbcTemplate.update("truncate dev2cplus");
        JSONArray array = jsonObject.getJSONArray("dev2cplus");
        for (int i = 0; i < array.size(); i++) {
            JSONObject object = array.getJSONObject(i);
            jdbcTemplate.update(sql,"cmadr",object.getString("Addr"), object.getString("Chx"), object.getString("DisplayName"),
                    object.getString("chValue"), object.getString("chError"), object.getString("chMan"),
                    object.getString("Unit"), object.getString("Flag"));
        }
        JSONArray array2 = jsonObject2.getJSONArray("dev2cplus");
        for(int j=0;j<array2.size();j++){
            JSONObject object = array2.getJSONObject(j);
            jdbcTemplate.update(sql,"cdhbzcs",object.getString("Addr"), object.getString("Chx"), object.getString("DisplayName"),
                    object.getString("chValue"), object.getString("chError"), object.getString("chMan"),
                    object.getString("Unit"), object.getString("Flag"));
        }
        //JSONArray array1 = jsonObject.getJSONArray("devcam");

        /*for (int i = 0; i < array2.size(); i++) {
            JSONObject object2 = array2.getJSONObject(i);
            jdbcTemplate.update(sql1, object2.getString("camip"), object2.getString("camname"), object2.getString("camwanip"), object2.getString("camFlag"));
        }*/
        System.out.println("用户设备更新成功！");
        return "用户设备更新成功！";
    }


    //@Scheduled(fixedRate = 5000000)//测试
    //每天3，21点分别全量更新一次
    //根据账号名获取设备  获取内容包括设备组、设备、通道、摄像头信息最新数据
    //数据库表 devicebyusername[] devarr[] channelarr[]
    //@Scheduled(cron = "0 0 3,21 * * ?")
    @Scheduled(fixedRate = 36000000)
    public void getDeviceByUsername() {
        String jsonText = getJson.getJson("businessUsername=cmadr&method=getDeviceByUsername&sessionkey=82BA0712C75800201C177AD425107EA2&havechannel=1");
        String jsonText2 = getJson.getJson("businessUsername=cdhbzcs&method=getDeviceByUsername&sessionkey=82BA0712C75800201C177AD425107EA2&havechannel=1");

        String sql = "insert into devicebyusername(username,orgid,orgname)values(?,?,?)";
        String sql1 = "insert into devarr(username,orgid,orgname,unitid,unitname,unittype,address,addr,devtype,gathertime,geom)values(?,?,?,?,?,?,?,?,?,?,?)";
        String sql2 = "insert into channelarr(addr,username,orgid,unitid,Chx,DisplayName,channelType,chValue,Unit,Flag)values(?,?,?,?,?,?,?,?,?,?)";

        jdbcTemplate.update("truncate devicebyusername");
        jdbcTemplate.update("truncate devarr");
        jdbcTemplate.update("truncate channelarr");
        JSONArray device = JSONArray.parseArray(jsonText);

        //JSONArray device2 = JSONArray.parseArray(jsonText2);
        for (int i = 0; i < device.size(); i++) {
            JSONObject object = device.getJSONObject(i);
            jdbcTemplate.update(sql, "cmadr",object.getInteger("orgid"), object.getString("orgname"));
            JSONArray devarr = object.getJSONArray("devarr");
            for (int j = 0; j < devarr.size(); j++) {
                JSONObject object1 = devarr.getJSONObject(j);
                jdbcTemplate.update(sql1,"cmadr", object.getInteger("orgid"), object.getString("orgname"), object1.getInteger("unitid"), object1.getString("unitname"), object1.getInteger("unittype"), object1.getString("address"), object1.getString("addr"), object1.getInteger("devtype"), object1.getTimestamp("gathertime"), object1.getJSONArray("geom").toString());

                JSONArray object2 = object1.getJSONArray("channelarr");
                for (int k = 0; k < object2.size(); k++) {
                    JSONObject object3 = object2.getJSONObject(k);
                    jdbcTemplate.update(sql2,object1.getString("addr"),"cmadr",object.getInteger("orgid"), object1.getInteger("unitid"), object3.getString("Chx"), object3.getString("DisplayName"), object3.getInteger("channelType"), object3.getString("chValue"), object3.getString("Unit"), object3.getString("Flag"));
                }
            }
        }

        JSONArray device2 = JSONArray.parseArray(jsonText2);
        for (int i = 0; i < device2.size(); i++) {
            JSONObject object = device2.getJSONObject(i);
            jdbcTemplate.update(sql, "cdhbzcs",object.getInteger("orgid"), object.getString("orgname"));
            JSONArray devarr = object.getJSONArray("devarr");
            for (int j = 0; j < devarr.size(); j++) {
                JSONObject object1 = devarr.getJSONObject(j);
                jdbcTemplate.update(sql1,"cdhbzcs", object.getInteger("orgid"), object.getString("orgname"), object1.getInteger("unitid"), object1.getString("unitname"), object1.getInteger("unittype"), object1.getString("address"), object1.getString("addr"), object1.getInteger("devtype"), object1.getTimestamp("gathertime"), object1.getJSONArray("geom").toString());

                JSONArray object2 = object1.getJSONArray("channelarr");
                for (int k = 0; k < object2.size(); k++) {
                    JSONObject object3 = object2.getJSONObject(k);
                    jdbcTemplate.update(sql2,object1.getString("addr"),"cdhbzcs",object.getInteger("orgid"), object1.getInteger("unitid"), object3.getString("Chx"), object3.getString("DisplayName"), object3.getInteger("channelType"), object3.getString("chValue"), object3.getString("Unit"), object3.getString("Flag"));
                }
            }
        }
        System.out.println("根据用户名获取设备");

    }


    //@Scheduled(fixedRate = 360000000)//测试
    //不区分用户 每天3点，22点更新数据
//获取设备历史数据 devicesensordatafromtime devicesensordatafromtime_dataarr
//@RequestMapping("/datafromtime")
    //@Scheduled(cron = "0 0 10,22 * * ?")
    @Scheduled(fixedRate = 36000000)
    public String getDeviceSensorDataFromTime(){
        //获取所有设备,然后获取该设备所有历史数据
        String addr;//设备地址
        List list = jdbcTemplate.queryForList("select distinct Addr from dev2cplus");
        Iterator iterator = list.iterator();
        while(iterator.hasNext()) {
            Map map = (Map) iterator.next();
            addr = (String) map.get("Addr");
            System.out.println("设备历史数据-设备地址Addr=" + addr);
            Map map1 = null;
            Timestamp timestamp = null;
            try {
                map1 = jdbcTemplate.queryForMap("select endTime from  devicesensordatafromtime where addr=? order by endTime desc limit 1", addr);
            }catch (Exception e) {
                //timestamp = (Timestamp) map1.get("endTime");

                //Timestamp timestamp = null;

            }
            if (timestamp == null) {
                timestamp = new Timestamp(2005 - 1900, 0, 1, 1, 0, 0, 0);//2005-1-1  01:00:00
            }else{
                timestamp = (Timestamp) map1.get("endTime");
            }
            System.out.println("startTime="+timestamp);
            String strs[] = timestamp.toString().split(" ");
            String startTime = strs[0];
            String afterstarttime = strs[1];
            Timestamp time = new Timestamp(System.currentTimeMillis());
            strs = time.toString().split(" ");
            String endTime = strs[0];
            String afterendtime = strs[1];
            String text = "addr=" + addr + "&method=getDeviceSensorDataFromTime&sessionkey=82BA0712C75800201C177AD425107EA2&startTime="+startTime+"%"+afterstarttime+"&endTime="+endTime+"%"+afterendtime;
            System.out.println("text="+text);
            String jsonText = getJson.getJson(text);
            System.out.println("根据日期获取历史数据:"+jsonText);
            JSONObject jsonObject = JSONObject.parseObject(jsonText);

            if(jsonObject.getString("error") != null)continue;
            String sql = "insert into devicesensordatafromtime(unitId,addr,unitName,upperLimit,lowerLimit,startTime,endTime,createtime)values(?,?,?,?,?,?,?,?)";
            System.out.println("json获取成功！unitName:" + jsonObject.getString("unitName"));
            //jdbcTemplate.update("truncate devicesensordatafromtime");
            jdbcTemplate.update(sql, jsonObject.getString("unitId"), jsonObject.getString("addr"), jsonObject.getString("unitName"), jsonObject.getInteger("upperLimit"), jsonObject.getInteger("lowerLimit"), jsonObject.getTimestamp("startTime"), jsonObject.getTimestamp("endTime"),time);
            System.out.println("datafromtime数据更新成功！");
            String dataarr = "insert into devicesensordatafromtime_dataarr(addr,unitId,dataid,gatherTime,column_0,column_1,column_2,column_3,column_4,column_5,column_6,column_7)values(?,?,?,?,?,?,?,?,?,?,?,?)";
            JSONArray array = jsonObject.getJSONArray("dataarr");
            for (int i = 0; i < array.size(); i++) {
                JSONObject arrjson = array.getJSONObject(i);
                //jdbcTemplate.update("truncate devicesensordatafromtime_dataarr");
                Float column_0 = new Float(0.0);
                if(arrjson.getString("column_0") != null)
                    column_0 = Float.parseFloat(arrjson.getString("column_0"));
                Float column_1 = new Float(0.0);
                if(arrjson.getString("column_0") != null)
                    column_1 = Float.parseFloat(arrjson.getString("column_1"));
                Float column_2 = new Float(0.0);
                if(arrjson.getString("column_2") != null)
                    column_2 = Float.parseFloat(arrjson.getString("column_2"));
                Float column_3 = new Float(0.0);
                if(arrjson.getString("column_3") != null)
                    column_3 = Float.parseFloat(arrjson.getString("column_3"));
                Float column_4 = new Float(0.0);
                if(jsonObject.getString("column_4") != null)
                    column_4 = Float.parseFloat(jsonObject.getString("column_4"));
                Float column_5 = new Float(0.0);
                if(arrjson.getString("column_5") != null)
                    column_5 = Float.parseFloat(arrjson.getString("column_5"));
                Float column_6 = new Float(0.0);
                if(arrjson.getString("column_6") != null)
                    column_6 = Float.parseFloat(arrjson.getString("column_6"));
                Float column_7 = new Float(0.0);
                if(arrjson.getString("column_7") != null)
                    column_7 = Float.parseFloat(arrjson.getString("column_7"));

                jdbcTemplate.update(dataarr, jsonObject.getString("addr"), jsonObject.getString("unitId"), arrjson.getString("dataid"), arrjson.getTimestamp("gatherTime"),column_0,column_1,column_2,column_3,column_4,column_5,column_6,column_7);
            }
        }
        return "设备历史数据更新成功！";
    }


    //获取设备历史图片//不区分用户
    //@Scheduled(fixedRate = 50000000)//测试
    @Scheduled(cron = "0 0 11,20 * * ?")//每天11，20点分别更新一次
    public String getCamImageFromTime() {
        jdbcTemplate.update("truncate camimagefromtime");
        //获取所有设备,然后获取该设备所有历史数据
        String addr;//设备地址
        List list = jdbcTemplate.queryForList("select distinct Addr from dev2cplus");
        Iterator iterator = list.iterator();
        String sql = "insert into camimagefromtime(addr,gatherTime,imgPath,imgPathWhole)values(?,?,?,?)";
        while (iterator.hasNext()) {
            Map map = (Map) iterator.next();
            addr = (String) map.get("Addr");
            System.out.println("设备历史图片--设备地址Addr=" + addr);
            int pageNumber = 0;

            Map map1 = null;
            Timestamp timestamp = null;
            try {
                map1 = jdbcTemplate.queryForMap("select gatherTime from  camimagefromtime where addr=? order by gatherTime desc limit 1", addr);
            }catch (Exception e) {
                //timestamp = (Timestamp) map1.get("endTime");

                //Timestamp timestamp = null;

            }
            if (timestamp == null) {
                timestamp = new Timestamp(2000 - 1900, 0, 1, 1, 0, 0, 0);//2000-1-1  01:00:00
            }else{
                timestamp = (Timestamp) map1.get("endTime");
            }
            System.out.println("startTime="+timestamp);
            String strs[] = timestamp.toString().split(" ");
            String startTime = strs[0];
            String afterstarttime = strs[1];
            Timestamp time = new Timestamp(System.currentTimeMillis());
            strs = time.toString().split(" ");
            String endTime = strs[0];
            String afterendtime = strs[1];

            while (true) {
                pageNumber++;//endTime=2019-04-12%00:00:00&
                String text = getJson.getJson("method=getCamImageFromTime&addr=" + addr + "&startTime="+startTime+"%"+afterstarttime+"&endTime="+endTime+"%"+afterendtime+"&sessionkey=82BA0712C75800201C177AD425107EA2&pageSize=500&pageNumber=" + pageNumber);
                System.out.println("历史图片text="+text);
                Object json = JSON.parse(text);
                //Object array = null;
                //判断返回的是jsonarray还是jsonobject
                if(json instanceof JSONArray) {
                    JSONArray array = (JSONArray) json;
                    if(array.isEmpty())break;
                    System.out.println("jsonArray="+array.toJSONString());
                    for (int i = 0; i < array.size(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        jdbcTemplate.update(sql, object.getString("addr"), object.getTimestamp("gatherTime"), object.getString("imgPath"), object.getString("imgPathWhole"));
                    }
                }else if(json instanceof JSONObject){
                    JSONObject object = (JSONObject)json;
                   // System.out.println("jsonObject="+object.toString());
                    if(object.getString("error") != null)break;
                    System.out.println("jsonObject不空="+object.toString());

                    jdbcTemplate.update(sql, object.getString("addr"), object.getTimestamp("gatherTime"), object.getString("imgPath"), object.getString("imgPathWhole"));

                }else{
                    break;
                }

            }
        }
            System.out.println("设备历史图片更新成功！");
            return "设备历史图片更新成功！";
    }



/*
    @Scheduled(fixedRate = 5000)
    //获取设备实时数据，最新的一条
    //@RequestMapping("/realtimedata")
    public String getRealTimeDeviceData() {
        String jsonText = getJson.getJson("addr=44041331&method=getRealTimeDeviceData&sessionkey=82BA0712C75800201C177AD425107EA2");
        JSONObject jsonObject = JSONObject.parseObject(jsonText);
        String sql = "insert into realtimedata(id,addr,devname,workunitid,workunitname,gathertime)values(?,?,?,?,?,?)";
        String sqljson = "insert into realtimedata_devvalue(id,addr,columnn,name,value,unit) values(?,?,?,?,?,?)";
        jdbcTemplate.update(sql, jsonObject.getString("id"), jsonObject.getString("addr"), jsonObject.getString("devname"), jsonObject.getInteger("workunitid"), jsonObject.getString("workunitname"), jsonObject.getTimestamp("gathertime"));
        System.out.println("realtime数据更新成功！");
        JSONArray array = jsonObject.getJSONArray("devValue");

        for (int i = 0; i < array.size(); i++) {
            JSONObject json = array.getJSONObject(i);

            jdbcTemplate.update(sqljson, jsonObject.getString("id"), jsonObject.getString("addr"), json.getString("columnn"), json.getString("name"), json.getString("value"), json.getString("unit"));

        }
        System.out.println("devValue数据更新成功！");
        return "设备实时数据更新成功！";
    }

    @Scheduled(fixedRate = 5000)
    //获取设备实时数据，最新的一条
    //@RequestMapping("/realtimedata")
    public String cdhbzcs_getRealTimeDeviceData() {
        String jsonText = getJson.getJson("addr=44041331&method=getRealTimeDeviceData&sessionkey=82BA0712C75800201C177AD425107EA2");
        JSONObject jsonObject = JSONObject.parseObject(jsonText);
        String sql = "insert into cdhbzcs_realtimedata(id,addr,devname,workunitid,workunitname,gathertime)values(?,?,?,?,?,?)";
        String sqljson = "insert into cdhbzcs_realtimedata_devvalue(id,addr,columnn,name,value,unit) values(?,?,?,?,?,?)";

        jdbcTemplate.update(sql, jsonObject.getString("id"), jsonObject.getString("addr"), jsonObject.getString("devname"), jsonObject.getInteger("workunitid"), jsonObject.getString("workunitname"), jsonObject.getTimestamp("gathertime"));
        System.out.println("realtime数据更新成功！");
        JSONArray array = jsonObject.getJSONArray("devValue");
        for (int i = 0; i < array.size(); i++) {
            JSONObject json = array.getJSONObject(i);

            jdbcTemplate.update(sqljson, jsonObject.getString("id"), jsonObject.getString("addr"), json.getString("columnn"), json.getString("name"), json.getString("value"), json.getString("unit"));

        }
        System.out.println("cdhbzcs设备实时数据更新成功！");
        return "设备实时数据更新成功！";
    }


    //获取实时图片
    @Scheduled(fixedRate = 5000)
    public String getCamRealTimeImage() {
        String jsonText = getJson.getJson("method=getCamRealTimeImage&addr=42082851");
        JSONObject jsonObject = JSONObject.parseObject(jsonText);
        String sql = "insert into camrealtimeimage(addr,imgPath)values(?,?)";

        jdbcTemplate.update(sql, jsonObject.getString("addr"), jsonObject.getString("imgPath"));
        System.out.println("实时图片获取成功！");
        return "实时图片获取成功！";
    }
    //cdhbzcs_camrealtimeimage
//获取实时图片
    @Scheduled(fixedRate = 5000)
    public String cdhbzcs_getCamRealTimeImage() {
        String jsonText = getJson.getJson("method=getCamRealTimeImage&addr=42082851");
        JSONObject jsonObject = JSONObject.parseObject(jsonText);
        String sql = "insert into cdhbzcs_camrealtimeimage(addr,imgPath)values(?,?)";

        jdbcTemplate.update(sql, jsonObject.getString("addr"), jsonObject.getString("imgPath"));
        System.out.println("cdhbzcs实时图片获取成功！");
        return "实时图片获取成功！";
    }
*/



/*
    //获取设备历史数据
    @Scheduled(fixedRate = 1800000)
    public String getHistoryData(){

        jdbcTemplate.update("truncate historydata");
        //获取所有设备,然后获取该设备所有历史数据
        String addr;//设备地址
        List list = jdbcTemplate.queryForList("select distinct Addr from dev2cplus");
        Iterator iterator = list.iterator();
        while(iterator.hasNext()) {
            Map map = (Map) iterator.next();
            addr = (String)map.get("Addr");
            System.out.println("设备地址Addr=" + addr);
            int pageNumber = 0;

            for (int t = 0; t < 100000; t++) {
                pageNumber++;

                String text = "method=getHistoryData&sessionkey=82BA0712C75800201C177AD425107EA2&addr=" +addr+ "&startTime=2000-06-08%2000:00:00&endTime=2019-04-12%2000:00:00&orderby=desc&pageSize=200&pageNumber=" + pageNumber;
                System.out.println("text="+text);
                JSONArray jsonArray = JSONArray.parseArray(getJson.getJson(text));
                System.out.println("jsonArray获取成功");
                if (jsonArray == null) break;
                String sql = "insert into historydata(id,设备编号,设备名称,工作单元ID,工作单元名称,采集数据时间,插入数据库时间,光照,气温,湿度,流量,土湿,土温)values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    if (object == null) break;
                    jdbcTemplate.update(sql, object.getString("id"), object.getString("设备编号"), object.getString("设备名称"), object.getString("工作单元ID"), object.getString("工作单元名称"), object.getString("采集数据时间"), object.getString("插入数据库时间"), object.getString("光照"), object.getString("气温"), object.getString("湿度"), object.getString("流量"), object.getString("土湿"), object.getString("土温"));
                }
            }
        }
        System.out.println("获取设备历史数据");
        return "获取设备历史数据";
    }

    //@Scheduled(fixedRate = 5000)//每5秒更新一次
    //根据账号名获取设备  获取内容包括设备组、设备、通道、摄像头信息
    //数据库表 cdhbzcs_devicebyusername[] cdhbzcs_devarr[] cdhbzcs_channelarr[]
    public void cdhbzcs_getDeviceByUsername() {
        String jsonText = getJson.getJson("businessUsername=cdhbzcs&method=getDeviceByUsername&sessionkey=82BA0712C75800201C177AD425107EA2&havechannel=1");
        System.out.println("jsonText:" + jsonText);
        JSONArray device = JSONArray.parseArray(jsonText);
        String sql = "insert into cdhbzcs_devicebyusername(orgid,orgname)values(?,?)";
        String sql1 = "insert into cdhbzcs_devarr(orgid,orgname,unitid,unitname,unittype,address,addr,devtype,gathertime,geom)values(?,?,?,?,?,?,?,?,?,?)";
        String sql2 = "insert into cdhbzcs_channelarr(unitid,Chx,DisplayName,channelType,chValue,Unit,Flag)values(?,?,?,?,?,?,?)";

        jdbcTemplate.update("truncate cdhbzcs_devicebyusername");
        jdbcTemplate.update("truncate cdhbzcs_devarr");
        jdbcTemplate.update("truncate cdhbzcs_channelarr");
        for (int i = 0; i < device.size(); i++) {
            JSONObject object = device.getJSONObject(i);
            jdbcTemplate.update(sql, object.getInteger("orgid"), object.getString("orgname"));
            JSONArray devarr = object.getJSONArray("devarr");
            for (int j = 0; j < devarr.size(); j++) {
                JSONObject object1 = devarr.getJSONObject(j);
                jdbcTemplate.update(sql1, object.getInteger("orgid"), object.getString("orgname"), object1.getInteger("unitid"), object1.getString("unitname"), object1.getInteger("unittype"), object1.getString("address"), object1.getString("addr"), object1.getInteger("devtype"), object1.getTimestamp("gathertime"), object1.getJSONArray("geom").toString());

                JSONArray object2 = object1.getJSONArray("channelarr");
                for (int k = 0; k < object2.size(); k++) {
                    JSONObject object3 = object2.getJSONObject(k);
                    jdbcTemplate.update(sql2, object1.getInteger("unitid"), object3.getString("Chx"), object3.getString("DisplayName"), object3.getInteger("channelType"), object3.getString("chValue"), object3.getString("Unit"), object3.getString("Flag"));
                }
            }
        }

        System.out.println("根据用户名获取设备");
    }


//@Scheduled(fixedRate = 3000000)
    //获取用户设备信息
    //数据库表 cdhbzcs_dev2cplus  cdhbzcs_devcam
    public String cdhbzcs_getDeviceChannel() {
        String jsonText = getJson.getJson("username=cdhbzcs&method=getDeviceChannel&sessionkey=82BA0712C75800201C177AD425107EA2");

        JSONObject jsonObject = JSONObject.parseObject(jsonText);
        String sql = "insert into cdhbzcs_dev2cplus(Addr,Chx,DisplayName,chValue,chError,chMan,Unit,Flag)values(?,?,?,?,?,?,?,?)";
        String sql1 = "insert into cdhbzcs_devcam(camip,camname,camwanip,camFlag)values(?,?,?,?)";

        JSONArray array = jsonObject.getJSONArray("dev2cplus");
        for (int i = 0; i < array.size(); i++) {
            JSONObject object = array.getJSONObject(i);
            jdbcTemplate.update(sql, object.getString("Addr"), object.getString("Chx"), object.getString("DisplayName"),
                    object.getString("chValue"), object.getString("chError"), object.getString("chMan"),
                    object.getString("Unit"), object.getString("Flag"));
        }
        JSONArray array1 = jsonObject.getJSONArray("devcam");

        JSONArray array2 = jsonObject.getJSONArray("devcam");
        for (int i = 0; i < array2.size(); i++) {
            JSONObject object2 = array2.getJSONObject(i);
            jdbcTemplate.update(sql1, object2.getString("camip"), object2.getString("camname"), object2.getString("camwanip"), object2.getString("camFlag"));
        }
        System.out.println("cdhbzcs_用户设备更新成功！");
        return "用户设备更新成功！";
    }


*/

}