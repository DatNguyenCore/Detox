package com.example.detox.fragments;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.appcompat.widget.Toolbar;

import com.example.detox.R;
import com.example.detox.adapter.WhiteListAdapter;
import com.example.detox.models.WhiteListModal;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WhiteListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WhiteListFragment extends Fragment {
    private static final String TAG = "WhiteListFragment";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ArrayList<WhiteListModal> listInstalledApp = new ArrayList();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WhiteListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WhiteListFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

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
                listInstalledApp.add(new WhiteListModal(indexApp, appName, icon));

                indexApp++;
            }

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_white_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        WhiteListAdapter whiteListAdapter = new WhiteListAdapter(getContext(), listInstalledApp);

        ListView mListView = view.findViewById(R.id.list_view_white_list);
        mListView.setAdapter(whiteListAdapter);

        Toolbar myToolbar = view.findViewById(R.id.toolbar_white_list);
//        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d(TAG, "onClick: ");
////                Navigation.findNavController(view).navigate(R.id.action_whiteListFragment_to_mainFragment);
//            }
//        });
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.setSupportActionBar(myToolbar);

        ActionBar actionBar = appCompatActivity.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: ");

        return super.onOptionsItemSelected(item);
    }
}