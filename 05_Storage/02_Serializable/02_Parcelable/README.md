统一进程中的 Android 组件之间传递的对象:
- 方案1：对象实现 Parcelable 接口。
> Serializable 接口实现也可以传递，但效率不如 Parcelable 。
- 方案2：通过 Application 对象共享对象。

不同进程的 Android 组件之间传递对象，对象只能实现 Parcelable 接口。
> AndroidManifest 文件中可以为 Android 组件指定 android:process 属性，定义子进程名：
```xml
<activity
    android:name=".DetailActivity"
    android:process=":process2"
    android:exported="false" />
```

实现 Parcelable 接口：
- 实现 describeContents 和 writeToParcel （负责写入 Parcel）。
- 提供 Creator 对象，负责从 Parcel 中读取数据，并填充到状态对象。

```java
public class Student implements Parcelable {
    private String name;
    private int age;
    private Score score;
    
    protected Student(Parcel in) {
        name = in.readString();
        age = in.readInt();
        score = in.readParcelable(Score.class.getClassLoader());
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };
    ...
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
        dest.writeParcelable(score, flags);
    }
}

class Score implements Parcelable {
    private int math;
    private int english;
    private int chinese;
    private final String grade;
    ...
    protected Score(Parcel in) {
        math = in.readInt();
        english = in.readInt();
        chinese = in.readInt();
        grade = in.readString();
    }

    public static final Creator<Score> CREATOR = new Creator<Score>() {
        @Override
        public Score createFromParcel(Parcel in) {
            return new Score(in);
        }

        @Override
        public Score[] newArray(int size) {
            return new Score[size];
        }
    };
    ...
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(math);
        dest.writeInt(english);
        dest.writeInt(chinese);
        dest.writeString(grade);
    }
}
```

传递对象:
- 直接传给 Intent
- 通过 Bundle 对象组织对象。
```java
protected void onCreate(Bundle savedInstanceState) {
    ...

    Student student = new Student("测试",10, new Score(90, 80, 70));
    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
//            intent.putExtra("student", student);
    Bundle bundle = new Bundle();
    bundle.putParcelable("student", student);
    intent.putExtra("data", bundle);
    startActivity(intent);
}
```
获取对象
```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    ...
    Student student = getIntent().getBundleExtra("data").getParcelable("student");
    ...
    name.setText(student.getName());
    age.setText(String.valueOf(student.getAge()));
    grade.setText(student.getScore().getGrade());
}
```