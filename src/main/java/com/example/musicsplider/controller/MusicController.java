package com.example.musicsplider.controller;

import com.example.musicsplider.entity.MusicData;
import com.example.musicsplider.service.MusicService;
import com.example.musicsplider.thread.MusicFetchThread;
import com.example.musicsplider.utils.EsQueryUtils;
import com.example.musicsplider.vo.AplayerVO;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.SimpleQueryStringBuilder;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Random;

/**
 * 音乐查询接口
 *
 * @author Sean
 * @date 2019/08/27
 */
@RestController
@RequestMapping("/hugeMusic")
public class MusicController {

    @Autowired
    private MusicService musicService;
    @Autowired
    private MusicFetchThread musicFetchThread;

    private static final Integer DEFAULT_PAGE_SIZE = 20;

    @GetMapping("/search")
    @CrossOrigin
    public List<MusicData> search(Integer pageNum, Integer pageSize, String queryString) {
        if (null == pageNum) {
            pageNum = 0;
        }
        if (null == pageSize) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        SearchQuery searchQuery = getSearchQuery(pageNum, pageSize, queryString);
        return musicService.search(searchQuery);
    }

    @GetMapping("/searchAplayer")
    @CrossOrigin
    public AplayerVO searchAplayer(Integer pageNum, Integer pageSize, String queryString){
        if (null == pageNum) {
            pageNum = 0;
        }
        if (null == pageSize) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        NativeSearchQuery query = EsQueryUtils.mathQuery("title", queryString);
        PageRequest pageRequest = PageRequest.of(pageNum, pageSize);
        query.setPageable(pageRequest);
        return musicService.searchAplayer(query);
    }

    @GetMapping("/getRandom")
    @CrossOrigin
    public AplayerVO getRandomMusic(Integer pageSize){
        if(pageSize == null){
            pageSize = DEFAULT_PAGE_SIZE;
        }
        Integer pageNum = new Random().nextInt(100);
        PageRequest pageRequest = PageRequest.of(pageNum, pageSize);
        return musicService.getAplayerRandomMusic(pageRequest);
    }

    @GetMapping("fetchMusic")
    @CrossOrigin
    public String fetchMusic()
    {
        musicFetchThread.start();
        return "success";
    }

    @GetMapping("/count")
    @CrossOrigin
    public long getCount(){
        return musicService.getCount();
    }

    private SearchQuery getSearchQuery(Integer pageNum, Integer pageSize, String queryString) {
        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(new SimpleQueryStringBuilder(queryString)
                .field("title"));
        PageRequest pageRequest = PageRequest.of(pageNum, pageSize);
        return new NativeSearchQueryBuilder().withPageable(pageRequest).withQuery(functionScoreQueryBuilder).build();
    }
}
