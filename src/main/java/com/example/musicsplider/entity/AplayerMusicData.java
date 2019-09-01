package com.example.musicsplider.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * aplayer播放器数据格式
 *
 * @author Sean
 * @date 2019/08/27
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AplayerMusicData {

    private String name;

    private String artist;

    private String url;
}
