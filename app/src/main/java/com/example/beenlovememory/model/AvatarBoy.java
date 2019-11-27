package com.example.beenlovememory.model;

public class AvatarBoy {
    private int id;
    private byte[] avtBoy;

    public AvatarBoy() {
    }

    public AvatarBoy(byte[] avtBoy) {
        this.avtBoy = avtBoy;
    }

    public AvatarBoy(int id, byte[] avtBoy) {
        this.id = id;
        this.avtBoy = avtBoy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getAvtBoy() {
        return avtBoy;
    }

    public void setAvtBoy(byte[] avtBoy) {
        this.avtBoy = avtBoy;
    }
}
