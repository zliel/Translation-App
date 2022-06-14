package com.example.translateapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("Language")
public class Language implements Comparable<Language>{

    @Id
    private String id;
    private String language;
    private String name;

    @Override
    public int compareTo(Language o) {
        return name.compareTo(o.getName());
    }
}
