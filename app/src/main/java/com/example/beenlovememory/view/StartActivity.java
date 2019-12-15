package com.example.beenlovememory.view;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.beenlovememory.R;
import com.example.beenlovememory.database.DatabaseManager;
import com.example.beenlovememory.database.UserDAO;
import com.example.beenlovememory.model.StartView;
import com.example.beenlovememory.model.User;
import com.example.beenlovememory.presenter.StartPresenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StartActivity extends AppCompatActivity
        implements StartView, View.OnClickListener {

    DatabaseManager db;
    UserDAO userDAO;

    ImageView imgDay;
    Button btn_khoitao;
    EditText edtBoy, edtGirl, edtDay;



    private int mY, mM, mD;
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    StartPresenter startPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        startPresenter = new StartPresenter(this);
        initView();
        initAction();
    }

    private void initAction() {
        btn_khoitao.setOnClickListener(this);
        imgDay.setOnClickListener(this);
    }


    public void setUp(View view) {
        try {
            setInformation();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    private void shopdatePicke() {
        final Calendar c = Calendar.getInstance();
        mD = c.get(Calendar.DAY_OF_MONTH);
        mM = c.get(Calendar.MONTH);
        mY = c.get(Calendar.YEAR);
        DatePickerDialog dpd = new DatePickerDialog(StartActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        edtDay.setText(year + "-"
                                + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, mY, mM, mD);
        dpd.show();
    }

    private void initView() {
        db = new DatabaseManager(this);
        userDAO = new UserDAO(this);
        imgDay = findViewById(R.id.imgDay);
        edtBoy = findViewById(R.id.edtBoy);
        edtDay = findViewById(R.id.edtDay);
        edtGirl = findViewById(R.id.edtGirl);
        btn_khoitao = findViewById(R.id.btnSubmit);
    }


    private void setInformation() throws ParseException {
        final String nameB = edtBoy.getText().toString();
        final String nameG = edtGirl.getText().toString();
        final String dayS = edtDay.getText().toString();
        startPresenter.check(nameB, nameG , dayS);
        final Date dateStart = sdf.parse(edtDay.getText().toString());
        User user = new User(1, nameB, nameG, dateStart);
        if (userDAO.inserUser(user) > 0) {
            startPresenter.khoitao();
        } else {
            Toast.makeText(this, "thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSubmit) {
            try {
                setInformation();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (v.getId() == R.id.imgDay) {
            shopdatePicke();
        }
    }


    @Override
    public void intentStart() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void successfulStart() {
        Toast.makeText(this, "khởi tạo thành công ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setErrorBoyname() {
        edtBoy.setError("cần nhập tên bạn nam");
    }

    @Override
    public void setErrorGirlname() {
        edtGirl.setError("cần nhập tên bạn nữ");
    }

    @Override
    public void playMusic() {
    }

    @Override
    public void checkDay() {
        edtDay.setError("còn chọn ngày ");
    }
}
