package com.zhifa.elastic.repository;

import com.zhifa.elastic.domain.City;
import com.zhifa.elastic.domain.Info;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CityRepository extends ElasticsearchRepository<City, Long> {
}
