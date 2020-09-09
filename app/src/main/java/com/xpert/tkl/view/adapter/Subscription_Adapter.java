package com.xpert.tkl.view.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.paykun.sdk.PaykunApiCall;
import com.paykun.sdk.eventbus.Events;
import com.paykun.sdk.eventbus.GlobalBus;
import com.paykun.sdk.helper.PaykunHelper;
import com.xpert.tkl.Presenter.utils.VolleySingleton;
import com.xpert.tkl.R;
import com.xpert.tkl.constant.SharedPrefManager;
import com.xpert.tkl.model.Subsription_plane_model;
import com.xpert.tkl.view.activity.MyWallet_Activity;
import com.xpert.tkl.view.activity.Wallter_Layout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Subscription_Adapter extends RecyclerView.Adapter<Subscription_Adapter.ViewHolder> {
    private ArrayList<Subsription_plane_model>subsription_plane_models;
    private Context context;
    AlertDialog dialog;
    String addamout,id;
    private String Testacceskey="289D665931662D236BFD9F8D156E2D8B";
    private String testmerchentid="384135165653649";
    private String TestacceskeyLIVE="6A75D3707CC25C07001C004D34286CD3";
    private String testmerchentidLIVE="837773351399967";

    public Subscription_Adapter(ArrayList<Subsription_plane_model> subsription_plane_models, Context context) {
        this.subsription_plane_models = subsription_plane_models;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.subscription_item, parent, false);
        return new Subscription_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final String heading = subsription_plane_models.get(position).getTitle();
        final String price = subsription_plane_models.get(position).getPrice();
        String type = subsription_plane_models.get(position).getType();
        id = subsription_plane_models.get(position).getId();
        final String dis = subsription_plane_models.get(position).getDiscription();
        String amount = subsription_plane_models.get(position).getCources();
        int gstamount = Integer.parseInt(amount)-Integer.parseInt(price);
       // Toast.makeText(context, ""+gstamount, Toast.LENGTH_SHORT).show();

        YoYo.with(Techniques.FlipInX)
                .duration(700)
                .repeat(0)
                .playOn(holder.cardview);

        if(heading.equals("Silver")){
            holder.cardview.setCardBackgroundColor(Color.parseColor("#6EA2FF"));
            holder.planename.setText(heading);
            holder.duration.setText(type);
            holder.price.setText("₹"+""+price);
            holder.gst.setText("GST-"+gstamount);
            holder.total_amount.setText("Total Amount-₹"+amount);
        }
            if(heading.equals("Gold")){
                holder.cardview.setCardBackgroundColor(Color.parseColor("#3FB444"));
                holder.planename.setText(heading);
                holder.duration.setText(type);
                holder.price.setText("₹"+""+price);
                holder.gst.setText("GST-"+gstamount);
                holder.total_amount.setText("Total Amount-₹"+amount);
            }
                if(heading.equals("Dimand"))
                {
                    holder.cardview.setCardBackgroundColor(Color.parseColor("#FF5A60"));
                    holder.planename.setText(heading);
                    holder.duration.setText(type);
                    holder.price.setText("₹"+""+price);
                    holder.gst.setText("GST-"+gstamount);
                    holder.total_amount.setText("Total Amount-₹"+amount);
                }
                    if(heading.equals("Platinum")){
                        holder.planename.setText(heading);
                        holder.duration.setText(type);
                        holder.price.setText("₹"+""+price);
                        holder.cardview.setCardBackgroundColor(Color.parseColor("#E0AB00"));
                        holder.gst.setText("GST-"+gstamount);
                        holder.total_amount.setText("Total Amount-₹"+amount);
                    }

                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            final AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
                            final View mView = LayoutInflater.from(context).inflate(R.layout.purchase_plane, null);

                            Button add_money = mView.findViewById(R.id.add_money_wallet_);
                            TextView textView = mView.findViewById(R.id.txtvw);
                            TextView textView1 = mView.findViewById(R.id.txtvw2);
                            textView1.setText(dis);
                            textView.setText(heading);
                            add_money.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                  //  paymentdatway(price,subsription_plane_models.get(position).getId());
                                 //   buy_planeapi(id);
                                    Intent intent = new Intent(context, Wallter_Layout.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra("id",subsription_plane_models.get(position).getId());
                                    intent.putExtra("money",subsription_plane_models.get(position).getPrice());
                                    context.startActivity(intent);
                                }
                            });

                            mBuilder.setView(mView);
                            dialog = mBuilder.create();
                            dialog.show();

                        }
                    });

    }

    @Override
    public int getItemCount() {
        return subsription_plane_models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView planename,duration,price,gst,total_amount;
        CardView cardview;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            planename  = itemView.findViewById(R.id.textview_headig);
            cardview  = itemView.findViewById(R.id.caedview);
            duration = itemView.findViewById(R.id.dis);
            price = itemView.findViewById(R.id.price_inrupee);
            gst = itemView.findViewById(R.id.gst);
            total_amount = itemView.findViewById(R.id.total_amount);
        }
    }
    private void paymentdatway(String money,String id){
        // mCurrentEnv = Instamojo.Environment.PRODUCTION;
        //Toast.makeText(Wallter_Layout.this, "money->"+addmoney, Toast.LENGTH_SHORT).show();
        JSONObject object = new JSONObject();
        try {
            object.put("merchant_id", testmerchentid);
            object.put("access_token",Testacceskey);
            object.put("customer_name", SharedPrefManager.getInstance(context).getUser().getName());
            object.put("customer_email","sauravmishrastp1@gmail.com");
            object.put("customer_phone", "9560618681");
            object.put("product_name","Add wallet");
            object.put("order_no",System.currentTimeMillis()); // order no. should have 10 to 30 character in numeric format
            object.put("amount",money);  // minimum amount should be 10
            object.put("isLive",false); // need to send false if you are in sandbox mode

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
        new PaykunApiCall.Builder((Activity) context).sendJsonObject(object); // Paykun api to initialize your payment and send info.

    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void getResults(Events.PaymentMessage message) {
        if(message.getResults().equalsIgnoreCase(PaykunHelper.MESSAGE_SUCCESS)){
            // onpaymnetcomplete();
            Toast.makeText(context, "hi11", Toast.LENGTH_SHORT).show();
            buy_planeapi(id);
            // do your stuff here
            // message.getTransactionId() will return your failed or succeed transaction id
            /* if you want to get your transaction detail call message.getTransactionDetail()
             *  getTransactionDetail return all the field from server and you can use it here as per your need
             *  For Example you want to get Order id from detail use message.getTransactionDetail().order.orderId */

            if(!TextUtils.isEmpty(message.getTransactionId())) {
                Toast.makeText(context, "Your Transaction is succeed with transaction id : "+message.getTransactionId(),
                        Toast.LENGTH_SHORT).show();


            }
        }

        else if(message.getResults().equalsIgnoreCase(PaykunHelper.MESSAGE_FAILED)){
            // do your stuff here
            Toast.makeText(context,"Your Transaction is failed",Toast.LENGTH_SHORT).show();
        }
        else if(message.getResults().equalsIgnoreCase(PaykunHelper.MESSAGE_SERVER_ISSUE)){
            // do your stuff here
            Toast.makeText(context,PaykunHelper.MESSAGE_SERVER_ISSUE,Toast.LENGTH_SHORT).show();
        }else if(message.getResults().equalsIgnoreCase(PaykunHelper.MESSAGE_ACCESS_TOKEN_MISSING)){
            // do your stuff here
            Toast.makeText(context,"Access Token missing",Toast.LENGTH_SHORT).show();
        }
        else if(message.getResults().equalsIgnoreCase(PaykunHelper.MESSAGE_MERCHANT_ID_MISSING)){
            // do your stuff here
            Toast.makeText(context,"Merchant Id is missing",Toast.LENGTH_SHORT).show();
        }
        else if(message.getResults().equalsIgnoreCase(PaykunHelper.MESSAGE_INVALID_REQUEST)){
            Toast.makeText(context,"Invalid Request",Toast.LENGTH_SHORT).show();
        }
        else if(message.getResults().equalsIgnoreCase(PaykunHelper.MESSAGE_NETWORK_NOT_AVAILABLE)){
            Toast.makeText(context,"Network is not available",Toast.LENGTH_SHORT).show();
        }
    }

    private void buy_planeapi(String final_money){
      //  Toast.makeText(context, "hi12", Toast.LENGTH_SHORT).show();

        RequestQueue queue =  Volley.newRequestQueue(context);
      //  String url= "https://tklpvtltd.com/tkl/api/user-subscription-plan?user_id=1&subcription_id=1&plan_id=1234"
        String url ="https://tklpvtltd.com/tkl/api/user-subscription-plan?user_id="+SharedPrefManager.getInstance(context).getUser().getId()+"subcription_id="+final_money+"&plan_id="+System.currentTimeMillis();
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if(status.equals("200")){
                        Toast.makeText(context, "Sucessfully", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }else {
                        Toast.makeText(context, "Operation fail", Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    Toast.makeText(context, "Somthing went wrong"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "server error!!"+error.networkResponse, Toast.LENGTH_SHORT).show();
            }
        }){

//            @Override
//            protected Map getParams()
//            {
//                Map params = new HashMap();
//                params.put("user_id",SharedPrefManager.getInstance(context).getUser().getId());
//                params.put("money",addmoney);
//
//                return params;
//            }

        };
        queue.getCache().clear();

        VolleySingleton.getInstance(context).getRequestQueue().getCache().clear();

        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }

}
