package com.xpert.tkl.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.xpert.tkl.R;
import com.xpert.tkl.model.MyWalletModel;
import com.xpert.tkl.view.activity.Wallter_Layout;

import java.util.ArrayList;

public class MyWalletLayout extends RecyclerView.Adapter<MyWalletLayout.ViewHolder> {
    private ArrayList<MyWalletModel> stateNames;
    private Context context;


    public MyWalletLayout(ArrayList<MyWalletModel> stateNames, Context context) {
        this.stateNames = stateNames;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.addmonylayout,parent,false);
        return new MyWalletLayout.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

       final String addmoney = stateNames.get(position).getPay();
       String cashback = stateNames.get(position).getCashback();
       holder.addmoney.setText("Pay"+" "+"\u20B9"+addmoney);
       holder.cashback.setText(cashback);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Wallter_Layout.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("money",addmoney);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return stateNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       private TextView addmoney,cashback;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            addmoney = itemView.findViewById(R.id.addrs);
            cashback = itemView.findViewById(R.id.cashback);

        }
    }
}
