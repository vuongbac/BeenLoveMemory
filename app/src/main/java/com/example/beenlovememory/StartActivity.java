package com.example.beenlovememory;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.beenlovememory.database.DatabaseManager;
import com.example.beenlovememory.database.UserDAO;
import com.example.beenlovememory.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StartActivity extends AppCompatActivity {
    DatabaseManager db;
    UserDAO userDAO;
    ImageView imgDay;
    EditText edtBoy , edtGirl , edtDay;
    private  int mY,mM,mD;
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        initView();
        onClick();
    }


    public void setUp(View view) {
        try {
            setInformation();
        }catch (ParseException e){
            e.printStackTrace();
        }
    }

    private void onClick() {
        imgDay.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        final Calendar c = Calendar.getInstance();
        mD = c.get(Calendar.DAY_OF_MONTH);
        mM = c.get(Calendar.MONTH);
        mY = c.get(Calendar.YEAR);
        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(StartActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        edtDay.setText(dayOfMonth + "-"
                                + (monthOfYear + 1) + "-" + year);
                    }
                }, mD, mM, mY);
        dpd.show();
    }
});
    }

    private void initView() {
        db = new DatabaseManager(this);
        userDAO = new UserDAO(this);
        imgDay = findViewById(R.id.imgDay);
        edtBoy = findViewById(R.id.edtBoy);
        edtDay = findViewById(R.id.edtDay);
        edtGirl = findViewById(R.id.edtGirl);
    }


    private void setInformation() throws ParseException {
        final String nameB = edtBoy.getText().toString();
        final String nameG = edtGirl.getText().toString();
        final Date dateStart = sdf.parse(edtDay.getText().toString());
        User user = new User(1,nameB , nameG , dateStart);
        if (userDAO.inserUser(user) > 0){
            Toast.makeText(this, "Thành công", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(StartActivity.this , MainActivity.class));
        }else {
            Toast.makeText(this, "thất bại", Toast.LENGTH_SHORT).show();
        }

    }


}
