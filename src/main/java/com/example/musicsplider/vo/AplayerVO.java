package com.example.musicsplider.vo;

import com.example.musicsplider.entity.AplayerMusicData;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 封装aplayer所需音乐数据
 *
 * @author Sean
 * 2019/09/05
 */
@Data
@NoArgsConstructor
public class AplayerVO {

    List<AplayerMusicData> list;

    Long total;
}
