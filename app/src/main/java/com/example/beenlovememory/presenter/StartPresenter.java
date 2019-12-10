package com.example.beenlovememory.presenter;

import android.widget.EditText;

import com.example.beenlovememory.model.StartView;

import java.util.Date;

public class StartPresenter {
    StartView startView;

    public StartPresenter(StartView startView) {
        this.startView = startView;
    }

    public void check(String nameB, String nameG  ) {
        if (nameB.isEmpty()) {
            startView.setErrorBoyname();
        }else if (nameG.isEmpty()) {
            startView.setErrorGirlname();
        }
        else {
            startView.intentStart();
            startView.successfulStart();
        }
    }

    public void khoitao() {
        startView.playMusic();


    }

}


