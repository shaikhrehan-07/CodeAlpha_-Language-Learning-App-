package com.example.languagelearningapp;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView title;
    TextView wordText;
    TextView meaningText;
    TextView scoreText;
    TextView progressText;

    Button startButton;
    Button listenButton;
    Button nextButton;

    String[] englishWords = {
            "Apple",
            "Book",
            "Water",
            "House",
            "Friend"
    };

    String[] hindiWords = {
            "सेब",
            "किताब",
            "पानी",
            "घर",
            "दोस्त"
    };

    int currentWord = 0;
    int score = 0;
    int learnedWords = 0;

    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        title = findViewById(R.id.title);
        wordText = findViewById(R.id.wordText);
        meaningText = findViewById(R.id.meaningText);
        scoreText = findViewById(R.id.scoreText);
        progressText = findViewById(R.id.progressText);

        startButton = findViewById(R.id.startButton);
        listenButton = findViewById(R.id.listenButton);
        nextButton = findViewById(R.id.nextButton);

        // Text To Speech
        textToSpeech = new TextToSpeech(
                this,
                status -> {
                    if (status == TextToSpeech.SUCCESS) {
                        textToSpeech.setLanguage(Locale.US);
                    }
                }
        );

        // Start Lesson
        startButton.setOnClickListener(view -> {

            currentWord = 0;
            learnedWords = 0;

            showWord();

            startButton.setVisibility(View.GONE);
            listenButton.setVisibility(View.VISIBLE);
            nextButton.setVisibility(View.VISIBLE);
        });

        // Listen
        listenButton.setOnClickListener(view -> {

            String word = englishWords[currentWord];

            textToSpeech.speak(
                    word,
                    TextToSpeech.QUEUE_FLUSH,
                    null,
                    "word"
            );
        });

        // Next
        nextButton.setOnClickListener(view -> {

            learnedWords++;

            if (currentWord < englishWords.length - 1) {

                currentWord++;

                showWord();

            } else {

                score += 10;

                currentWord = 0;

                title.setText("🎉 Lesson Complete!");

                wordText.setText("Great Job!");

                meaningText.setText(
                        "You learned " + learnedWords + " words."
                );

                startButton.setText("Start Again");

                startButton.setVisibility(View.VISIBLE);

                listenButton.setVisibility(View.GONE);

                nextButton.setVisibility(View.GONE);

                updateProgress();
            }
        });
    }

    private void showWord() {

        title.setText("📚 Learn English");

        wordText.setText(
                englishWords[currentWord]
        );

        meaningText.setText(
                hindiWords[currentWord]
        );

        updateProgress();
    }

    private void updateProgress() {

        scoreText.setText(
                "Score: " + score
        );

        progressText.setText(
                "Words Learned: " + learnedWords
        );
    }

    @Override
    protected void onDestroy() {

        if (textToSpeech != null) {

            textToSpeech.stop();
            textToSpeech.shutdown();
        }

        super.onDestroy();
    }
}