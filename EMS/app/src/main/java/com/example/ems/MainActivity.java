package com.example.ems;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ems.controller.LoginActivity;


public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    });
    }
private void initView(){
    textView = findViewById(R.id.tvHome);
    button = findViewById(R.id.btnSurvey);
    }

}
/**
 *                         AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
 *                         builder.setMessage("Are you sure u want to delete " + books.get(position).getName()+"??");
 *                         builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
 *                             @Override
 *                             public void onClick(DialogInterface dialogInterface, int i) {
 *                                 if(Utils.getInstance().removeFromAlreadyRead(books.get(position))){
 *                                     Toast.makeText(mcontext, "Book Removed", Toast.LENGTH_SHORT).show();
 *                                     notifyDataSetChanged();
 *                                 }
 *                             }
 *
 *                         });
 *                         builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
 *                             @Override
 *                             public void onClick(DialogInterface dialogInterface, int i) {
 *
 *                             }
 *                         });
 *                         builder.create().show();
 */