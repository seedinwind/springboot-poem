package com.seed.poem.publish.service;

import com.seed.poem.publish.model.UserImageInfo;

import java.util.List;
import java.util.Map;

public interface MaterialService {
    void storeImageInfo(String category, List<String> res);

    Map<String,List<String>> getUserFiles();
}
