package com.example.detox.database;

import android.provider.BaseColumns;

public class AppReaderContract {
    private AppReaderContract() {
    }

    public static class AppEntry implements BaseColumns {
        public static final String TABLE_NAME = "app";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_PACKAGE = "package";
        public static final String COLUMN_NAME_ICON = "icon";
    }

}
