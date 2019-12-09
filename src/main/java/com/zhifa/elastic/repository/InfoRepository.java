package com.zhifa.elastic.repository;

import com.zhifa.elastic.domain.Info;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface InfoRepository extends ElasticsearchRepository<Info, Long> {
}
