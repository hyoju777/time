package com.example.blog.controller;

import com.example.blog.model.Post;
import com.example.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public String listPosts(Model model) {
        model.addAttribute("posts", postService.findAll());
        return "post/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("post", new Post());
        return "post/create";
    }

    @PostMapping
    public String createPost(@ModelAttribute("post") Post post) {
        postService.save(post);
        return "redirect:/posts";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        postService.findById(id).ifPresent(post -> model.addAttribute("post", post));
        return "post/edit";
    }

    @PostMapping("/{id}")
    public String updatePost(@PathVariable("id") Long id, @ModelAttribute("post") Post post) {
        post.setId(id);
        postService.save(post);
        return "redirect:/posts";
    }

    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable("id") Long id) {
        postService.deleteById(id);
        return "redirect:/posts";
    }
}
