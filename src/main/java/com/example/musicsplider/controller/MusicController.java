package com.example.musicsplider.controller;

import com.example.musicsplider.entity.MusicData;
import com.example.musicsplider.service.MusicService;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.SimpleQueryStringBuilder;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    private static final Integer DEFAULT_PAGE_SIZE = 20;

    @GetMapping("/search")
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

    private SearchQuery getSearchQuery(Integer pageNum, Integer pageSize, String queryString) {
        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(new SimpleQueryStringBuilder(queryString)
                .field("title"));
        PageRequest pageRequest = PageRequest.of(pageNum, pageSize);
        return new NativeSearchQueryBuilder().withPageable(pageRequest).withQuery(functionScoreQueryBuilder).build();
    }
}
