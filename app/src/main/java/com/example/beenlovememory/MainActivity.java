package com.example.beenlovememory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.beenlovememory.adapter.ViewPagerAdapter;
import com.example.beenlovememory.database.UserDAO;
import com.example.beenlovememory.model.AvatarBoy;
import com.example.beenlovememory.model.AvatarGirl;
import com.example.beenlovememory.model.User;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import me.itangqi.waveloadingview.WaveLoadingView;

public class MainActivity extends AppCompatActivity {
    CircleImageView avtB , avtG;
    UserDAO userDAO;
    TextView tvB , tvG;
    Bitmap resizedBitmap = null;
    ViewPager viewPager;
    ViewPagerAdapter adapter;


    private final int SELECT_PHOTO = 1;
    private final int SELECT_PHOTO2 = 2;


    @SuppressLint("SimpleDateFormat")
    static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");


    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userDAO = new UserDAO(this);
        initView();
        onClick();

        new AsyncTask< Void , Void , Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    if (getSizeUser() == 0) {
                        Intent intent = new Intent(MainActivity.this, StartActivity.class);
                        startActivity(intent);
                        Log.i("tag", "abc");
                    } else {
                        Timer T = new Timer();
                        T.scheduleAtFixedRate(new TimerTask() {
                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        setInfor();
                                    }
                                });
                            }
                        }, 1000, 1000);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private void setInfor() {
        new AsyncTask<Void, Void, List<User>>() {
            @Override
            protected List<User> doInBackground(Void... voids) {
                List<User> users;
                users = UserDAO.getAllUser();
                return users;
            }
            @Override
            protected void onPostExecute(List<User> users) {
                super.onPostExecute(users);
                if (users.size() > 0) {
                    tvB.setText(users.get(0).getTenBan());
                    tvG.setText(users.get(0).getTenNguoiAy());
                }
            }
        }.execute();
    }

    //  sử lý ảnh
    private void onClick() {
        avtB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,SELECT_PHOTO);
            }
        });

        avtG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,SELECT_PHOTO2);
            }
        });
    }

    private byte[] ImageViewChange(CircleImageView imageView) {
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {

            case SELECT_PHOTO:
                if(resultCode == RESULT_OK){
                    try {
                        final Uri imageUri = data.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        avtB.setImageBitmap(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } }

                break;
            case SELECT_PHOTO2:
                if(resultCode == RESULT_OK){
                    try {
                        final Uri imageUri = data.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        avtG.setImageBitmap(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } }
        }
    }

    private void initView() {
        avtB = findViewById(R.id.avatarB);
        avtG = findViewById(R.id.avatarG);
        tvB = findViewById(R.id.tvBoy);
        tvG = findViewById(R.id.tvGirl);
        viewPager = findViewById(R.id.viewPager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager(),this);
        viewPager.setAdapter(adapter);

    }

    private int getSizeUser() throws ParseException {
        final List<User> users = UserDAO.getAllUser();
        Log.d("SIZEE", users.size() + "");
        return users.size();
    }
}
