package com.pproject.board.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //RestApi용 컨트롤러! JSON을 반환. 원래 controller는 뷰페이지를 반환했음
public class FirstApiController {

    @GetMapping("/api/hello")
    public String hello(){
        return "hello world";
    }
}
