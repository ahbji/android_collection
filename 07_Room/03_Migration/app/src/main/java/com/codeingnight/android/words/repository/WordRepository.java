package com.codeingnight.android.words.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.codeingnight.android.words.persistent.Word;
import com.codeingnight.android.words.persistent.WordDao;
import com.codeingnight.android.words.persistent.WordDatabase;

import java.util.List;
import java.util.concurrent.Executor;

public class WordRepository {
    private final LiveData<List<Word>> allWordsLive;
    private final WordDao wordDao;
    private final Executor executor;

    public WordRepository(Context context, Executor executor) {
        this.executor = executor;
        WordDatabase wordDatabase = WordDatabase.getDatabase(context.getApplicationContext());
        wordDao = wordDatabase.getWordDao();
        allWordsLive = wordDao.getAllWordsLive();
    }

    public LiveData<List<Word>> getAllWordsLive() {
        return allWordsLive;
    }

    public void insertWords(Word... words) {
        executor.execute(() -> {
            wordDao.insertWords(words);
        });
    }

    public void blukInsertWords(List<Word> words) {
        executor.execute(() -> {
            wordDao.bulkInsertWords(words);
        });
    }

    public void updateWords(Word... words) {
        executor.execute(() -> {
            wordDao.updateWords(words);
        });
    }

    public void deleteWords(Word... words) {
        executor.execute(() -> {
            wordDao.deleteWords(words);
        });
    }

    public void deleteAllWords(Word... words) {
        executor.execute(wordDao::deleteAllWords);
    }
}
