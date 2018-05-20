package com.seed.poem.business.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import com.seed.poem.business.model.Author;

//@RepositoryRestResource(collectionResourceRel = "author", path = "authors")
public interface AuthorRepository extends MongoRepository<Author,String>{
    Author findByName(@Param("name") String name);
}