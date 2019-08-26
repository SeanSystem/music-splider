package com.example.musicsplider.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 音乐数据
 *
 * @author Sean
 * 2019/08/26
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MusicData {

    private String id;

    private String title;

    private String url;
}
