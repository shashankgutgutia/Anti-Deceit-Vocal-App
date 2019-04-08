package com.google.android.gms.samples.vision.ocrreader;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class TextTranslationActivity extends AppCompatActivity {

    Spinner spinner;
    String text;
    EditText editText;
    private TextToSpeech t,t1;
    String selected,result,code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_translation);
        editText=findViewById(R.id.transtext);
        spinner=findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Toast.makeText(parentView.getContext(),
                        "Language Selected : " + parentView.getItemAtPosition(position).toString(),
                        Toast.LENGTH_SHORT).show();
                selected=parentView.getItemAtPosition(position).toString();
                t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if(status != TextToSpeech.ERROR) {
                            Locale locale;
                            String toSpeak;
                            if(selected.equals("Hindi")) {
                                locale = new Locale("hi", "IN");
                                toSpeak= "नमस्ते";
                            }
                            else if(selected.equals("English")) {
                                locale = new Locale("en", "IN");
                                toSpeak= "Hello";
                            }
                            else{
                                locale = new Locale("bn", "IN");
                                toSpeak= "নমস্কার";
                            }
                            t1.setLanguage(locale);
                    /*
                    Set<Voice> voiceList = t1.getVoices();
                    for (Voice voice : voiceList) {
                        Log.v(TAG, "Voice: " + voice.getName());
                        if (voice.getName().equalsIgnoreCase("ta"))
                        {
                            Log.v(TAG, "Voice available: " + voice.getName());
                            t1.setVoice(voice);
                        }
                    }
                    */
                            t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                            Log.d("Translated", t1.getAvailableLanguages().toString());
                        }
                    }
                });
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
        Button btnSubmit=findViewById(R.id.transbtn);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected=String.valueOf(spinner.getSelectedItem());
                Log.d("Translated",selected);
                text= String.valueOf(editText.getText());
                Log.d("TextTranslated",text);
                if(selected.equals("Hindi")) {
                    code="hi";
                }
                else if(selected.equals("English")) {
                    code="en";
                }
                else{
                    code="bn";
                }

                TextTranslate textTranslate = new TextTranslate();
                try {
                    result = textTranslate.execute(text, "en", code).get();
                    TextView textView=findViewById(R.id.ttext);
                    textView.setText(result);
                    Log.d("Translated", result);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
        Button spk=findViewById(R.id.spkbtn);
        spk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if(status != TextToSpeech.ERROR) {
                            Locale locale = new Locale(code,"IN");
                            t.setLanguage(locale);
                    /*
                    Set<Voice> voiceList = t1.getVoices();
                    for (Voice voice : voiceList) {
                        Log.v(TAG, "Voice: " + voice.getName());
                        if (voice.getName().equalsIgnoreCase("ta"))
                        {
                            Log.v(TAG, "Voice available: " + voice.getName());
                            t1.setVoice(voice);
                        }
                    }
                    */
                            t.speak(result, TextToSpeech.QUEUE_FLUSH, null);
                        }
                    }
                });
            }
        });
    }


}

