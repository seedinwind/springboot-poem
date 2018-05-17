package com.seed.poem.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.List;
import com.seed.poem.pojo.Poem;

//@RepositoryRestResource(collectionResourceRel = "poems", path = "poems")
public interface PoemRepository extends MongoRepository<Poem,String>{
    List<Poem> findByAuthor(@Param("author") String author);
    List<Poem> findByTitle(@Param("title") String title);
    List<Poem> findAll();
}