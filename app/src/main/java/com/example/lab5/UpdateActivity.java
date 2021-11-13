package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.lab5.Dao.MyCreditDAO;
import com.example.lab5.Model.MyCredit;

public class UpdateActivity extends AppCompatActivity {

    private MyCreditDAO myCreditDAO;
    private boolean needRefresh=false;

    private EditText txtTitle;
    private EditText txtCash;
    private RadioButton rdUp;
    private RadioButton rdDown;
    private EditText txtDescription;

    private Button btnCancel;
    private Button btnUpdate;

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
        setContentView(R.layout.activity_update);

        txtTitle=(EditText) findViewById(R.id.editTextTitle2);
        rdUp=(RadioButton) findViewById(R.id.radioBtnUp2);
        rdDown=(RadioButton) findViewById(R.id.radioBtnDown2);
        txtCash=(EditText) findViewById(R.id.editTextCash2);
        txtDescription=(EditText) findViewById(R.id.editTextDescip2);

        btnUpdate=(Button) findViewById(R.id.btnUpdate);
        btnCancel=(Button) findViewById(R.id.btnCancel2);

        myCreditDAO=new MyCreditDAO(this);

        Intent intent=this.getIntent();
        MyCredit myCredit= (MyCredit) intent.getSerializableExtra("credit");

        txtTitle.setText(myCredit.getTitle());
        txtCash.setText(myCredit.getCash()+"");
        txtDescription.setText(myCredit.getDescription());
        if(myCredit.getTypeTransaction()==1){
            rdUp.setChecked(true);
        }else{
            rdDown.setChecked(true);
        }


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title=txtTitle.getText().toString();
                String cash=txtCash.getText().toString();
                String description=txtDescription.getText().toString();
                int typeTrans=-1;
                if(rdUp.isSelected()==true){
                    typeTrans=1;
                }else{
                    typeTrans=0;
                }

                MyCredit myCredit1=new MyCredit(myCredit.getId(), title, "", Double.parseDouble(cash),  typeTrans,  description);
                myCreditDAO.update(myCredit1);
                needRefresh=true;

                finish();
            }
        });
    }
}