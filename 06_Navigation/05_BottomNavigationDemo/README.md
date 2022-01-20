# 使用 BottomNavigationView

基本的 BottomNavigationView 包括：
- menu
- navigation
- FragmentContainerView
- BottomNavigationView

定义 menu 资源：
```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">

    <item
        android:id="@+id/firstFragment"
        android:icon="@drawable/ic_looks_one_black_24dp"
        android:title="@string/rotate" />
    <item
        android:id="@+id/secondFragment"
        android:icon="@drawable/ic_looks_two_black_24dp"
        android:title="@string/scale" />
    <item
        android:id="@+id/thirdFragment"
        android:icon="@drawable/ic_looks_3_black_24dp"
        android:title="@string/translate" />
</menu>
```

定义 navigation 资源：
```xml
<?xml version="1.0" encoding="utf-8"?>
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools" android:id="@+id/navigation"
    app:startDestination="@id/firstFragment">

    <fragment android:id="@+id/firstFragment"
        android:name="com.codeingnight.android.bottomnavigationdemo.FirstFragment" android:label="旋转"
        tools:layout="@layout/first_fragment" />
    <fragment android:id="@+id/secondFragment"
        android:name="com.codeingnight.android.bottomnavigationdemo.SecondFragment" android:label="缩放"
        tools:layout="@layout/second_fragment" />
    <fragment android:id="@+id/thirdFragment"
        android:name="com.codeingnight.android.bottomnavigationdemo.ThirdFragment" android:label="移动"
        tools:layout="@layout/third_fragment" />
</navigation>
```

在 MainActivity Layout 中定义 BottomNavigationView 和 FragmentContainerView
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
xmlns:app="http://schemas.android.com/apk/res-auto"
xmlns:tools="http://schemas.android.com/tools"
android:layout_width="match_parent"
android:layout_height="match_parent"
tools:context="com.codeingnight.android.bottomnavigationdemo.MainActivity">

    <com.google.android.material.bottomnavigation.BottomNavigationView
        android:id="@+id/bottomNavigationView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="#F9F9F9"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:menu="@menu/menu" />

    <androidx.fragment.app.FragmentContainerView
        android:id="@+id/fragment"
        android:name="androidx.navigation.fragment.NavHostFragment"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        app:defaultNavHost="true"
        app:layout_constraintBottom_toTopOf="@+id/bottomNavigationView"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:navGraph="@navigation/navigation" />
</androidx.constraintlayout.widget.ConstraintLayout>
```

最后在 MainActivity 中装配这些：
```java
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 获取 BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        // 获取 NavController 
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();
        // 使用 bottomNavigationView menu 构建 AppBarConfiguration
        AppBarConfiguration configuration = new AppBarConfiguration.Builder(bottomNavigationView.getMenu()).build();
        // 将 NavController 和 AppBarConfiguration 设置到 ActionBar
        NavigationUI.setupActionBarWithNavController(this,navController,configuration);
        // 将 bottomNavigationView 设置到 NavController
        NavigationUI.setupWithNavController(bottomNavigationView,navController);
    }
}
```
