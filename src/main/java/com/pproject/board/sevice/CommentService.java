package com.pproject.board.sevice;

import com.pproject.board.dto.CommentDto;
import com.pproject.board.entity.Article;
import com.pproject.board.entity.Comment;
import com.pproject.board.repository.ArticleRepository;
import com.pproject.board.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ArticleRepository articleRepository;

    public List<CommentDto> comments(Long articleId) {
        //리포지토리로 댓글들 모두 가져오고 앤티티 리스트에 담는다.
        //앤티티를 dto로 바꿔서 dto리스트에 담는다
        //리턴한다.
        //조회 : 댓글 목록
        List<Comment> comments=commentRepository.findByArticleId(articleId);
        //변환 : 엔티ㅣ -> dto형태로
//        List<CommentDto> dtos=new ArrayList<CommentDto>();
//        for(int i=0; i<comments.size();i++){
//            Comment c=comments.get(i);
//            CommentDto dto=CommentDto.createCommentDto(c);
//            dtos.add(dto);
//        }
        //반환
        return commentRepository.findByArticleId(articleId)
                .stream()
                .map(comment->CommentDto.createCommentDto(comment))
                .collect(Collectors.toList());
    }

    @Transactional //db를 건드리는 것이기 때문에 트랜잭션을 걸어놔야 함.
    public CommentDto create(Long articleId, CommentDto dto) {
        //게시글 조회 및 예외 발생
        Article article=articleRepository.findById(articleId)
                .orElseThrow(()->new IllegalArgumentException("댓글 생성 실패! 대상 게시글이 없습니다."));

        //댓글 엔티티 생성
        Comment comment=Comment.createComment(dto,article);

        //댓글 애티티를 db에 저장
        Comment created=commentRepository.save(comment);

        //dto를 번경하여 반환
        return CommentDto.createCommentDto(created);
    }

    @Transactional
    public CommentDto update(Long id, CommentDto dto) {
        //댓글 조회 및 예외 발생
        Comment target=commentRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("댓글 수정 실패! 대상 댓글이 없습니다."));

        //댓글 수정
        target.patch(dto);

        //db로 갱신
        Comment updated=commentRepository.save(target);

        //댓글 앤티티를 dto로 변환 및 반환
        return CommentDto.createCommentDto(updated);
    }
@Transactional
    public CommentDto delete(Long id) {
        //댓글 조회 및 예외 발생
        Comment target=commentRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("댓글 삭제 실패! 대상이 없습니다."));
        // 댓글 db에서 삭제
        commentRepository.delete(target);
        //삭제 댓글을 dto로 변환
        return CommentDto.createCommentDto(target);
    }
}
