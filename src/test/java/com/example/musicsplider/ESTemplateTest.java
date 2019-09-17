package com.example.musicsplider;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * ES模板对象测试
 *
 * @author Sean
 * @date 2019/09/17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ESTemplateTest {

    @Autowired
    private ElasticsearchTemplate template;

    @Test
    public void testAdd(){

    }
}
