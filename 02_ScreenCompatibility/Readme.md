# Android 屏幕适配

## 布局适配 

尽量使用相对布局或相对约束

例如，在 ConstraintLayout 中为引导线 Guideline 设置百分比，可以是 UI 在不同尺寸的屏幕上一相同的比例排布元素。

```xml
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <android.support.constraint.Guideline
        android:id="@+id/guideline1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        app:layout_constraintGuide_percent="0.1" />

    <android.support.constraint.Guideline
        android:id="@+id/guideline2"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        app:layout_constraintGuide_percent="0.4" />

    <android.support.constraint.Guideline
        android:id="@+id/guideline3"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        app:layout_constraintGuide_percent="0.7" />

    <android.support.constraint.Guideline
        android:id="@+id/guideline4"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        app:layout_constraintGuide_percent="0.5" />

</android.support.constraint.ConstraintLayout>
```

## 屏幕方向适配

对于不需要横竖屏切换的 Activity ，可以设置 Activity 的 screenOrientation 属性为 "portrait" 或 "landscape" 。

```xml
<activity
    android:name=".MainActivity"
    android:exported="true"
    android:screenOrientation="portrait">
</activity>
```

对于需要切换横竖屏的 Activity ，则需要额外准备 landscape 版本或 tablet 版本的 layout 文件
- landscape layout （layout-land）是手机横屏时使用的布局文件
- tablet layout （layout-sw600dp）是大屏设备使用的 layout 文件

另外，因为横竖屏切换时 Activity 被销毁然后重新创建，因此需要缓存必要的数据。

Activity 提供了 onSaveInstanceState 回调用于将数据存入 Bundle 缓存中。

然后在 onCreate 中从 Bundle 缓存中读取数据并写到对应的 UI widget 中。

```java
@Override
protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putString("KEY", textView.getText().toString());
}

@Override
protected void onCreate(Bundle savedInstanceState) {
    ...
    if (savedInstanceState != null) {
        String s = savedInstanceState.getString("KEY");
        textView.setText(s);
    }
    ...
}
```