package com.ruoyi.web.controller.others;

import com.ruoyi.common.annotation.Anonymous;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.http.HttpUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@RestController
public class WeatherController {

    @Anonymous
    @GetMapping("/getWeatherByLocalIP")
    public AjaxResult getWeather() throws UnsupportedEncodingException {
        AjaxResult result = AjaxResult.success();

        //String localCityName = GetLocationAndIP.getLocalCityName();
        String localCityName = "北京";
        //调用天气API
        String encodeCity = URLEncoder.encode(localCityName, "UTF-8");
        System.out.println(encodeCity);
        String url = "http://apis.juhe.cn/simpleWeather/query?city=" + encodeCity + "&key=81fe33a6077267b2e4ae2967af47eeb7";
        String weatherInfo = HttpUtils.sendGet(url);
        result.put("msg", weatherInfo);
        return result;
    }

}

class GetLocationAndIP {
    private static String readAll(BufferedReader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }


    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String jsonText = readAll(rd);
            return new JSONObject(jsonText);
        }
    }

    public Map<String, Object> getAddress() {
        String ip = "";
        // 这个网址似乎不能了用了
        // String chinaz = "http://ip.chinaz.com";
        // 改用了太平洋的一个网址
        String chinaz = "http://whois.pconline.com.cn/";

        StringBuilder inputLine = new StringBuilder();
        String read = "";
        URL url = null;
        HttpURLConnection urlConnection = null;
        BufferedReader in = null;

        Map<String, Object> map = new HashMap<>();
        try {
            url = new URL(chinaz);
            urlConnection = (HttpURLConnection) url.openConnection();
            // 如有乱码的，请修改相应的编码集，这里是 gbk
            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "gbk"));
            while ((read = in.readLine()) != null) {
                inputLine.append(read).append("\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // 这个是之前的正则表达式,
        // Pattern p = Pattern.compile("\\<dd class\\=\"fz24\">(.*?)\\<\\/dd>");
        // 通过正则表达式匹配我们想要的内容，根据拉取的网页内容不同，正则表达式作相应的改变
        Pattern p = Pattern.compile("显示IP地址为(.*?)的位置信息");
        Matcher m = p.matcher(inputLine.toString());
        if (m.find()) {
            String ipstr = m.group(0);
            // 这里根据具体情况，来截取想要的内容
            ip = ipstr.substring(ipstr.indexOf("为") + 2, ipstr.indexOf("的") - 1);
            map.put("ip", ip);
        }
        JSONObject json = null;
        try {
            // 这里调用百度的ip定位api服务 详见 http://api.map.baidu.com/lbsapi/cloud/ip-location-api.htm
            json = readJsonFromUrl("http://api.map.baidu.com/location/ip?ak=laOQElaF53xGGBjscGtrd10nN4j1zGki&ip=" + ip);
            //city = (((JSONObject) ((JSONObject) json.get("content")).get("address_detail")).get("city")).toString();
            map.put("city", ((JSONObject) ((JSONObject) json.get("content")).get("address_detail")).get("city").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public static String getLocalCityName() {
        GetLocationAndIP getLocationANDIp = new GetLocationAndIP();
        Map<String, Object> map = getLocationANDIp.getAddress();
        String city = map.get("city").toString();
        return city.substring(0, city.length() - 1);
    }

    public static void main(String[] args) {
        GetLocationAndIP getLocationANDIp = new GetLocationAndIP();
        Map<String, Object> map = getLocationANDIp.getAddress();
        String city = map.get("city").toString();
        String city_1 = city.substring(0, city.length() - 1);
        System.out.println(city_1);
    }
}
