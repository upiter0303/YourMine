package com.bit.yourmain.service;

import com.bit.yourmain.domain.Role;
import com.bit.yourmain.domain.Users;
import com.bit.yourmain.domain.UsersRepository;
import com.bit.yourmain.dto.PasswordModifyDto;
import com.bit.yourmain.dto.UserModifyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class UsersService implements UserDetailsService {
    private final UsersRepository usersRepository;

    public void save(Users users) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        users.setRole(Role.USER);
        usersRepository.save(users);
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        try {
            Users users = getUsers(id);
            if (users == null) {
                return null;
            } else {
                List<GrantedAuthority> authorities = new ArrayList<>();
//                authorities.add(new SimpleGrantedAuthority(users.getRole().getValue()));
                return new User(users.getId(), users.getPassword(), authorities);
            }
        } catch (Exception e) {
            System.out.println("account empty");
        }

        return null;
    }

    public Users getUsers(String id) {
        Users users = usersRepository.findById(id).get();
        return users;
    }

    public void userModify(UserModifyDto modifyDto) {
        Users users = getUsers(modifyDto.getId());
        users.setName(modifyDto.getName());
        if (modifyDto.getPhone() != null) {
            users.setPhone(modifyDto.getPhone());
        }
        if (modifyDto.getAddress() != null) {
            users.setAddress(modifyDto.getAddress());
            users.setDetailAddress(modifyDto.getDetailAddress());
        }
        if (modifyDto.getPhone().equals("") || modifyDto.getAddress().equals("")) {
            users.setRole(Role.SEMI);
        } else {
            users.setRole(Role.USER);
        }
        usersRepository.save(users);
    }

    public String profileModify(MultipartFile profile, String id) {
        try {
            String savePath = "C:\\Users\\User\\Documents\\profile";
            String origin = profile.getOriginalFilename();
            String nameCut = origin.substring(origin.lastIndexOf("."));
            String saveFileName = getSaveFileName(nameCut);

            if(!profile.isEmpty())
            {
                File file = new File(savePath, saveFileName);
                profile.transferTo(file);
                Users users = getUsers(id);
                users.setProfile(saveFileName);
                usersRepository.save(users);
                return saveFileName;
            }
        }catch(Exception e)
        {
            e.getCause();
        }
        return null;
    }

    private String getSaveFileName(String extName) {
        String fileName = "profile_";

        Calendar calendar = Calendar.getInstance();
        fileName += calendar.get(Calendar.YEAR);
        fileName += calendar.get(Calendar.MONTH);
        fileName += calendar.get(Calendar.DATE);
        fileName += calendar.get(Calendar.HOUR);
        fileName += calendar.get(Calendar.MINUTE);
        fileName += calendar.get(Calendar.SECOND);
        fileName += calendar.get(Calendar.MILLISECOND);
        fileName += extName.toLowerCase();

        return fileName;
    }

    public void passwordModify(PasswordModifyDto modifyDto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Users users = getUsers(modifyDto.getId());
        users.setPassword(passwordEncoder.encode(modifyDto.getPassword()));
        usersRepository.save(users);
    }

    public Users delProfile(String id) {
        Users users = getUsers(id);
        users.setProfile(null);
        usersRepository.save(users);
        return users;
    }
}
