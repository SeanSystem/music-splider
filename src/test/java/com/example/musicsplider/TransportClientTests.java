package com.example.musicsplider;

import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.avg.Avg;
import org.elasticsearch.search.aggregations.metrics.avg.AvgAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.cardinality.Cardinality;
import org.elasticsearch.search.aggregations.metrics.cardinality.CardinalityAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.max.Max;
import org.elasticsearch.search.aggregations.metrics.max.MaxAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.min.Min;
import org.elasticsearch.search.aggregations.metrics.min.MinAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.aggregations.metrics.sum.SumAggregationBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * es原生方法测试类
 *
 * @author Sean
 * 2019/09/14
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TransportClientTests {

    @Autowired
    private TransportClient transportClient;

    @Test
    public void testAdd() throws IOException {
        XContentBuilder doc = XContentFactory.jsonBuilder()
                .startObject()
                .field("id", "1")
                .field("title", "java开发教程")
                .field("postdate", "2019-09-14")
                .endObject();
        IndexResponse indexResponse = transportClient.prepareIndex("index", "blog", "1").setSource(doc).get();

        System.out.println(indexResponse.status());
    }

    @Test
    public void testDelete() {
        DeleteResponse deleteResponse = transportClient.prepareDelete("index", "blog", "1").get();
        System.out.println(deleteResponse.status());
    }

    @Test
    public void testUpdate() throws IOException {
        XContentBuilder doc = XContentFactory.jsonBuilder()
                .startObject()
                .field("title", "php开发教程")
                .endObject();
        UpdateResponse updateResponse = transportClient.prepareUpdate("index", "blog", "1").setDoc(doc).get();
        System.out.println(updateResponse.status());
    }

    @Test
    public void testUpdate2() throws IOException, ExecutionException, InterruptedException {
        UpdateRequest request = new UpdateRequest("index", "blog", "1");
        request.doc(
                XContentFactory.jsonBuilder().startObject()
                        .field("title", "java开发教程")
                        .endObject()
        );
        UpdateResponse updateResponse = transportClient.update(request).get();
        System.out.println(updateResponse.status());
    }

    @Test
    public void testUpsert() throws IOException, ExecutionException, InterruptedException {

        IndexRequest indexRequest = new IndexRequest("index", "blog", "1");
        indexRequest.source(
                XContentFactory.jsonBuilder().startObject()
                        .field("id", "1")
                        .field("title", "php开发教程")
                        .field("postdate", "2019-09-14")
                        .endObject()
        );
        UpdateRequest request = new UpdateRequest("index", "blog", "1");
        request.doc(
                XContentFactory.jsonBuilder().startObject()
                        .field("title", "java开发教程")
                        .endObject()
        ).upsert(indexRequest);
        UpdateResponse updateResponse = transportClient.update(request).get();
        System.out.println(updateResponse.status());
    }

    @Test
    public void testMget() {
        MultiGetResponse multiGetItemResponses = transportClient.prepareMultiGet()
                .add("index", "blog", "1")
                .add("music", "data", "73423")
                .get();
        MultiGetItemResponse[] responses = multiGetItemResponses.getResponses();
        for (MultiGetItemResponse itemResponse : responses) {
            Map<String, Object> source = itemResponse.getResponse().getSource();
            System.out.println(source.toString());
        }
    }

    @Test
    public void testBulk() throws IOException {
        IndexRequest indexRequest = new IndexRequest("index", "blog", "2");
        indexRequest.source(
                XContentFactory.jsonBuilder().startObject()
                        .field("id", "2")
                        .field("title", "php开发教程")
                        .field("postdate", "2019-09-14")
                        .endObject()
        );
        UpdateRequest request = new UpdateRequest("index", "blog", "1");
        request.doc(
                XContentFactory.jsonBuilder().startObject()
                        .field("title", "java开发教程")
                        .endObject()
        );
        BulkResponse bulkItemResponses = transportClient.prepareBulk()
                .add(indexRequest)
                .add(request)
                .get();
        BulkItemResponse[] items = bulkItemResponses.getItems();
        for (BulkItemResponse item : items) {
            RestStatus status = item.status();
            System.out.println(status);
        }
        if (bulkItemResponses.hasFailures()) {
            String s = bulkItemResponses.buildFailureMessage();
            System.out.println(s);
        }
    }

    @Test
    public void testDeleteByQuery() {
        BulkByScrollResponse bulkByScrollResponse = DeleteByQueryAction.INSTANCE
                .newRequestBuilder(transportClient)
                .filter(QueryBuilders.matchQuery("title", "教程"))
                .source("index")
                .get();
        long counts = bulkByScrollResponse.getDeleted();
        System.out.println(counts);
    }

    @Test
    public void testMatch() {
        SearchResponse searchResponse = transportClient.prepareSearch("music").setQuery(QueryBuilders.matchQuery("title", "周杰伦"))
                .setSize(10)
                .get();
        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();
            System.out.println(sourceAsString);
        }
    }

    @Test
    public void testMultiMatch(){
        SearchResponse searchResponse = transportClient.prepareSearch("music")
                .setQuery(QueryBuilders.multiMatchQuery("周杰伦", "title", "url"))
                .get();
        SearchHits hits = searchResponse.getHits();
        for(SearchHit hit : hits){
            String sourceAsString = hit.getSourceAsString();
            System.out.println(sourceAsString);
        }
    }

    @Test
    public void testTerm(){
        SearchResponse searchResponse = transportClient.prepareSearch("music")
                .setQuery(QueryBuilders.termQuery("id", "73423"))
                .get();
        SearchHits hits = searchResponse.getHits();
        for(SearchHit hit : hits){
            String sourceAsString = hit.getSourceAsString();
            System.out.println(sourceAsString);
        }
    }

    @Test
    public void testTerms(){
        SearchResponse searchResponse = transportClient.prepareSearch("music")
                .setQuery(QueryBuilders.termsQuery("id", "73423", "35681"))
                .get();
        SearchHits hits = searchResponse.getHits();
        for(SearchHit hit : hits){
            String sourceAsString = hit.getSourceAsString();
            System.out.println(sourceAsString);
        }
    }

    @Test
    public void testRange(){
        SearchResponse searchResponse = transportClient.prepareSearch("music")
                .setQuery(QueryBuilders.rangeQuery("id").from("35681").to("35691"))
                .get();
        SearchHits hits = searchResponse.getHits();
        for(SearchHit hit : hits){
            String sourceAsString = hit.getSourceAsString();
            System.out.println(sourceAsString);
        }
    }

    @Test
    public void testAgg(){
        MaxAggregationBuilder maxAggregationBuilder = AggregationBuilders.max("aggMax").field("price");
        MinAggregationBuilder minAggregationBuilder = AggregationBuilders.min("aggMin").field("price");
        SumAggregationBuilder aggSum = AggregationBuilders.sum("aggSum").field("price");
        AvgAggregationBuilder avgAggregationBuilder = AggregationBuilders.avg("aggAvg").field("price");
        CardinalityAggregationBuilder cardinalityAggregationBuilder = AggregationBuilders.cardinality("aggCardinality").field("price");
        SearchResponse searchResponse = transportClient.prepareSearch("lib1")
                .addAggregation(maxAggregationBuilder)
                .addAggregation(minAggregationBuilder)
                .addAggregation(aggSum)
                .addAggregation(avgAggregationBuilder)
                .addAggregation(cardinalityAggregationBuilder)
                .get();
        Max max = searchResponse.getAggregations().get("aggMax");
        Min min = searchResponse.getAggregations().get("aggMin");
        Sum sum = searchResponse.getAggregations().get("aggSum");
        Avg avg = searchResponse.getAggregations().get("aggAvg");
        Cardinality cardinality = searchResponse.getAggregations().get("aggCardinality");
        double value = max.getValue();
        double value1 = min.getValue();
        double value2 = sum.getValue();
        double value3 = avg.getValue();
        long value4 = cardinality.getValue();
        System.out.println(value+ " " + value1 + " " + value2+ " " + value3+ " "+value4);
    }

    @Test
    public void testBool(){
        SearchResponse searchResponse = transportClient.prepareSearch("lib1")
                .setQuery(QueryBuilders.boolQuery()
                        .must(QueryBuilders.termQuery("price", "20"))
                        .mustNot(QueryBuilders.termsQuery("productID", "1234sw"))
                )
                .get();
        SearchHits hits = searchResponse.getHits();
        for (SearchHit hit : hits){
            String sourceAsString = hit.getSourceAsString();
            System.out.println(sourceAsString);
        }
    }

    @Test
    public void testBucket(){
        TermsAggregationBuilder field = AggregationBuilders.terms("aggTerms").field("price");
        SearchResponse searchResponse = transportClient.prepareSearch("lib1").addAggregation(field).get();
        Terms terms = searchResponse.getAggregations().get("aggTerms");
        List<? extends Terms.Bucket> buckets = terms.getBuckets();
        for (Terms.Bucket bucket : buckets){
            System.out.println(bucket.getKeyAsString()+" "+bucket.getDocCount());
        }
    }

    @Test
    public void testMultiBucket(){
        SumAggregationBuilder sumAggregationBuilder = AggregationBuilders.sum("sum_price").field("price");
        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("term_price").field("price").subAggregation(sumAggregationBuilder);
        SearchResponse searchResponse = transportClient.prepareSearch("lib1").addAggregation(termsAggregationBuilder).get();
        Terms terms = searchResponse.getAggregations().get("term_price");
        List<? extends Terms.Bucket> buckets = terms.getBuckets();
        for(Terms.Bucket bucket : buckets){
            String key = bucket.getKeyAsString();
            long docCount = bucket.getDocCount();
            Sum sum = bucket.getAggregations().get("sum_price");
            double value = sum.getValue();
            System.out.println("价格为"+key+"的商品数量为"+docCount+"该价格商品的总价为"+value);
        }
    }

}
