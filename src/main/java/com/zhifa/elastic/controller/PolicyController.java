package com.zhifa.elastic.controller;

import com.zhifa.elastic.domain.Policy;
import com.zhifa.elastic.repository.PolicyRepository;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PolicyController {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private PolicyRepository policyRepository;

    @RequestMapping("/policy/{data}")
    public Object getData(@PathVariable(value = "data") String data) {

        //1.创建QueryBuilder(即设置查询条件)这儿创建的是组合查询(也叫多条件查询),后面会介绍更多的查询方法
        /*组合查询BoolQueryBuilder
         * must(QueryBuilders)   :AND
         * mustNot(QueryBuilders):NOT
         * should:               :OR
         */
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        QueryStringQueryBuilder queryStringQueryBuilder = QueryBuilders.queryStringQuery(data);


        //builder下有must、should以及mustNot 相当于sql中的and、or以及not
        //组合查询，boost即为权重，数值越大，权重越大
         builder.should(QueryBuilders.fuzzyQuery("title",data).boost(30));
         builder.should(QueryBuilders.matchQuery("content",data).boost(1));
        //添加查询的字段内容
        String[] fileds = {"title", "content"};

       // builder.should(QueryBuilders.multiMatchQuery(data, fileds));


        // 高亮设置
        List<String> highlightFields = new ArrayList<String>();
        highlightFields.add("title");
        highlightFields.add("content");
        HighlightBuilder.Field[] fields = new HighlightBuilder.Field[highlightFields.size()];
        for (int x = 0; x < highlightFields.size(); x++) {
            fields[x] = new HighlightBuilder.Field(highlightFields.get(x)).preTags("").postTags("");
        }
        //高亮
        HighlightBuilder.Field highlightField = new HighlightBuilder.Field("title")
                .preTags("<span style='color: red'>")
                .postTags("</span>");



        //设置分页(从第一页开始，一页显示10条)
        //注意开始是从0开始，有点类似sql中的方法limit 的查询
        PageRequest page = PageRequest.of(0, 20, Sort.by(Sort.Order.desc("publish")));


        //按照日期排序
        //FieldSortBuilder titleSort = SortBuilders.fieldSort("title").order(SortOrder.DESC);
        //FieldSortBuilder contentSort = SortBuilders.fieldSort("content").order(SortOrder.DESC);
        FieldSortBuilder publishSort = SortBuilders.fieldSort("publish").order(SortOrder.DESC);
        //SortBuilders.scoreSort().order(SortOrder.DESC).order();
        //Sort sort = new Sort(Sort.Direction.ASC,"field1").and(new Sort(Sort.Direction.DESC, "field2"));


        //2.构建查询
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        //将搜索条件设置到构建中
        nativeSearchQueryBuilder.withQuery(builder);
        nativeSearchQueryBuilder.withHighlightFields(fields);
        //nativeSearchQueryBuilder.withHighlightBuilder();
        //将分页设置到构建中
        nativeSearchQueryBuilder.withPageable(page);

        nativeSearchQueryBuilder.withSort(publishSort);


        //生产NativeSearchQuery
        NativeSearchQuery query = nativeSearchQueryBuilder.build();


        String string = builder.toString();

        System.out.println(string);

        Page<Policy> policyPage = policyRepository.search(query);
       // List<Policy> policies = policyPage.get().collect(Collectors.toList());

        //List<Policy> list = elasticsearchTemplate.queryForList(query, Policy.class);


        return policyPage;

    }

}
