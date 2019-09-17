package com.example.musicsplider.service.impl;

import com.example.musicsplider.dao.MusicRepository;
import com.example.musicsplider.entity.AplayerMusicData;
import com.example.musicsplider.entity.MusicData;
import com.example.musicsplider.service.MusicService;
import com.example.musicsplider.vo.AplayerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public AplayerVO searchAplayer(SearchQuery query) {
        ArrayList<AplayerMusicData> list = new ArrayList<>();
        AplayerVO aplayerVO = new AplayerVO();
        try {
            Page<MusicData> search = musicRepository.search(query);
            List<MusicData> content = search.getContent();
            List<AplayerMusicData> aplayerMusicData = formatData(list, content);
            aplayerVO.setList(aplayerMusicData);
            aplayerVO.setTotal(search.getTotalElements());
           // aplayerVO.setTotal(search.get);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return aplayerVO;
    }

    @Override
    public AplayerVO getAplayerRandomMusic(Pageable pageable) {
        ArrayList<AplayerMusicData> list = new ArrayList<>();
        AplayerVO aplayerVO = new AplayerVO();
        try {
            Page<MusicData> all = musicRepository.findAll(pageable);
            List<MusicData> content = all.getContent();
            List<AplayerMusicData> aplayerMusicData = formatData(list, content);
            aplayerVO.setList(aplayerMusicData);
            aplayerVO.setTotal(all.getTotalElements());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return aplayerVO;
    }

    private List<AplayerMusicData> formatData(ArrayList<AplayerMusicData> list, List<MusicData> content) {
        for (MusicData music : content) {
            AplayerMusicData aplayerMusicData = new AplayerMusicData();
            aplayerMusicData.setName(music.getTitle());
            aplayerMusicData.setArtist("");
            aplayerMusicData.setUrl(music.getUrl());
            list.add(aplayerMusicData);
        }
        return list;
    }

    @Override
    public long getCount() {
        return musicRepository.count();
    }
}
