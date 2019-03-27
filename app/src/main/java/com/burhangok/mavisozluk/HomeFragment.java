package com.burhangok.mavisozluk;


import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_home, null);

        init();
        mactivity = this.getActivity();
        searchBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchedKeyword = keywordET.getText().toString();

                Bundle bundle = new Bundle();
                bundle.putString("keyword", searchedKeyword);

                KeywordsFragment keywordsFragment = new KeywordsFragment();
                keywordsFragment.setArguments(bundle);

                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);

                fragmentTransaction.replace(R.id.fragment_area, keywordsFragment);
                fragmentTransaction.commit();
            }
        });

        vt = new Veritabani(this.getContext());
        historySozlukList = vt.GecmisListele();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        historyAdapter = new HistoryAdapter(this.getContext(), historySozlukList);

        listRV.setLayoutManager(linearLayoutManager);
        listRV.setItemAnimator(new DefaultItemAnimator());
        listRV.setAdapter(historyAdapter);

        return fragmentView;
    }

    void init() {
        keywordET = fragmentView.findViewById(R.id.searchKeyword);
        searchBTN = fragmentView.findViewById(R.id.searchBtn);
        listRV = fragmentView.findViewById(R.id.list);

    }

}
