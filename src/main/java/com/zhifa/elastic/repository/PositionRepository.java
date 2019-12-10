package com.zhifa.elastic.repository;

import com.zhifa.elastic.domain.Position;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PositionRepository extends ElasticsearchRepository<Position, Long> {
}
