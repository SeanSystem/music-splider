package com.example.musicsplider;

import com.example.musicsplider.dao.MusicRepository;
import com.example.musicsplider.entity.AplayerMusicData;
import com.example.musicsplider.entity.MusicData;
import com.example.musicsplider.service.MusicService;
import com.example.musicsplider.utils.EsQueryUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.valuecount.ValueCountAggregationBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.annotation.AccessType;
import org.springframework.data.domain.Page;
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
    }

    @Test
    public void testAggs(){
        NativeSearchQuery query = EsQueryUtils.mathQuery("title", "周杰伦");
        //聚合操作
        query.addAggregation(AggregationBuilders.count("count_num").field("id"));
        Page<MusicData> search = repository.search(query);
        System.out.println();
    }

}
