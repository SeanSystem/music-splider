package com.example.musicsplider.dao;

import com.example.musicsplider.entity.MusicData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * 音乐数据持久化层
 * @author Sean
 */
@Repository
public interface MusicRepository extends ElasticsearchRepository<MusicData,String> {
}
