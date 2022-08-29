package com.pproject.board.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity//db가 해당 객체를 인식 가능하게 한다.(해당 클래스로 테이블을 만든다)
@AllArgsConstructor
@NoArgsConstructor // 디폴트 생성자 추가
@ToString
@Getter
public class Article {

    @Id // 대푯값을 지정! like 주민등록번호
    @GeneratedValue // 1,2,3,... 자동 생성 어노테이션1
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private String account;

    public void patch(Article article) {
        if(article.title!=null){
            this.title=article.title;
        }
        if(article.content!=null){
            this.content=article.content;
        }
    }

    public Article toEntity() {
        return new Article(id,title,content,account);
    }


    //@AllArgsConstructor
/*    public Article(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }*/

    //@ToString
/*    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }*/
}
