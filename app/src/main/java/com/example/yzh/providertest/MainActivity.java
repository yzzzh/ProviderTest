package com.example.yzh.providertest;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String newId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        Button btnQuery = (Button) findViewById(R.id.btnQuery);
        Button btnDelete = (Button) findViewById(R.id.btnDelete);
        Button btnUpdate = (Button) findViewById(R.id.btnUpdate);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://com.example.yzh.databasetest.provider/book");
                ContentValues values = new ContentValues();
                values.put("name","A Clash of Kings");
                values.put("author","Geogre Martin");
                values.put("price",22.85);
                values.put("pages",1040);
                Uri newUri = getContentResolver().insert(uri, values);
                newId = newUri.getPathSegments().get(1);
                Toast.makeText(MainActivity.this, "插入数据成功！", Toast.LENGTH_SHORT).show();
            }
        });

        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://com.example.yzh.databasetest.provider/book");
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                if(cursor != null){
                    while(cursor.moveToNext()){
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        String content = String.format("姓名：%s，作者：%s，价钱：%.2f，页数%d",name,author,price,pages);
                        Toast.makeText(MainActivity.this,content,Toast.LENGTH_SHORT).show();
                    }
                }
                cursor.close();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://com.example.yzh.databasetest.provider/book/"+newId);
                ContentValues values = new ContentValues();

                values.put("name","A Storm of Swords");
                values.put("pages",1216);
                values.put("price",24.05);
                getContentResolver().update(uri, values, null, null);
                Toast.makeText(MainActivity.this,"更新数据成功~！",Toast.LENGTH_SHORT).show();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://com.example.yzh.databasetest.provider/book/"+newId);
                getContentResolver().delete(uri, null, null);
                Toast.makeText(MainActivity.this,"删除数据成功！",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
