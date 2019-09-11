package me.lexichristiansen.lyrics;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;

import me.lexichristiansen.lyrics.database.DatabaseHelper;
import me.lexichristiansen.lyrics.database.Lyric;

public class AddLyricActivity extends AppCompatActivity {

    private EditText english;
    private EditText japanese;
    private EditText title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lyric);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        english = findViewById(R.id.lyrics_english);
        japanese = findViewById(R.id.lyrics_japanese);
        title = findViewById(R.id.title);

        try {
            english.setText(GlobalStuff.service.getEnglish().execute().body());
            japanese.setText(GlobalStuff.service.getRomaji().execute().body());
        } catch (IOException e) {
            e.printStackTrace();
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper helper = new DatabaseHelper(getBaseContext());
                helper.addLyric(new Lyric(title.getText().toString(), english.getText().toString(), japanese.getText().toString()));
                Snackbar.make(view, "Lyrics saved", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

}
