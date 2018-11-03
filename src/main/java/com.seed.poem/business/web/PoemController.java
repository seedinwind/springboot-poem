package com.seed.poem.business.web;

import com.seed.poem.JsonResult;
import com.seed.poem.PageResult;
import com.seed.poem.business.model.Poem;
import com.seed.poem.business.repo.PoemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PoemController {

    @Autowired
    private PoemRepository poemRepository;

    @RequestMapping("/content/poem/author")
    public PageResult<List<Poem>> findPoemListByAuthor(@RequestParam(value = "author", defaultValue = "李白") String author, @RequestParam(value = "page", defaultValue = "0") String page) {
        Pageable p = PageRequest.of(Integer.valueOf(page), 20);
        Page res = poemRepository.findByAuthor(author, p);
        List<Poem> data = res.getContent();
        return new PageResult<>(data, res.getTotalPages() - 1, p.getPageNumber());
    }

    @RequestMapping("/content/poem/title")
    public JsonResult<List<Poem>> poemWithTitle(@RequestParam(value = "title", defaultValue = "望岳") String title) {
        return new JsonResult<>(poemRepository.findByTitle(title));
    }
}