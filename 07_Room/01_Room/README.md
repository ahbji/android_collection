# Room

Room 框架是 SQLite 的抽象。

Room 框架在充分利用 SQLite 的强大功能的同时，能够流畅地访问数据库。

Room 框架数据库访问抽象成 3 个主要组件：
- Database：包含数据库持有者，并作为应用已保留的持久关系型数据的底层连接的主要接入点。
- DAO：定义用于访问数据库的方法
- Entity：描述数据库中的表结构

应用通过访问 Room 数据库来获取与该数据库关联的数据访问对象 (DAO)。
DAO 负责从数据库中获取 Entity 对象，并将更改后的 Entity 保存回数据库中。
应用通过 Entity 来获取和设置与数据库中的表列相对应的数据。

具体如下图所示：
![](https://developer.android.com/images/training/data-storage/room_architecture.png)

## Room 基本使用

### 添加依赖

```groovy
dependencies {
    def room_version = "2.4.1"

    implementation "androidx.room:room-runtime:$room_version"
    annotationProcessor "androidx.room:room-compiler:$room_version"
}
```

### 定义 Database

下面是最简单的定义方式。

```java
@Database(entities = {Word.class}, version = 1, exportSchema = false)
public abstract class WordDatabase extends RoomDatabase {
    public abstract WordDao getWordDao();
}
```
- entities 中指定 Entity 列表
- version 中指定 version code ，用于数据库升级
- exportSchema = false ：不导出 Database Schema

### 定义 Entity

```java
@Entity
public class Word {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "english_word")
    private String word;
    @ColumnInfo(name = "chinese_meaning")
    private String chineseMeaning;

    public Word(String word, String chineseMeaning) {
        this.word = word;
        this.chineseMeaning = chineseMeaning;
    }
}
```
- @PrimaryKey(autoGenerate = true) 标识主键字段，autoGenerate = true 表示主键值自动生成
- @ColumnInfo(name = "xxx") 标识普通字段，name = "xxx" 表示数据库中的字段名

### 定义 DAO

```java
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

    @Query("SELECT * FROM WORD ORDER BY ID DESC")
    List<Word> getWllWords();
}
```
- @Insert 标识 insert 操作，
- @Update 标识 update 操作，
- @Delete 标识 delete 操作，
- @Query 用于自定义 SQL 查询操作，Room 会在编译时验证 SQL ，如果 SQL 有错误，则编译失败；另外，Room 还会验证查询的返回值，以确保返回的对象中的字段名称与查询响应中的对应列名称匹配，如果不匹配，则对不匹配的部分进行警告，或在全部不匹配时报错。
- ... 表示一次可以操作多个对象

@Query 也可以为 SQL 传参，例如：
```java
@Dao
public interface WordDao {
    @Query("SELECT * FROM WORD WHERE english_word = :english")
    Word getWordsByEnglish(String english);
}
```
Room 在生成 DAO 实现时，自动为 SQL 的参数位置设置占位符，以避免 SQL 注入。

对于查询结果为空的情况，需要进行判空处理。

### 获取 Database

在 Activity 中，通过 Room.databaseBuilder 函数获取 Database 对象，如果数据库文件不存在，则在第一次调用时创建数据库文件。

```java
public class MainActivity extends AppCompatActivity {

    WordDatabase wordDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        WordDatabase db = Room.databaseBuilder(getApplicationContext(), WordDatabase.class, "word_database")
                .allowMainThreadQueries()
                .build();
    }
}
```
- allowMainThreadQueries() 则允许在 MainThread 中执行 DAO 操作。

在 App 中， Database 对象通常是在应用运行的整个生命周期中存在，另外，创建 Database 的成本非常高，所以一般单例模式封装 Database 类，通过单例方法获取 Database 对象。

```java
@Database(entities = {Word.class}, version = 1, exportSchema = false)
public abstract class WordDatabase extends RoomDatabase {
    private static WordDatabase INSTANCE;

    static WordDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), WordDatabase.class, "word_database")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    public abstract WordDao getWordDao();
}
```
