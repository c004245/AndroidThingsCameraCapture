package hyunwook.co.kr.thingscamera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by hyunwook on 2018-08-02.
 */

public class CaptureView extends View implements CaptureViewer {


    static final String TAG = CaptureView.class.getSimpleName();

    private Bitmap mBitmap = null;
    private Bitmap mCleanImage = null;

    private Rect src = new Rect(160, 106, 482, 342);
    private Rect dst;

    Paint paint = new Paint();

    private int viewWidth, viewHeight = 0;

    public CaptureView(Context context) {
        super(context);
    }

    public CaptureView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CaptureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        viewWidth = getLayoutParams().width;
        viewHeight = getLayoutParams().height;

        Log.d(TAG, "viewWidth -->" + viewWidth +"--"+ viewHeight);
        mCleanImage = Bitmap.createBitmap(390, 390, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(mCleanImage);
        canvas.drawColor(getResources().getColor(R.color.purple));

        dst = new Rect(0, 0, viewWidth, viewHeight);

    }

    //   2015-12-01 added by CHO (No image 텍스트 삽입)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.GRAY);
        paint.setTextSize(30);
        paint.setTextAlign(Paint.Align.CENTER);
        if (mCleanImage != null) {
            canvas.drawBitmap(mCleanImage, src, dst, null);
            canvas.drawText("No Image", canvas.getWidth() / 2, canvas.getHeight() / 2, paint);
            mCleanImage = null;
        } else if (mBitmap != null) {
            canvas.drawBitmap(mBitmap, src, dst, null);
            mBitmap = null;
        }
    }

    // -- add end
    @Override
    public void displayImage(Bitmap image) {
        Log.d(TAG, "displayImage -->" + image);
        mBitmap = Bitmap.createBitmap(image);
        postInvalidate();/** 화면 갱신 */ //보라색에서 -> 캡처 사진으로 (No image)가 없어져야함
    }
    //2015-12-02 added by CHO (캡처 후 사진 클리어시에 'No image' 텍스트 삽입)
    @Override
    public void imageCleared() {
        mBitmap = mCleanImage;
        viewWidth = getLayoutParams().width;
        viewHeight = getLayoutParams().height;
        mCleanImage = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(mCleanImage);
        canvas.drawColor(getResources().getColor(R.color.purple));
        postInvalidate();
        /** 화면 갱신 */ //캡처된 사진 에서 -> 보라색으로 (NO image)가 보여야함 _
    }

}


