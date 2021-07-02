package com.bit.yourmain.controller.api;

import com.bit.yourmain.dto.reviews.ReviewSaveRequestDto;
import com.bit.yourmain.service.FileService;
import com.bit.yourmain.service.PostsService;
import com.bit.yourmain.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final FileService fileService;
    private final PostsService postsService;
    private final ReviewService reviewService;

    @GetMapping("/post/files/{id}")
    public List<String> getFiles(@PathVariable Long id) {
        return fileService.getFiles(id);
    }

    @PutMapping("/post/status/{id}/{status}")
    public void setStatus(@PathVariable Long id, @PathVariable String status, @RequestBody ReviewSaveRequestDto requestDto) {
        postsService.SetStatus(id, status);
        if (status.equals("거래완료")) {
            reviewService.save(requestDto);
        }
    }

    @DeleteMapping("/post/files/del")
    public void delFiles(@RequestParam String fileName) {
        System.out.println("del file");
        fileService.delFiles(fileName);
    }
}