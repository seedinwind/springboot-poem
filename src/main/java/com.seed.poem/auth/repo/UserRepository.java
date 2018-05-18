package com.seed.poem.auth.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import com.seed.poem.auth.model.User;

//@RepositoryRestResource(collectionResourceRel = "author", path = "authors")
public interface UserRepository extends MongoRepository<User,String>{
    User findByName(@Param("name") String name);
}