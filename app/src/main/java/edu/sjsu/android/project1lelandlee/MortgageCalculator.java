package edu.sjsu.android.project1lelandlee;

public class MortgageCalculator {

    public static double calculateMonthlyPayment(double principle, double annualInterestRate,
                                                 int loanTermYears, boolean includeTaxes) {
        int numberOfMonths = loanTermYears * 12;
        double monthlyTax = includeTaxes ? principle * 0.001 : 0; // 0.1% of principle

        if (annualInterestRate == 0) {
            return principle / numberOfMonths + monthlyTax;
        }

        double monthlyInterestRate = annualInterestRate / 100 / 12;
        double numerator = principle * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, numberOfMonths);
        double denominator = Math.pow(1 + monthlyInterestRate, numberOfMonths) - 1;

        return numerator / denominator + monthlyTax;
    }
}