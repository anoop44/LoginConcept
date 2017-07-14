package com.vpaliy.flip_concept;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;

import butterknife.BindView;
import butterknife.OnClick;

public abstract class AuthFragment extends Fragment {

    protected Callback callback;

    @BindView(R.id.controller)
    protected TextView controller;

    @BindView(R.id.parent)
    protected ViewGroup parent;

    @BindView(R.id.first)
    protected View first;

    @BindView(R.id.second)
    protected View second;

    @BindView(R.id.last)
    protected View last;

    @BindView(R.id.focus_hider)
    protected View logo;


    public void setCallback(@NonNull Callback callback) {
        this.callback = callback;
    }

    public abstract void fireAnimation();
    public abstract void cleaFocus();

    interface Callback {
        void remove(AuthFragment fragment);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KeyboardVisibilityEvent.setEventListener(getActivity(), isOpen -> {
            final float scale=isOpen?0.75f:1f;
            ViewCompat.animate(logo)
                    .scaleX(scale)
                    .scaleY(scale)
                    .setDuration(300)
                    .start();
            if(!isOpen) cleaFocus();
        });
    }

    @OnClick(R.id.controller)
    public void makeTransition(){
        if(callback!=null){
            callback.remove(this);
        }
    }
}
