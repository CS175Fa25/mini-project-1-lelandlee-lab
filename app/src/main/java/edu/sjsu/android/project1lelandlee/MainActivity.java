package edu.sjsu.android.project1lelandlee;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private EditText principleEditText;
    private SeekBar interestSeekBar;
    private TextView interestTextView;
    private RadioGroup loanTermRadioGroup;
    private CheckBox taxesCheckBox;
    private Button calculateButton;
    private TextView resultTextView;
    private Button uninstallButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        principleEditText = findViewById(R.id.principleEditText);
        interestSeekBar = findViewById(R.id.interestSeekBar);
        interestTextView = findViewById(R.id.interestTextView);
        loanTermRadioGroup = findViewById(R.id.loanTermRadioGroup);
        taxesCheckBox = findViewById(R.id.taxesCheckBox);
        calculateButton = findViewById(R.id.calculateButton);
        resultTextView = findViewById(R.id.resultTextView);
        uninstallButton = findViewById(R.id.uninstallButton);

        // Set default radio button
        RadioButton fifteenYearsRadioButton = findViewById(R.id.fifteenYearsRadioButton);
        fifteenYearsRadioButton.setChecked(true);

        // Set up interest seek bar listener
        interestSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                double interestRate = progress / 10.0;
                interestTextView.setText(String.format("%.1f%%", interestRate));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Calculate button click listener
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateMonthlyPayment();
            }
        });

        // Uninstall button click listener
        uninstallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uninstallApp();
            }
        });
    }

    private void calculateMonthlyPayment() {
        // Validate principle input
        String principleString = principleEditText.getText().toString().trim();

        if (TextUtils.isEmpty(principleString)) {
            resultTextView.setText(getString(R.string.empty_input_error));
            return;
        }

        // Validate decimal places
        if (!isValidDecimal(principleString)) {
            resultTextView.setText(getString(R.string.decimal_error));
            return;
        }

        try {
            double principle = Double.parseDouble(principleString);
            if (principle <= 0) {
                resultTextView.setText(getString(R.string.empty_input_error));
                return;
            }

            // Get interest rate
            double annualInterestRate = interestSeekBar.getProgress() / 10.0;

            // Get loan term
            int selectedId = loanTermRadioGroup.getCheckedRadioButtonId();
            int loanTermYears = 15; // default
            if (selectedId == R.id.fifteenYearsRadioButton) {
                loanTermYears = 15;
            } else if (selectedId == R.id.twentyYearsRadioButton) {
                loanTermYears = 20;
            } else if (selectedId == R.id.thirtyYearsRadioButton) {
                loanTermYears = 30;
            }

            // Check if taxes are included
            boolean includeTaxes = taxesCheckBox.isChecked();

            // Calculate monthly payment
            double monthlyPayment = MortgageCalculator.calculateMonthlyPayment(
                    principle, annualInterestRate, loanTermYears, includeTaxes);

            // Display result
            resultTextView.setText(String.format(getString(R.string.monthly_payment), monthlyPayment));

        } catch (NumberFormatException e) {
            resultTextView.setText(getString(R.string.decimal_error));
        }
    }

    private boolean isValidDecimal(String input) {
        // Check if input has more than 2 decimal places
        Pattern pattern = Pattern.compile("^\\d+(\\.\\d{1,2})?$");
        return pattern.matcher(input).matches();
    }

    private void uninstallApp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Uninstall App");
        builder.setMessage("Are you sure you want to uninstall this app?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            Intent delete = new Intent(Intent.ACTION_DELETE);
            delete.setData(Uri.parse("package:" + getPackageName()));
            startActivity(delete);
        });
        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        builder.show();
    }
}