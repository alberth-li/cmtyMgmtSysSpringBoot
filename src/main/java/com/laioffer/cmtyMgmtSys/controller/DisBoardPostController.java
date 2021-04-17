package com.laioffer.cmtyMgmtSys.controller;

import com.laioffer.cmtyMgmtSys.entity.DisBoardPost;
import com.laioffer.cmtyMgmtSys.dao.DisBoardPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DisBoardPostController {
    @Autowired
    DisBoardPostRepository postRepo;

    //get all posts
    @GetMapping("/allPosts")
    public List<DisBoardPost> getAllPosts(){
        return postRepo.findAll();
    }

    //create a new Post
    @PostMapping("/posts")
    public DisBoardPost createNode(@RequestBody DisBoardPost post){
        return postRepo.save(post);
    }
}
