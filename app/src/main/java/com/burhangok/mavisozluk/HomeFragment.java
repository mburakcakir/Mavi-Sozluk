package com.burhangok.mavisozluk;


import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    View fragmentView;

    public EditText keywordET;
    public Button searchBTN;
    RecyclerView listRV;
    List<String> historySozlukList;
    public Veritabani vt;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    HistoryAdapter historyAdapter;
    private Paint p = new Paint();
    Activity mactivity;
    public String aranacakKelime;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_home, null);

        init();
        mactivity = this.getActivity();


        vt = new Veritabani(this.getContext());
        historySozlukList = vt.GecmisListele();

      LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        historyAdapter = new HistoryAdapter(this.getContext(), historySozlukList);

        listRV.setLayoutManager(linearLayoutManager);
        listRV.setItemAnimator(new DefaultItemAnimator());
        listRV.setAdapter(historyAdapter);


        keywordET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                aranacakKelime=s.toString();
                List<Sozluk> sozlukList = vt.kelimeleriGetir(aranacakKelime);

                if(!aranacakKelime.isEmpty() && !vt.IsSavedHistory(aranacakKelime) && aranacakKelime.length()>3){
                    vt.GecmisEkle(aranacakKelime);
                }

                KeywordsAdapter keywordsAdapter = new KeywordsAdapter(HomeFragment.this.getContext(),sozlukList);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HomeFragment.this.getContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                listRV.setLayoutManager(linearLayoutManager);
                listRV.setItemAnimator(new DefaultItemAnimator());
                listRV.setAdapter(keywordsAdapter);

            }
        });


        return fragmentView;
    }

    void init() {
        keywordET = fragmentView.findViewById(R.id.searchKeyword);
        listRV = fragmentView.findViewById(R.id.list);

    }

}
