package com.pproject.board.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity//db가 해당 객체를 인식 가능하게 한다.(해당 클래스로 테이블을 만든다)
@AllArgsConstructor
@NoArgsConstructor // 디폴트 생성자 추가
@ToString
@Getter
public class Idd {

    @Id
    @Column
    private String id;

    @Column
    private String password;


}
