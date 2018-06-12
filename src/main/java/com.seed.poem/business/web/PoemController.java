package com.seed.poem.business.web;

import com.seed.poem.JsonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import com.seed.poem.business.repo.PoemRepository;
import com.seed.poem.business.model.Poem;

import java.util.List;

@RestController
public class PoemController {

    @Autowired
    private PoemRepository poemRepository;

    @RequestMapping("/content/poem/author")
    public JsonResult<List<Poem>> findPoemListByAuthor(@RequestParam(value="author", defaultValue="李白") String author) {
        return  new JsonResult<>(poemRepository.findByAuthor(author));
    }

    @RequestMapping("/content/poem/title")
    public JsonResult<List<Poem>> poemWithTitle(@RequestParam(value="title", defaultValue="望岳") String title) {
        return new JsonResult<>(poemRepository.findByTitle(title));
    }
}