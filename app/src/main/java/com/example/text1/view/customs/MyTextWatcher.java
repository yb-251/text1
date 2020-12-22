package com.example.text1.view.customs;

import android.text.Editable;
import android.text.TextWatcher;

public abstract class MyTextWatcher implements TextWatcher {

    @Override
    public void beforeTextChanged(CharSequence pCharSequence, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        whenTextChanged(s,start,before,count);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    protected abstract void whenTextChanged(CharSequence s, int start, int before, int count);
}
