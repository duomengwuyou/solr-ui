package org.myapp.common.httpservice;

import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class CrawPage {
    private static Logger logger = Logger.getLogger(CrawPage.class);

    // 爬取网页信息
    private String doCrawlPage(String url) {
        // 创建HttpClientBuilder
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        // HttpClient
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        
        // 请求地址
        HttpGet httpGet = new HttpGet(url);

        UrlEncodedFormEntity entity;
        try {

            CloseableHttpResponse response = closeableHttpClient.execute(httpGet);
            // getEntity()
            HttpEntity httpEntity = response.getEntity();

            // 获取连接状态
            int statusCode = response.getStatusLine().getStatusCode();
            // 如果连接正常 处理返回信息
            if (statusCode == 200) {
                if (httpEntity != null) {
                    // 处理响应内容
                    String content = EntityUtils.toString(httpEntity, "UTF-8");
                    return parseHtml(content);
                }
            }

        } catch (Exception e) {
            logger.error("爬取网页信息有误：" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析html内容
     * 
     * @param htmlContent
     */
    private String parseHtml(String htmlContent) {
        float score = -1.0f;
        String reviewsNum = "";
        String oneRatingStarNum = "";
        String twoRatingStarNum = "";
        String threeRatingStarNum = "";
        String fourRatingStarNum = "";
        String fiveRatingStarNum = "";

        Document doc = Jsoup.parse(htmlContent);
        Elements ratingboxElements = doc.getElementsByClass("rating-box");
        if (ratingboxElements.size() != 1) {
            logger.error("获取网页内容解析class:rating-box div数目有误：" + ratingboxElements.size());
        } else {
            Element ratingBoxDiv = ratingboxElements.get(0);
            // 获取score分数
            Elements scoreElements = ratingBoxDiv.getElementsByClass("score");
            if (scoreElements.size() != 1) {
                logger.error("获取网页内容解析class:rating-box class:score div数目有误：" + scoreElements.size());
            } else {
                Element scoreDiv = scoreElements.get(0);
                score = Float.parseFloat(scoreDiv.text());
            }

            // 获取总评论数
            Elements reviewsNumElements = ratingBoxDiv.getElementsByClass("reviews-num");
            if (reviewsNumElements.size() != 1) {
                logger.error("获取网页内容解析class:rating-box class:reviews-num div数目有误：" + reviewsNumElements.size());
            } else {
                Element reviewNumDiv = reviewsNumElements.get(0);
                reviewsNum = reviewNumDiv.text();
            }

            // 获取五种评分的数目
            Elements fiveRatingStarDiv =
                    ratingBoxDiv.getElementsByClass("rating-bar-container").get(0).getElementsByClass("bar-number");
            fiveRatingStarNum = fiveRatingStarDiv.get(0).text();

            Elements fourRatingStarDiv =
                    ratingBoxDiv.getElementsByClass("rating-bar-container").get(1).getElementsByClass("bar-number");
            fourRatingStarNum = fourRatingStarDiv.get(0).text();

            Elements threeRatingStarDiv =
                    ratingBoxDiv.getElementsByClass("rating-bar-container").get(2).getElementsByClass("bar-number");
            threeRatingStarNum = threeRatingStarDiv.get(0).text();

            Elements twoRatingStarDiv =
                    ratingBoxDiv.getElementsByClass("rating-bar-container").get(3).getElementsByClass("bar-number");
            twoRatingStarNum = twoRatingStarDiv.get(0).text();

            Elements oneRatingStarDiv =
                    ratingBoxDiv.getElementsByClass("rating-bar-container").get(4).getElementsByClass("bar-number");
            oneRatingStarNum = oneRatingStarDiv.get(0).text();

            
            return "success";
        }

        return null;
    }
}
