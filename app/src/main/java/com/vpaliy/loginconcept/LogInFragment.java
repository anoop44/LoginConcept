package com.vpaliy.loginconcept;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.MotionEvent;
import android.view.View;
import android.annotation.TargetApi;
import android.support.annotation.Nullable;

import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;

public class LogInFragment extends AuthFragment{

    @BindViews(value = {R.id.email_input_edit,R.id.password_input_edit})
    protected List<TextInputEditText> views;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(view!=null){
            caption.setText(getString(R.string.log_in_label));
            view.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.color_log_in));
            views.forEach(editText->{
                if(editText.getId()==R.id.password_input_edit){
                    final TextInputLayout inputLayout=ButterKnife.findById(view,R.id.password_input);
                    Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
                    inputLayout.setTypeface(boldTypeface);
                    editText.addTextChangedListener(new TextWatcherAdapter(){
                        @Override
                        public void afterTextChanged(Editable editable) {
                            inputLayout.setPasswordVisibilityToggleEnabled(editable.length()>0);
                        }
                    });
                }
                editText.setOnFocusChangeListener((temp,hasFocus)->{
                    if(!hasFocus){
                        boolean isEnabled=editText.getText().length()>0;
                        editText.setSelected(isEnabled);
                    }
                });
            });
        }
    }

    @Override
    public int authLayout() {
        return R.layout.login_fragment;
    }

    @Override
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void fold() {
        lock=false;
        TransitionManager.beginDelayedTransition(parent);
        caption.setVerticalText(true);
        caption.setScaleY(0.5f);
        caption.setScaleX(0.5f);
        ConstraintLayout.LayoutParams params=getParams();
        params.leftToLeft=ConstraintLayout.LayoutParams.UNSET;
        params.verticalBias=0.5f;
        caption.setLayoutParams(params);
    }

    @Override
    public void clearFocus() {
        views.forEach(View::clearFocus);
    }

    @Override
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Transition unfoldTransition() {
        return TransitionInflater.from(getContext())
                .inflateTransition(R.transition.left_to_right);
    }
}
