package com.example.musicsplider;

import com.example.musicsplider.dao.MusicRepository;
import com.example.musicsplider.entity.AplayerMusicData;
import com.example.musicsplider.entity.MusicData;
import com.example.musicsplider.service.MusicService;
import com.example.musicsplider.utils.EsQueryUtils;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.valuecount.ValueCountAggregationBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.annotation.AccessType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MusicSpliderApplicationTests {

    @Autowired
    private MusicService musicService;

    @Autowired
    private MusicRepository repository;

    @Test
    public void contextLoads() {
        NativeSearchQuery query = EsQueryUtils.functionScoreQuery("周杰伦");
        List<AplayerMusicData> aplayerMusicData = musicService.searchAplayer(query);
        System.out.println("");
    }

    @Test
    public void testAggs(){

    }

}
