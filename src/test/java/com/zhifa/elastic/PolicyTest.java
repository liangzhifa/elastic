package com.zhifa.elastic;


import com.zhifa.elastic.domain.Info;
import com.zhifa.elastic.domain.Policy;
import com.zhifa.elastic.mapper.InfoMapper;
import com.zhifa.elastic.mapper.PolicyMapper;
import com.zhifa.elastic.repository.InfoRepository;
import com.zhifa.elastic.repository.PolicyRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PolicyTest {

    @Autowired
    private InfoMapper infoMapper;

    @Autowired
    private PolicyMapper policyMapper;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private InfoRepository infoRepository;

    @Autowired
    private PolicyRepository policyRepository;


    /**
     * 创建索引
     */
    @Test
    public void createInfoIndex() {
        // 创建索引，会根据Item类的@Document注解信息来创建

        elasticsearchTemplate.createIndex(Policy.class);
        // 配置映射，会根据Item类中的id、Field等字段来自动完成映射
        elasticsearchTemplate.putMapping(Policy.class);
    }
    @Test
    public void loadToES(){
        elasticsearchTemplate.deleteIndex(Policy.class);
        elasticsearchTemplate.createIndex(Policy.class);
        // 配置映射，会根据Item类中的id、Field等字段来自动完成映射
        elasticsearchTemplate.putMapping(Policy.class);
        List<Policy> policyList = policyMapper.selectList(null);
        policyRepository.saveAll(policyList);
    }

}
