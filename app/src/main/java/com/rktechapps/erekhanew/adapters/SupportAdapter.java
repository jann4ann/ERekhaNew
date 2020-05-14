package com.rktechapps.erekhanew.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rktechapps.erekhanew.R;
import com.rktechapps.erekhanew.models.DashboardModel;
import com.rktechapps.erekhanew.models.HelpDeskResponse;

import java.util.List;

public class SupportAdapter extends RecyclerView.Adapter<SupportAdapter.ViewHolder> {


    public interface onContactClickedListener{
        void onHelpNumberClicked(String number);
    }
    private  onContactClickedListener mListener;
    public void setOnContactClickedLister(onContactClickedListener listener){
        this.mListener = listener;
    }
    List<HelpDeskResponse.HelpDeskData> list;
    Context context;
    public SupportAdapter(Context context, List<HelpDeskResponse.HelpDeskData> values) {

        list = values;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView txtName, txtMobile;

        public ViewHolder(View v) {

            super(v);
            txtName = (TextView) v.findViewById(R.id.txtContactName);
            txtMobile = (TextView) v.findViewById(R.id.txtContactNumber);
            txtMobile.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onHelpNumberClicked(txtMobile.getText().toString().trim());
        }
    }

    @Override
    public SupportAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.support_recycler_row_layout, parent, false);
        return new SupportAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SupportAdapter.ViewHolder holder, int position) {
        holder.txtName.setText(list.get(position).getName().toString());
        holder.txtMobile.setText(list.get(position).getMobile().toString());
    }



    @Override
    public int getItemCount() {

        return list.size();
    }


}
