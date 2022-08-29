package com.pproject.board.sevice;

import com.pproject.board.dto.ArticleForm;
import com.pproject.board.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // 해당 클래스는 스프링부트와 연동되어 테스팅된다.
class ArticleServiceTest {

    @Autowired ArticleService articleService;

    @Test
    void index() {
        //예상
        Article a=new Article(1L,"가가가가","1111","asdf");
        Article b=new Article(2L,"가가가가","1111","asdf");
        Article c=new Article(3L,"가가가가","1111","asdf");

        //실제
        List<Article> articles=articleService.index();

        //비교
    }

    @Test
    void show_성공() {
        //예상
        Long id =15L;
        Article expected=new Article(id,"가가가가","1234","asdf");

        //실제
        Article article=articleService.show(id);

        //비교
        assertEquals(expected.toString(),article.toString());
    }
    @Test
    void show_실패() {
        //예상
        Long id =-1L;
        Article expected=null;

        //실제
        Article article=articleService.show(id);

        //비교
        assertEquals(expected,article);
    }

    @Test
    @Transactional //롤백하게 해줘야 한다.
    void create_성공() {
        //예상
        String title="lalaal";
        String content="4444";
        ArticleForm dto =new ArticleForm(null,title,content,"asdf");
        Article expected=new Article(29L,title,content,"asdf");

        //실제
        Article article=articleService.create(dto);

        //비교
        assertEquals(expected.toString(),article.toString());
    }
    @Test
    void create_실패__id가_포함된_dto입력(){
        //예상
        String title="lalaal";
        String content="4444";
        ArticleForm dto =new ArticleForm(15L,title,content,"asdf");
        Article expected=null;

        //실제
        Article article=articleService.create(dto);

        //비교
        assertEquals(expected,article);
    }
}