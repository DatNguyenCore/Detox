package com.example.detox.models;

import android.graphics.drawable.Drawable;

public class WhiteListModal {
    private String name;
    private long position;

    private Drawable icon;

    public WhiteListModal(long _position, String _name, Drawable _icon) {
        this.position = _position;
        this.name = _name;
        this.icon = _icon;
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

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}
