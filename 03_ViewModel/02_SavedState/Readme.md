# 保存 ViewModel 状态

虽然 ViewModel 的生命周期与 Activity/Fragment 分离，可以在屏幕旋转的时候自行恢复数据，但当内存不足时，ViewModel 仍会被系统回收。

通常有两种方案解决这个问题：
- 使用 Singleton 的 Model 类。使用单例可能会导致内存泄漏，例如一个对象已经没有用处了，但是单例中还持有它的引用，那么在整个应用程序的生命周期它都不能正常被回收，从而导致内存泄露。
- 使用 SavedState 库。

## 使用 SavedState 库

SavedState 库是 ViewModel 的一部分，使用时需要添加依赖。
```groovy
implementation 'androidx.lifecycle:lifecycle-viewmodel-savedstate:2.4.0'
```

在 ViewModel 的构造函数中需要初始化 SavedStateHandle 对象。
```java
public class SavedStateViewModel extends ViewModel {
    private SavedStateHandle handle;

    public SavedStateViewModel(SavedStateHandle savedStateHandle) {
        this.handle = savedStateHandle;
    }

    ...
}
```

在 UIController 中可以正常使用 ViewModel ，代码不需要改动。
```java
class MainFragment extends Fragment {
private SavedStateViewModel vm;

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(SavedStateViewModel.class);
        ...
        
    }
    ...
}
```

在提供自定义 ViewModelProvider. Factory 实例时，需要继承 AbstractSavedStateViewModelFactory 来启用 SavedStateHandle。
```java
class MainFragment extends Fragment {
private SavedStateViewModel vm;

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        vm = new ViewModelProvider(this, new SavedStateViewModelFactory(getApplication(), this)).get(ScoreboardViewModel.class);
        ...
        
    }
    ...
}
```

SavedStateHandle 类是一个键值映射，可以通过 set ()和 get ()方法写入和检索与保存状态之间的数据。

此外，还可以使用 getLiveData() 获取 SavedStateHandle 中的 LiveData。当键值更新时，LiveData 接收新值。
```java
class MainFragment extends Fragment {
private SavedStateViewModel vm;
    ...
    public MutableLiveData<ScoreStore> getLiveData() {
        if (!handle.contains(KEY)) {
            handle.set(KEY, new ScoreStore());
            init(new ScoreModel(0, 0));
        }
        return handle.getLiveData(KEY);
    }

    private void initLiveData(ScoreModel item) {
        Objects.requireNonNull(getScoreStore().getValue()).push(item);
        getScoreStore().setValue(getScoreStore().getValue());
    }
    ...
}
```

SavedStateHandle 中维护的数据可以跨进程的生命周期，和 savedInstanceState 一样，将序列化的数据临时持久化到 Parcel，所以序列化的对象不能太复杂，否则会占用大量内存。

SavedStateHandle 支持维护的数据类型包括：
- 基本类型、String、CharSquence 、Binder 、Bundle 、ArrayList 、SparseArray 、Serializable 、Parcelable 类型
- 基本类型数组、String/CharSequence 数组、Serializable 数组 、Parcelable 数组。

对于需要维护的复杂状态类，Android 建议实现 Parcelable 接口：
- 实现 describeContents 和 writeToParcel （负责写入 Parcel）。
- 提供 Creator 对象，负责从 Parcel 中读取数据，并填充到状态对象。

```java
public class ScoreStore implements Parcelable {
    
    private final Stack<ScoreModel> scoreStack;
    ...
    private ScoreStore(Parcel in, Stack<ScoreModel> scoreStack) {
        this.scoreStack = scoreStack;
        Parcelable[] parcelableArray = in.readParcelableArray(ScoreModel.class.getClassLoader());
        ScoreModel[] scores = new ScoreModel[parcelableArray.length];
        if (parcelableArray != null) {
            scores = Arrays.copyOf(parcelableArray, parcelableArray.length, ScoreModel[].class);
        }
        assert scores != null;
        Arrays.stream(scores).forEach(this.scoreStack::push);
    }

    public static final Creator<ScoreStore> CREATOR = new Creator<ScoreStore>() {
        public ScoreStore createFromParcel(Parcel in) {
            return new ScoreStore(in, new Stack<>());
        }

        public ScoreStore[] newArray(int size) {
            return new ScoreStore[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        ScoreModel[] array = new ScoreModel[scoreStack.size()];
        for (int i = 0; i < scoreStack.size(); i++ ) {
            array[i] = scoreStack.get(i);
        }
        out.writeParcelableArray(array, flags);
    }
    ...
}
```

测试 SavedStateHandle 需要在手机的开发者选项中将`不保留活动`开启，然后按 home 键将应用挂后台。
- 不保存 ViewModel 状态，恢复前台，ViewModel 状态数据清空。
- 保存 ViewModel 状态，恢复前台，挂后台前的 ViewModel 状态数据仍然存在。



