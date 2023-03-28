package com.example.ecg_prediction;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ecg_prediction.ml.UpdatedModel;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;

public class MainActivity extends AppCompatActivity {

    private ImageView imgView;
    private Button select, predict;
    private TextView tv;
    private Bitmap img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgView = (ImageView) findViewById(R.id.imageView);
        tv = (TextView) findViewById(R.id.textView);
        select = (Button) findViewById(R.id.button);
        predict = (Button) findViewById(R.id.button2);

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 100);
            }
        });

        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img = Bitmap.createScaledBitmap(img, 224, 224,true);
                //img = androidGrayScale(img);
                try {
                    UpdatedModel model = UpdatedModel.newInstance(getApplicationContext());

                    // Creates inputs for reference.
                    TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 1}, DataType.FLOAT32);
                    TensorImage tensorImage = new TensorImage(DataType.FLOAT32);
                    tensorImage.load(img);
                    Log.d("tensorImage", String.valueOf(tensorImage.getBuffer()));
                    Log.d("img", String.valueOf(img.getAllocationByteCount()));
                    // ByteBuffer byteBuffer = tensorImage.getBuffer();
                    Log.d("shape", tensorImage.getBuffer().toString());
                    Log.d("shape", inputFeature0.getBuffer().toString());
                    // inputFeature0.loadBuffer(tensorImage.getBuffer());


                    // Runs model inference and gets result.
                    UpdatedModel.Outputs outputs = model.process(inputFeature0);
                    TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                    // Releases model resources if no longer used.
                    model.close();
                    int maxAt = 0;

                    for (int i = 0; i < outputFeature0.length; i++) {
                        maxAt = outputFeature0[i] > outputFeature0[maxAt] ? i : maxAt;
                    }
                    String[] outputNames = new String[]{"ABNORMAL!", "ARRHYTHMIA!", "COVID-19!", "HISTORY MI!", "MI PATIENT!", "NORMAL!"};
                    tv.setText(outputNames[maxAt]);
                    // tv.setText((CharSequence) outputFeature0);
                } catch (IOException e) {
                    // TODO Handle the exception
                    e.printStackTrace();
                }

            }
        });

    }

    private Bitmap androidGrayScale(Bitmap img) {
        int width, height;
        height = img.getHeight();
        width = img.getWidth();
        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, img.getConfig());
        Canvas canvas = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(colorMatrix);
        paint.setColorFilter(colorMatrixFilter);
        canvas.drawBitmap(img, 0, 0, paint);
        return bmpGrayscale;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            imgView.setImageURI(data.getData());

            Uri uri = data.getData();
            try {
                img = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}