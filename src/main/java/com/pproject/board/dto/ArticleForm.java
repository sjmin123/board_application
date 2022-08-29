package com.pproject.board.dto;

import com.pproject.board.entity.Article;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@NoArgsConstructor // 디폴트 생성자 추가
@Setter
public class ArticleForm {

    private Long id;
    private String title;
    private String content;
    private String account;

    public Article toEntity() {
        return new Article(id,title,content,account);
    }
    // 밑의 생성자을 위이 @AllArgsConstructor로 대체가능!
/*    public ArticleForm(String title, String content){
        this.title=title;
        this.content=content;
    }

    밑의 toString을 @ToString으로 대체 가능!
    @Override
    public String toString() {
        return "ArticleForm{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }*/



}