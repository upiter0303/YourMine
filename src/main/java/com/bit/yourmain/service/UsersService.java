package com.bit.yourmain.service;

import com.bit.yourmain.domain.users.Role;
import com.bit.yourmain.domain.users.SessionUser;
import com.bit.yourmain.domain.users.Users;
import com.bit.yourmain.domain.users.UsersRepository;
import com.bit.yourmain.dto.users.PasswordModifyDto;
import com.bit.yourmain.dto.users.UserModifyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersService implements UserDetailsService {
    private final UsersRepository usersRepository;
    private final FileService fileService;

    public void save(Users users) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        users.setRole(Role.USER);
        users.setScore(50L);

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

    public SessionUser userModify(UserModifyDto modifyDto, SessionUser sessionUser) {
        Users users = getUsers(modifyDto.getId());
        users.setName(modifyDto.getName());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(authentication.getAuthorities());

        // 번호
        users.setPhone(modifyDto.getPhone());
        sessionUser.setPhone(modifyDto.getPhone());
        // 주소
        users.setAddress(modifyDto.getAddress());
        users.setDetailAddress(modifyDto.getDetailAddress());
        sessionUser.setAddress(modifyDto.getAddress());
        sessionUser.setDetailAddress(modifyDto.getDetailAddress());
        // 권한
        users.setRole(Role.USER);
        sessionUser.setRole(Role.USER);

        grantedAuthorities.add(new SimpleGrantedAuthority(Role.USER.getValue()));
        Authentication newAuthentication = new UsernamePasswordAuthenticationToken(
                authentication.getPrincipal(), authentication.getCredentials(), grantedAuthorities);

        SecurityContextHolder.getContext().setAuthentication(newAuthentication);

        usersRepository.save(users);
        return sessionUser;
    }

    public String profileModify(MultipartFile profile, String id) {

        Users users = getUsers(id);
        String saveFileName = fileService.fileSave(profile, "profile");
        users.setProfile(saveFileName);
        usersRepository.save(users);

        return saveFileName;

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

    public String findId(String phone) {
        String phoneNum = phone.substring(phone.lastIndexOf("=")+1);
        return usersRepository.findByPhone(phoneNum).getId();
    }

    public void leave(String id) {
        usersRepository.delete(getUsers(id));
    }
}
