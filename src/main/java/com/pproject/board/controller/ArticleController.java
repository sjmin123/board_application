package com.pproject.board.controller;

import com.pproject.board.dto.ArticleForm;
import com.pproject.board.dto.CommentDto;
import com.pproject.board.dto.IdDto;
import com.pproject.board.entity.Article;
import com.pproject.board.entity.Comment;
import com.pproject.board.entity.Idd;
import com.pproject.board.repository.ArticleRepository;
import com.pproject.board.sessionData.SessionConstants;
import com.pproject.board.sevice.CommentService;
import com.pproject.board.sevice.IdService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j //로깅을 위한 골뱅이(어노테이션)
public class ArticleController {

    //리포지토리 선언
    @Autowired // springboot가 미리 생생해놓은 객체를 가져다가 자동 연결. 그래서 객체 생성 필요 없음
    private ArticleRepository articleRepository;
    @Autowired
    private CommentService commentService;

    @Autowired
    private IdService idService;


    //세션(로그인값)이 있는지 판별. 없으면 밑의 url에 접속 못함.
//    @SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false)
//    public Idd setMember() {
//        System.out.println("*********setMember()*******");
//        return new Idd();
//    }


    @GetMapping("/articles/mypost/{id}")
    public String showMyPost(@PathVariable String id,Model model){

        // 1. id로 데이터를 가져옴
        List <Article> myPostEntity = articleRepository.findByAccount(id);

         model.addAttribute("account",id);
         model.addAttribute("myPostList",myPostEntity);
         return "articles/mypost";
    }

    @GetMapping("/articles/new")
    public String newArticleForm(HttpServletRequest request,
                                 Model model){

        log.info("(컨트롤러)new호출!");

        HttpSession session=request.getSession();
        //String account=session.getAttribute(SessionConstants.LOGIN_MEMBER).toString();
        model.addAttribute("account",session.getAttribute(SessionConstants.LOGIN_MEMBER));

        return "articles/new";
    }

    //post로 new.mustache에서 받아온 정보
    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){
        log.info(form.toString());
        //System.out.println(form.toString()); ->로깅으로 대체






        //log.info(account);

        //setter로 dto에 계정 넣기

        // 1. dto를 변환! entity
        Article article =form.toEntity();
        log.info(form.toString());
        //System.out.println(article.toString());

        // 2. repository에게 entity를 db언어에 저장하게 함.
        Article saved = articleRepository.save(article);
        log.info(saved.toString());
        //System.out.println(saved.toString());

        return "redirect:/articles/"+saved.getId();
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model, HttpServletRequest request){
        log.info("id="+id);

        HttpSession session=request.getSession(false);

        // 1. id로 데이터를 가져옴
        Article articleEntity = articleRepository.findById(id).orElse(null);
        List<CommentDto> commentDtos=commentService.comments(id);

        //자신이 쓴 글인지 확인
        Idd idd= (Idd) session.getAttribute(SessionConstants.LOGIN_MEMBER);
        if(articleEntity.getAccount().equals(idd.getId())){
            log.info("세션과 모델값이 같음!");
            model.addAttribute("mine",".");
        };
        // 2. 가져온 데이터를 모델에 등록
        model.addAttribute("id",idd);
        model.addAttribute("article",articleEntity);
        model.addAttribute("commentDtos",commentDtos);

        // 3. 보여줄 페이지를 설정
        return"articles/show";
    }

    @RequestMapping("/articles")
    public String index(Model model,
                        //@SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false)Idd idd,
                        RedirectAttributes rttr,
                        HttpServletRequest request
                        ){

//        if(idd==null){
//            log.info("로그인 되있지 않아서 로그인 창으로 반환됨");
//            return"/login";
//        }
//        log.info("article controller에 접속 성공!");

        HttpSession session=request.getSession();
        //session.getAttribute(SessionConstants.LOGIN_MEMBER);

        // 1. 모든 article을 가져온다
        List<Article> articleEntityList=articleRepository.findAll();


        // 2. 가져온 article묶음을 뷰로 전달
        model.addAttribute("articleList",articleEntityList);
        model.addAttribute("id",session.getAttribute(SessionConstants.LOGIN_MEMBER));

        // 3. 뷰 페이지를 설정
        return "articles/index";

    }

    //getMapping의 id와 Long id가 같아야지 pathvariable이 정상적으로 가져와 지는거다
    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){

        //수정할 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);

        //모델에 데이터를 등록
        model.addAttribute("article", articleEntity);

        //뷰페이지
        return"articles/edit";
    }


    @PostMapping("/articles/update")
    public String update(ArticleForm form){
        log.info(form.toString());

        // 1. dto 를 entity로 변환
        Article articleEntity=form.toEntity();
        log.info(articleEntity.toString());

        // 2. entity를 db로 저장
        // 2-1. db에 기존 데이터를 가져온다.
        Article target=articleRepository.findById(articleEntity.getId()).orElse(null);

        // 2. 기존 데이터의 값을 갱신한다.
        if(target != null){
            articleRepository.save(articleEntity);
        }

        //3. 수정 결과 페이지로 라다이랙트 한다.
        return"redirect:/articles/"+articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
        log.info("삭제요청");

        // 1. 삭제 대상을 가져온다
        Article target=articleRepository.findById(id).orElse(null);
        log.info("삭제대상 id는"+target.toString());

        //댓글 먼저 삭제
        List<CommentDto> commentDtos=commentService.comments(id);
        for(CommentDto i:commentDtos){
            CommentDto commentDto=commentService.delete(i.getId());
        }

        // 2. 대상을 삭제한다.
        if(target!=null){
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg","삭제가 완료되었습니다.");
        }

        // 3. 결과 페이지로 리다이랙트한다.

        return "redirect:/articles";
    }

}
