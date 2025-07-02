package kuke.board.hotarticle.client;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;

@Component
public class ArticleClient {
    private static final Logger log = LoggerFactory.getLogger(ArticleClient.class);
    private RestClient restClient;
    @Value("${endpoints.kuke-board-article-service.url}")
    private String articleServiceUrl;

    @PostConstruct
    void initRestClient() {
        restClient = RestClient.create(articleServiceUrl);
    }

    public ArticleResponse read(Long articleId) {
        try {
            return restClient.get()
                    .uri("/v1/articles/{articleId}", articleId)
                    .retrieve()
                    .body(ArticleResponse.class);
        } catch (Exception e) {
            log.error("[ArticleClient.read] articleId: {}", articleId, e);
        }
        return null;
    }

    @Getter
    public static class ArticleResponse {
        private Long articleId;
        private String title;
        private LocalDateTime createdAt;
    }
}
