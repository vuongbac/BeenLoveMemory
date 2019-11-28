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

import com.example.beenlovememory.R;
import com.example.beenlovememory.view.StartActivity;
import com.example.beenlovememory.database.UserDAO;
import com.example.beenlovememory.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import me.itangqi.waveloadingview.WaveLoadingView;

public class WaveLoadingFragment extends Fragment {
    private WaveLoadingView loadingView;
    @SuppressLint("SimpleDateFormat")
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @SuppressLint("StaticFieldLeak")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_wave_loading,container,false);
        loadingView = view.findViewById(R.id.waveLoadingView);
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

    @SuppressLint({"StaticFieldLeak", "SetTextI18n"})
    private void setTime(String dayStart) throws ParseException {
        try {
            Date startDay = sdf.parse(dayStart);
            Date thisDay = new Date();
            final Long diff = thisDay.getTime() - startDay.getTime();

            if (startDay.before(thisDay)) {
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(diff);
                Long day = diff / 86400000;
                int days = day.intValue();

                if (days < 100) {
                    loadingView.setProgressValue(days);
                } else if (days < 200) {
                    loadingView.setProgressValue(days - 100);
                } else if (days < 300) {
                    loadingView.setProgressValue(days - 200);
                } else if (days < 400) {
                    loadingView.setProgressValue(days - 300);
                } else if (days < 500) {
                    loadingView.setProgressValue(days - 400);
                } else if (days < 600) {
                    loadingView.setProgressValue(days - 500);
                } else if (days < 700) {
                    loadingView.setProgressValue(days - 600);
                } else if (days < 800) {
                    loadingView.setProgressValue(days - 700);
                } else if (days < 900) {
                    loadingView.setProgressValue(days - 800);
                } else if (days < 1000) {
                    loadingView.setProgressValue(days - 900);
                }
                loadingView.setShapeType(WaveLoadingView.ShapeType.CIRCLE);
                loadingView.setTopTitle("Đã yêu");
                loadingView.setCenterTitle(day +" Day");
                loadingView.setBottomTitle("ngày");
                loadingView.pauseAnimation();
                loadingView.resumeAnimation();
                loadingView.cancelAnimation();
                loadingView.startAnimation();

            } else {
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
