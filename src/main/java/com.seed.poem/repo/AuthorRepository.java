package com.seed.poem.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.List;
import com.seed.poem.pojo.Author;

//@RepositoryRestResource(collectionResourceRel = "author", path = "authors")
public interface AuthorRepository extends MongoRepository<Author,String>{
    Author findByName(@Param("name") String name);
}