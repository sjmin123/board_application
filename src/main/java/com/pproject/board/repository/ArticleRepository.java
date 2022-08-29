package com.pproject.board.repository;

import com.pproject.board.entity.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//crudRepository를 가져와서 제공하는 기능들을 사용한다.
//이렇게 하면 articl을 crud(create,read,use,delete)를 추가코드 구현없이 사용 가능
public interface ArticleRepository extends CrudRepository<Article,Long> {
    @Override
    ArrayList<Article> findAll();

    public List<Article> findByAccount(String account);
}
