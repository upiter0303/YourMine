package com.bit.yourmain.controller.api;

import com.bit.yourmain.service.FileService;
import com.bit.yourmain.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final FileService fileService;
    private final PostsService postsService;

    @GetMapping("/post/files/{id}")
    public List<String> getFiles(@PathVariable Long id) {
        return fileService.getFiles(id);
    }

    @PutMapping("/post/status/{id}/{status}")
    public void setStatus(@PathVariable Long id, @PathVariable String status) {
        postsService.SetStatus(id, status);
        if (status.equals("거래완료")) {

        }
    }

}