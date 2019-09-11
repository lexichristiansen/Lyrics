package me.lexichristiansen.lyrics.lyriclist;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.View;


import me.lexichristiansen.lyrics.AddLyricActivity;
import me.lexichristiansen.lyrics.LyricDetailActivity;
import me.lexichristiansen.lyrics.R;

/**
 * A list of Lyrics. On small screens, a list of items which open a {@link LyricDetailActivity} when clicked.
 * On tablets, the list of items are on the left and item details open on the right in a double-pane view.
 */
public class LyricListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TEMPORARY CODE WHILE NOT CALLING NETWORK CALLS ON ANOTHER THREAD.
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        setContentView(R.layout.activity_lyric_list);

        //Set toolbar title
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        //Floating button opens add-lyric page
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddLyricActivity.class);
                startActivity(intent);
            }
        });

        //Set recycler view to show list of lyrics
        RecyclerView recyclerView = findViewById(R.id.lyric_list);
        recyclerView.setAdapter(new LyricListAdapter(this, mTwoPane, getSupportFragmentManager()));

        //Determine single or double-pane mode
        if (findViewById(R.id.lyric_detail_container) != null) {
            mTwoPane = true;
        }
    }


}
