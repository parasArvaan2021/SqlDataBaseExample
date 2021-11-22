package com.databaseexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.databaseexample.Adapter.RecyclerAdapter;
import com.databaseexample.DataBase.MyDataBase;
import com.databaseexample.Model.DataModel;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements RecyclerInterface{
    Button btn_add,btn_clear_all;
    EditText edt_enter_name;
    ArrayList<DataModel> listOfName=new ArrayList<>();
    MyDataBase mydb;
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mydb=new MyDataBase(this);
        btn_add=findViewById(R.id.btn_add);
        btn_clear_all=findViewById(R.id.btn_clear_recyclerview);
        recyclerView=findViewById(R.id.recycle_display_name);
        edt_enter_name=findViewById(R.id.edt_name);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        ProgressDialog progressDialog=new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Please Wait...");
        displayAllData();


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=edt_enter_name.getText().toString();
                if (name.length()==0){
                    Toast.makeText(MainActivity.this, "Please Enter value", Toast.LENGTH_SHORT).show();
                }else {
                    progressDialog.show();
                    DataModel model = new DataModel();
                    model.setName(name);
                    mydb.insert_InfoTable_Data(model);
                    recyclerAdapter.notifyDataSetChanged();
                    edt_enter_name.setText("");

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(1000);
                                if (progressDialog.isShowing()){
                                    progressDialog.dismiss();
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    displayAllData();
                }
            }
        });
        btn_clear_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    public void displayAllData(){
        listOfName=mydb.get_All_InfoTable_Data();
        recyclerAdapter = new RecyclerAdapter(MainActivity.this,listOfName,this);
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public void removeData(int position) {
        MyDataBase mydb=new MyDataBase(this);
        mydb.deleteDataPosition(listOfName.get(position).getName());
        listOfName.remove(position);
        recyclerAdapter.notifyItemRemoved(position);

    }

    @Override
    public void createToast(int pos) {
        Toast.makeText(this,""+ listOfName.get(pos).getName(), Toast.LENGTH_SHORT).show();
    }
}