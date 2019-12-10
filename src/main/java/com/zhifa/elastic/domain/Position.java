package com.zhifa.elastic.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "position")
@Document(indexName = "positionindex", type = "position", shards = 1, replicas = 0, createIndex = false)
public class Position implements Serializable {

    @Id
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Field(type = FieldType.Text, searchAnalyzer = "ik_max_word", analyzer = "ik_max_word")
    @TableField(value = "position")
    private String position;

    @Field(type = FieldType.Text, searchAnalyzer = "ik_smart", analyzer = "ik_smart")
    @TableField(value = "enterprise")
    private String enterprise;

    @Field(type = FieldType.Text, searchAnalyzer = "ik_smart", analyzer = "ik_smart")
    @TableField(value = "companytext")
    private String companytext;


    @Field(type = FieldType.Keyword,index = false)
    @TableField(value = "url")
    private String url;

    @Field(type = FieldType.Keyword)
    @TableField(value = "addr")
    private String addr;

    @Field(type = FieldType.Long)
    @TableField(value = "pricemin")
    private Long pricemin;

    @Field(type = FieldType.Long)
    @TableField(value = "pricemax")
    private Long pricemax;

    @Field(type = FieldType.Keyword)
    @TableField(value = "price")
    private String price;

    public static final String COL_ID = "id";

    public static final String COL_POSITION = "position";

    public static final String COL_ENTERPRISE = "enterprise";

    public static final String COL_COMPANYTEXT = "companytext";

    public static final String COL_URL = "url";

    public static final String COL_ADDR = "addr";

    public static final String COL_PRICEMIN = "pricemin";

    public static final String COL_PRICEMAX = "pricemax";

    public static final String COL_PRICE = "price";
}
