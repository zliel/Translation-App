package com.example.translateapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TranslationRequest {
    private String sourceText;
    private String sourceLanguage;
    private String targetLanguage;
}
