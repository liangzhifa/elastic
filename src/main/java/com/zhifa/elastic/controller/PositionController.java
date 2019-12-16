package com.zhifa.elastic.controller;

import com.zhifa.elastic.domain.Position;
import com.zhifa.elastic.repository.PolicyRepository;
import com.zhifa.elastic.repository.PositionRepository;
import com.zhifa.elastic.vo.PageResult;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PositionController {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private PositionRepository positionRepository;

    @PostMapping("/position")
    public Object getData(@RequestBody Position position,
                          @RequestParam(value = "page") Integer page,
                          @RequestParam(value = "size") Integer size) {

        /*
        *
        * {
            "position":"php 后台",
            "enterprise":"高德地图",
            "addr":"北京",
            "pricemax":"20"
        }
        *
        * */


        //设置分页(从第一页开始，一页显示10条)
        //注意开始是从0开始，有点类似sql中的方法limit 的查询
        PageRequest pageRequest = PageRequest.of(page - 1, size);

        //1.创建QueryBuilder(即设置查询条件)这儿创建的是组合查询(也叫多条件查询),后面会介绍更多的查询方法
        /*组合查询BoolQueryBuilder
         * must(QueryBuilders)   :AND
         * mustNot(QueryBuilders):NOT
         * should:               :OR
         */
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        //builder.should(QueryBuilders.boolQuery());
        if (!StringUtils.isEmpty(position.getPosition())) {
            builder.must(QueryBuilders.queryStringQuery(position.getPosition()).field("position"));
        }
        if (!StringUtils.isEmpty(position.getEnterprise())) {
            builder.must(QueryBuilders.matchQuery("enterprise", position.getEnterprise()));
        }
        if (!StringUtils.isEmpty(position.getAddr())) {
            builder.must(QueryBuilders.termQuery("addr", position.getAddr()));
        }
        //资薪范围是否有交集
        if (!StringUtils.isEmpty(position.getPricemax())) {
            builder.must(QueryBuilders.rangeQuery("pricemin").lte(position.getPricemax()));
        }

        /*QueryBuilders.termsQuery(, );*/


        //2.构建查询
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        //将搜索条件设置到构建中
        nativeSearchQueryBuilder.withQuery(builder);

        nativeSearchQueryBuilder.withSort(SortBuilders.scoreSort().order(SortOrder.DESC));
        //将分页设置到构建中
        nativeSearchQueryBuilder.withPageable(pageRequest);

        //生产NativeSearchQuery
        NativeSearchQuery query = nativeSearchQueryBuilder.build();

        String string = builder.toString();

        System.out.println(string);

        Page<Position> positions = positionRepository.search(query);


        return new PageResult(positions.getTotalElements(), positions.getContent());
    }

}
