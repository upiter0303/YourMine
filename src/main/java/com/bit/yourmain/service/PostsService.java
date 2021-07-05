package com.bit.yourmain.service;

import com.bit.yourmain.domain.files.Files;
import com.bit.yourmain.domain.files.FilesRepository;
import com.bit.yourmain.domain.posts.Posts;
import com.bit.yourmain.domain.posts.PostsRepository;
import com.bit.yourmain.domain.users.Users;
import com.bit.yourmain.domain.users.UsersRepository;
import com.bit.yourmain.dto.posts.PostsResponseDto;
import com.bit.yourmain.dto.posts.PostsSaveRequestDto;
import com.bit.yourmain.dto.posts.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;
    private final UsersRepository usersRepository;
    private final FileService fileService;
    private final FilesRepository filesRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        Users users = usersRepository.findById(requestDto.getAuthor()).get();
        requestDto.setUsers(users);
        requestDto.setStatus("거래대기");
        requestDto.setHit(0L);
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    public void imageSave(MultipartFile image, Long id) {
        Posts posts = postsRepository.findById(id).get();
        String saveFileName = fileService.fileSave(image, "postImage");

        Files files = new Files(saveFileName, posts);
        System.out.println(saveFileName);
        filesRepository.save(files);
    }

    @Transactional
    public void update(PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(requestDto.getId()).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + requestDto));

        posts.update(requestDto.getTitle(), requestDto.getContent(), requestDto.getPrice(),
                requestDto.getArea(), requestDto.getWay(),
                requestDto.getOfSize(), requestDto.getCategory());
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
    public List<PostsResponseDto> search(String keyword, Pageable pageable, Long cursor) {
        return postsRepository.findAllSearch(keyword, pageable, cursor).stream()
                .map(PostsResponseDto::new)
                .collect(Collectors.toList());
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsResponseDto> findAllDesc(Pageable pageable, Long cursor) {
        return postsRepository.findAllDesc(pageable, cursor).stream()
                .map(PostsResponseDto::new)
                .collect(Collectors.toList());
    }
//    public List<Posts> findAllDesc() {
//        return postsRepository.findAllDesc();
//    }

    @Transactional
    public void hitUpdate(Long id) {
        postsRepository.hitUpdate(id);
    }

    public List<PostsResponseDto> findByCategory(String category, Pageable pageable, Long cursor) {
        return postsRepository.findByCategory(category, pageable, cursor).stream()
                .map(PostsResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<PostsResponseDto> findByAddress(String area, Pageable pageable, Long cursor) {
        int index = area.indexOf(" ",area.indexOf(" ")+1);
        String address = area.substring(0, index);
        return postsRepository.findByAddress(address, pageable, cursor).stream()
                .map(PostsResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<PostsResponseDto> findByHit(Pageable pageable, Long cursor) {
        return postsRepository.HitDesc(pageable, cursor).stream()
                .map(PostsResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<PostsResponseDto> findByAttention(List<Long> idList) {
        List<PostsResponseDto> responseDtos = new ArrayList<>();
        for (Long id: idList) {
            responseDtos.add(new PostsResponseDto(postsRepository.findById(id).get()));
        }
        return responseDtos;
    }

    public void SetStatus(Long id, String status) {
        Posts posts = postsRepository.findById(id).get();
        posts.updateStatus(status);
        postsRepository.save(posts);
    }
}