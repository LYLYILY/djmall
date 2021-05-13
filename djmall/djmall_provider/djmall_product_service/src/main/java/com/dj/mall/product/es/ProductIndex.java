package com.dj.mall.product.es;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "product_info")
public class ProductIndex {

    //商品ID
    @Id
    @Field(name = "product_id")
    private String productId;

    //商品名称
    @Field(name = "product_name", analyzer = "keywords")
    private String productName;

    //商品类型
    @Field(name = "product_type", analyzer = "keywords")
    private String productType;

    //邮费
    private Double freight;

    //商品图片
    @Field(name = "product_img")
    private String productImg;

    //描述
    @Field(analyzer = "keywords")
    private String productDisc;

    //点赞量
    @Field(name = "like_num")
    private Integer likeNum;

    //商品类型名称
    @Field(name = "product_type_show")
    private String productTypeShow;

    //SKU价格
    @Field(name = "sku_price")
    private BigDecimal skuPrice;

    //sku库存
    @Field(name = "sku_count")
    private Integer skuCount;

    //SKU折扣,0表示无折扣
    @Field(name = "sku_rate")
    private Integer skuRate;

}
