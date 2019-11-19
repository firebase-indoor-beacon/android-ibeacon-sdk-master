package com.example.ibeacon;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class MessageFragment extends Fragment implements View.OnClickListener {
    private ImageButton ranking_show;
    private ImageButton article_show;
    private ImageButton found_show;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ranking_show:
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new PopularFragment(), null)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.article_show:
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new PttarticleFragment(), null)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.found_show:
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new SaleFragment(), null)
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_message, container, false);

        ImageButton d = (ImageButton) v.findViewById(R.id.ranking_show);
        d.setOnClickListener(this);
        ImageButton b = (ImageButton) v.findViewById(R.id.article_show);
        b.setOnClickListener(this);
        ImageButton c = (ImageButton) v.findViewById(R.id.found_show);
        c.setOnClickListener(this);

        return  v;


    }

}
