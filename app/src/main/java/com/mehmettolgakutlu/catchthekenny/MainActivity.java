package com.mehmettolgakutlu.catchthekenny;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // Tanımlamaları globalde yapıyoruz ki her yerden erişebilelim.
    TextView scoreText;
    TextView timeText;
    int score;
    ImageView imageView;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;
    ImageView imageView7;
    ImageView imageView8;
    ImageView imageView9;
    ImageView[] imageArray;
    Handler handler;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Uygulama açılır açılmaz, gerçekleşmesini istediğimiz işlemler burada tanımlanır.
        // Yukarıda tanımladıgımız nesne burada initialize ediliyor.
        // findViewById : Görünümü bul.

        timeText = (TextView) findViewById(R.id.timeText);
        scoreText = (TextView) findViewById(R.id.scoreText);
        imageView = findViewById(R.id.imageView);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);
        imageView5 = findViewById(R.id.imageView5);
        imageView6 = findViewById(R.id.imageView6);
        imageView7 = findViewById(R.id.imageView7);
        imageView8 = findViewById(R.id.imageView8);
        imageView9 = findViewById(R.id.imageView9);

        imageArray = new ImageView[] {imageView, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8, imageView9};
        hideImage(); // App açılır açılmaz görselleri saklama işlemini gerçekleştiriyor.

        score = 0;

        new CountDownTimer(10000,1000){ // Default değeri milisaniye olduğu için...
            @Override
            public void onTick(long millisUntilFinished) { // Her saniyede ne yapılacağını yazıyoruz
                timeText.setText("Time: " + millisUntilFinished/1000);
            }

            @Override
            public void onFinish() { // Timer bitince ne olacağını yazıyoruz

                timeText.setText("Time Off!!");
                handler.removeCallbacks(runnable); // Runnable'ı durdurur.
                for (ImageView image : imageArray){
                    image.setVisibility(View.INVISIBLE);
                }

                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Restart Game ?");
                alert.setMessage("Are you sure to restart game");   // Oyun bittikten sonra yeniden başlamak ister misiniz mesajı veriyor.

                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Yes cevabı verilirse oyun restart yapılacak.

                        Intent intent = getIntent();
                        finish();  // Uygulamayı bitirir.
                        startActivity(intent);  // Yeniden Başlatır.
                    }
                });

                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this,"Game Ovar!", Toast.LENGTH_SHORT).show(); // No cevabı verilirse "Game Ovar!" mesagı verilecek.

                    }
                });

                alert.show(); // Seçilen işlemi ekrana göstermek.
            }

        }.start(); // Timer'ı başlat methodu.
    }

    public void increaseScore(View view){  // --> Bir View tarafından çağırılacağını göstermek için ( View view )

        score++;
        scoreText.setText("Score: " + score); // Onclick özelliğine her tıkladığımızda score'u arttırıyoruz.
    }

    public void hideImage(){ // View'leri Saklayan method.

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {

                for (ImageView image : imageArray){
                    image.setVisibility(View.INVISIBLE);
                }

                Random random = new Random();
                int i = random.nextInt(9); // 0 - 8 arası rasgele rakam getirir.
                imageArray[i].setVisibility(View.VISIBLE); // Dizi içinden rasgele bir indeksi görünür yapar.

                handler.postDelayed(this,500); // İstediğimiz periyotta yukarıdaki işlemi gerçekleştiriyor.

            }
        };

        handler.post(runnable); // Runnable ' başlatma methodu.



    }
}