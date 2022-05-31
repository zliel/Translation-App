package com.example.translateapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TranslationResponseContainer implements Serializable {

    private TranslationResponse data;
}
