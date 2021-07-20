package com.bit.yourmine.config.auth;

import com.bit.yourmine.domain.users.Role;
import com.bit.yourmine.domain.users.Users;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private final Map<String, Object> attributes;
    private final String nameAttributeKey;
    private final String id;
    private final String name;
    private final String phone;
    private final String address;
    private final String email;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes,
                           String nameAttributeKey, String id, String name,
                           String phone, String address, String email) {
        this.attributes = attributes;
        this.nameAttributeKey= nameAttributeKey;
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = email;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if ("naver".equals(registrationId)) {
            return ofNaver(attributes);
        } else if ("kakao".equals(registrationId)){
            return ofKakao(attributes);
        }
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        String mail = (String) attributes.get("email");

        return OAuthAttributes.builder()
                .id(mail)
                .name((String) attributes.get("name"))
                .email(mail)
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofKakao(Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        String mail = (String) kakaoAccount.get("email");

        return OAuthAttributes.builder()
                .id(mail)
                .name((String) properties.get("nickname"))
                .email(mail)
                .attributes(attributes)
                .nameAttributeKey("id")
                .build();
    }

    private static OAuthAttributes ofNaver(Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        String mail = (String) response.get("email");

        return OAuthAttributes.builder()
                .id(mail)
                .name((String) response.get("name"))
                .email(mail)
                .attributes(response)
                .nameAttributeKey("id")
                .build();
    }

    public Users toEntity() {
        return Users.builder()
                .id(id)
                .name(name)
                .email(email)
                .role(Role.SEMI)
                .score(50L)
                .build();
    }
}