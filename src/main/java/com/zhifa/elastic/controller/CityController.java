package com.zhifa.elastic.controller;

import com.zhifa.elastic.domain.City;
import com.zhifa.elastic.dto.CityBean;
import com.zhifa.elastic.repository.CityRepository;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CityController {

    @Autowired
    private CityRepository cityRepository;



    @PostMapping("/addcity")
    public Object add(@RequestBody List<CityBean> cityBeanList){

        List<City> list = new ArrayList<>(500);
        List<CityBean> resCitys = new ArrayList<>(200);
        List<CityBean> beanList = getcitys(cityBeanList, resCitys);

        for (int i = 0; i < beanList.size(); i++) {
            CityBean bean = beanList.get(i);
            City city = new City();
            city.setId(Long.valueOf(i+1));
            city.setTitle(bean.getName());
            GeoPoint geoPoint = new GeoPoint(Double.valueOf(bean.getLat()), Double.valueOf(bean.getLog()));
            city.setLocation(geoPoint);
            list.add(city);
        }
        cityRepository.saveAll(list);

        return beanList;
    }

    private List<CityBean> getcitys(List<CityBean> cityBeanList,List<CityBean> resCitys){

        for (int i = 0; i < cityBeanList.size(); i++) {
            CityBean bean = cityBeanList.get(i);
            resCitys.add(bean);
            if (bean.getChildren()!=null&&bean.getChildren().size()>0){
                getcitys(bean.getChildren(), resCitys);

            }
        }
        return resCitys;
    }

    @RequestMapping("/city")
    public Object add(Double latitude, Double longitude){
       /* City city = new City();
        city.setId(1L);
        city.setTitle("背景ask叫大法黄金卡收到货副科级安徽省的接口");
        //需要特别注意的就是纬度在前边经度在后边（latitude,longitude），数组表示形式是经度在前纬度在后（[longitude,latitude]）
        GeoPoint geoPoint = new GeoPoint(39.662263,118.197815);
        city.setLocation(geoPoint);
        cityRepository.save(city);

        return null;*/

        BoolQueryBuilder builder = QueryBuilders.boolQuery();

        /*Double latitude = 39.662263;
        Double longitude = 117.197815;*/

        // 以某点为中心，搜索指定范围
        GeoDistanceQueryBuilder distanceQueryBuilder = new GeoDistanceQueryBuilder("location");
        distanceQueryBuilder.point(latitude, longitude);
        //查询单位：km
        distanceQueryBuilder.distance(5D, DistanceUnit.KILOMETERS);
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
        return search;
    }

    @RequestMapping("/getcity")
    public Object add(@RequestParam(value = "page", defaultValue = "1",required = false) Integer page,
                      @RequestParam(value = "size", defaultValue = "100",required = false) Integer size){



        //注意开始是从0开始，有点类似sql中的方法limit 的查询
        PageRequest pageRequest = PageRequest.of(page - 1, size);


        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        builder.should(QueryBuilders.matchAllQuery());
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        //将搜索条件设置到构建中
        nativeSearchQueryBuilder.withQuery(builder);
        //将分页设置到构建中
        nativeSearchQueryBuilder.withPageable(pageRequest);

        //生产NativeSearchQuery
        NativeSearchQuery query = nativeSearchQueryBuilder.build();
        Page<City> search = cityRepository.search(query);
        //System.out.println(search);
        return search;

    }

}
