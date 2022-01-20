package com.codeingnight.android.bottomnavigationdemo;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class FirstFragment extends Fragment {
    private static final String TAG = "hello";
    private FirstViewModel mViewModel;
    private ImageView imageView;

    public static FirstFragment newInstance() {
        return new FirstFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_fragment, container, false);
        imageView = view.findViewById(R.id.imageView);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(requireActivity()).get(FirstViewModel.class);
        imageView.setRotation(mViewModel.rotationPosition);
        final ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(imageView, "rotation", 0, 0);
        objectAnimator.setDuration(500);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!objectAnimator.isRunning()) {
                    objectAnimator.setFloatValues(imageView.getRotation(), imageView.getRotation() + 100);
                    mViewModel.rotationPosition += 100;
                    objectAnimator.start();
                }
            }
        });
    }

}
