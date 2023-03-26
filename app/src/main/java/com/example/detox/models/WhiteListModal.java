package com.example.detox.models;

public class WhiteListModal {
    private String name;
    private long position;

    public WhiteListModal(long _position, String _name) {
        this.position = _position;
        this.name = _name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }
}
