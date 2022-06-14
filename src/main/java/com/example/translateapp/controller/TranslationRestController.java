package com.example.translateapp.controller;

import com.example.translateapp.config.HttpConfig;
import com.example.translateapp.model.Language;
import com.example.translateapp.model.LanguageResponse;
import com.example.translateapp.model.Translation;
import com.example.translateapp.model.TranslationResponseContainer;
import com.example.translateapp.repository.LanguageRepository;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TranslationRestController {

    private final RestTemplate template;

    private final LanguageRepository repository;

    private final String POST_URL = "https://deep-translate1.p.rapidapi.com/language/translate/v2";
    private final String GET_URL = "https://deep-translate1.p.rapidapi.com/language/translate/v2/languages";

    public TranslationRestController(RestTemplate template, LanguageRepository repository) {
        this.template = template;
        this.repository = repository;
    }

    @GetMapping("/api/all")
    public ResponseEntity<List<Language>> getAllLanguages() {
        ArrayList<Language> languages = new ArrayList<>();
        repository.findAll().forEach(languages::add);
        return ResponseEntity.ok().body(languages);
    }

//    @GetMapping("/api/languages")
//    public void saveAllLanguages() {
//        LanguageResponse response = template.exchange(GET_URL, HttpMethod.GET, HttpConfig.getHeaders(), LanguageResponse.class)
//                .getBody();
//        assert response != null;
//
//        repository.saveAll(response.getLanguages());
//    }

    @GetMapping("/api/translate/{src}/{target}/{text}")
    public ResponseEntity<Translation> translateText(@PathVariable("src") String sourceLang, @PathVariable("target") String targetLang, @PathVariable("text") String text) {
        TranslationResponseContainer response = template.exchange(POST_URL, HttpMethod.POST, HttpConfig.postHeaders(text, sourceLang, targetLang), TranslationResponseContainer.class)
                .getBody();
        assert response != null;

        Translation translation = response.getData().getTranslations();
        translation.setSourceText(text);

        return ResponseEntity.ok().body(translation);
    }
}
