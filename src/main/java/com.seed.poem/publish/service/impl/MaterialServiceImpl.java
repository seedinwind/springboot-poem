package com.seed.poem.publish.service.impl;

import com.seed.poem.publish.model.UserImageInfo;
import com.seed.poem.publish.repo.ImageMaterialRepository;
import com.seed.poem.publish.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MaterialServiceImpl implements MaterialService {

    @Autowired
    private ImageMaterialRepository materialRepository;

    @Override
    public void storeImageInfo(String category, List<String> res) {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserImageInfo info = materialRepository.findByUidAndFolder(userId, category);
        if (info == null) {
            info = new UserImageInfo();
            info.setUid(userId);
            info.setFolder(category);
            info.setOpen(0);
        }
        info.getFiles().addAll(res);
        materialRepository.save(info);
    }

    @Override
    public Map<String, List<String>> getUserFiles() {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<UserImageInfo> res = materialRepository.findByUid(userId);
        Map<String,List<String>> rs = new HashMap<>();

        for (UserImageInfo i : res) {
            rs.put(i.getFolder(), i.getFiles());
        }

        return rs;
    }
}
