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

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "info")
@Document(indexName = "userindex", type = "info", shards = 1, replicas = 0,createIndex = false)
public class Info {
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    @TableField(value = "keyword")
    private String keyword;

    @TableField(value = "url")
    private String url;



    public static final String COL_ID = "id";

    public static final String COL_KEYWORD = "keyword";

    public static final String COL_URL = "url";
}
