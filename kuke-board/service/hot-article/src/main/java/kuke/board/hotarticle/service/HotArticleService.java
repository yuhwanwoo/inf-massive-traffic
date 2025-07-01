package kuke.board.hotarticle.service;

import kuke.board.hotarticle.client.ArticleClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class HotArticleService {
    private final ArticleClient articleClient;
}
