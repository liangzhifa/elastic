package com.zhifa.elastic;

import cn.hutool.core.util.RandomUtil;
import com.zhifa.elastic.domain.City;
import com.zhifa.elastic.repository.CityRepository;
import com.zhifa.elastic.util.NameRandon;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CityTest {


    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    @Autowired
    private CityRepository cityRepository;


    /**
     * 创建索引
     */
    @Test
    public void createCityIndex() {
        elasticsearchTemplate.deleteIndex(City.class);
        // 创建索引，会根据Item类的@Document注解信息来创建
        elasticsearchTemplate.createIndex(City.class);
        // 配置映射，会根据Item类中的id、Field等字段来自动完成映射
        elasticsearchTemplate.putMapping(City.class);
    }


    @Test
    public void add() {
        City city = new City();
        city.setId(1L);
        city.setTitle("背景ask叫大法黄金卡收到货副科级安徽省的接口");
        //需要特别注意的就是纬度在前边经度在后边（latitude,longitude），数组表示形式是经度在前纬度在后（[longitude,latitude]）
        GeoPoint geoPoint = new GeoPoint(39.662263, 118.197815);
        city.setLocation(geoPoint);
        cityRepository.save(city);

    }


    @Test
    public void rand() {

        for (int i = 0; i < 100; i++) {
            double d = 50 + Math.random() * 200 % (200 - 50 + 1);

            System.out.println(d);
        }

    }

    @Test
    public void addAll() {
       List<City> list = new ArrayList<>(101);

       for (int i = 1; i <= 300; i++) {
           City city = new City();

            city.setId(Long.valueOf(i));
            String randomLonLat = NameRandon.randomLonLat(39.662263, 39.662863, 118.197315, 118.197815);
            String[] split = randomLonLat.split(",");
            city.setTitle(NameRandon.init());
            //需要特别注意的就是纬度在前边经度在后边（latitude,longitude），数组表示形式是经度在前纬度在后（[longitude,latitude]）
            //要获取一个[x,y]的double类型的随机数 | 左闭右闭
            //double lat = 150 + Math.random() * 200 % (200 - 50 + 1);
           // double lon = 150 + Math.random() * 200 % (200 - 50 + 1);
            GeoPoint geoPoint = new GeoPoint(Double.valueOf(split[0]), Double.valueOf(split[1]));
            city.setLocation(geoPoint);
           list.add(city);
           //cityRepository.save(city);

        }

        /*city.setId(200L);
        city.setTitle("可见到过快捷方式打不开结构很难开机快年北京");
        GeoPoint geoPoint = new GeoPoint(63.083764, 56.269295);
        city.setLocation(geoPoint);*/
        cityRepository.saveAll(list);
    }


    @Test
    public void get() {

       /* BoolQueryBuilder builder = QueryBuilders.boolQuery();

        Double latitude = 39.662263;
        Double longitude = 117.197815;

        // 以某点为中心，搜索指定范围
        GeoDistanceQueryBuilder distanceQueryBuilder = new GeoDistanceQueryBuilder("location");
        distanceQueryBuilder.point(latitude, longitude);
        //查询单位：km
        distanceQueryBuilder.distance(2D, DistanceUnit.KILOMETERS);
        builder.filter(distanceQueryBuilder);


        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        //将搜索条件设置到构建中
        nativeSearchQueryBuilder.withQuery(builder);

        // 按距离升序
        GeoDistanceSortBuilder distanceSortBuilder = new GeoDistanceSortBuilder("location", latitude, longitude);
        distanceSortBuilder.unit(DistanceUnit.KILOMETERS);
        distanceSortBuilder.order(SortOrder.ASC);
        nativeSearchQueryBuilder.withSort(distanceSortBuilder);

        //生产NativeSearchQuery
        NativeSearchQuery query = nativeSearchQueryBuilder.build();
        Page<City> search = cityRepository.search(query);
        System.out.println(search);
*/

    }
}
