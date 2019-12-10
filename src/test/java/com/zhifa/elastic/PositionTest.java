package com.zhifa.elastic;


import com.zhifa.elastic.domain.Policy;
import com.zhifa.elastic.domain.Position;
import com.zhifa.elastic.mapper.InfoMapper;
import com.zhifa.elastic.mapper.PolicyMapper;
import com.zhifa.elastic.mapper.PositionMapper;
import com.zhifa.elastic.repository.InfoRepository;
import com.zhifa.elastic.repository.PolicyRepository;
import com.zhifa.elastic.repository.PositionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PositionTest {

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

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private PositionMapper positionMapper;


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
        elasticsearchTemplate.deleteIndex(Position.class);
        elasticsearchTemplate.createIndex(Position.class);
        // 配置映射，会根据Item类中的id、Field等字段来自动完成映射
        elasticsearchTemplate.putMapping(Position.class);
        List<Position> positions = positionMapper.selectList(null);
        positionRepository.saveAll(positions);

    }

    @Test
    public void format(){
        List<Position> positions = positionMapper.selectList(null);
        positions.forEach(position -> {
           /* String addr = position.getAddr();
            String[] split = addr.split("[ ]+");
            position.setAddr(split[0]);*/
           String price = position.getPrice();
            /*
            if (price.contains("天")){
                price = "6-9K";
            }*/
           // price=price.substring(0, price.length() - 1);
            String[] split = price.split("-");
            position.setPricemin(Long.valueOf(split[0]));
            position.setPricemax(Long.valueOf(split[1]));

           // position.setPrice(price);


            positionMapper.updateById(position);
        });
    }

    @Test
    public void add(){

    }

}
