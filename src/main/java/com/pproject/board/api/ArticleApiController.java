package com.pproject.board.api;

import com.pproject.board.dto.ArticleForm;
import com.pproject.board.entity.Article;
import com.pproject.board.repository.ArticleRepository;
import com.pproject.board.sevice.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Slf4j
@RestController //RestApi용 컨트롤러
public class ArticleApiController {

    @Autowired //DI, 생성 객체를 가져와 연결!
    private ArticleService articleService;



    //GET
    //여러개
    @GetMapping("/api/articles")
    public List<Article> index(){
        return articleService.index();
    }
    //단일
    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id){

        log.info("api를 통해서 옴!!!!!!!!!!!!!!!!!!!!!!ㅇㅉㄸㅉㄸ");

        return articleService.show(id);
        //return articleRepository.findById(id).orElse((null));
    }

    //POST
    @PostMapping("/api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto){  //Restapi는 @RequestBody를 이용하여 받아야 한다.
        Article created=articleService.create(dto);
        return (created!=null)?
                ResponseEntity.status(HttpStatus.OK).body(created):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        //Article article=dto.toEntity();
        //return articleRepository.save(article);
    }

    //PATCH
    @PatchMapping("/api/articles/{id}")
    //Article데이터가 ResponseEntity에 담겨서 전송된다.
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto){

        Article updated=articleService.update(id,dto);
        return (updated!=null)?
                ResponseEntity.status(HttpStatus.OK).body(updated):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

//        // 1. 수정 엔티티 생성
//        Article article=dto.toEntity();
//        log.info("id: {}, article : {}",id,article.toString());
//
//        // 2. 대상 엔티티를 조회
//        Article target=articleRepository.findById(id).orElse(null);
//
//        // 3. 잘못된 요철 처리(대상이 없거나, id가 다른 경우)
//        if(target==null || id!=article.getId()){
//            //400
//            log.info("잘못된 요청! id: {}, article : {}",id,article.toString());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//
//        // 4. 업데이트 및 정상 응답(200)
//        target.patch(article);
//        Article updated=articleRepository.save(target);
//        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    //DELETE
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id){


        Article deleted=articleService.delete(id);
        return (deleted!=null)?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build():
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

//        // 1. 찾아오기
//        Article target=articleRepository.findById(id).orElse(null);
//
//        // 2.1. 잘못된 요청 처리
//        if (target == null){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//
//        // 2.2. 없애기
//        articleRepository.delete(target);
//
//        // 3. 반환
//        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //트랜잭션
    @PostMapping("/api/transaction-test")
    public ResponseEntity<List<Article>> transactionTest(@RequestBody List<ArticleForm> dtos){
        List<Article> createdList=articleService.createArticles(dtos);
        return (createdList!=null)?
                ResponseEntity.status(HttpStatus.OK).body(createdList):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
