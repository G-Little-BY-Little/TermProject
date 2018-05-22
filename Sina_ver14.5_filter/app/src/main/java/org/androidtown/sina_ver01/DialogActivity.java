package org.androidtown.sina_ver01;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

public class DialogActivity extends AppCompatActivity {

    TextView rec_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.recommend_back));
        setContentView(R.layout.activity_dialog);

        rec_text = (TextView) findViewById(R.id.recommend_text);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReferenceFromUrl("gs://sinabro-1526195392107.appspot.com");

        StorageReference pathReference = storageReference.child("글귀.text");
    }
}