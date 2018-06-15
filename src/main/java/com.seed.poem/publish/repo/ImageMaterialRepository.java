package com.seed.poem.publish.repo;

import com.seed.poem.publish.model.UserImageInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageMaterialRepository  extends MongoRepository<UserImageInfo,String> {
    UserImageInfo findByUidAndFolder(@Param("uid") String uid,@Param("folder") String folder);

    List<UserImageInfo> findByUid(@Param("uid")String uid);
}
