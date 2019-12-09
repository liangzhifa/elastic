package com.zhifa.elastic.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhifa.elastic.domain.Policy;
import com.zhifa.elastic.repository.PolicyRepository;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
         //builder.should(QueryBuilders.matchPhraseQuery("title",data).boost(30));
         //builder.should(QueryBuilders.matchQuery("content",data).boost(1));
        //模糊查询常见的5个方法如下
        //1.常用的字符串查询
        //QueryBuilders.queryStringQuery("fieldValue").field("fieldName");//左右模糊
        //2.常用的用于推荐相似内容的查询
        //QueryBuilders.moreLikeThisQuery(new String[] {"fieldName"}).addLikeText("pipeidhua");//如果不指定filedName，则默认全部，常用在相似内容的推荐上
        //3.前缀查询  如果字段没分词，就匹配整个字段前缀
        //QueryBuilders.prefixQuery("fieldName","fieldValue");
        //4.fuzzy query:分词模糊查询，通过增加fuzziness模糊属性来查询,如能够匹配hotelName为tel前或后加一个字母的文档，fuzziness 的含义是检索的term 前后增加或减少n个单词的匹配查询
        //QueryBuilders.fuzzyQuery("hotelName", "tel").fuzziness(Fuzziness.ONE);
        //5.wildcard query:通配符查询，支持* 任意字符串；？任意一个字符
        //QueryBuilders.wildcardQuery("fieldName","ctr*");//前面是fieldname，后面是带匹配字符的字符串
        //QueryBuilders.wildcardQuery("fieldName","c?r?");
        //builder.should(QueryBuilders.wildcardQuery("title", "*"+data+"*").boost(100));
        builder.should(QueryBuilders.queryStringQuery(data).field("title").boost(2f));
        builder.should(QueryBuilders.queryStringQuery(data).field("content").boost(1f));
       // builder.should(QueryBuilders.queryStringQuery(data).field("content").boost(1));
        //builder.should(QueryBuilders.wildcardQuery("title", data).boost(100));
        //添加查询的字段内容
        String[] fileds = {"title", "content"};

       // builder.should(QueryBuilders.multiMatchQuery(data, fileds));


        // 高亮设置
        List<String> highlightFields = new ArrayList<String>();
        highlightFields.add("title");
        highlightFields.add("content");
        HighlightBuilder.Field[] fields = new HighlightBuilder.Field[highlightFields.size()];
        for (int x = 0; x < highlightFields.size(); x++) {
            fields[x] = new HighlightBuilder.Field(highlightFields.get(x)).preTags("<span style='color: red'>").postTags("</span>");
        }
        //高亮
        HighlightBuilder.Field highlightField = new HighlightBuilder.Field("title")
                .preTags("<span style='color: red'>")
                .postTags("</span>");



        //设置分页(从第一页开始，一页显示10条)
        //注意开始是从0开始，有点类似sql中的方法limit 的查询
        PageRequest page = PageRequest.of(0, 20);


        //按照日期排序
        //FieldSortBuilder titleSort = SortBuilders.fieldSort("title").order(SortOrder.DESC);
        //FieldSortBuilder contentSort = SortBuilders.fieldSort("content").order(SortOrder.DESC);
        //FieldSortBuilder publishSort = SortBuilders.fieldSort().order(SortOrder.DESC);
        //SortBuilders.scoreSort().order(SortOrder.DESC).order();
        //Sort sort = new Sort(Sort.Direction.ASC,"field1").and(new Sort(Sort.Direction.DESC, "field2"));


        //2.构建查询
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        //将搜索条件设置到构建中
        nativeSearchQueryBuilder.withQuery(builder);
        nativeSearchQueryBuilder.withHighlightFields(fields);
        //nativeSearchQueryBuilder.withSort(publishSort);
        nativeSearchQueryBuilder.withSort(SortBuilders.scoreSort().order(SortOrder.DESC));
        nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("publish").order(SortOrder.DESC));
        //nativeSearchQueryBuilder.withHighlightBuilder();
        //将分页设置到构建中
        nativeSearchQueryBuilder.withPageable(page);




        //生产NativeSearchQuery
        NativeSearchQuery query = nativeSearchQueryBuilder.build();


        String string = builder.toString();

        System.out.println(string);

        //获得首次查询结果
        AggregatedPage<Policy> policies = elasticsearchTemplate.queryForPage(query, Policy.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> aClass, Pageable pageable) {
                List<Policy> list = new ArrayList<>();
                SearchHits hits = response.getHits();
                for (SearchHit hit : hits) {
                    String hitSourceAsString = hit.getSourceAsString();
                    Policy policy = JSONObject.parseObject(hitSourceAsString, Policy.class);
                    Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                    //匹配到的title字段里面的信息
                    HighlightField titleHighlight = highlightFields.get("title");
                    if (titleHighlight != null) {
                        Text[] fragments = titleHighlight.fragments();
                        String fragmentString = fragments[0].string();
                        policy.setTitle(fragmentString);
                    }
                    //匹配到的content字段里面的信息
                    HighlightField contentHighlight = highlightFields.get("content");
                    if (contentHighlight != null) {
                        Text[] fragments = contentHighlight.fragments();
                        String fragmentString = fragments[0].string();
                        policy.setContent(fragmentString);
                    }
                    list.add(policy);
                }
                if (list.size() > 0) {
                    return new AggregatedPageImpl<T>((List<T>) list);
                }
                return null;
            }
        });
        List<Policy> content = policies.getContent();
        return content;

        /*System.out.println("命中总数量:"+response.getHits().getTotalHits());
        SearchHits hits = response.getHits();
*/













       // Page<Policy> policyPage = policyRepository.search(query);

       // List<Policy> policies = policyPage.get().collect(Collectors.toList());

       /* AggregatedPage<Policy> policyAggregatedPage = elasticsearchTemplate.queryForPage(query, Policy.class, new SearchResultMapper() {

            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                List<Policy> content = new LinkedList<>();
                SearchHits hits = searchResponse.getHits();
                for (SearchHit hit : hits){
                    if (hits.totalHits <= 0){
                        return null;
                    }
                    Policy policy = new Policy();
                    policy.setId(Long.valueOf(hit.getId()));
                    Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                    System.out.println();
                    //policy.setTitle();

                }
                return null;
            }
        });
        List<Policy> content = policyAggregatedPage.getContent();*/




       // return policyPage;

    }

}
