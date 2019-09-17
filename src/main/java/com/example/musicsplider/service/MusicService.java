package com.example.musicsplider.service;

import com.example.musicsplider.entity.AplayerMusicData;
import com.example.musicsplider.entity.MusicData;
import com.example.musicsplider.vo.AplayerVO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import java.util.List;

public interface MusicService {
    /**
     * 查询音乐
     * @param query
     * @return
     */
    List<MusicData> search(SearchQuery query);

    /**
     * 查询aplayer播放器所需音乐数据
     * @param query
     * @return
     */
    AplayerVO searchAplayer(SearchQuery query);

    /**
     * 获取aplery播放器所需随机音乐
     * @param pageable
     * @return
     */
    AplayerVO getAplayerRandomMusic(Pageable pageable);

    /**
     * 获取歌曲总数
     * @return
     */
    long getCount();
}
