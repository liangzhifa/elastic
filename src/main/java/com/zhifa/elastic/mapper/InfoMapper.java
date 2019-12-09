package com.zhifa.elastic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhifa.elastic.domain.Info;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InfoMapper extends BaseMapper<Info> {

    List<Info> selectInfosBetweenid(@Param("start") Long start, @Param("end") Long end);

}
