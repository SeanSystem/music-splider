package com.example.musicsplider.utils;

import org.elasticsearch.index.query.*;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

/**
 * Es查询工具类
 *
 * @author Sean
 * 2019/09/02
 */
public final class EsQueryUtils {

    private EsQueryUtils() {
    }

    /**
     * 获取mathch查询
     *
     * @param filedName 文档字段
     * @param content   内容
     * @return matchQuery对象
     */
    public static NativeSearchQuery mathQuery(String filedName, Object content) {
        MatchQueryBuilder matchQuery = QueryBuilders.matchQuery(filedName, content);
        return new NativeSearchQueryBuilder().withQuery(matchQuery).build();
    }

    /**
     * 获取matchAll查询
     *
     * @return matchAllQuery对象
     */
    public static NativeSearchQuery mathAllQuery() {
        MatchAllQueryBuilder matchAllQuery = QueryBuilders.matchAllQuery();
        return new NativeSearchQueryBuilder().withQuery(matchAllQuery).build();
    }

    /**
     * 获取termQuery查询
     *
     * @param filedName 文档字段
     * @param content   内容
     * @return termQuery对象
     */
    public static NativeSearchQuery termQuery(String filedName, Object content) {
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery(filedName, content);
        return new NativeSearchQueryBuilder().withQuery(termQueryBuilder).build();
    }

    /**
     * 获取fuzzyQuery对象
     *
     * @param filedName 文档字段
     * @param content   内容
     * @return fuzzyQuery对象
     */
    public static NativeSearchQuery fuzzyQuery(String filedName, Object content) {
        FuzzyQueryBuilder fuzzyQueryBuilder = QueryBuilders.fuzzyQuery(filedName, content);
        return new NativeSearchQueryBuilder().withQuery(fuzzyQueryBuilder).build();
    }

    /**
     * 获取boolQuery对象
     *
     * @return
     */
    public static NativeSearchQuery boolQuery() {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        return new NativeSearchQueryBuilder().withQuery(boolQueryBuilder).build();
    }

    /**
     * 获取functionScoreQuery对象
     *
     * @param content 查询内容
     * @return functionScoreQuery对象
     */
    public static NativeSearchQuery functionScoreQuery(String content) {
        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(QueryBuilders.simpleQueryStringQuery(content));
        return new NativeSearchQueryBuilder().withQuery(functionScoreQueryBuilder).build();
    }

    /**
     * 获取rangeQuery对象
     *
     * @param fieldName 文档字段
     * @return rangeQuery对象
     */
    public static NativeSearchQuery rangeQuery(String fieldName) {
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(fieldName);
        return new NativeSearchQueryBuilder().withQuery(rangeQueryBuilder).build();
    }
}
