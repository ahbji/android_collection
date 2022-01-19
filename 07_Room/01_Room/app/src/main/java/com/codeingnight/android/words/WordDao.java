package com.codeingnight.android.words;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WordDao {

    @Insert
    void insertWord(Word... words);

    @Update
    void updateWords(Word... words);

    @Delete
    void deleteWords(Word... words);

    @Query("DELETE FROM WORD")
    void deleteAllWords();

    @Query("SELECT * FROM WORD WHERE english_word = :english")
    Word getWordsByEnglish(String english);

    @Query("SELECT * FROM WORD ORDER BY ID DESC")
    List<Word> getWllWords();
}
