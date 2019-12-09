package com.zhifa.elastic;

import com.alibaba.fastjson.JSONArray;
import com.zhifa.elastic.domain.Policy;
import com.zhifa.elastic.mapper.PolicyMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*@RunWith(SpringRunner.class)
@SpringBootTest*/
public class JsoupTest {

 /*   @Autowired
    private PolicyMapper policyMapper;
*/
    @Test
    public void huawei() throws IOException, ParseException {
        CloseableHttpClient httpclient; // 创建httpclient实例
        httpclient = HttpClients.createDefault();
        //HttpGet httpget = new HttpGet("https://consumer.huawei.com/cn/phones/?ic_medium=hwdc&ic_source=corp_header_consumer"); // 创建httpget实例
        HttpGet httpget = new HttpGet("http://zwgk.gz.gov.cn/GZ12/mljj/list1.shtml"); // 创建httpget实例

        CloseableHttpResponse response = httpclient.execute(httpget); // 执行get请求
        HttpEntity entity = response.getEntity(); // 获取返回实体
        //System.out.println("网页内容："+ EntityUtils.toString(entity, "utf-8")); // 指定编码打印网页内容
        String content = EntityUtils.toString(entity, "utf-8");
        response.close(); // 关闭流和释放系统资源

//        System.out.println(content);

        Document document = Jsoup.parse(content);
        //Elements elements = document.select("#content-v3-plp #pagehidedata .plphidedata");
//html body div.xxgkml_list table#bbsTab.csgk tbody
        Elements elements = document.select("tr");
        for (int i = 1; i < elements.size(); i++) {
            Element element = elements.get(i);
            //String href = element.childNodes().get(5).attr("href");
            String hrefStr = element.childNodes().get(5).toString();
            int start = hrefStr.indexOf("<a href=\"../..");
            start += 14;
            int end = hrefStr.indexOf("\" target=");
            String href = hrefStr.substring(start, end);


            String text = element.text();
            String[] strings = text.split(" ");
            Policy policy = new Policy();
            //policy.setId(strings[0]);
            policy.setTitle(strings[1]);
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(strings[2]);
            policy.setPublish(date);
            policy.setType(strings[3]);
           // policyMapper.insert(policy);

           System.out.println();
        }
        for (Element element : elements) {
//            System.out.println(element.text());
            String jsonStr = element.text();
            // System.out.println();
            JSONArray parseArray = JSONArray.parseArray(jsonStr);
            parseArray.stream().forEach(p -> {

            });


            System.out.println();
        }
    }
}
