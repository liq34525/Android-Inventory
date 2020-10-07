package comp3350.ei.presentation;

import android.app.Activity;

import comp3350.ei.presentation.Messages;

public class FieldParser {
    private final Activity owner;

    public FieldParser(Activity owner) {
        this.owner = owner;
    }

    public int getIntFromString(String field, String quantity) {
        int value = -1;

        if (quantity == null || quantity.isEmpty()) {
            quantity = "0";
        }
        else {
            if (quantity.trim().length() >= Integer.toString(Integer.MAX_VALUE).length()) {
                Messages.warning(owner, "Invalid " + field + ": too large");
                quantity = "-1";
            }
        }

        try {
            value = Integer.parseInt(quantity);
        }
        catch (final NumberFormatException e) {
            Messages.warning(owner, e.getMessage());
        }

        return value;
    }

    public double getDoubleFromString(String field, String price, int maxDecimalPlaces) {
        double value = -1;

        if (price == null || price.isEmpty()) {
            price = "0";
        }
        else if (price.length() + maxDecimalPlaces >= Integer.toString(Integer.MAX_VALUE).length()) {
            Messages.warning(owner, "Invalid " + field + ": too many digits");
            price = "-1";
        }

        try {
            value = Double.parseDouble(price);
        }
        catch (final NumberFormatException e) {
            Messages.warning(owner, e.getMessage());
        }

        return value;
    }
}
