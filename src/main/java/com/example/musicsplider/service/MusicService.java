package com.example.musicsplider.service;

import com.example.musicsplider.entity.AplayerMusicData;
import com.example.musicsplider.entity.MusicData;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import java.util.List;

public interface MusicService {
    /**
     * 查询音乐
     * @param query
     * @return
     */
    List<MusicData> search(SearchQuery query);

    List<AplayerMusicData> searchAplayer(SearchQuery query);
}
