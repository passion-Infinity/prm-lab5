package com.example.lab5;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lab5.Dao.MyCreditDAO;
import com.example.lab5.Model.CustomListAdapter;
import com.example.lab5.Model.MyCredit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final MyCreditDAO myCreditDAO =new MyCreditDAO(this);
    private static final int REQUEST_CODE=1000;

    private ListView listView;
    private TextView txtBalance;
    private Button btnCreate;
    private Button btnExit;

    private final List<MyCredit> listCredit=new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            boolean needRefresh = data.getBooleanExtra("needRefresh", true);
            if (needRefresh) {
                this.listCredit.clear();
                List<MyCredit> list = myCreditDAO.readAll();
                this.listCredit.addAll(list);

                listView.setAdapter(new CustomListAdapter(MainActivity.this, listCredit));

                double balance=0;
                for (MyCredit cr: listCredit) {
                    if(cr.getTypeTransaction()==1){
                        balance=balance+cr.getCash();
                    }else{
                        balance=balance-cr.getCash();
                    }
                }
                txtBalance.setText("Balance: "+balance);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=(ListView) findViewById(R.id.listView);
        txtBalance=(TextView) findViewById(R.id.txtBalance);
        btnCreate=(Button) findViewById(R.id.btnCreate);
        btnExit=(Button) findViewById(R.id.btnExit);

        myCreditDAO.seed();//tao moi neu ko co du lieu
        List<MyCredit> list=myCreditDAO.readAll();
        this.listCredit.addAll(list);

        listView.setAdapter(new CustomListAdapter(MainActivity.this, listCredit));

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                moveTaskToBack(true);
            }
        });


        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,CreateActivity.class);
                //Muon gui them du lieu thi khai bao them data dua vao bundle
                MainActivity.this.startActivityForResult(intent,REQUEST_CODE);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(MainActivity.this, UpdateActivity.class);
                MyCredit selectedItem= (MyCredit) parent.getItemAtPosition(position);
                intent.putExtra("credit", selectedItem);
                MainActivity.this.startActivityForResult(intent,REQUEST_CODE);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final MyCredit seletedItem= (MyCredit) parent.getItemAtPosition(position);

                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("Are you sure you want to delete?")
                        .setCancelable(false)
                        .setPositiveButton("Yes",(dialogInterface, i) -> {
                            myCreditDAO.delete(seletedItem.getId());
                            MainActivity.this.listCredit.remove(seletedItem);
                            double balance = 0;
                            for (MyCredit cr: listCredit) {
                                System.out.println("List credit " + cr.getTitle() + " " + cr.getCash());
                                if(cr.getTypeTransaction()==1){
                                    balance += cr.getCash();
                                }else{
                                    balance -= cr.getCash();
                                }
                            }
                            txtBalance.setText("Balance: " + balance);
                            listView.setAdapter(new CustomListAdapter(MainActivity.this, listCredit));
                        })
                        .setNegativeButton("No",null)
                        .show();

                return true;
            }
        });

        double balance = 0;
        for (MyCredit cr: listCredit) {
            System.out.println("List credit " + cr.getTitle() + " " + cr.getCash());
            if(cr.getTypeTransaction()==1){
                balance += cr.getCash();
            }else{
                balance -= cr.getCash();
            }
        }
        txtBalance.setText("Balance: " + balance);
    }
}