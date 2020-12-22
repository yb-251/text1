package com.example.pop.design;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import com.example.pop.R;
import com.example.pop.basepopup.BasePopupWindow;


public class SlideFromBottomPhotoPopup extends BasePopupWindow implements View.OnClickListener {

    private View popupView;


    public SlideFromBottomPhotoPopup(Activity context) {
        super(context);
        bindEvent();
    }

    @Override
    protected Animation initShowAnimation() {
        return getTranslateVerticalAnimation(1f, 0, 500);
    }

    @Override
    protected Animation initExitAnimation() {
        return getTranslateVerticalAnimation(0, 1f, 500);
    }

    @Override
    public View getClickToDismissView() {
        return null;
    }

    @Override
    public View onCreatePopupView() {
        popupView = LayoutInflater.from(getContext()).inflate(R.layout.popup_slide_from_bottom_photo, null);
        return popupView;
    }

    @Override
    public View initAnimaView() {
        return popupView.findViewById(R.id.popup_anima);
    }

    private void bindEvent() {
        if (popupView != null) {
            TextView camera = popupView.findViewById(R.id.take_photo);
            TextView capture = popupView.findViewById(R.id.capture);
            TextView cancel = popupView.findViewById(R.id.cancel);
            setViewClickListener(this, camera, capture, cancel);
        }
    }

    public static final int CAMERA = 1, CAPTURE = 2, CANCEL = 3;

    @Override
    public void onClick(View v) {
        if (sBottomPopClick == null) return;
        if (v.getId() == R.id.take_photo) sBottomPopClick.takePhotoClick(CAMERA);
        else if (v.getId() == R.id.capture) sBottomPopClick.takePhotoClick(CAPTURE);
        else if (v.getId() == R.id.cancel) sBottomPopClick.takePhotoClick(CANCEL);
    }

    public interface BottomPopClick {
        void takePhotoClick(int pos);
    }

    private BottomPopClick sBottomPopClick;

    public void setBottomClickListener(BottomPopClick clickListener) {
        sBottomPopClick = clickListener;
    }

}
