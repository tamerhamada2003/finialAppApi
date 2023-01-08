package com.example.applicatioprojectxd.Validation;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    static public boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());

    }



    public boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    public static boolean validCellPhone(String number) {
        return android.util.Patterns.PHONE.matcher(number).matches();
    }


    public static boolean validateUserName(String username) {
        Pattern patternUsername;
        Matcher matcher;
        final String USERNAME_PATTERN =
                "^(?![_-]).[A-Za-z0-9_-]((?!_-|-_).)(?<![-_]){3,9}$";


        patternUsername = Pattern.compile(USERNAME_PATTERN);
        matcher = patternUsername.matcher(USERNAME_PATTERN);
        return matcher.matches();

    }

}