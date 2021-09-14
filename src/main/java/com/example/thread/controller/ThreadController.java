package com.example.thread.controller;

import java.util.List;
import java.util.Map;

import com.example.thread.config.JwtUtils;
import com.example.thread.model.Response;
import com.example.thread.model.Thread;
import com.example.thread.repository.ThreadRepository;
import com.example.thread.service.ThreadService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(value = "*")
@RestController
@RequestMapping("/thread")
public class ThreadController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private ThreadRepository threadRepository;
    
    @Autowired
    private ThreadService threadService;
    
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> listByCardId(long cardId){
        List<Thread> threads = threadRepository.findByCardId(cardId);
        return ResponseEntity.ok().body(new Response("list threads", threads));
    }
    @PostMapping(value="")
    public ResponseEntity<?> postMethodName(@RequestHeader Map<String, String> headers, @RequestBody Thread thread) throws Exception {
        String tokenString = headers.get("authorization");
        String token = tokenString.substring(7);
        String username = jwtUtils.getUsernameFromToken(token);
        String userId = threadService.getUserIdFromUsername(username, tokenString);
        if (userId == "-1"){
            return ResponseEntity.badRequest().body(new Response("wrong username", null));
        }
        long authorId = Integer.parseInt(userId);
        thread.setAuthorId(authorId);
        threadRepository.save(thread);
        return ResponseEntity.ok().body(new Response("thread created", thread));
    }
    
}
