package com.example.beenlovememory.model;

public class AvatarGirl {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private byte[] Avt_girl;

    public AvatarGirl() {

    }

    public byte[] getAvt_girl() {
        return Avt_girl;
    }

    public void setAvt_girl(byte[] avt_girl) {
        Avt_girl = avt_girl;
    }

    public AvatarGirl(byte[] avt_girl) {
        Avt_girl = avt_girl;
    }
}
