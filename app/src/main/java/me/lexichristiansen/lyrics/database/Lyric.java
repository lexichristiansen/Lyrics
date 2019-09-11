package me.lexichristiansen.lyrics.database;

/**
 * Lyric database object.
 */
public class Lyric {

    int _id;
    String title;
    String lyricsEnglish;
    String lyricsJapanese;

    public Lyric(){

    }

    public Lyric(int id, String name, String lyricsEnglish, String lyricsJapanese){
        this._id = id;
        this.title = name;
        this.lyricsEnglish = lyricsEnglish;
        this.lyricsJapanese = lyricsJapanese;
    }

    public Lyric(String name, String lyricsEnglish, String lyricsJapanese){
        this.title = name;
        this.lyricsEnglish = lyricsEnglish;
        this.lyricsJapanese = lyricsJapanese;
    }

    public int getID(){
        return this._id;
    }

    public void setID(int id){
        this._id = id;
    }

    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getLyricsEnglish(){
        return this.lyricsEnglish;
    }

    public void setLyricsEnglish(String lyricsEnglish){
        this.lyricsEnglish = lyricsEnglish;
    }

    public String getLyricsJapanese(){
        return this.lyricsJapanese;
    }

    public void setLyricsJapanese(String lyricsEnglish){
        this.lyricsJapanese = lyricsEnglish;
    }
}
