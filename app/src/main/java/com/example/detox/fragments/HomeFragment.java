package com.example.detox.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.detox.R;
import com.example.detox.configs.DurationLock;
import com.example.detox.services.LockService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private Context mContext;
    private Activity mActivity;
    private static final String TAG = "HomeFragment";
    private ActivityResultLauncher mActivityResultLauncher;
    private EditText mEditTextHours;
    private EditText mEditTextMinus;
    public static final String HONE_INTENT_DURATION_OVERLAY = "HONE_INTENT_DURATION_OVERLAY";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

        mContext = this.getContext();
        mActivity = this.getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        try {
                            Long duration = result.getData().getLongExtra(HomeFragment.HONE_INTENT_DURATION_OVERLAY, DurationLock.NONE);

                            if(duration != DurationLock.NONE) {
                                HomeFragment.this.startService(duration);
                            }
                        } catch (Exception err) {
                            Log.d(TAG, "onActivityResult: " + err.toString());
                        }
                    }
                }
        );

        view.findViewById(R.id.button_home_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    long hours = 0;
                    long minutes = 0;
                    String stringHours = mEditTextHours.getText().toString();
                    String stringMinutes = mEditTextMinus.getText().toString();

                    if(stringHours.matches("") && stringMinutes.matches("")) {
                        Toast.makeText(mContext, "Need to set Select time", Toast.LENGTH_LONG).show();
                        return;
                    }

                    if(stringHours.matches("") && !stringMinutes.matches("")) {
                        long longMinutes = Long.parseLong(stringMinutes);
                        minutes = longMinutes * 60 * 1000;
                    }

                    if(!stringHours.matches("") && stringMinutes.matches("")) {
                        long longHours = Long.parseLong(stringHours);
                        hours = longHours * 60 * 60 * 1000;
                    }

                    if(!stringHours.matches("") && !stringMinutes.matches("")) {
                        long longMinutes = Long.parseLong(stringMinutes);
                        long longHours = Long.parseLong(stringHours);

                        minutes = longMinutes * 60 * 1000;
                        hours = longHours * 60 * 60 * 1000;
                    }

                    HomeFragment.this.checkOverlayPermission(hours + minutes);
                } catch (Exception err) {
                    Log.d(TAG, "onClick: " + err.toString());
                }
            }
        });

        view.findViewById(R.id.button_home_start_time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_whiteListFragment);
                HomeFragment.this.checkOverlayPermission(DurationLock.TEN_MINS);
            }
        });

        view.findViewById(R.id.button_home_start_time_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_whiteListFragment);
                HomeFragment.this.checkOverlayPermission(DurationLock.TWENTY_MINS);
            }
        });

        view.findViewById(R.id.button_home_start_time_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_whiteListFragment);
                HomeFragment.this.checkOverlayPermission(DurationLock.ONE_HOUR);
            }
        });

        mEditTextHours = view.findViewById(R.id.edit_text_home_hours);
        mEditTextMinus = view.findViewById(R.id.edit_text_home_minus);

        mEditTextHours.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }


            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (mEditTextHours.getText().toString().length() == 2) {
                    mEditTextMinus.requestFocus();
                    mEditTextMinus.setSelection(mEditTextMinus.length());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mEditTextMinus.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    String textValue = mEditTextMinus.getText().toString();

                    if (!textValue.matches("")) {
                        if (Integer.parseInt(textValue) > 59) {
                            mEditTextMinus.setText("59");
                            mEditTextMinus.setSelection(mEditTextMinus.length());
                        }
                    } else {
                        mEditTextHours.requestFocus();
                    }
                } catch (Exception e) {
                    mEditTextHours.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private boolean isGrandDrawOverlays() {
        return Settings.canDrawOverlays(mContext);
    }

    // method for starting the service
    public void startService(long duration) {
        // check if the user has already granted
        // the Draw over other apps permission
        if (isGrandDrawOverlays()) {
            // start the service based on the android version
            Intent intent = new Intent(mContext, LockService.class);
            intent.putExtra(HONE_INTENT_DURATION_OVERLAY, duration);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mContext.startForegroundService(intent);
            } else {
                mActivity.startService(intent);
            }
        } else {
            Toast.makeText(mContext,
                    "Detox has not granted permission.",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void requestOverlayPermission(long duration) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + mActivity.getPackageName()));
        intent.putExtra(HONE_INTENT_DURATION_OVERLAY, duration);
        mActivityResultLauncher.launch(intent);
    }

    // method to ask user to grant the Overlay permission
    private void checkOverlayPermission(long duration) {
        if (isGrandDrawOverlays()) {
            this.startService(duration);
        } else {
            requestOverlayPermission(duration);
        }
    }

}