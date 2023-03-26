package com.example.detox.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.detox.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CongratulationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CongratulationFragment extends Fragment {
    private static final String TAG = "CongratulationFragment";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CongratulationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CongratulationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CongratulationFragment newInstance(String param1, String param2) {
        CongratulationFragment fragment = new CongratulationFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_congratulation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View mViewForm = view.findViewById(R.id.constraint_congratulation_form);
        View mViewCongratulation = view.findViewById(R.id.constraint_congratulation);

        Button mBtnSeeResult = view.findViewById(R.id.button_congratulation_start);
        mBtnSeeResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewCongratulation.setVisibility(View.GONE);
                mViewForm.setVisibility(View.VISIBLE);
            }
        });

        final String prefixName = "Yes, I'm ";
        Button mButtonName = view.findViewById(R.id.button_congratulation_name);
        mButtonName.setText(prefixName + "...");
        mButtonName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_congratulationFragment2_to_mainFragment);
            }
        });

        EditText mEditTitleName = view.findViewById(R.id.edit_text_congratulation_title_name);
        mEditTitleName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().matches("")) {
                    mButtonName.setText(prefixName + "...");
                } else {
                    mButtonName.setText(prefixName + charSequence);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}