package com.seed.poem.business.repo;

import com.seed.poem.business.model.Poem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

//@RepositoryRestResource(collectionResourceRel = "poems", path = "poems")
public interface PoemRepository extends MongoRepository<Poem,String> {
    Page<Poem> findByAuthor(@Param("author") String author, Pageable pageable);
    List<Poem> findByTitle(@Param("title") String title);
    List<Poem> findAll();
}