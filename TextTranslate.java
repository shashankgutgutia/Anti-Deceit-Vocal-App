package com.google.android.gms.samples.vision.ocrreader;

import android.os.AsyncTask;
import android.util.Log;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.translate.Translate;
import com.google.api.services.translate.model.TranslationsListResponse;
import java.util.Arrays;

public class TextTranslate extends AsyncTask<String, Void, String>{

    @Override
    protected String doInBackground(String... params){


        final String textToTranslate = params[0];


        final String SOURCE_LANGUAGE = params[1];


        final String TARGET_LANGUAGE = params[2];

        try {

            NetHttpTransport netHttpTransport 	= new NetHttpTransport();

            JacksonFactory jacksonFactory 		= new JacksonFactory();

            Translate translate = new Translate.Builder(netHttpTransport, jacksonFactory, null).build();


            Translate.Translations.List listToTranslate = translate.new Translations().list(
                    Arrays.asList(textToTranslate), TARGET_LANGUAGE).setKey(API_KEY);


            listToTranslate.setSource(SOURCE_LANGUAGE);


            TranslationsListResponse response = listToTranslate.execute();


            return response.getTranslations().get(0).getTranslatedText();
        } catch (Exception e){

            return "";
        }
    }
}
