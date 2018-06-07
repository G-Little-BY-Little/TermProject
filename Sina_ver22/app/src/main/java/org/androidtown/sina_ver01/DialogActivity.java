package org.androidtown.sina_ver01;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.util.Random;

public class DialogActivity extends AppCompatActivity {
    FirebaseStorage storage = FirebaseStorage.getInstance();

    ImageView imageView;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.recommend_back));
        setContentView(R.layout.activity_dialog);


        i = (int)(Math.random()*10)+1;

        // Create a storage reference from our app
        StorageReference storageReference = storage.getReference().child("images/"+i+".png");

        imageView = (ImageView)findViewById(R.id.rec_image);

        Glide.with(this)
                .using(new FirebaseImageLoader())
                .load(storageReference)
                .into(imageView);

    }
}