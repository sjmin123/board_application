package com.pproject.board.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pproject.board.entity.Comment;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CommentDtoMine {
    private Long id;
    @JsonProperty("article_id")
    private Long articleId;
    private String nickname;
    private String body;
    private String isMine;

    public static CommentDtoMine createCommentDto(CommentDto commentDto) {
        return new CommentDtoMine(
                commentDto.getId(),
                commentDto.getArticleId(),
                commentDto.getNickname(),
                commentDto.getBody(),
                null
        );
    }
}
