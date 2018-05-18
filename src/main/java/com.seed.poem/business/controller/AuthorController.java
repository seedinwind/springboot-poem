package com.seed.poem.business.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import com.seed.poem.business.repo.AuthorRepository;
import com.seed.poem.business.pojo.Author;

@RestController
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepository;

    @RequestMapping("/author")
    public Author findAuthor(@RequestParam(value="name", defaultValue="World") String name) {
        return authorRepository.findByName(name);
    }
}