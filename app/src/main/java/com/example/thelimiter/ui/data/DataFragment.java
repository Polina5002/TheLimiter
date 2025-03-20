package com.example.thelimiter.ui.data;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.thelimiter.R;
import com.example.thelimiter.utils.LimitCalculator;

public class DataFragment extends Fragment {

    private EditText editMonthlyAmount;
    private Button btnSetAmount;
    private TextView textDailyLimit;
    private LimitCalculator limitCalculator;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data, container, false);

        editMonthlyAmount = view.findViewById(R.id.edit_data_limit);
        btnSetAmount = view.findViewById(R.id.btn_set_data);
        textDailyLimit = view.findViewById(R.id.textSetData);

        limitCalculator = new LimitCalculator(requireContext(), "DataPrefs");

        if (limitCalculator.hasDailyLimit("daily_data_limit")) {
            textDailyLimit.setText(String.format("Daily Data Limit: %.2f MB", limitCalculator.getDailyLimit("daily_data_limit")));
            editMonthlyAmount.setVisibility(View.GONE);
            btnSetAmount.setText("Edit");
        } else {
            textDailyLimit.setText("Set monthly data limit:");
            btnSetAmount.setText("Save");
        }

        btnSetAmount.setOnClickListener(v -> handleButtonClick());

        return view;
    }

    private void handleButtonClick() {
        if (btnSetAmount.getText().equals("Edit")) {
            editMonthlyAmount.setVisibility(View.VISIBLE);
            btnSetAmount.setText("Save");
        } else {
            String amountStr = editMonthlyAmount.getText().toString();

            if (!amountStr.isEmpty()) {
                float dailyLimit = limitCalculator.calculateAndSaveLimit("daily_data_limit", Double.parseDouble(amountStr));
                textDailyLimit.setText(String.format("Daily Data Limit: %.2f MB", dailyLimit));

                editMonthlyAmount.setVisibility(View.GONE);
                btnSetAmount.setText("Edit");
            } else {
                textDailyLimit.setText("Please enter a valid amount.");
            }
        }
    }
}
