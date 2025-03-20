package com.example.thelimiter.ui.money;

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

public class MoneyFragment extends Fragment {

    private EditText editMonthlyAmount;
    private Button btnSetAmount;
    private TextView textDailyBudget;
    private LimitCalculator limitCalculator;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_money, container, false);

        editMonthlyAmount = view.findViewById(R.id.edit_monthly_amount);
        btnSetAmount = view.findViewById(R.id.btn_set_amount);
        textDailyBudget = view.findViewById(R.id.textSetLei);

        limitCalculator = new LimitCalculator(requireContext(), "BudgetPrefs");

        if (limitCalculator.hasDailyLimit("daily_budget")) {
            textDailyBudget.setText(String.format("Daily Budget: %.2f Lei", limitCalculator.getDailyLimit("daily_budget")));
            editMonthlyAmount.setVisibility(View.GONE);
            btnSetAmount.setText("Edit");
        } else {
            textDailyBudget.setText("Set monthly amount:");
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
                float dailyBudget = limitCalculator.calculateAndSaveLimit("daily_budget", Double.parseDouble(amountStr));
                textDailyBudget.setText(String.format("Daily Budget: %.2f Lei", dailyBudget));

                editMonthlyAmount.setVisibility(View.GONE);
                btnSetAmount.setText("Edit");
            } else {
                textDailyBudget.setText("Please enter a valid amount.");
            }
        }
    }
}
