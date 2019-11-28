package com.example.beenlovememory.presenter;

import com.example.beenlovememory.model.StartView;

public class StartPresenter {
    StartView startView;

    public StartPresenter(StartView startView) {
        this.startView = startView;
    }

    public void check(String nameB, String nameG) {
        if (nameB.isEmpty() && nameG.isEmpty()) {
            startView.setErrorBoyname();
        }else {
            startView.intentStart();
            startView.successfulStart();
        }
    }

    public void khoitao() {
        startView.playMusic();


    }
}


