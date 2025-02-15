package april.yun.tabstyle;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;

import april.yun.ISlidingTabStrip;

import static android.graphics.PorterDuff.Mode.SRC_IN;

/**
 * @author yun.
 * @date 2017/4/21
 * @des [一句话描述]
 * @since [https://github.com/ZuYun]
 * <p><a href="https://github.com/ZuYun">github</a>
 */
public class RoundTabStyle extends JTabStyle {

  private float mOutRadio;
  private Path mClipath;

  private static final String TAG = RoundTabStyle.class.getSimpleName();
  private PorterDuffXfermode mXfermode = new PorterDuffXfermode(SRC_IN);


  public RoundTabStyle(ISlidingTabStrip slidingTabStrip) {
    super(slidingTabStrip);
  }


  @Override
  public void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    //        mH = h;
  }

  @Override
  public void afterLayout() {
    if (mLastTab != null) {
      mOutRadio = mTabStyleDelegate.getCornerRadio() == 0 ? mH / 2 : mTabStyleDelegate.getCornerRadio();
      getClipPath(padingVerticalOffect, mLastTab.getRight());
    }
  }

  private void getClipPath(float pading, float width) {
    RectF clip = new RectF(pading, pading, width - pading, mH - pading);
    mClipath = new Path();
    mClipath.addRoundRect(clip, mOutRadio, mOutRadio, Path.Direction.CCW);
  }


  @Override
  public void onDraw(Canvas canvas, ViewGroup tabsContainer, float currentPositionOffset, int lastCheckedPosition) {

    updateTabTextScrollColor();

    if (mTabStyleDelegate.getIndicatorColor() != Color.TRANSPARENT && mClipath != null) {

//                        int save = canvas.save();
      int save = canvas.saveLayer(0, 0, mW, mH, null, Canvas.ALL_SAVE_FLAG);
      canvas.clipPath(mClipath);
      // draw indicator line
      mIndicatorPaint.setColor(mTabStyleDelegate.getIndicatorColor());

      calcuteIndicatorLinePosition(tabsContainer, currentPositionOffset, lastCheckedPosition);
      //draw indicator
      canvas.drawRect(mLinePosition.x, padingVerticalOffect, mLinePosition.y, mH - padingVerticalOffect, mIndicatorPaint);
      mIndicatorPaint.setXfermode(mXfermode);
      canvas.drawPath(mClipath, mIndicatorPaint);
      mIndicatorPaint.setXfermode(null);
      canvas.restoreToCount(save);
    }

    if (mTabStyleDelegate.getUnderlineColor() != Color.TRANSPARENT) {
      // draw underline
      mIndicatorPaint.setColor(mTabStyleDelegate.getUnderlineColor());
      canvas.drawRect(padingVerticalOffect, mH - padingVerticalOffect - mTabStyleDelegate.getUnderlineHeight(), tabsContainer.getWidth(), mH,
          mIndicatorPaint);
    }

    if (mTabStyleDelegate.getFrameColor() != Color.TRANSPARENT) {
      //画边框
      mDividerPaint.setColor(mTabStyleDelegate.getFrameColor());
      mDividerPaint.setStrokeWidth(mTabStyleDelegate.getFrameWidth());
      drawRoundRect(canvas, padingVerticalOffect + mTabStyleDelegate.getFrameWidth() / 2f + 1,
          padingVerticalOffect + mTabStyleDelegate.getFrameWidth() / 2f + 1,
          mLastTab.getRight() - mTabStyleDelegate.getFrameWidth() / 2f - 1 - padingVerticalOffect,
          this.mH - padingVerticalOffect - mTabStyleDelegate.getFrameWidth() / 2f - 1, mOutRadio, mOutRadio, mDividerPaint);
    }

    if (mTabStyleDelegate.getDividerColor() != Color.TRANSPARENT) {
      // draw divider
      mDividerPaint.setColor(mTabStyleDelegate.getDividerColor());
      for (int i = 0; i < tabsContainer.getChildCount() - 1; i++) {
        View tab = tabsContainer.getChildAt(i);
        canvas.drawLine(tab.getRight(), padingVerticalOffect + mTabStyleDelegate.getDividerPadding(), tab.getRight(),
            mH - mTabStyleDelegate.getDividerPadding() - padingVerticalOffect, mDividerPaint);
      }
    }
  }
}
