package com.ruoyi.web.controller.others;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class HolidayTest {
    public static void main(String[] args) {
        String url = "http://timor.tech/api/holiday/year/2022";
        //String url = "http://api.map.baidu.com/location/ip?ak=laOQElaF53xGGBjscGtrd10nN4j1zGki&ip=221.222.54.13";
        String json = loadJson(url);
        System.out.println("json = " + json);
        /**
         * 输出结果：
         * json = {"code":0,"holiday":{"01-01":{"holiday":true,"name":"元旦","wage":3,"date":"2022-01-01","rest":85},"01-02":{"holiday":true,"name":"元旦","wage":2,"date":"2022-01-02","rest":1},"01-03":{"holiday":true,"name":"元旦","wage":2,"date":"2022-01-03","rest":1},"01-29":{"holiday":false,"name":"春节前补班","after":false,"wage":1,"target":"春节","date":"2022-01-29","rest":14},"01-30":{"holiday":false,"name":"春节前补班","after":false,"wage":1,"target":"春节","date":"2022-01-30","rest":1},"01-31":{"holiday":true,"name":"除夕","wage":2,"date":"2022-01-31","rest":16},"02-01":{"holiday":true,"name":"初一","wage":3,"date":"2022-02-01","rest":1},"02-02":{"holiday":true,"name":"初二","wage":3,"date":"2022-02-02","rest":1},"02-03":{"holiday":true,"name":"初三","wage":3,"date":"2022-02-03","rest":1},"02-04":{"holiday":true,"name":"初四","wage":2,"date":"2022-02-04","rest":1},"02-05":{"holiday":true,"name":"初五","wage":2,"date":"2022-02-05","rest":1},"02-06":{"holiday":true,"name":"初六","wage":2,"date":"2022-02-06","rest":1},"04-02":{"holiday":false,"name":"清明节前补班","after":false,"wage":1,"target":"清明节","date":"2022-04-02","rest":1},"04-03":{"holiday":true,"name":"清明节","wage":2,"date":"2022-04-03","rest":2},"04-04":{"holiday":true,"name":"清明节","wage":2,"date":"2022-04-04","rest":1},"04-05":{"holiday":true,"name":"清明节","wage":3,"date":"2022-04-05","rest":1},"04-24":{"holiday":false,"name":"劳动节前补班","after":false,"wage":1,"target":"劳动节","date":"2022-04-24","rest":1},"04-30":{"holiday":true,"name":"劳动节","wage":2,"date":"2022-04-30","rest":6},"05-01":{"holiday":true,"name":"劳动节","wage":3,"date":"2022-05-01","rest":1},"05-02":{"holiday":true,"name":"劳动节","wage":2,"date":"2022-05-02","rest":1},"05-03":{"holiday":true,"name":"劳动节","wage":2,"date":"2022-05-03","rest":1},"05-04":{"holiday":true,"name":"劳动节","wage":2,"date":"2022-05-04","rest":1},"05-07":{"holiday":false,"name":"劳动节后补班","after":true,"wage":1,"target":"劳动节","date":"2022-05-07","rest":3},"06-03":{"holiday":true,"name":"端午节","wage":3,"date":"2022-06-03","rest":28},"06-04":{"holiday":true,"name":"端午节","wage":2,"date":"2022-06-04","rest":1},"06-05":{"holiday":true,"name":"端午节","wage":2,"date":"2022-06-05","rest":1},"09-10":{"holiday":true,"name":"中秋节","wage":3,"date":"2022-09-10","rest":14},"09-11":{"holiday":true,"name":"中秋节","wage":2,"date":"2022-09-11","rest":1},"09-12":{"holiday":true,"name":"中秋节","wage":2,"date":"2022-09-12","rest":1},"10-01":{"holiday":true,"name":"国庆节","wage":3,"date":"2022-10-01","rest":18},"10-02":{"holiday":true,"name":"国庆节","wage":3,"date":"2022-10-02","rest":1},"10-03":{"holiday":true,"name":"国庆节","wage":3,"date":"2022-10-03","rest":1},"10-04":{"holiday":true,"name":"国庆节","wage":2,"date":"2022-10-04","rest":1},"10-05":{"holiday":true,"name":"国庆节","wage":2,"date":"2022-10-05","rest":1},"10-06":{"holiday":true,"name":"国庆节","wage":2,"date":"2022-10-06","rest":1},"10-07":{"holiday":true,"name":"国庆节","wage":2,"date":"2022-10-07","rest":1},"10-08":{"holiday":false,"after":true,"wage":1,"name":"国庆节后补班","target":"国庆节","date":"2022-10-08"},"10-09":{"holiday":false,"after":true,"wage":1,"name":"国庆节后补班","target":"国庆节","date":"2022-10-09"}}}
         */
    }

    /**
     * 根据请求路径返回json数据
     *
     * @param url 请求接口路径
     * @return
     */
    public static String loadJson(String url) {
        //字符串工具类
        StringBuilder json = new StringBuilder();
        try {
            //链接URL
            URL urlObject = new URL(url);
            //创建连接
            URLConnection uc = urlObject.openConnection();
            //设置请求属性，缺少此语句报错Server returned HTTP response code: 403 for URL
            //参考链接：https://blog.csdn.net/xc9711/article/details/124047530https://blog.csdn.net/xc9711/article/details/124047530
            uc.setRequestProperty("User-Agent", "Mozilla/4.76");
            //读取返回结果集，此处记得设置编码格式为UTF-8，防止中文乱码
            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream(), "utf-8"));
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //将json数据以字符串的形式进行返回
        return json.toString();
    }
}