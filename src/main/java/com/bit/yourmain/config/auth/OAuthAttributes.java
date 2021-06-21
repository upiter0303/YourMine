package com.bit.yourmain.config.auth;

import com.bit.yourmain.domain.users.Role;
import com.bit.yourmain.domain.users.Users;
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

    @Builder
    public OAuthAttributes(Map<String, Object> attributes,
                           String nameAttributeKey, String id, String name,
                           String phone, String address) {
        this.attributes = attributes;
        this.nameAttributeKey= nameAttributeKey;
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
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
                .id(mail.substring(0,mail.indexOf("@")))
                .name((String) attributes.get("name"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofKakao(Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        String mail = (String) kakaoAccount.get("email");

        return OAuthAttributes.builder()
                .id(mail.substring(0,mail.indexOf("@")))
                .name((String) properties.get("nickname"))
                .attributes(attributes)
                .nameAttributeKey("id")
                .build();
    }

    private static OAuthAttributes ofNaver(Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        String mail = (String) response.get("email");

        return OAuthAttributes.builder()
                .id(mail.substring(0,mail.indexOf("@")))
                .name((String) response.get("name"))
                .attributes(response)
                .nameAttributeKey("id")
                .build();
    }

    public Users toEntity() {
        return Users.builder()
                .id(id)
                .name(name)
                .role(Role.SEMI)
                .score(50L)
                .build();
    }
}