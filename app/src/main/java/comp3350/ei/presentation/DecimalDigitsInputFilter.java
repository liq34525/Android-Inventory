package comp3350.ei.presentation;

import java.util.regex.Pattern;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;

public class DecimalDigitsInputFilter implements InputFilter {
    private final Pattern mPattern;

    public DecimalDigitsInputFilter() {
        mPattern = Pattern.compile("\\d*+((\\.\\d{0,2})?)");
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        Matcher matcher;
        String replacement = source.subSequence(start, end).toString();
        String newVal = dest.subSequence(0, dstart).toString() + replacement
                + dest.subSequence(dend, dest.length()).toString();
        matcher = mPattern.matcher(newVal);
        if (!matcher.matches()) {
            return "";
        }
        return null;    //return the original
    }
}
