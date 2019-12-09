package com.zhifa.elastic;

import com.zhifa.elastic.domain.Info;
import com.zhifa.elastic.mapper.InfoMapper;
import com.zhifa.elastic.repository.InfoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InfoTest {

    @Autowired
    private InfoMapper infoMapper;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private InfoRepository infoRepository;


    /**
     * 创建索引
     */
    @Test
    public void createInfoIndex() {
        // 创建索引，会根据Item类的@Document注解信息来创建
        elasticsearchTemplate.createIndex(Info.class);
        // 配置映射，会根据Item类中的id、Field等字段来自动完成映射
        elasticsearchTemplate.putMapping(Info.class);
    }

    @Test
    public void findAll(){
        List<Info> infoList = infoMapper.selectInfosBetweenid(600000l, 620000l);
        infoRepository.saveAll(infoList);
        System.out.println();
        //infoRepository.search()
    }
}
