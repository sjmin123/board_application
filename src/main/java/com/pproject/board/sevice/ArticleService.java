package com.pproject.board.sevice;

import com.pproject.board.dto.ArticleForm;
import com.pproject.board.entity.Article;
import com.pproject.board.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service //서비스 선언. (서비스 객체를 스프링 부트에 생성)
public class ArticleService {
    @Autowired //DI
    private ArticleRepository articleRepository;

    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) {
        Article article=dto.toEntity();
        if(article.getId()!=null){
            return null;
        }
        return articleRepository.save(article);
    }

    public Article update(Long id, ArticleForm dto) {
        // 1. 수정 엔티티 생성
        Article article=dto.toEntity();
        log.info("id: {}, article : {}",id,article.toString());

        // 2. 대상 엔티티를 조회
        Article target=articleRepository.findById(id).orElse(null);

        // 3. 잘못된 요철 처리(대상이 없거나, id가 다른 경우)
        if(target==null || id!=article.getId()){
            //400
            log.info("잘못된 요청! id: {}, article : {}",id,article.toString());
            return null;
        }

        // 4. 업데이트 및 정상 응답(200)
        target.patch(article);
        Article updated=articleRepository.save(target);
        return updated;
    }

    public Article delete(Long id) {
        // 1. 찾아오기
        Article target=articleRepository.findById(id).orElse(null);

        // 2.1. 잘못된 요청 처리
        if (target == null){
            return null;
        }

        // 2.2. 없애기
        articleRepository.delete(target);

        // 3. 반환
        return target;
    }

    @Transactional //해당 메소드를 트랜잭션으로 묶는다.
    public List<Article> createArticles(List<ArticleForm> dtos) {
        // dto묶음을 entity묶음으로 변환
        List<Article> articleList=dtos.stream().map(dto->dto.toEntity()).collect(Collectors.toList());

        //entity묶음을 DB로 저장
        articleList.stream().forEach(article->articleRepository.save(article));

        //강제 예외 발생
        articleRepository.findById(-1L).orElseThrow(
                ()->new IllegalArgumentException("결재 실패!")
        );

        //결과값 반환
        return articleList;
    }
}
