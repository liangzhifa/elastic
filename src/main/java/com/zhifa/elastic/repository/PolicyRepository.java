package com.zhifa.elastic.repository;

import com.zhifa.elastic.domain.Policy;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PolicyRepository extends ElasticsearchRepository<Policy, Long> {
}
