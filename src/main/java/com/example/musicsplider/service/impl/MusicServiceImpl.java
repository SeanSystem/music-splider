package com.example.musicsplider.service.impl;

import com.example.musicsplider.dao.MusicRepository;
import com.example.musicsplider.entity.MusicData;
import com.example.musicsplider.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 提供音乐链接的服务类
 *
 * @author Sean
 * @date 2019/08/27
 */
@Service
public class MusicServiceImpl implements MusicService {

    @Autowired
    private MusicRepository musicRepository;

    @Override
    public List<MusicData> search(SearchQuery query) {
        try {
            Page<MusicData> search = musicRepository.search(query);
            return search.getContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}