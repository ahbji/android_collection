# LiveData

ViewModel 和 LiveData 结合使用实现对数据的监听，UI 通过监听数据的变化，从而实现自动更新。

LiveData 库基于观察者模式，UIController 通过为 LiveData 对象设置 Observer 驱动 UI 的更新。

## 使用 LiveData

下面以计数器为例，看看如何使用 LiveData 库。

Model 类定义不变：

```java
public class MainModel {
    private int number = 0;

    public void increment() {
        number++;
    }

    public int getNumber() {
        return number;
    }
}
```

在 ViewModel 类中，使用 LiveData 封装 Model 对象。

需要注意的是，increment 方法中，更新完数据后，需要调用 liveData 对象的 setValue 方法刷新 Model 对象。

使用 LiveData 对象后，原先向 UIController 提供 Model 对象的读取通道则改为提供 LiveData 对象的读取通道。

```java
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private MutableLiveData<MainModel> liveData;

    public MutableLiveData<MainModel> getData() {
        if (liveData == null) {
            // 初始化 LiveData
            liveData = new MutableLiveData<>();
            // 为 LiveData 设置初始值
            liveData.setValue(new MainModel());
        }
        return liveData;
    }

    private MainModel getModel() {
        return liveData.getValue();
    }

    public void increment() {
        // 更新 Model 数据
        MainModel model = getValue();
        model.increment();
        // 刷新 LiveData
        liveData.setValue(model);
    }
}
```

> LiveData 对象的初始化可以在构造函数中，也可以在 UIController 第一次读取 LiveData 时初始化。

UIController 则通过 ViewModel 对象获取 LiveData 对象，然后对 LiveData 对象设置 Observer。

当 Model 对象有更新时，会通知 Observer ，这里在 onChanged 方法中根据 Model 对象的变化更新 UI。

```java
public class MainActivity extends AppCompatActivity {
    FloatingActionButton fab;
    TextView textView;
    MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ...
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.getLiveData().observe(this, new Observer<MainModel>() {
            @Override
            public void onChanged(MainModel mainModel) {
                textView.setText(String.valueOf(mainModel.getNumber()));
            }
        });
        fab.setOnClickListener((view) -> {
            viewModel.increment();
        });
    }
}
```

## LiveData 特性

LiveData 的特点： 
- **确保界面符合数据状态**
    对比使用 LiveData 前后，代码得到精简，去掉了冗余的更新 UI 的代码。当底层数据发生变化时，LiveData 会通知 [`Observer`](https://developer.android.com/reference/androidx/lifecycle/Observer) 对象。在 UIController 中，统一使用  `Observer`  对象更新 UI ，这样一来，就无须在各个交互场景下分别对 UI 进行更新。
- **不会发生内存泄漏**
    `Observer` 对象 会绑定到 [`Lifecycle`](https://developer.android.com/reference/androidx/lifecycle/Lifecycle) 对象，并在其关联的生命周期遭到销毁后进行自我清理。
- **不会因 Activity 停止而导致崩溃**
    如果 `Observer` 的生命周期处于非活跃状态（如返回栈中的 Activity），则它不会接收任何 LiveData 事件。
- **不再需要手动处理生命周期**
    界面组件只是观察相关数据，不会停止或恢复观察。Jetpack 框架将自动管理所有这些操作，因为它在观察时可以感知相关的生命周期状态变化。
- **数据始终保持最新状态**
    如果生命周期变为非活跃状态，它会在再次变为活跃状态时接收最新的数据。例如，曾经在后台的 Activity 会在返回前台后立即接收最新的数据。
- **适当的配置更改**
    如果由于配置更改（如设备旋转）而重新创建了 Activity 或 Fragment，它会立即接收最新的可用数据。
- **共享资源**
    可以使用单例模式扩展 [`LiveData`](https://developer.android.com/reference/androidx/lifecycle/LiveData) 对象以封装系统服务，以便在应用中共享它们。`LiveData` 对象连接到系统服务一次，然后需要相应资源的任何观察者只需观察 `LiveData` 对象。如需了解详情，请参阅 [扩展 LiveData](https://developer.android.com/topic/libraries/architecture/livedata#extend_livedata)。