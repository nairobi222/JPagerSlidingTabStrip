package april.yun.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.jonas.librarys.R;

import april.yun.other.IPrompt;

/**
 * @another 江祖赟
 * @date 2017/9/12 0012.
 */
public class PromptImageView extends androidx.appcompat.widget.AppCompatImageView implements IPrompt {

    protected SuperPrompt mPromptHelper;

    public PromptImageView(Context context){
        this(context, null);
    }

    public PromptImageView(Context context, @Nullable AttributeSet attrs){
        this(context, attrs, 0);
    }

    public PromptImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        mPromptHelper = new SuperPrompt(this);
        mPromptHelper.mIsAniShow = getContext().getResources().getBoolean(R.bool.jtabstrip_anishow);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh){
        super.onSizeChanged(w, h, oldw, oldh);
        mPromptHelper.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(isClickable()) {
            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    setColorFilter(mPromptHelper.mDimColorFilter);
                    break;
                case MotionEvent.ACTION_CANCEL:
                    clearColorFilter();
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    clearColorFilter();
                    invalidate();
                    break;
            }
        }
        return super.onTouchEvent(event);
    }


    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        mPromptHelper.onDraw(canvas);
    }

    public SuperPrompt getPromptHelper(){
        return mPromptHelper;
    }

    public PromptImageView setPromptMsg(String promptMsg){
        mPromptHelper.setPromptMsg(promptMsg);
        return this;
    }

    public PromptImageView showNotify(){
        mPromptHelper.setPromptMsg(SuperPrompt.NOTIFY);
        return this;
    }


    public PromptImageView forcePromptCircle(){
        mPromptHelper.forcePromptCircle(true);
        return this;
    }


    /**
     * @param offset
     *         px
     * @return
     */
    public PromptImageView setPromptOffset(int offset){
        mPromptHelper.setPromptOutOffset(offset);
        return this;
    }

    public PromptImageView forceCenterVertical(){
        mPromptHelper.centerVertical(true);
        return this;
    }


    public PromptImageView configPrompt(int promptBgColor, int promptColor){
        mPromptHelper.setColor_bg(promptBgColor).setColor_num(promptBgColor);
        return this;
    }

    @Override
    public IPrompt asOnlyNum(){
        mPromptHelper.asNewMsgNums();
        return this;
    }

    @Override
    protected void onDetachedFromWindow(){
        super.onDetachedFromWindow();
        mPromptHelper.cancelAni();
    }

    @Override
    public void setImageDrawable(@Nullable Drawable drawable){
//        LayerDrawable
        super.setImageDrawable(drawable);
    }
}
