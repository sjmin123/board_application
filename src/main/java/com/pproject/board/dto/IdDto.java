package com.pproject.board.dto;

import com.pproject.board.entity.Idd;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Setter
public class IdDto {

    private String id;
    private String password;

    public Idd toEntity() {
        return new Idd(id,password);
    }
}
