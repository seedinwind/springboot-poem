package  com.seed.poem.controller;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import com.seed.poem.repo.PoemRepository;
import com.seed.poem.pojo.Poem;

import java.util.List;

@RestController
public class PoemController {

    @Autowired
    private PoemRepository poemRepository;

    @RequestMapping("/poem")
    public List<Poem> findPoemListByAuthor(@RequestParam(value="author", defaultValue="李白") String author) {
        return poemRepository.findAll();//poemRepository.findByAuthor(author);
    }

    @RequestMapping("/poem/title")
    public List<Poem> poemWithTitle(@RequestParam(value="title", defaultValue="望岳") String title) {
        return poemRepository.findByTitle(title);
    }
}