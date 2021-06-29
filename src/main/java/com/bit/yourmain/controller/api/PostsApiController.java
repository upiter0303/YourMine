package com.bit.yourmain.controller.api;

import com.bit.yourmain.dto.posts.PostsUpdateRequestDto;
import com.bit.yourmain.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;



}