package com.example.detox.models;

import android.graphics.drawable.Drawable;

public class WhiteListModal {
    private String name;
    private long position;

    private Drawable icon;

    private int intIcon;

    public String getAppPackage() {
        return appPackage;
    }

    public void setAppPackage(String appPackage) {
        this.appPackage = appPackage;
    }

    private String appPackage;

    public WhiteListModal(long _position, String _name, Drawable _icon, String _appPackage, int _intIcon) {
        this.position = _position;
        this.name = _name;
        this.icon = _icon;
        this.appPackage = _appPackage;
        this.intIcon = _intIcon;
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

    public int getIntIcon() {
        return intIcon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}
