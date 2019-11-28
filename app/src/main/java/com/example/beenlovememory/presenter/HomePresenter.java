package com.example.beenlovememory.presenter;

import android.media.MediaPlayer;
import android.widget.ImageView;

import com.example.beenlovememory.model.HomeView;

public class HomePresenter {
    HomeView homeView;

    public HomePresenter(HomeView homeView) {
        this.homeView = homeView;
    }

    public void media( MediaPlayer mediaPlayer){
        if (mediaPlayer.isPlaying()){
            homeView.pauseMusic();
        }else {
            homeView.startMusic();
        }
    }

    public void avatarBoy() {
        homeView.AvatarBoy();
    }

    public void avatarGirl() {
        homeView.AvatarGirl();
    }

    public void setImageGirl() {
        homeView.setAvatarGirl();
    }
    public void setImageBoy() {
        homeView.setAvatarBoy();
    }
    public void setInfo() {
        homeView.setInfo();
    }


}
