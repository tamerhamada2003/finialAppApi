package com.example.applicatioprojectxd.Activity;

import static java.security.AccessController.getContext;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Messenger;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.applicatioprojectxd.Constant.Constant;
import com.example.applicatioprojectxd.databinding.ActivityProblemBinding;

import java.io.IOException;

public class ProblemActivity extends AppCompatActivity {

    ActivityProblemBinding binding;

    int positionAllWork; //خاص بلايدي الخاص بلمهن الصناعيه

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    private static final int REQUEST_PERMISSIONS = 100;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityProblemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        preferences = getSharedPreferences(Constant.FILE, MODE_PRIVATE);
        editor = preferences.edit();


        getIntentPositionAllWork();



        binding.LinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                    if ((ActivityCompat.shouldShowRequestPermissionRationale(ProblemActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(ProblemActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE))) {

                    } else {
                        ActivityCompat.requestPermissions(ProblemActivity.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                                REQUEST_PERMISSIONS);
                    }
                } else {
                    showFileChooser();
                }


            }
        });

        binding.btnNextProblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!binding.etTextProblem.getText().toString().isEmpty()) {
                    sendDataToLocation();


                } else {
                    Toast.makeText(ProblemActivity.this, "الرجاء ادخال وصف المشكله", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    private void showFileChooser() {

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");

        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            Uri picUri = data.getData();
            String filePath = getPath(picUri);
            if (filePath != null) {

                editor.putString(Constant.IMAGE_PROBLEM, picUri.toString()).commit();
                binding.imageProblem.setImageURI(picUri);
                binding.cardViewImage.setVisibility(View.VISIBLE);
                binding.LinearLayout.setVisibility(View.GONE);


            }
        }

    }


    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        @SuppressLint("Range") String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }


    //  الخاص بلمهن الصناعيه Id  داله لجلب الايدي الخاص بل
    private void getIntentPositionAllWork() {
        Intent intent = getIntent();
        if (intent != null) {
            positionAllWork = intent.getIntExtra(Constant.ID_ALL_WORK, -1);
        }


    }

    private void sendDataToLocation() {
        Intent intent = new Intent(getBaseContext(), LocationActivity.class);

        intent.putExtra(Constant.TEXT_PROBLEM, binding.etTextProblem.getText().toString());
        intent.putExtra(Constant.SEND_ID_ALL_Work, positionAllWork);


        startActivity(intent);

        finish();
    }


}