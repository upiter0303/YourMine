package com.bit.yourmain.service;

import com.bit.yourmain.domain.posts.Posts;
import com.bit.yourmain.domain.posts.PostsRepository;
import com.bit.yourmain.domain.users.Users;
import com.bit.yourmain.domain.users.UsersRepository;
import com.bit.yourmain.dto.posts.PostsListResponseDto;
import com.bit.yourmain.dto.posts.PostsResponseDto;
import com.bit.yourmain.dto.posts.PostsSaveRequestDto;
import com.bit.yourmain.dto.posts.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;
    private final UsersRepository usersRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        Users users = usersRepository.findById(requestDto.getAuthor()).get();
        requestDto.setUsers(users);
        requestDto.setStatus("거래대기");
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public void update(PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(requestDto.getId()).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + requestDto));

        posts.update(requestDto.getTitle(), requestDto.getContent(), requestDto.getStatus());
    }

    public void delete(Long id) {
        Posts posts = postsRepository.findById(id).get();
        postsRepository.delete(posts);
    }

    // Posts Searching only in Title
//    @Transactional
//    public List<Posts> search(String keyword) {
//        List<Posts> postsList = postsRepository.findByTitleContaining(keyword);
//        return postsList;
//    }

    // Posts Searching in Title and Content
    @Transactional
    public List<Posts> search(String keyword) {
        List<Posts> postsList = postsRepository.findAllSearch(keyword);
        return postsList;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }
}