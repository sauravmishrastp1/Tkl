package com.xpert.tkl.view.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.xpert.tkl.Presenter.utils.VolleySingleton;
import com.xpert.tkl.R;
import com.xpert.tkl.constant.SharedPrefManager;
import com.xpert.tkl.model.StudentData;
import com.xpert.tkl.view.activity.MyWallet_Activity;
import com.xpert.tkl.view.activity.StudentViewProfile;
import com.xpert.tkl.view.activity.Suscription_Plane_Activity;
import com.xpert.tkl.view.activity.Wallter_Layout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

  public class StudentDataAdapter extends RecyclerView.Adapter<StudentDataAdapter.ViewHolder> {
    private ArrayList<StudentData>studentData;
    private Context context;
    String wallet_momey="0";
    String student_id;
    AlertDialog dialog;

    public StudentDataAdapter(ArrayList<StudentData> studentData, Context context) {
        this.studentData = studentData;
        this.context = context;
    }

       @NonNull
       @Override
       public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.student_data_item, parent, false);
        return new StudentDataAdapter.ViewHolder(view);
      }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        String s_namme = studentData.get(position).getName();
        student_id = studentData.get(position).getId();
        String s_class = studentData.get(position).getClass_();
        String s_address = studentData.get(position).getCity();
        String s_subject = studentData.get(position).getSubject();
        String img = studentData.get(position).getImage();
        holder.student_name.setText(s_namme);
        holder.studen_sjubject.setText(s_subject);
        holder.studen_class.setText("class:"+s_class);
        holder.student_address.setText("Greater noida");
          // holder.student_img.setImageResource(img);
       Picasso.with(context).load(img).placeholder(R.drawable.img22).into(holder.student_img);

        holder.profileview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(wallet_momey.equals("0")){

                    final AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
                    final View mView = LayoutInflater.from(context).inflate(R.layout.wallet_low, null);

                   Button add_money = mView.findViewById(R.id.add_money_wallet_);
                  //  TextView add_money = mView.findViewById(R.id.add_money_wallet_);
                   add_money.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           Intent intent = new Intent(context, Suscription_Plane_Activity.class);
                           intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                           context.startActivity(intent);
                       }
                   });

                    mBuilder.setView(mView);
                    dialog = mBuilder.create();
                    dialog.show();


                }else{
                    Intent intent = new Intent(context, StudentViewProfile.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("name",studentData.get(position).getName());
                intent.putExtra("address",studentData.get(position).getAddress());
                intent.putExtra("phone",studentData.get(position).getPhone_no());
                intent.putExtra("sunject",studentData.get(position).getSubject());
                intent.putExtra("class",studentData.get(position).getClass_());
                intent.putExtra("id",studentData.get(position).getId());
                intent.putExtra("img",studentData.get(position).getImage());
                intent.putExtra("dis",studentData.get(position).getDescription());

                    context.startActivity(intent);

                        view_student_profile();

                }
            }
        });
        getwalletblance();

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

    private void updatewallet(String new_balance){

        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="https://tklpvtltd.com/tkl/api/update-user-wallet?user_id="+SharedPrefManager.getInstance(context).getUser().getId()+"&money="+new_balance;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                  //  JSONArray jsonArray = jsonObject.getJSONArray("$data");
                    if(status.equals("200")){
                       // Toast.makeText(context, "update", Toast.LENGTH_SHORT).show();


                    }else {
                        //  Toast.makeText(MyWallet_Activity.this, "Data is not submit", Toast.LENGTH_SHORT).show();
                    }


                }catch (Exception e){
                    Toast.makeText(context, "somtihing wet wrong"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    // progressDialog.dismiss();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                 Toast.makeText(context, "Server Error!!", Toast.LENGTH_SHORT).show();
                //   progressDialog.dismiss();

            }
        });
        queue.getCache().clear();

        VolleySingleton.getInstance(context).getRequestQueue().getCache().clear();

        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }
    private void getwalletblance(){

        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="https://tklpvtltd.com/tkl/api/view-wallet?user_id="+ SharedPrefManager.getInstance(context).getUser().getId();
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    JSONArray jsonArray = jsonObject.getJSONArray("$data");
                    if(status.equals("200")){

                        for(int i=0;i<jsonArray.length();i++) {
                            JSONObject walletjson = jsonArray.getJSONObject(i);
                             wallet_momey = walletjson.getString("money");
                        }

                          //  walletmoney.setText("₹"+wallet_momey);
                        ///Toast.makeText(context, "size+"+wallet_momey, Toast.LENGTH_SHORT).show();
                    }else {

                        //  Toast.makeText(MyWallet_Activity.this, "Data is not submit", Toast.LENGTH_SHORT).show();
                    }


                }catch (Exception e){

                    //Toast.makeText(MyWallet_Activity.this, "somtihing wet wrong"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    // progressDialog.dismiss();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, "Server Error!!", Toast.LENGTH_SHORT).show();
                //   progressDialog.dismiss();

            }
        });
        queue.getCache().clear();

        VolleySingleton.getInstance(context).getRequestQueue().getCache().clear();

        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }
    private void view_student_profile(){

        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="https://tklpvtltd.com/tkl/api/view-student-lead?user_id="+SharedPrefManager.getInstance(context).getUser().getId()+"&student_id="+student_id+"&status="+"1"+"&money=10";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                   // JSONArray jsonArray = jsonObject.getJSONArray("$data");
                    if(status.equals("200")){
                     //   Toast.makeText(context, "udpate", Toast.LENGTH_SHORT).show();



                    }else {
                        //  Toast.makeText(MyWallet_Activity.this, "Data is not submit", Toast.LENGTH_SHORT).show();
                    }


                }catch (Exception e){
                    // Toast.makeText(HomeActivity.this, "somtihing wet wrong"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    // progressDialog.dismiss();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Toast.makeText(HomeActivity.this, "Server Error!!", Toast.LENGTH_SHORT).show();
                //   progressDialog.dismiss();

            }
        });
        queue.getCache().clear();

        VolleySingleton.getInstance(context).getRequestQueue().getCache().clear();

        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }
}
