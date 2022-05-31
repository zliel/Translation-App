package com.example.translateapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("Translation")
public class Translation implements Serializable {

    @Id
    private String id;
    @Indexed
    private String sourceText;
    @Indexed
    private String translatedText;
    @Indexed
    private String sourceLanguage;
    @Indexed
    private String targetLanguage;
}
