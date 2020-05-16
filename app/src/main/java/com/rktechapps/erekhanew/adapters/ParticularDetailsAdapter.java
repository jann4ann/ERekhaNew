package com.rktechapps.erekhanew.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rktechapps.erekhanew.R;

import java.util.Collections;
import java.util.List;

public class ParticularDetailsAdapter extends RecyclerView.Adapter<ParticularDetailsAdapter.ViewHolder> {

    List<String> list;
    Context context;
    public ParticularDetailsAdapter(Context context, List<String> values) {
        list = values;
        Collections.reverse(list);
        this.context = context;
    }

    public void setDataList(List<String> list) {
        this.list = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txtId, txtName, txtParentId;

        public ViewHolder(View v) {
            super(v);
            txtName = (TextView) v.findViewById(R.id.txtNameParticularDetail);
        }
    }

    @Override
    public ParticularDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.particular_details_recycler_row_layout, parent, false);
        return new ParticularDetailsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParticularDetailsAdapter.ViewHolder holder, int position) {
        holder.txtName.setText(list.get(position).toString());

    }



    @Override
    public int getItemCount() {
        if(list.size() > 5)
            return 5;
        else
            return list.size();
    }


}
