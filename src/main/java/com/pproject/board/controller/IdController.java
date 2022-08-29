package com.pproject.board.controller;

import com.pproject.board.dto.IdDto;
import com.pproject.board.entity.Article;
import com.pproject.board.entity.Idd;
import com.pproject.board.repository.ArticleRepository;
import com.pproject.board.sessionData.SessionConstants;
import com.pproject.board.sevice.IdService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Slf4j //로깅을 위한 골뱅이(어노테이션)
public class IdController {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private IdService idService;

    //login화면
    @GetMapping("/login")
    public String login(){
        return "login/login";
    }

    @GetMapping("/logout")
    public String logOut(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate(); // 비활성화 시킴으로 세션 끊김
        return "redirect:/login";
    }

    @PostMapping("/login/connect")
    public String connect(IdDto idDto, Model model,
                          RedirectAttributes rttr
                          ,HttpServletRequest  request
    ){

        Idd idd=idService.connect(idDto);

        //아이디를 검색하고, 그 아이디의 비밀번호도 맞는지 확인한다.
        if(idd!=null){
            model.addAttribute("id",idService.connect(idDto));
            log.info(idService.connect(idDto).toString());

//            //session 저장하는 부분
//            //Member loginMember = loginService.login(loginForm.getLoginId(), loginForm.getPassword());
//            HttpSession session = request.getSession();
//            session.setAttribute(SessionConstants.LOGIN_MEMBER, idd);

            //세션 매니저를 통해 세션 생성및 회원정보 보관
            //세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
            HttpSession session = request.getSession();
            session.setAttribute(SessionConstants.LOGIN_MEMBER, idd);

            //1,2,3은 로그인 성공 시 모든 게시물을 가져오기 위해 있는 것임!
            // 1. 모든 article을 가져온다
            List<Article> articleEntityList=articleRepository.findAll();
            // 2. 가져온 article묶음을 뷰로 전달
            model.addAttribute("articleList",articleEntityList);
            // 3. 뷰 페이지를 설정
            log.info("아이디,비밀번호 있음!");
            return "redirect:/articles";
        }
        //없는 경우
        else {
            rttr.addFlashAttribute("msg","아이디가 존재하지 않거나, 비빈번호가 옳지 않습니다.");
            log.info("아이디가 존재하지 않거나, 비빈번호가 옳지 않습니다.");
            return "redirect:/login";
        }
    }
    @GetMapping("/join")
    public String join(){
        return "join/join";
    }

    @GetMapping("/join/create")
    public String create(IdDto idDto, RedirectAttributes rttr){
        log.info("create에 입장");
        Idd idd = idService.createId(idDto);

        if(idd==null){
            log.info("아디나 비번을 입력하시오.");
            rttr.addFlashAttribute("msg","아이디나 비밀번호를 적지 않았거나 동일한 아이디가 있습니다.");
            return "redirect:/join";
        }

        log.info("가입완료!");
        rttr.addFlashAttribute("msg","가입 완료!");
        return"redirect:/login";
    }



}
