package com.example.beenlovememory.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.beenlovememory.R;
import com.example.beenlovememory.view.StartActivity;
import com.example.beenlovememory.database.UserDAO;
import com.example.beenlovememory.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DetailFragment extends Fragment {
    private Button btnYear,btnMonth,btnDay,btnHour,btnMinute,btnSeconds;
    @SuppressLint("SimpleDateFormat")
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    @SuppressLint("StaticFieldLeak")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_detail,container,false);
        btnYear = view.findViewById(R.id.bt_nam);
        btnMonth = view.findViewById(R.id.bt_thang);
        btnDay = view.findViewById(R.id.bt_ngay);
        btnHour = view.findViewById(R.id.bt_gio);
        btnMinute = view.findViewById(R.id.bt_phut);

        new AsyncTask< Void , Void , Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    if (getSizeUser() == 0) {
                        Intent intent = new Intent(getContext(), StartActivity.class);
                        startActivity(intent);
                        Log.i("tag", "abc");
                    } else {
                        setInfor();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
        return view;
    }

    @SuppressLint("StaticFieldLeak")
    private void setInfor() {
        new AsyncTask<Void, Void, List<User>>() {
            @Override
            protected List<User> doInBackground(Void... voids) {
                List<User> users;
                users = UserDAO.getAllUser();
                return users;
            }
            @Override
            protected void onPostExecute(List<User> users) {
                super.onPostExecute(users);
                try {
                    setTime(sdf.format(users.get(0).getDateStart()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private void setTime(String startDays) throws ParseException {

        try {
            Date startDay = sdf.parse(startDays);
            Date thisDay = new Date();
            final Long diff = thisDay.getTime() - startDay.getTime();

            if (startDay.before(thisDay)) {

                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(diff); //truyen vao
                int mYear = c.get(Calendar.YEAR) - 1970;
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH) - 1;
                int hr = c.get(Calendar.HOUR);
                int min = c.get(Calendar.MINUTE);


                if (mYear < 10){
                    btnYear.setText("0"+mYear);
                }else {
                    btnYear.setText(String.valueOf(mYear));
                }
                if (mMonth < 10){
                    btnMonth.setText("0"+mMonth);
                }else {
                    btnMonth.setText(String.valueOf(mMonth));
                }
                if (mDay < 10){
                    btnDay.setText("0"+mDay);
                }else {
                    btnDay.setText(String.valueOf(mDay));
                }
                if (hr < 10){
                    btnHour.setText("0"+hr);
                }else {
                    btnHour.setText(String.valueOf(hr));
                }
                if (min < 10){
                    btnMinute.setText("0"+min);
                }else {
                    btnMinute.setText(String.valueOf(min));
                }

            }else {
                Intent intent = new Intent(getContext(), StartActivity.class);
                startActivity(intent);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private int getSizeUser() throws ParseException {
        final List<User> users = UserDAO.getAllUser();
        Log.d("SIZEE", users.size() + "");
        return users.size();
    }
}
