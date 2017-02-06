package com.example.idea.customviewcollection.act;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.SeekBar;

import com.example.idea.customviewcollection.R;
import com.example.idea.customviewcollection.utils.Blur;
import com.example.idea.customviewcollection.view.WoolglassImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by idea on 2017/1/4.
 */

public class WoolglassActivity extends BaseActivity {


    @BindView(R.id.wiv_woolglass)
    WoolglassImageView wiv_woolglass;

    @BindView(R.id.sb_seekbar)
    SeekBar sb_seekbar;

    private int mScreenWidth;

    private float alpha;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_woolglass);

        ButterKnife.bind(this);

        initBitmap();
        initView();
    }

    private void initBitmap() {
        mScreenWidth = getScreenWidth(this);
        final File blurredImage = new File(getFilesDir() + "zuomian.jpeg");
        if (!blurredImage.exists()) {

            // launch the progressbar in ActionBar
            setProgressBarIndeterminateVisibility(true);

            new Thread(new Runnable() {

                @Override
                public void run() {

                    // No image found => let's generate it!
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 2;
                    Bitmap image = BitmapFactory.decodeResource(getResources(), R.mipmap.zuomian, options);
                    Bitmap newImg = Blur.fastblur(WoolglassActivity.this, image, 12);
                    storeImage(newImg, blurredImage);
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            updateView(mScreenWidth);

                            // And finally stop the progressbar
                            setProgressBarIndeterminateVisibility(false);
                        }
                    });

                }
            }).start();

        } else {

            // The image has been found. Let's update the view
            updateView(mScreenWidth);

        }
    }

    private void initView() {


        sb_seekbar.setMax(100);
        sb_seekbar.setProgress(sb_seekbar.getMax());
        sb_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                alpha = (float) progress / 100;
                wiv_woolglass.setAlpha(alpha);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    private void updateView(final int screenWidth) {
        Bitmap bmpBlurred = BitmapFactory.decodeFile(getFilesDir() + "zuomian.jpeg");
        bmpBlurred = Bitmap.createScaledBitmap(bmpBlurred, screenWidth, (int) (bmpBlurred.getHeight()
                * ((float) screenWidth) / (float) bmpBlurred.getWidth()), false);

        wiv_woolglass.setImageBitmap(bmpBlurred);
    }

    public void storeImage(Bitmap image, File pictureFile) {
        if (pictureFile == null) {
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

    public static int getScreenWidth(Activity context) {

        Display display = context.getWindowManager().getDefaultDisplay();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            Point size = new Point();
            display.getSize(size);
            return size.x;
        }
        return display.getWidth();
    }


}
