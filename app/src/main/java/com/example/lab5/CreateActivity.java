package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.lab5.Dao.MyCreditDAO;
import com.example.lab5.Model.MyCredit;

public class CreateActivity extends AppCompatActivity {

    private MyCreditDAO myCreditDAO;
    private boolean needRefresh=false;

    private EditText txtTitle;
    private EditText txtCash;
    private RadioButton rdUp;
    private RadioButton rdDown;
    private EditText txtDescription;

    private Button btnCancel;
    private Button btnAdd;

    @Override
    public void finish() {
        Intent data=new Intent();
        data.putExtra("needRefresh",needRefresh);
        this.setResult(Activity.RESULT_OK,data);
        super.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        txtTitle=(EditText) findViewById(R.id.editTextTitle1);
        rdUp=(RadioButton) findViewById(R.id.radioBtnUp1);
        rdDown=(RadioButton) findViewById(R.id.radioBtnDown1);
        txtCash=(EditText) findViewById(R.id.editTextCash1);
        txtDescription=(EditText) findViewById(R.id.editTextDercip1);



        btnAdd=(Button) findViewById(R.id.btnAdd);
        btnCancel=(Button) findViewById(R.id.btnCancel1);

        myCreditDAO=new MyCreditDAO(this);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title=txtTitle.getText().toString();
                String cash=txtCash.getText().toString();
                String description=txtDescription.getText().toString();
                int typeTrans=-1;
                if(rdUp.isChecked()) {
                    typeTrans=1;
                } else {
                    typeTrans=0;
                }
                System.out.println("check " + typeTrans);
                MyCredit myCredit =new MyCredit("", title, "", Double.parseDouble(cash),  typeTrans,  description);
                myCreditDAO.create(myCredit);
                needRefresh=true;
                finish();
            }
        });
    }

}