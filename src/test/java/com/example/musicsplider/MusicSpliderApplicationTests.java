package com.example.musicsplider;

import com.example.musicsplider.dao.MusicRepository;
import com.example.musicsplider.entity.MusicData;
import com.example.musicsplider.service.MusicService;
import com.example.musicsplider.utils.EsQueryUtils;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MusicSpliderApplicationTests {

    @Autowired
    private MusicService musicService;

    @Autowired
    private MusicRepository repository;

    @Autowired
    private TransportClient transportClient;

    @Autowired
    private ElasticsearchTemplate template;

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

    @Test
    public void testRange(){
        NativeSearchQuery query = EsQueryUtils.rangeQuery("id");
       RangeQueryBuilder queryBuilder =  (RangeQueryBuilder)query.getQuery();
       queryBuilder.from("53454").to("53484");
        Page<MusicData> search = repository.search(query);
        System.out.println();
    }

    @Test
    public void testTransportClient(){
        GetResponse getResponse = transportClient.prepareGet("music", "data", "73423").execute().actionGet();
        Map<String, Object> source = getResponse.getSource();
        System.out.println("");
    }

    @Test
    public void testTemplate(){
       // UpdateQuery updateQuery = new UpdateQuery();
       // template.update(updateQuery);
        NativeSearchQuery query = EsQueryUtils.mathQuery("title", "周杰伦");
        List<MusicData> musicData = template.queryForList(query, MusicData.class);
        System.out.println("");
    }
}
