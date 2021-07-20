package com.bit.yourmine.controller.api;

import com.bit.yourmine.dto.attention.AttentionRequestDto;
import com.bit.yourmine.service.AttentionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AttentionApiController {

    private final AttentionService attentionService;

    @PostMapping("/attention/button")
    public boolean attention(@RequestBody AttentionRequestDto requestDto) {

        if (attentionService.attentionCheck(requestDto)) {
            return attentionService.attentionSave(requestDto);
        } else {
            return attentionService.attentionDelete(requestDto);
        }
    }
}
