package hyunwook.co.kr.thingscamera;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2015-01-12.
 */
public interface CaptureViewer {
    public void displayImage(Bitmap image);

    public void imageCleared();
}
