package com.seed.poem.business.web;

import com.seed.poem.JsonResult;
import com.seed.poem.business.model.Poem;
import com.seed.poem.business.repo.PoemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PoemController {

    @Autowired
    private PoemRepository poemRepository;

    @RequestMapping("/content/poem/author")
    public JsonResult<List<Poem>> findPoemListByAuthor(@RequestParam(value="author", defaultValue="李白") String author,@RequestParam(value="page",defaultValue = "0")String page) {

        return  new JsonResult<List<Poem>>(poemRepository.findByAuthor(author, PageRequest.of(Integer.valueOf(page), 20)).getContent());
    }

    @RequestMapping("/content/poem/title")
    public JsonResult<List<Poem>> poemWithTitle(@RequestParam(value="title", defaultValue="望岳") String title) {
        return new JsonResult<>(poemRepository.findByTitle(title));
    }
}