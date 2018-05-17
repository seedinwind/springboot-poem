package  com.seed.poem.controller;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import com.seed.poem.repo.AuthorRepository;
import com.seed.poem.pojo.Author;

@RestController
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepository;

    @RequestMapping("/author")
    public Author findAuthor(@RequestParam(value="name", defaultValue="World") String name) {
        return authorRepository.findByName(name);
    }
}