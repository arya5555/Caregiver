package com.care.technovation.care;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

public class NewRecipientActivity extends AppCompatActivity {
    private EditText name, birthdate;
    private FloatingActionButton fabSubmit;
    private DatabaseReference mDatabase;
    private Button chooseImageButton;
    private ImageView profileImageView;
    private Uri filePath;
    private static final int SELECT_PHOTO = 0;
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipient);

        name = (EditText) findViewById(R.id.email);
        birthdate = (EditText) findViewById(R.id.password);
        fabSubmit = findViewById(R.id.FabSubmit);
        chooseImageButton = findViewById(R.id.ChooseImageButton);
        profileImageView = findViewById(R.id.ProfileImage);

        chooseImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , SELECT_PHOTO);
            }
        });

        fabSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case SELECT_PHOTO:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    //profileImageView.setImageURI(selectedImage);
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                        profileImageView.setImageBitmap(bitmap);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }

                break;
        }
    }
}
