package com.bit.yourmine.controller.api;

import com.bit.yourmine.dto.admin.UsersModifyRequestDto;
import com.bit.yourmine.service.AdminService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class AdminApiController {

    private final AdminService adminService;

    @PutMapping("/adminPage/users/modify/{no}")
    public Long modify(@PathVariable Long no, @RequestBody UsersModifyRequestDto requestDto) {
        return adminService.update(no, requestDto);
    }

    @DeleteMapping("/adminPage/{no}")
    public Long delete(@PathVariable Long no) {
        adminService.delete(no);
        return no;
    }

    @PostMapping("/request/users/{no}")
    public String getUsersDto(@PathVariable int no) {
        PageRequest newPage = PageRequest.of(no, 8);
        return new Gson().toJson(adminService.getResponseDtoList(newPage));
    }
}
