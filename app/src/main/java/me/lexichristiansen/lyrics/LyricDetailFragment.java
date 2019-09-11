package me.lexichristiansen.lyrics;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.lexichristiansen.lyrics.database.DatabaseHelper;
import me.lexichristiansen.lyrics.database.Lyric;



public class LyricDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";
    private Lyric lyric;

    public LyricDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            int id = getArguments().getInt(ARG_ITEM_ID);
            DatabaseHelper db = new DatabaseHelper(getContext());
            this.lyric = db.getLyric(id);

            getActivity().setTitle(lyric.getTitle());

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.lyric_detail, container, false);
        ((TextView) rootView.findViewById(R.id.lyrics_english)).setText(lyric.getLyricsEnglish());
        ((TextView) rootView.findViewById(R.id.lyrics_japanese)).setText(lyric.getLyricsJapanese());
        return rootView;
    }
}
