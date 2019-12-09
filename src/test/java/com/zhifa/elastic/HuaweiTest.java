package com.zhifa.elastic;

import com.zhifa.elastic.tempModel.HuaWeiPhone;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HuaweiTest {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    /**
     * 创建索引
     */
    @Test
    public void createIndex() {
        // 创建索引，会根据Item类的@Document注解信息来创建
        elasticsearchTemplate.createIndex(HuaWeiPhone.class);
        // 配置映射，会根据Item类中的id、Field等字段来自动完成映射
        elasticsearchTemplate.putMapping(HuaWeiPhone.class);
    }

}
