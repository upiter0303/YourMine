package com.bit.yourmain.controller.api;

import com.bit.yourmain.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final FileService fileService;

    @GetMapping("/post/files/{id}")
    public List<String> getFiles(@PathVariable Long id) {
        return fileService.getFiles(id);
    }

}