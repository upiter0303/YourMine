package com.bit.yourmain.controller.api;

import com.bit.yourmain.dto.posts.PostsSaveRequestDto;
import com.bit.yourmain.dto.posts.PostsUpdateRequestDto;
import com.bit.yourmain.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/posts/modify")
    public void postModify(@RequestBody PostsUpdateRequestDto requestDto) {
        postsService.update(requestDto);
    }

    @GetMapping("/posts/delete/{id}")
    public void postDelete(@PathVariable Long id) {
        postsService.delete(id);
    }

}