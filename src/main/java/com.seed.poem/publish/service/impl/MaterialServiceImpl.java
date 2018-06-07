package com.seed.poem.publish.service.impl;

import com.seed.poem.publish.model.UserImageInfo;
import com.seed.poem.publish.repo.ImageMaterialRepository;
import com.seed.poem.publish.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialServiceImpl implements MaterialService {

    @Autowired
    private ImageMaterialRepository materialRepository;

    @Override
    public void storeImageInfo(String category, List<String> res) {
        String userId=(String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserImageInfo info=materialRepository.findByUidAndFolder(userId,category);
        if(info==null){
            info=new UserImageInfo();
            info.setUid(userId);
            info.setFolder(category);
            info.setOpen(0);
        }
        info.getFiles().addAll(res);
        materialRepository.save(info);
    }
}
