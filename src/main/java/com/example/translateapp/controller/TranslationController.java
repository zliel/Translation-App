package com.example.translateapp.controller;

import com.example.translateapp.config.HttpConfig;
import com.example.translateapp.model.Language;
import com.example.translateapp.model.Translation;
import com.example.translateapp.model.TranslationRequest;
import com.example.translateapp.model.TranslationResponseContainer;
import com.example.translateapp.repository.LanguageRepository;
import com.example.translateapp.repository.TranslationRepository;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.Collections;

@Controller
public class TranslationController {

    final RestTemplate template;

    final LanguageRepository languageRepository;

    final TranslationRepository translationRepository;

    private final String POST_URL = "https://deep-translate1.p.rapidapi.com/language/translate/v2";

    public TranslationController(RestTemplate template, LanguageRepository languageRepository, TranslationRepository translationRepository) {
        this.template = template;
        this.languageRepository = languageRepository;
        this.translationRepository = translationRepository;
    }

    @GetMapping("/translate")
    public String home(Model model) {
        ArrayList<Language> languages = new ArrayList<>();
        languageRepository.findAll().forEach(languages::add);
        Collections.sort(languages);
        model.addAttribute("languages", languages);
        model.addAttribute("translationRequest", new TranslationRequest());
        return "Home";
    }

    @PostMapping("/translate")
    public ModelAndView translateText(@ModelAttribute(value="translationRequest") TranslationRequest request, ModelMap model) {
        Translation translationFromDb = translationRepository.findTranslationBySourceTextAndTargetLanguage(request.getSourceText(), request.getTargetLanguage());
        if(translationFromDb != null) {
            System.out.println("From DB: " + translationFromDb);
            translationFromDb.setId(null);
            model.addAttribute("translation", translationFromDb);
            return new ModelAndView("translation-response", model);
        }

        TranslationResponseContainer response = template.exchange(POST_URL, HttpMethod.POST, HttpConfig.postHeaders(request.getSourceText(), request.getSourceLanguage(), request.getTargetLanguage()), TranslationResponseContainer.class)
                .getBody();
        assert response != null;

        Translation translation = response.getData().getTranslations();
        saveTranslationToDB(request, translation);

        saveReverseTranslationToDB(translation);

        translation.setId(null);
        model.addAttribute("translation", translation);
        return new ModelAndView("translation-Response", model);
    }

    @GetMapping("/translation-response")
    public String translationResponse() {
        return "Translation-Response";
    }

    private void saveTranslationToDB(TranslationRequest request, Translation translation) {
        translation.setSourceText(request.getSourceText());
        translation.setSourceLanguage(request.getSourceLanguage());
        translation.setTargetLanguage(request.getTargetLanguage());
        translationRepository.save(translation);
    }

    private void saveReverseTranslationToDB(Translation translation) {
        Translation reverseTranslation = new Translation();
        reverseTranslation.setTranslatedText(translation.getSourceText());
        reverseTranslation.setSourceText(translation.getTranslatedText());
        reverseTranslation.setTargetLanguage(translation.getSourceLanguage());
        reverseTranslation.setSourceLanguage(translation.getTargetLanguage());
        translationRepository.save(reverseTranslation);
    }
}
