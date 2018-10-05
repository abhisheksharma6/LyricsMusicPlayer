package com.vs.lyricsmusicplayer;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView textView1, textView2, textView3, textView4, textView5;
    private ImageView imageView1, imageView2, imageView3, imageView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);
        textView5 = (TextView) findViewById(R.id.textView5);
        imageView1 = (ImageView) findViewById(R.id.imageView1);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
        imageView4 = (ImageView) findViewById(R.id.imageView4);

        textView1.setOnClickListener(this);
        textView2.setOnClickListener(this);
        textView3.setOnClickListener(this);
        textView4.setOnClickListener(this);
        textView5.setOnClickListener(this);
        imageView1.setOnClickListener(this);
        imageView2.setOnClickListener(this);
        imageView3.setOnClickListener(this);
        imageView4.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.textView1:
                //Implement the clicks for textView1
                Toast.makeText(getApplicationContext(), "Click is not implemented", Toast.LENGTH_SHORT).show();
                break;
            case R.id.textView2:
                //Implement the clicks for textView2
                Intent intent = new Intent(MainActivity.this, RagasActivity.class);
                startActivity(intent);
                break;
            case R.id.textView3:
                //Implement the clicks for textView3
                Toast.makeText(getApplicationContext(), "Click is not implemented", Toast.LENGTH_SHORT).show();
                break;
            case R.id.textView4:
                //Implement the clicks for textView4
                Toast.makeText(getApplicationContext(), "Click is not implemented", Toast.LENGTH_SHORT).show();
                break;
            case R.id.textView5:
                //Implement the clicks for textView5
                Toast.makeText(getApplicationContext(), "Click is not implemented", Toast.LENGTH_SHORT).show();
                break;
            case R.id.imageView1:
                //Implement the clicks for imageView1
                break;
            case R.id.imageView2:
                //Implement the clicks for imageview2
                break;
            case R.id.imageView3:
                //Implement the clicks for imageView3
                break;
            case R.id.imageView4:
                //Implement the clicks for imageView4
                break;
        }
    }
}





