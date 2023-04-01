package com.example.detox.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

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
    private FragmentActivity mActivity;
    private NavController mNavController;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ArrayList<WhiteListModal> listInstalledApp = new ArrayList();


    public WhiteListFragment() {
        // Required empty public constructor
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
                listInstalledApp.add(new WhiteListModal(indexApp, appName, icon));

                indexApp++;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflatedView = inflater.inflate(R.layout.fragment_white_list, container, false);

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

        WhiteListAdapter whiteListAdapter = new WhiteListAdapter(getContext(), listInstalledApp);

        ListView mListView = view.findViewById(R.id.list_view_white_list);
        mListView.setAdapter(whiteListAdapter);

        EditText editTextSearch = view.findViewById(R.id.edit_text_search_white_list);
        editTextSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mActivity, "Hello", Toast.LENGTH_SHORT).show();
            }
        });

        mNavController = Navigation.findNavController(view);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        mActivity = requireActivity();
    }
}