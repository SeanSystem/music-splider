package com.example.musicsplider.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 测试类
 *
 * @author Sean
 * 2019/09/18
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Document(indexName = "lib1", type = "good")
public class Good {

    @Field(type = FieldType.Text)
    private String productID;
    @Field(type = FieldType.Long)
    private Long price;
}
