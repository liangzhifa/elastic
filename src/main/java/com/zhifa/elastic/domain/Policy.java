package com.zhifa.elastic.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "policy")
@Document(indexName = "policyindex", type = "policy", shards = 1, replicas = 0, createIndex = false)
public class Policy implements Serializable {

    @Id
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Field(type = FieldType.Keyword)
    @TableField(value = "articleId")
    private String articleid;

    //searchAnalyzer = "ik_max_word"
    @Field(type = FieldType.Text, searchAnalyzer = "ik_max_word", analyzer = "ik_max_word")
    @TableField(value = "title")
    private String title;

    @Field(type = FieldType.Keyword)
    @TableField(value = "url")
    private String url;


    /**
     *  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     *     @JsonFormat(shape =JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
     *     @Field(type = FieldType.Date,format = DateFormat.custom,pattern ="yyyy-MM-dd HH:mm:ss" )
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Field(type = FieldType.Date)
    @TableField(value = "publish")
    private Date publish;

    @Field(type = FieldType.Keyword)
    @TableField(value = "type")
    private String type;

    @Field(type = FieldType.Text, searchAnalyzer = "ik_smart", analyzer = "ik_smart")
    @TableField(value = "content")
    private String content;

    public static final String COL_ID = "id";

    public static final String COL_ARTICLEID = "articleId";

    public static final String COL_TITLE = "title";

    public static final String COL_URL = "url";

    public static final String COL_PUBLISH = "publish";

    public static final String COL_TYPE = "type";

    public static final String COL_CONTENT = "content";
}
