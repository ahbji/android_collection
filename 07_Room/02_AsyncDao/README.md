# 异步执行 DAO

在实际开发中，为了防止 DAO 操作阻塞主线程，一般是在单独线程中执行 DAO 操作。

## 实现异步 DAO

### 使用 LiveData 进行可观察查询

执行查询时，您通常会希望应用的界面在数据发生变化时自动更新。

为此，可以在查询方法中使用 LiveData 类型的返回值。当数据库更新时，Room 会生成更新 LiveData 所必需的所有代码。

```java
public class WordRepository {
    LiveData<List<Word>> getAllWordsLive() {
        return allWordsLive;
    }
}
```

### 异步实现的方式

对于修改数据库的操作，则以异步的方式执行。

#### 使用 AsyncTask 执行 DAO

```java
public class WordRepository {
    void insertWords(Word... words) {
        new InsertAsyncTask(wordDao).execute(words);
    }
    
    static class InsertAsyncTask extends AsyncTask<Word, Void, Void> {
        private final WordDao wordDao;

        InsertAsyncTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            wordDao.insertWords(words);
            return null;
        }
    }
}
```

> AsyncTask 自从 API 30 开始被废弃，官方建议用标准 JUC 库中的线程池来执行异步任务。

### 使用 JUC 库执行异步 DAO

```java
public class WordRepository {
    private final Executor executor;

    WordRepository(Context context, Executor executor) {
        this.executor = executor;
        WordDatabase wordDatabase = WordDatabase.getDatabase(context.getApplicationContext());
        wordDao = wordDatabase.getWordDao();
        allWordsLive = wordDao.getAllWordsLive();
    }

    void insertWords(Word... words) {
        executor.execute(new Task());
    }

    class Task implements Runnable {
        @Override
        public void run() {
            wordDao.insertWords(words);
        }
    }
}
```

这种方式需要在 Application 类中编写一个线程池 executor ，然后注入 WordRepository 类。

```java
public class MyApplication extends Application {
    /*
     * 获取可用核心cpu核心数
     * (not always the same as the maximum number of cores)
     */
    private static int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();

    // 实例化 Runnable 队列
    private final BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();

    // Sets the amount of time an idle thread waits before terminating
    private static final int KEEP_ALIVE_TIME = 1;
    // 设置时间单位
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

    // Creates a thread pool manager
    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            NUMBER_OF_CORES,       // Initial pool size
            NUMBER_OF_CORES,       // Max pool size
            KEEP_ALIVE_TIME,
            KEEP_ALIVE_TIME_UNIT,
            workQueue
    );
}

public class WordViewModel extends AndroidViewModel {
    private final WordRepository wordRepository;
    public WordViewModel(@NonNull Application application) {
        super(application);
        MyApplication app = (MyApplication) application;
        wordRepository = new WordRepository(application, app.threadPoolExecutor);
    }
}
```

### 其他方案

RxJava 、Guava 、Kotlin 协程、Flow 等
