package com.example.detox.fragments;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.detox.R;
import com.example.detox.adapter.WhiteListAdapter;
import com.example.detox.database.AppReaderContract;
import com.example.detox.database.AppReaderContract.AppEntry;
import com.example.detox.database.AppReaderDbHelper;
import com.example.detox.models.WhiteListModal;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WhiteListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WhiteListFragment extends Fragment implements WhiteListAdapter.OnWhiteListClickedListener {
    private static final String TAG = "WhiteListFragment";
    private FragmentActivity mActivity;
    private NavController mNavController;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ArrayList<WhiteListModal> listInstalledApp = new ArrayList();
    private AppReaderDbHelper dbHelper;

    public WhiteListFragment() {
        // Required empty public constructor
//        this.dbHelper = new AppReaderDbHelper(this.getContext());
    }

    public static WhiteListFragment newInstance(String param1, String param2) {
        WhiteListFragment fragment = new WhiteListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getAllApplications();
    }

    private void getAllApplications() {
        PackageManager packageManager = getActivity().getPackageManager();
        List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);

        long indexApp = 1;
        for(PackageInfo packageInfo: installedPackages) {
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {

                String appName = ((String) packageInfo.applicationInfo.loadLabel(packageManager)).trim();
                Drawable icon = packageManager.getApplicationIcon(packageInfo.applicationInfo);
                int intIcon = packageInfo.applicationInfo.icon;
                String appPackage = packageInfo.packageName;
                listInstalledApp.add(new WhiteListModal(indexApp, appName, icon, appPackage, intIcon));

                indexApp++;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflatedView = inflater.inflate(R.layout.fragment_white_list, container, false);

        this.dbHelper = new AppReaderDbHelper(container.getContext());

        this.setupToolBar(inflatedView);

        return inflatedView;
    }

    private void setupToolBar(View inflatedView) {
        Toolbar myToolbar = inflatedView.findViewById(R.id.toolbar_white_list);

        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.setSupportActionBar(myToolbar);
        ActionBar actionBar = appCompatActivity.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mActivity.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {

            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                mNavController.popBackStack();
                return false;
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        WhiteListAdapter whiteListAdapter = new WhiteListAdapter(this, listInstalledApp);

        ListView mListView = view.findViewById(R.id.list_view_white_list);
        mListView.setAdapter(whiteListAdapter);

        EditText editTextSearch = view.findViewById(R.id.edit_text_search_white_list);
        editTextSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_whiteListFragment_to_searchWhiteListFragment);
            }
        });

        mNavController = Navigation.findNavController(view);

        Log.d(TAG, "onViewCreated: ");

        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(AppEntry.TABLE_NAME, new String[] {
                            AppEntry._ID,
                            AppReaderContract.AppEntry.COLUMN_NAME_NAME,
                        AppReaderContract.AppEntry.COLUMN_NAME_ICON,
                            AppEntry.COLUMN_NAME_PACKAGE
                    },
                    null, null, null, null, null);

//                    db.query(AppEntry.TABLE_NAME,
//                    [
//                    AppReaderContract.AppEntry.COLUMN_NAME_NAME,
//            AppReaderContract.AppEntry.COLUMN_NAME_ICON,
//                    AppEntry.COLUMN_NAME_PACKAGE
//
//                            ],             // The array of columns to return (pass null to get all)
//                    null,              // The columns for the WHERE clause
//                    null,          // The values for the WHERE clause
//                    null,                   // don't group the rows
//                    null,                   // don't filter by row groups
//                    null);

            while(cursor.moveToNext()) {
                long itemId = cursor.getLong(
                        cursor.getColumnIndexOrThrow(AppEntry._ID));
                String name = String.valueOf(cursor.getColumnIndexOrThrow(AppEntry.COLUMN_NAME_NAME));
                Log.d(TAG, "onViewCreated name: " + name);

                String icon = String.valueOf(cursor.getColumnIndexOrThrow(AppEntry.COLUMN_NAME_ICON));
                Log.d(TAG, "onViewCreated icon: " + icon);

                String packageName = String.valueOf(cursor.getColumnIndexOrThrow(AppEntry.COLUMN_NAME_PACKAGE));

                Log.d(TAG, "onViewCreated packageName: " + packageName);

            }
            cursor.close();
        } catch (Exception ex) {
            Log.d(TAG, "onViewCreated error: " + ex.toString());
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        mActivity = requireActivity();
    }

    @Override
    public void ItemClicked(WhiteListModal data) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AppReaderContract.AppEntry.COLUMN_NAME_NAME, data.getName());
        values.put(AppReaderContract.AppEntry.COLUMN_NAME_ICON, data.getIntIcon());
        values.put(AppReaderContract.AppEntry.COLUMN_NAME_PACKAGE, data.getAppPackage());

        long newRowId = db.insert(AppReaderContract.AppEntry.TABLE_NAME, null, values);
    }
}