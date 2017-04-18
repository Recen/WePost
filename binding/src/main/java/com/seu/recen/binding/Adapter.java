package com.seu.recen.binding;

import android.databinding.BindingAdapter;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * Created by Recen on 2017/4/16.
 */

public class Adapter {

    @BindingAdapter("android:onClick")
    public static void setOnClick(View view, final OnClickCallback callback) {
        view.setOnClickListener(new View.OnClickListener() {
            private FastClickChecker checker = new FastClickChecker();

            @Override
            public void onClick(View view) {
                if (!checker.isFastClick() && callback != null) {
                    callback.onClick();
                }
            }
        });
    }

    @BindingAdapter("android:onLongClick")
    public static void setOnLongClick(View view, final OnLongClickCallback callback) {
        view.setOnLongClickListener(new View.OnLongClickListener() {
            private FastClickChecker checker = new FastClickChecker();

            @Override
            public boolean onLongClick(View view) {
                if (!checker.isFastClick() && callback != null) {
                    callback.onLongClick();
                }
                return false;
            }
        });
    }

    @BindingAdapter("frescoUrl")
    public static void setFrescoUrl(SimpleDraweeView draweeView, String url) {
        if (!TextUtils.isEmpty(url)) {
            if (!url.contains("://")) {
                url = "file://" + url;
            }
            Uri uri = Uri.parse(url);

            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri).
                    setProgressiveRenderingEnabled(true).build();
            DraweeController controller = Fresco.newDraweeControllerBuilder().setImageRequest(request).build();
            draweeView.setController(controller);
        }
    }
}
