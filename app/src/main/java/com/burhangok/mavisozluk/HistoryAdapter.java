package com.burhangok.mavisozluk;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryHolder> implements View.OnClickListener {

    List<String> keywords;
    LayoutInflater layoutInflater;



    Context context;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    public HistoryAdapter(Context context, List<String> keywords) {
        this.keywords = keywords;
        this.context = context;
        this.layoutInflater= (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public void removeItem(int position) {
        keywords.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, keywords.size());
    }

    public void restoreItem(String model, int position) {
        keywords.add(position, model);
        // notify item added by position
        notifyItemInserted(position);
    }

    public Context getContext() {
        return context;
    }
    @Override
    public HistoryHolder onCreateViewHolder( ViewGroup viewGroup, int i) {

        View rowLayout = layoutInflater.inflate(R.layout.history_item_layout,null);

        rowLayout.setOnClickListener(this);
        HistoryHolder historyHolder= new HistoryHolder(rowLayout);

        return historyHolder;
    }

    @Override
    public void onBindViewHolder( HistoryHolder historyHolder, int i) {
        String keyword = keywords.get(i);
        historyHolder.keyword.setText(keyword);
    }

    @Override
    public int getItemCount() {
        return keywords.size();
    }

    @Override
    public void onClick(View v) {
        String searchedKeyword = ((TextView)v.findViewById(R.id.keyword)).getText().toString();

        Bundle bundle = new Bundle();
        bundle.putString("keyword",searchedKeyword);

        KeywordsFragment keywordsFragment = new KeywordsFragment();
        keywordsFragment.setArguments(bundle);

        fragmentManager =((AppCompatActivity)context).getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);

        fragmentTransaction.replace(R.id.fragment_area,keywordsFragment);
        fragmentTransaction.commit();
    }



    class HistoryHolder extends RecyclerView.ViewHolder {

        TextView keyword;
        public HistoryHolder(@NonNull View itemView) {
            super(itemView);
            keyword = itemView.findViewById(R.id.keyword);
        }
    }
}
