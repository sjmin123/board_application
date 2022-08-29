package com.pproject.board.repository;

import com.pproject.board.entity.Article;
import com.pproject.board.entity.Idd;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IdRepository extends CrudRepository<Idd,String> {


}
