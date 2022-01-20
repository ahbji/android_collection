# 使用 Paging 实现分页加载

添加依赖
```groovy
dependencies {
  def paging_version = "2.1.2"

  implementation "androidx.paging:paging-runtime:$paging_version" // For Kotlin use paging-runtime-ktx
}
```

数据集类型为 DataSource.Factory<Integer, Student>

```java
@Dao
public interface StudentDao {
    @Query("SELECT * FROM student_table ORDER BY id")
    DataSource.Factory<Integer, Student> getAllStudents();
}
```

定义 PagedListAdapter

PagedListAdapter 是 RecyclerView.Adapter 的子类。

构造 PagedListAdapter 时，需要设置 DiffUtil.ItemCallback 接口匹配数据 Item。

BindViewHolder 时，要分两种情况处理：
- getItem(position) 为空时
- getItem(position) 有数据时

```java
public class MyPagedAdapter extends PagedListAdapter<Student, MyPagedAdapter.MyViewHolder> {
    public MyPagedAdapter() {
        super(new DiffUtil.ItemCallback<Student>() {
            // 匹配是否为同一个 Item
            @Override
            public boolean areItemsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
                return oldItem.getId() == newItem.getId();
            }
            
            // 匹配 Item 的内容是否一样。
            @Override
            public boolean areContentsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
                return oldItem.getStudentNumber() == newItem.getStudentNumber();
            }
        });
    }
    ...
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Student student = getItem(position);
        if (student == null) {
            holder.textView.setText("loading");
        } else {
            holder.textView.setText(String.valueOf(student.getStudentNumber()));
        }
    }
    ...
}
```

在 Activity 中使用 PagedListAdapter 和 RecyclerViewAdapter 也基本一样。

监听 PagedList<Student> 时，使用 LivePagedListBuilder 构建构建 LiveData<PagedList<Student>>。

在 Observer 中监听到 PagedList<Student> 变化时，通过 pagedAdapter.submitList 提交数据。

```java
public class MainActivity extends AppCompatActivity {
    ...
    LiveData<PagedList<Student>> allStudentsLivePaged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ...
        pagedAdapter = new MyPagedAdapter();
        ...
        recyclerView.setAdapter(pagedAdapter);
        ...
        allStudentsLivePaged = new LivePagedListBuilder<>(studentDao.getAllStudents(), 2)
                .build();
        allStudentsLivePaged.observe(this, new Observer<PagedList<Student>>() {
            @Override
            public void onChanged(final PagedList<Student> students) {
                pagedAdapter.submitList(students);
            }
        });
        ...
    }
}
```

pagedList 可以设置 PagedList.Callback 监听分页加载的过程：

```java
allStudentsLivePaged.observe(this, new Observer<PagedList<Student>>() {
    @Override
    public void onChanged(final PagedList<Student> students) {
        pagedAdapter.submitList(students);
        students.addWeakCallback(null, new PagedList.Callback() {
            @Override
            public void onChanged(int position, int count) {
                Log.d("myLog", "onChanged: " + students);
            }

            @Override
            public void onInserted(int position, int count) {

            }

            @Override
            public void onRemoved(int position, int count) {

            }
        });
    }
});
```