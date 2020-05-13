package com.rktechapps.erekhanew.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rktechapps.erekhanew.R;
import com.rktechapps.erekhanew.models.DashboardModel;

import java.util.List;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> {
    List<DashboardModel> list;
    Context context;
    public DashboardAdapter(Context context, List<DashboardModel> values) {

        list = values;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtCount, txtLabel;

        public ViewHolder(View v) {

            super(v);
            txtCount = (TextView) v.findViewById(R.id.txtCountDashboard);
            txtLabel = (TextView) v.findViewById(R.id.txtLabelDashboard);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.dashboard_recycler_row_layout, parent, false);
        return new DashboardAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtCount.setText(list.get(position).getCount().toString());
        holder.txtLabel.setText(list.get(position).getCategory().toString());
    }



    @Override
    public int getItemCount() {

        return list.size();
    }


}
