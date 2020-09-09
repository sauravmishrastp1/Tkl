package com.xpert.tkl.view.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import com.xpert.tkl.view.activity.MyWallet_Activity;
import com.xpert.tkl.R;
import com.xpert.tkl.constant.SharedPrefManager;
import com.xpert.tkl.view.activity.*;


public class SideMenuFragment extends Fragment {


    private View rootView;
    private View shareearnn,myvalletview,addvacation,profileview,contactuslayout,
            offrerlayout,helpview,addplaneview,viewbilllayout,subscription_plane_layout,
            logout,mycleanderview,logivueww,deleveryboylayout,orderhistorylayout,total_lead_layout;

        private TextView nametxt,emialtxt;


    public SideMenuFragment() {

    }

    public static SideMenuFragment newInstance() {
        return new SideMenuFragment();
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.ly_sidebar, container, false);
        shareearnn= rootView.findViewById(R.id.orderhistory);
        myvalletview = rootView.findViewById(R.id.mywallet);
        offrerlayout = rootView.findViewById(R.id.offerlayoutview);
        addvacation  = rootView.findViewById(R.id.addvactionview);
        deleveryboylayout = rootView.findViewById(R.id.deliveryboy);
        addplaneview = rootView.findViewById(R.id.addplaneview);
        helpview = rootView.findViewById(R.id.helpview);
        orderhistorylayout=rootView.findViewById(R.id.sharearn);
        logivueww = rootView.findViewById(R.id.LOGIN);
        subscription_plane_layout = rootView.findViewById(R.id.subscription_plane_layout);
        logout = rootView.findViewById(R.id.logoutview);
        profileview = rootView.findViewById(R.id.profileviewlayout);
        mycleanderview = rootView.findViewById(R.id.taotal_lead);
        contactuslayout = rootView.findViewById(R.id.contactuss);

         total_lead_layout = rootView.findViewById(R.id.taotal_lead);
        emialtxt = rootView.findViewById(R.id.user_email);
        nametxt = rootView.findViewById(R.id.name_user);
        mycleanderview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Total_Lead_Activity.class);
                startActivity(intent);
            }
        });
        subscription_plane_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Suscription_Plane_Activity.class);
                startActivity(intent);
            }
        });

        String email = SharedPrefManager.getInstance(getContext()).getUser().getPhone();
        String name = SharedPrefManager.getInstance(getContext()).getUser().getName();
        nametxt.setText(name);
       emialtxt.setText(email);
//        if(email.equals("null")){
//            profileview.setVisibility(View.GONE);
//            logout.setVisibility(View.GONE);
//            logivueww.setVisibility(View.VISIBLE);
//            deleveryboylayout.setVisibility(View.VISIBLE);
//        }
        orderhistorylayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AllApotimentActivity.class);
                startActivity(intent);
            }
        });
        orderhistorylayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SupporDetailtActivity.class);
                startActivity(intent);
            }
        });

//        contactuslayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), ContactUs.class);
//                startActivity(intent);
//            }
//        });
//        deleveryboylayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), DeliveryBoyActivityLogin.class);
//                startActivity(intent);
//            }
//        });
//        logivueww.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), LoginActivity.class);
//                startActivity(intent);
//            }
//        });
//        mycleanderview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), MainActivity.class);
//                startActivity(intent);
//            }
//        });

        addvacation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), TermConditionActivity.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                builder1.setMessage("Are You Sure !! to Logout ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                SharedPrefManager.getInstance(getContext()).logout();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }


        });
//
//        viewbilllayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), ViewBillActivity.class);
//                startActivity(intent);
//            }
//        });
        addplaneview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AllApotimentActivity.class);
                startActivity(intent);
            }
        });
//        helpview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), HelpActivity.class);
//                startActivity(intent);
//            }
//        });
//
        offrerlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FaqAvctivity.class);
                startActivity(intent);
            }
        });
        profileview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Pofile_Activity.class);
                startActivity(intent);
            }
        });
//        addvacation = rootView.findViewById(R.id.addvactionview);
//        addvacation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), ViewVacationActivtiy.class);
//                startActivity(intent);
//            }
//        });
        myvalletview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MyWallet_Activity.class);
                startActivity(intent);
            }
        });
        shareearnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShareAndEarnActivity.class);
                startActivity(intent);
            }
        });

        return rootView;

    }



}






