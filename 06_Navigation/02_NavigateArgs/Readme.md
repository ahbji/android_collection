# 导航参数

## 参数设置

设置步骤如下图所示：
![参数设置](./def_nav_args.png)

这里需要提供设置项：
- name: 导航参数名称
- type: 导航参数类型
- default value: 默认值

以下两个地方可以查看参数设置
- 在节点中查看
![在节点中查看参数设置](./browse_nav_args_frag.png)
- 在路径中查看
![在路径中查看参数设置](./browse_nav_args_path.png)

## 获取参数

在 HomeFragment 中，直接通过 NavController 执行导航

```kotlin
class HomeFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireView().findViewById<View>(R.id.button).setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_detailFragment)
        }
    }
}
```

在目标节点中，通过 get arguments 获取导航参数 

```kotlin
const val NAME_PARAM = "name"

class DetailFragment : Fragment() {
    private var name: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString(NAME_PARAM)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireView().findViewById<TextView>(R.id.name).text = name
    }
}
```

## 动态传参

NavController 执行导航时，也可以通过 bundle 传递导航参数：

```kotlin
class HomeFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireView().findViewById<View>(R.id.button).setOnClickListener {
            val str = requireView().findViewById<EditText>(R.id.editText).text.toString();
            if (!TextUtils.isEmpty(str)) {
                val bundle = Bundle()
                bundle.putString(NAME_PARAM, str)
                Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_detailFragment, bundle)
                return@setOnClickListener
            }
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_detailFragment)
        }
    }
}
```