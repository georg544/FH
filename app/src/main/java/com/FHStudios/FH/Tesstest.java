package com.FHStudios.FH;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;



import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;



public class Tesstest extends AppCompatActivity implements View.OnClickListener {

    private ProgressDialog mProgressDialog;
    private TesseractOCR mTessOCR;
    private Context context;
    protected String mCurrentPhotoPath;
    private Uri photoURI1;
    private Uri oldPhotoURI;

    private static final String errorFileCreate = "Error file create!";
    private static final String errorConvert = "Error convert!";
    private static final int REQUEST_IMAGE1_CAPTURE = 1;
    private static final int REQUEST_IMAGE_STORAGE = 3;


    ImageView firstImage;
    TextView ocrText;
    Button scanbtn;



    int PERMISSION_ALL = 1;
    boolean flagPermissions = false;

    String[] PERMISSIONS = {
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA
    };
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tesstest);
        context = Tesstest.this;
         firstImage =findViewById(R.id.ocr_image);
         ocrText=findViewById(R.id.ocr_text);
         scanbtn=findViewById(R.id.scan_button);


     //   ButterKnife.bind(this);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        if (!flagPermissions) {
            checkPermissions();
        }
        String language = "heb";
        mTessOCR = new TesseractOCR(this, language);
        scanbtn.setOnClickListener(this);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_IMAGE1_CAPTURE: {
                if (resultCode == RESULT_OK) {
                    Bitmap bmp = null;
                    try {
                        InputStream is = context.getContentResolver().openInputStream(photoURI1);
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        bmp = BitmapFactory.decodeStream(is, null, options);

                    } catch (Exception ex) {
                        Log.i(getClass().getSimpleName(), ex.getMessage());
                        Toast.makeText(context, errorConvert, Toast.LENGTH_SHORT).show();
                    }

                    firstImage.setImageBitmap(bmp);
                    doOCR(bmp);

                    OutputStream os;
                    try {
                        os = new FileOutputStream(photoURI1.getPath());
                        if (bmp != null) {
                            bmp.compress(Bitmap.CompressFormat.JPEG, 100, os);
                        }
                        os.flush();
                        os.close();
                    } catch (Exception ex) {
                        Log.e(getClass().getSimpleName(), ex.getMessage());
                        Toast.makeText(context, errorFileCreate, Toast.LENGTH_SHORT).show();
                    }

                } else {
                    {
                        photoURI1 = oldPhotoURI;
                        firstImage.setImageURI(photoURI1);
                    }
                }
            }
            case REQUEST_IMAGE_STORAGE:{
                if(resultCode == RESULT_OK){
                    if(data!=null){
                        Uri selectedImageUri=data.getData();
                        if (selectedImageUri != null) {
                            try {
                                InputStream inputStream=getContentResolver().openInputStream(selectedImageUri);
                                bitmap=BitmapFactory.decodeStream(inputStream);
                            } catch (Exception e) {
                                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                            firstImage.setImageBitmap(bitmap);
                            doOCR(bitmap);
                        }
                    }
                }
            }
        }
    }

    public void onClick(View view)
    { switch (view.getId()){
        case R.id.scan_button:

        if (!flagPermissions) {
            checkPermissions();
            return;
        }
//        Intent intent= new Intent(Intent.ACTION_PICK);
//        if(intent.resolveActivity(getPackageManager())!=null){startActivityForResult(intent,REQUEST_IMAGE_STORAGE);}
      //  prepare intent

            openCameraOrGallery();
//         Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//            } catch (IOException ex) {
//                Toast.makeText(context, errorFileCreate, Toast.LENGTH_SHORT).show();
//                Log.i("File error", ex.toString());
//            }
//            // Continue only if the File was successfully created
//            if (photoFile != null) {
//                oldPhotoURI = photoURI1;
//                photoURI1 = Uri.fromFile(photoFile);
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI1);
//                startActivityForResult(takePictureIntent, REQUEST_IMAGE1_CAPTURE);
//            }
//        }
//        case R.id.pick_button:  if (!flagPermissions) {
//            checkPermissions();
//            return;
//        }
//            Intent intent= new Intent(Intent.ACTION_PICK);
//           if(intent.resolveActivity(getPackageManager())!=null){startActivityForResult(intent,REQUEST_IMAGE_STORAGE);}

    }
    }

    public File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("MMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


    void checkPermissions() {
        if (!hasPermissions(context, PERMISSIONS)) {
            requestPermissions(PERMISSIONS,
                    PERMISSION_ALL);
            flagPermissions = false;
        }
        flagPermissions = true;

    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void doOCR(final Bitmap bitmap) {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog.show(this, "Processing",
                    "Doing OCR...", true);
        } else {
            mProgressDialog.show();
        }
        new Thread(new Runnable() {
            public void run() {
                final String srcText = mTessOCR.getOCRResult(bitmap);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (srcText != null && !srcText.equals("")) {
                            ocrText.setText(srcText);
                        }
                        mProgressDialog.dismiss();
                    }
                });
            }
        }).start();
    }
    private void openCameraOrGallery() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

        Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Picture");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{cameraIntent});

        startActivityForResult(chooserIntent, REQUEST_IMAGE1_CAPTURE);
    }

}
