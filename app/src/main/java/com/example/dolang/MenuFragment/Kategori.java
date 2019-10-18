package com.example.dolang.MenuFragment;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dolang.Adapter.FragmentAdapter;
import com.example.dolang.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Kategori extends Fragment {



    public Kategori() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_kategori, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewPager viewPager = view.findViewById(R.id.viewPager);
        TabLayout tabs = view.findViewById(R.id.tabLayout);
        Bundle b1 = new Bundle();
        Bundle b2 = new Bundle();
        Bundle b3 = new Bundle();
        Bundle b4 = new Bundle();
        Bundle b5 = new Bundle();
        b1.putString("CATEGORY", "curug");
        b2.putString("CATEGORY", "bukit");
        b3.putString("CATEGORY", "pantai");
        b4.putString("CATEGORY", "taman");
        b5.putString("CATEGORY", "kolam renang");
        WisataFragment curugFragment = new WisataFragment();
        WisataFragment bukitFragment = new WisataFragment();
        WisataFragment pantaiFragment = new WisataFragment();
        WisataFragment tamanFragment = new WisataFragment();
        WisataFragment kolamrenagFragment = new WisataFragment();
        curugFragment.setArguments(b1);
        bukitFragment.setArguments(b2);
        pantaiFragment.setArguments(b3);
        tamanFragment.setArguments(b4);
        kolamrenagFragment.setArguments(b5);
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getChildFragmentManager());
        fragmentAdapter.addFragment(curugFragment, "Wisata Curug");
        fragmentAdapter.addFragment(bukitFragment, "Wisata Bukit");
        fragmentAdapter.addFragment(pantaiFragment, "Wisata Pantai");
        fragmentAdapter.addFragment(tamanFragment, "Wisata Taman");
        fragmentAdapter.addFragment(kolamrenagFragment, "Wisata Kolam Renang");
        viewPager.setAdapter(fragmentAdapter);
        tabs.setupWithViewPager(viewPager);

    }

}