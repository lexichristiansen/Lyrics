package me.lexichristiansen.lyrics.lyriclist;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.lexichristiansen.lyrics.LyricDetailActivity;
import me.lexichristiansen.lyrics.LyricDetailFragment;
import me.lexichristiansen.lyrics.R;
import me.lexichristiansen.lyrics.database.DatabaseHelper;
import me.lexichristiansen.lyrics.database.Lyric;
import me.lexichristiansen.lyrics.lyriclist.LyricListAdapter.LyricListViewHolder;

/**
 * RecyclerView adapter for {@link LyricListActivity}.
 */
public class LyricListAdapter extends RecyclerView.Adapter<LyricListViewHolder> {

    /**
     * Backing list of lyrics from the database.
     */
    private List<Lyric> allLyrics;
    /**
     * True if landscape mode.
     */
    private boolean isTwoPane;
    /**
     * A Reference to fragment manager needed for two-pane mode.
     */
    private FragmentManager fragmentManager;
    /**
     * Context for DatabaseHelper and AlertDialog builder.
     */
    private Context context;

    /**
     * Ctor.
     * @param context Any context
     * @param isTwoPane true if a tablet size
     * @param fragmentManager link to FragmentManager
     */
    public LyricListAdapter(Context context, boolean isTwoPane, FragmentManager fragmentManager) {
        DatabaseHelper db = new DatabaseHelper(context);
        allLyrics = new ArrayList<>(Arrays.asList(db.getAllLyrics()));
        this.fragmentManager = fragmentManager;
        this.isTwoPane = isTwoPane;
        this.context = context;
    }

    /**
     * Removes lyric from database, and from adapter backing data, at given index.
     * @param position 0-based index of lyric on list
     */
    public void removeLyric(int position) {
        DatabaseHelper db = new DatabaseHelper(context);
        db.deleteLyric(allLyrics.get(position));
        allLyrics.remove(position);
    }

    @Override
    public LyricListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View lyricListItemLayout = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new LyricListViewHolder(lyricListItemLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull LyricListViewHolder holder, int position) {
        final Lyric lyric = allLyrics.get(position);
        holder.setTitle(lyric.getTitle());
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putInt(LyricDetailFragment.ARG_ITEM_ID, lyric.getID());
                    LyricDetailFragment fragment = new LyricDetailFragment();
                    fragment.setArguments(arguments);
                    fragmentManager.beginTransaction()
                            .replace(R.id.lyric_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, LyricDetailActivity.class);
                    intent.putExtra(LyricDetailFragment.ARG_ITEM_ID, lyric.getID());
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return allLyrics.size();
    }

    /**
     * ViewHolder for {@link LyricListAdapter}.
     */
    public class LyricListViewHolder extends RecyclerView.ViewHolder {

        private TextView lyricsTitle;
        private View entireLayout;

        public LyricListViewHolder(View itemView) {
            super(itemView);
            lyricsTitle = itemView.findViewById(android.R.id.text1);
            entireLayout = itemView;
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    //Show Dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage(R.string.delete_lyrics_prompt)
                            .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //Delete from view and database.
                                    removeLyric(getAdapterPosition());
                                    notifyItemRemoved(getAdapterPosition());
                                }
                            })
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User cancelled the dialog, do nothing.
                                }
                            })
                            .create()
                            .show();
                    return true;
                }
            });
        }

        public void setOnClickListener(View.OnClickListener onClickListener) {
            this.entireLayout.setOnClickListener(onClickListener);
        }

        public void setTitle(String title) {
            lyricsTitle.setText(title);
        }
    }
}