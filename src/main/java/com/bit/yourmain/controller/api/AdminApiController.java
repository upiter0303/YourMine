package com.bit.yourmain.controller.api;

import com.bit.yourmain.dto.admin.UsersModifyRequestDto;
import com.bit.yourmain.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class AdminApiController {

    private final AdminService adminService;

    @PutMapping("/adminPage/users/modify/{no}")
    public Long modify(@PathVariable Long no, @RequestBody UsersModifyRequestDto requestDto) {
        return adminService.update(no, requestDto);
    }

}
