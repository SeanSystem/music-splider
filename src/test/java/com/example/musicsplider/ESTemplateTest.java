package com.example.musicsplider;

import com.example.musicsplider.entity.Good;
import com.example.musicsplider.entity.MusicData;
import com.example.musicsplider.utils.EsQueryUtils;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

/**
 * ES模板类测试
 *
 * @author Sean
 * 2019/09/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ESTemplateTest {
    @Autowired
    private ElasticsearchTemplate template;


    @Test
    public void testIndex(){
        IndexQuery indexQuery = new IndexQuery();
        indexQuery.setObject(new Good("sdfdsf",12L));
        String index = template.index(indexQuery);
        System.out.println(index);
    }

    @Test
    public void testDelete(){
        String id = template.delete(Good.class, "dSuGRG0BOV1gX0PQVMit");
        System.out.println(id);
    }

    @Test
    public void testUpdate() throws IOException {
        UpdateRequest request = new UpdateRequest();
        request.doc(XContentFactory.jsonBuilder()
                .startObject()
                .field("price","30")
                .endObject()
        );
        UpdateQuery updateQuery = new UpdateQuery();
        updateQuery.setUpdateRequest(request);
        updateQuery.setClazz(Good.class);
        updateQuery.setId("2");
        UpdateResponse update = template.update(updateQuery);
        System.out.println(update.status());
    }

    @Test
    public void testMatch() {
        List<Good> list = template.queryForList(EsQueryUtils.mathQuery("price", "30"), Good.class);
        for (Good good : list) {
            System.out.println(good.toString());
        }
    }
}











