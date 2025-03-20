package com.example.thelimiter.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.thelimiter.R;
import com.example.thelimiter.databinding.FragmentHomeBinding;

import java.util.Calendar;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private TextView limitBudget;
    private TextView limitData;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        SharedPreferences dataPrefs = requireActivity().getSharedPreferences("DataPrefs", Context.MODE_PRIVATE);
        SharedPreferences budgetPrefs = requireActivity().getSharedPreferences("BudgetPrefs", Context.MODE_PRIVATE);

        float dailyDataLimit = dataPrefs.getFloat("daily_data_limit", 0);
        float dailyBudget = budgetPrefs.getFloat("daily_budget", 0);

        float availableData = dailyDataLimit * currentDay;
        float availableBudget = dailyBudget * currentDay;


        limitBudget = view.findViewById(R.id.textLei);
        limitData = view.findViewById(R.id.textMB);
        if (dailyDataLimit > 0) {
            limitData.setText(String.format("Your data limit by today: \n %.2f MB", availableData));
        } else {
            limitData.setText("Set your data limit");
        }

        if (dailyBudget > 0) {
            limitBudget.setText(String.format("Your money limit by today:\n %.2f Lei", availableBudget));
        } else {
            limitBudget.setText("Set your budget limit");
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
