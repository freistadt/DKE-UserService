package com.userservice.demo.repo;

import com.userservice.demo.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "userdata",
        path = "userdata")
public interface UserRepository extends MongoRepository<User,String> {

}
