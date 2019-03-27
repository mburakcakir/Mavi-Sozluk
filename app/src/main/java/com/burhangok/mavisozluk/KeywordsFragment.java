package com.burhangok.mavisozluk;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class KeywordsFragment extends Fragment {


    public KeywordsFragment() {
        // Required empty public constructor
    }
    View fragmentView;
    public String aranacakKelime;

    public RecyclerView kelimelerRV;
    public Veritabani vt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_keywords,null);

        kelimelerRV = fragmentView.findViewById(R.id.keywords);

        this.aranacakKelime=getArguments().getString("keyword");

        vt = new Veritabani(this.getContext());

        List<Sozluk> sozlukList = vt.kelimeleriGetir(aranacakKelime);

        if(sozlukList.isEmpty()) {
            Toast.makeText(this.getContext(),  R.string.uyari, Toast.LENGTH_SHORT).show();
            getFragmentManager().popBackStack();
        }

        if(!this.aranacakKelime.isEmpty() && !vt.IsSavedHistory(this.aranacakKelime)){
            vt.GecmisEkle(this.aranacakKelime);
        }

        KeywordsAdapter keywordsAdapter = new KeywordsAdapter(this.getContext(),sozlukList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        kelimelerRV.setLayoutManager(linearLayoutManager);
        kelimelerRV.setItemAnimator(new DefaultItemAnimator());
        kelimelerRV.setAdapter(keywordsAdapter);



        return fragmentView;
    }

}
