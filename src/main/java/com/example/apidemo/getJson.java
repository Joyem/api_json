package com.example.apidemo;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Timestamp;

@Component
public class getJson {

    public String getJson(String str){


        StringBuilder stringBuilder = new StringBuilder();
        String json = null;
        //String urlstr = "http://api.nongyongtong.com:8081/qfls/api?method=loginBusiness&businessUsername=cmadr&businessPassword=123456";
        try {
            String urlstr = "http://api.nongyongtong.com:8081/qfls/api?" + str;
            URL url = new URL(urlstr);

            InputStream is = url.openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("gbk")));
            int cp;
            while ((cp = rd.read()) != -1) {
                stringBuilder.append((char) cp);
            }
            String jsonText = stringBuilder.toString();

            json = (jsonText);
            is.close();
            //System.out.println(jsonText);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return json;

    }

    public String getBusinessJson(String str){
        StringBuilder stringBuilder = new StringBuilder();
        String json = null;
        //String urlstr = "http://api.nongyongtong.com:8081/qfls/business?method=loginBusiness&businessUsername=cmadr&businessPassword=123456";
        try {
            String urlstr = "http://api.nongyongtong.com:8081/qfls/business?" + str;
            URL url = new URL(urlstr);

            InputStream is = url.openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("gbk")));
            int cp;
            while ((cp = rd.read()) != -1) {
                stringBuilder.append((char) cp);
            }
            String jsonText = stringBuilder.toString();

            json = jsonText;
            is.close();
            //System.out.println(jsonText);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return json;

    }
}
