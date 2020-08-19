package com.xpert.tkl.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.xpert.tkl.R;
import com.xpert.tkl.model.StudentData;
import com.xpert.tkl.view.activity.StudentViewProfile;

import java.util.ArrayList;

public class Apotiment_Adapter extends RecyclerView.Adapter<Apotiment_Adapter.ViewHolder> {
    private ArrayList<StudentData>studentData;
    private Context context;

    public Apotiment_Adapter(ArrayList<StudentData> studentData, Context context) {
        this.studentData = studentData;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.aptionment_it, parent, false);
        return new Apotiment_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        String s_namme = studentData.get(position).getName();
        String s_class = studentData.get(position).getClass_();
        String s_address = studentData.get(position).getAddress();
        String s_subject = studentData.get(position).getSubject();
        int img = studentData.get(position).getImage();
        holder.student_name.setText(s_namme);
        holder.studen_sjubject.setVisibility(View.GONE);
        holder.studen_class.setVisibility(View.GONE);
        holder.student_address.setText(s_address);
        holder.student_img.setImageResource(img);
      //  Picasso.with(context).load(img).into(holder.student_img);
        holder.profileview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, StudentViewProfile.class);
                intent.putExtra("name",studentData.get(position).getName());
                intent.putExtra("address",studentData.get(position).getAddress());
                intent.putExtra("phone",studentData.get(position).getPhone_no());
                intent.putExtra("sunject",studentData.get(position).getSubject());
                intent.putExtra("class",studentData.get(position).getClass_());
                intent.putExtra("id",studentData.get(position).getId());
                intent.putExtra("img",studentData.get(position).getImage());
                intent.putExtra("dis",studentData.get(position).getDescription());

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return studentData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView student_img;
        Button profileview;
        TextView student_name,student_address,studen_class,studen_sjubject;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            student_name = itemView.findViewById(R.id.name);
            student_address =itemView.findViewById(R.id.address);
            studen_class = itemView.findViewById(R.id.classs);
            studen_sjubject = itemView.findViewById(R.id.subject);
            student_img = itemView.findViewById(R.id.student_img);
            profileview = itemView.findViewById(R.id.button);
        }
    }
}
