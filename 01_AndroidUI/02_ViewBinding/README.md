# ViewBinding

ViewBinding 是 DataBinding 的轻量版，不需要事先将 Layout 转换为 binding Layout。

启用 ViewBinding

```groovy
viewBinding {
    enabled true
}
```

在 Acitivity 中使用 ViewBinding
```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.textView.text = "Text"
    }
}
```

在 Fragment 中使用 ViewBinding
```kotlin
class FirstFragment : Fragment() {
    private lateinit var binding: FirstFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FirstFragmentBinding.inflate(layoutInflater)
        binding.textView.text = "Text"
        return binding.root
    }
    ...
}
```