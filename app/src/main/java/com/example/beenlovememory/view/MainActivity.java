package com.example.beenlovememory.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.beenlovememory.R;
import com.example.beenlovememory.adapter.ViewPagerAdapter;
import com.example.beenlovememory.database.AvatarBoyDAO;
import com.example.beenlovememory.database.AvatarGirlDAO;
import com.example.beenlovememory.database.UserDAO;
import com.example.beenlovememory.model.AvatarBoy;
import com.example.beenlovememory.model.AvatarGirl;
import com.example.beenlovememory.model.HomeView;
import com.example.beenlovememory.model.Song;
import com.example.beenlovememory.model.User;
import com.example.beenlovememory.presenter.HomePresenter;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements HomeView, View.OnClickListener {
    CircleImageView avtB, avtG;

    List<User> users;
    UserDAO userDAO;

    List<AvatarBoy> avatarBoys;
    AvatarBoyDAO avtBoyDAO;

    List<AvatarGirl> avatarGirls;
    AvatarGirlDAO avtGirlDAO;

    TextView tvB, tvG;

    FloatingActionMenu fam;
    FloatingActionButton fab;
    ViewPager viewPager;
    ViewPagerAdapter adapter;

    MediaPlayer media;

    ArrayList<Song> songs;
    int p = 0;


    private final int SELECT_PHOTO = 1;
    private final int SELECT_PHOTO2 = 2;

    HomePresenter homePresenter;


    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userDAO = new UserDAO(this);
        avtBoyDAO = new AvatarBoyDAO(this);
        avtGirlDAO = new AvatarGirlDAO(this);
        homePresenter = new HomePresenter(this);

        initView();
        songs = new ArrayList<>();
        songs.add(new Song(R.raw.ido));
        media = MediaPlayer.create(this, songs.get(p).getFile());
        media.start();
        homePresenter.setImageGirl();
        homePresenter.setImageBoy();
        initAction();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    if (getSizeUser() == 0) {
                        Intent intent = new Intent(MainActivity.this, StartActivity.class);
                        media.stop();
                        startActivity(intent);
                        Log.i("tag", "abc");
                    } else {
                        homePresenter.setInfo();
                        homePresenter.setImageBoy();
                        homePresenter.setImageGirl();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

    private void initAction() {
        avtB.setOnClickListener(this);
        avtG.setOnClickListener(this);
        fab.setOnClickListener(this);
    }


    private byte[] ImageViewChange(ImageView imageView) {
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        avtB.setImageBitmap(selectedImage);
                        AvatarBoy avatarBoy = new AvatarBoy(ImageViewChange(avtB));
                        avtBoyDAO.insertAvatarBoy(avatarBoy);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;


            case SELECT_PHOTO2:
                if (resultCode == RESULT_OK) {
                    try {
                        final Uri imageUri2 = imageReturnedIntent.getData();
                        final InputStream imageStream2 = getContentResolver().openInputStream(imageUri2);
                        final Bitmap selectedImage2 = BitmapFactory.decodeStream(imageStream2);
                        avtG.setImageBitmap(selectedImage2);
                        AvatarGirl avatarGirl = new AvatarGirl(ImageViewChange(avtG));
                        avtGirlDAO.insertAvatarGIRL(avatarGirl);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    private void initView() {
        fam = findViewById(R.id.fab);
        fab = findViewById(R.id.floatingMute);
        avtB = findViewById(R.id.avatarB);
        avtG = findViewById(R.id.avatarG);
        tvB = findViewById(R.id.tvBoy);
        tvG = findViewById(R.id.tvGirl);
        viewPager = findViewById(R.id.viewPager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);
    }

    private int getSizeUser() throws ParseException {
        final List<User> users = UserDAO.getAllUser();
        Log.d("SIZEE", users.size() + "");
        return users.size();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.avatarB:
                homePresenter.avatarBoy();
                break;

            case R.id.avatarG:
                homePresenter.avatarGirl();
                break;

            case R.id.floatingMute:
                homePresenter.media(media);
                break;
            default:
        }
    }


    @Override
    public void AvatarGirl() {
        Intent intent1 = new Intent(Intent.ACTION_PICK);
        intent1.setType("image/*");
        startActivityForResult(intent1, SELECT_PHOTO2);
    }

    @Override
    public void AvatarBoy() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, SELECT_PHOTO);
    }

    @Override
    public void startMusic() {
        fab.setImageResource(R.drawable.ic_volume_off);
        media.start();
    }

    @Override
    public void pauseMusic() {
        fab.setImageResource(R.drawable.ic_volume_on);
        media.pause();
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void setInfo() {
        new AsyncTask<Void, Void, List<User>>() {
            @Override
            protected List<User> doInBackground(Void... voids) {
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


    @SuppressLint("StaticFieldLeak")
    @Override
    public void setAvatarGirl() {
        new AsyncTask<Void, Void, List<AvatarGirl>>() {
            @Override
            protected List<AvatarGirl> doInBackground(Void... voids) {
                avatarGirls = avtGirlDAO.getAllAvatarGirl();
                return avatarGirls;
            }
            @Override
            protected void onPostExecute(List<AvatarGirl> avtGirls) {
                super.onPostExecute(avtGirls);
                if (avtGirls.size() > 0) {
                    Glide.with(MainActivity.this).load(avtGirls.get(0).getAvt_girl()).into(avtG);
                }
            }
        }.execute();
    }


    @SuppressLint("StaticFieldLeak")
    @Override
    public void setAvatarBoy() {
        new AsyncTask<Void, Void, List<AvatarBoy>>() {
            @Override
            protected List<AvatarBoy> doInBackground(Void... voids) {
                avatarBoys = avtBoyDAO.getAllAvatarBoy();
                return avatarBoys;
            }
            @Override
            protected void onPostExecute(List<AvatarBoy> avtBoys) {
                super.onPostExecute(avtBoys);
                if (avtBoys.size() > 0) {
                    Glide.with(MainActivity.this).load(avtBoys.get(0).getAvtBoy()).into(avtB);
                }
            }
        }.execute();
    }
}
