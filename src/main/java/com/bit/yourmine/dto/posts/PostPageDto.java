package com.bit.yourmine.dto.posts;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostPageDto {
    private String kind;
    private String value;
    private int cursor;
    private Long id;

    public PostPageDto(String kind, int cursor, Long id) {
        this.kind = kind;
        this.cursor = cursor;
        this.id = id;
    }

    public PostPageDto(String kind, String value, int cursor, Long id) {
        this.kind = kind;
        this.value = value;
        this.cursor = cursor;
        this.id = id;
    }
}
