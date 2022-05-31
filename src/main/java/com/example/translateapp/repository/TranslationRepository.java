package com.example.translateapp.repository;

import com.example.translateapp.model.Translation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TranslationRepository extends CrudRepository<Translation, String> {

    public Translation findTranslationBySourceTextAndTargetLanguage(String sourceText, String targetLanguage);
}
