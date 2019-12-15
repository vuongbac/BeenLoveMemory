package com.example.beenlovememory.presenter;

import android.widget.EditText;

import com.example.beenlovememory.model.StartView;

import java.util.Date;

public class StartPresenter {
    StartView startView;

    public StartPresenter(StartView startView) {
        this.startView = startView;
    }

    public void check(String nameB, String nameG ,String dayS) {
        if (nameB.isEmpty()) {
            startView.setErrorBoyname();
        }else if (nameG.isEmpty()) {
            startView.setErrorGirlname();
        } else if (dayS.isEmpty()) {
            startView.checkDay();
        } else {
            startView.intentStart();
            startView.successfulStart();
        }
    }

    public void khoitao() {
        startView.playMusic();


    }

}


