package com.example.musicsplider.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

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
    private String id;

    private String title;

    private String url;
}
