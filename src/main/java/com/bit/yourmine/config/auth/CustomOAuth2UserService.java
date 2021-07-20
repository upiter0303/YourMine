package com.bit.yourmine.config.auth;

import com.bit.yourmine.domain.users.SessionUser;
import com.bit.yourmine.domain.users.Users;
import com.bit.yourmine.domain.users.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;


@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UsersRepository usersRepository;
    private final HttpSession session;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();    // 진행중인 서비스

        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        // 받아와서 db업데이트 , 세션에 추가
        Users users = saveOrUpdate(attributes);
        SessionUser sessionUser = new SessionUser(users);
        session.setAttribute("userInfo" , sessionUser);
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(sessionUser.getRole().getValue())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey()
        );
    }
    private Users saveOrUpdate(OAuthAttributes attributes) {
        Users users = usersRepository.findById(attributes.getId())
                .map(entity-> entity.update(attributes.getId()))
                .orElse(attributes.toEntity());

        return usersRepository.save(users);
    }

}