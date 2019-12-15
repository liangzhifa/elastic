package com.zhifa.elastic.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@ToString
@Data
public class CityBean implements Serializable {

    /**
     * name : 北京市
     * log : 116.46
     * lat : 39.92
     * children : [{"name":"北京","log":"116.46","lat":"39.92"},{"name":"平谷","log":"117.1","lat":"40.13"},{"name":"密云","log":"116.85","lat":"40.37"},{"name":"顺义","log":"116.65","lat":"40.13"},{"name":"通县","log":"116.67","lat":"39.92"},{"name":"怀柔","log":"116.62","lat":"40.32"},{"name":"大兴","log":"116.33","lat":"39.73"},{"name":"房山","log":"115.98","lat":"39.72"},{"name":"延庆","log":"115.97","lat":"40.47"},{"name":"昌平","log":"116.2","lat":"40.22"}]
     */

    private String name;
    private String log;
    private String lat;
    private List<CityBean> children;


}
