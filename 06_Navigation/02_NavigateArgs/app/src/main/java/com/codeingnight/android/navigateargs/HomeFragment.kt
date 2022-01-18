package com.codeingnight.android.navigateargs

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.Navigation

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

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