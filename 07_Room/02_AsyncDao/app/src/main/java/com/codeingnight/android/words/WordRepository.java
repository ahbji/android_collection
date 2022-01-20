package com.codeingnight.android.words;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;

public class WordRepository {
    private final LiveData<List<Word>> allWordsLive;
    private final WordDao wordDao;
    private final Executor executor;

    WordRepository(Context context, Executor executor) {
        this.executor = executor;
        WordDatabase wordDatabase = WordDatabase.getDatabase(context.getApplicationContext());
        wordDao = wordDatabase.getWordDao();
        allWordsLive = wordDao.getAllWordsLive();
    }

    LiveData<List<Word>> getAllWordsLive() {
        return allWordsLive;
    }

    void insertWords(Word... words) {
        executor.execute(() -> {
            wordDao.insertWords(words);
        });
    }

    void updateWords(Word... words) {
        executor.execute(() -> {
            wordDao.updateWords(words);
        });
    }

    void deleteWords(Word... words) {
        executor.execute(() -> {
            wordDao.deleteWords(words);
        });
    }

    void deleteAllWords(Word... words) {
        executor.execute(wordDao::deleteAllWords);
    }
}
