package com.example.musicsplider.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.io.Serializable;

/**
 * 音乐数据
 *
 * @author Sean
 * 2019/08/26
 */
@Document(indexName = "music",type = "data")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MusicData implements Serializable {

    @Id
    @Field(type = FieldType.Keyword)
    private String id;
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title;
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String url;
}
