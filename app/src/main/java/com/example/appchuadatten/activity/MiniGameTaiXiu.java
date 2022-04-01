package com.example.appchuadatten.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appchuadatten.R;

import java.util.Random;

public class MiniGameTaiXiu extends AppCompatActivity implements View.OnClickListener {

    ImageView phom, taiXiu, Baucua, coTuong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.giao_dien);
        fullScreen();
        init();
    }

    int count = 0, money = 1000000;
    TextView tVMoney;
    RadioButton checkBox, checkBox2, checkBox3;
    Dialog dialog;
    Button tai, xiu, datCuoc;
    ImageView xucXac1, xucXac2, xucXac3, dia2;
    Animation animation;
    int so[] = {1, 2, 3, 4, 5, 6};

    public void init() {
        animation = AnimationUtils.loadAnimation(this, R.anim.animation);

        Baucua = findViewById(R.id.bauCua);
        phom = findViewById(R.id.phom);
        coTuong = findViewById(R.id.coTuong);
        taiXiu = findViewById(R.id.taiXiu);
        Baucua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Chuẩn bị ra mắt");
            }
        });
        phom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Chuẩn bị ra mắt");
            }
        });
        coTuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Chuẩn bị ra mắt");
            }
        });

        taiXiu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDialogTaiXiu();
            }
        });
    }

    public void setDialogTaiXiu() {
        dialog = new Dialog(MiniGameTaiXiu.this);
        dialog.setContentView(R.layout.activity_giao_dien);
        dia2 = dialog.findViewById(R.id.btnDia2);
        xucXac1 = dialog.findViewById(R.id.xucSac);
        xucXac2 = dialog.findViewById(R.id.xucSac2);
        xucXac3 = dialog.findViewById(R.id.xucSac3);
        tVMoney = dialog.findViewById(R.id.money);
        checkBox = dialog.findViewById(R.id.checkBox);
        checkBox2 = dialog.findViewById(R.id.checkBox2);
        checkBox3 = dialog.findViewById(R.id.checkBox3);
        tai = dialog.findViewById(R.id.btnTai);
        xiu = dialog.findViewById(R.id.btnXiu);
        datCuoc = dialog.findViewById(R.id.btnDatCuoc);
        datCuoc.setVisibility(View.INVISIBLE);
        datCuoc.setOnClickListener(MiniGameTaiXiu.this);
        tai.setOnClickListener(MiniGameTaiXiu.this);
        xiu.setOnClickListener(MiniGameTaiXiu.this);
        checkBox.setOnClickListener(MiniGameTaiXiu.this);
        checkBox2.setOnClickListener(MiniGameTaiXiu.this);
        checkBox3.setOnClickListener(MiniGameTaiXiu.this);
        dia2.setOnClickListener(MiniGameTaiXiu.this);
        tVMoney.setText("$: " + money);
        dialog.show();
    }

    public void randomXucSac() {
        CountDownTimer countDownTimer = new CountDownTimer(2000, 50) {
            @Override
            public void onTick(long millisUntilFinished) {
                thietLapRandom();
                dia2.setClickable(false);
            }

            @Override
            public void onFinish() {
                dia2.setClickable(true);
                showToast("Mở Bát");
            }
        }.start();
    }

    int number, number2, number3;

    public void thietLapRandom() {
        number = random(5);
        String nameXucXac = "xucsac" + so[number];
        int id = getResources().getIdentifier(nameXucXac, "drawable", getPackageName());
        xucXac1.setImageResource(id);
        number2 = random(5);
        String nameXucXac2 = "xucsac" + so[number2];
        int id2 = getResources().getIdentifier(nameXucXac2, "drawable", getPackageName());
        xucXac2.setImageResource(id2);
        number3 = random(5);
        String nameXucXac3 = "xucsac" + so[number3];
        int id3 = getResources().getIdentifier(nameXucXac3, "drawable", getPackageName());
        xucXac3.setImageResource(id3);
    }

    public int random(int a) {
        Random random = new Random();
        return 0 + random.nextInt(a - 0 + 1);
    }

    public void thietLapAnimationReset() {
        RotateAnimation rotateAnimation = new RotateAnimation(90, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 90);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setFillAfter(true);
        dia2.startAnimation(rotateAnimation);
    }

    public void thietLapAnimationStart() {
        RotateAnimation rotateAnimation = new RotateAnimation(0, 90, Animation.RELATIVE_TO_SELF, 90, Animation.RELATIVE_TO_SELF, 0);
        rotateAnimation.setDuration(10000);
        rotateAnimation.setFillAfter(true);
        dia2.startAnimation(rotateAnimation);
    }

    public void fullScreen() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    void showToast(String str) {
        Toast.makeText(MiniGameTaiXiu.this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDia2:
                eventDia2();
                break;
            case R.id.checkBox:
                eventCheckBox();
                break;
            case R.id.checkBox2:
                eventCheckBox2();
                break;
            case R.id.checkBox3:
                eventCheckBox3();
                break;
            case R.id.btnTai:
                eventTai();
                break;
            case R.id.btnXiu:
                eventXiu();
                break;
            case R.id.btnDatCuoc:
                eventDatCuoc();
                break;
        }
    }

    int count2, count3;

    public void eventDia2() {
        if (!(checkBox.isChecked() || checkBox2.isChecked() || checkBox3.isChecked())) {
            showToast("Chưa đặt cược");
        } else if (count2 == 0 && count3 == 0) {
            showToast("Chưa lựa chọn hoặc chưa bấm bắt đầu");
        } else if (count2 >= 1 && count3 >= 1) {
            count++;
            if (count == 1) {
                thietLapAnimationStart();
                tinhDiem();
            } else {
                thietLapAnimationReset();
                scoreTai = 0;
                scoreXiu = 0;
                score = 0;
                count = 0;
                count2 = 0;
                count3 = 0;
                tai.setClickable(true);
                xiu.setClickable(true);
                checkBox.setChecked(false);
                checkBox2.setChecked(false);
                checkBox3.setChecked(false);
                checkBox.setBackgroundResource(0);
                checkBox2.setBackgroundResource(0);
                checkBox3.setBackgroundResource(0);
                checkBox.setClickable(true);
                checkBox2.setClickable(true);
                checkBox3.setClickable(true);
                count4 = 0;
                tai.setVisibility(View.VISIBLE);
                xiu.setVisibility(View.VISIBLE);
                moneyPlus = 0;
                if (money <= 0) {
                    money = 1000000;
                    showToast("Được khuyến mãi thêm +" + money);
                    tVMoney.setText("$:" + money);
                }
            }
        }
    }

    int scoreTai, scoreXiu;

    int moneyPlus = 0;
    int count4;

    public void eventCheckBox() {
        count4 += 1;
        int a = Integer.parseInt(checkBox.getText().toString().trim());
        moneyPlus += a;
        if (moneyPlus >= money) {
            showToast("Không đủ tiền");
        } else {
            showToast("" + moneyPlus);
            if (checkBox.isChecked()) {
                checkBox2.setChecked(false);
                checkBox3.setChecked(false);
                checkBox2.setBackgroundResource(0);
                checkBox3.setBackgroundResource(0);
                checkBox.setBackgroundColor(Color.YELLOW);
            }
        }
    }

    public void eventCheckBox2() {
        count4 += 1;
        int a = Integer.parseInt(checkBox2.getText().toString().trim());
        moneyPlus += a;
        if (moneyPlus >= money) {
            showToast("Không đủ tiền");
        } else {
            showToast("" + moneyPlus);
            if (checkBox2.isChecked()) {
                checkBox.setChecked(false);
                checkBox3.setChecked(false);
                checkBox.setBackgroundResource(0);
                checkBox3.setBackgroundResource(0);
                checkBox2.setBackgroundColor(Color.YELLOW);
            }
        }
    }

    public void eventCheckBox3() {
        count4 += 1;
        int a = Integer.parseInt(checkBox3.getText().toString().trim());
        moneyPlus += a;
        if (moneyPlus >= money) {
            showToast("Không đủ tiền");
        } else {
            showToast("" + moneyPlus);
            if (checkBox3.isChecked()) {
                checkBox.setChecked(false);
                checkBox2.setChecked(false);
                checkBox.setBackgroundResource(0);
                checkBox2.setBackgroundResource(0);
                checkBox3.setBackgroundColor(Color.YELLOW);
            }
        }
    }

    public void eventDatCuoc() {
        checkBox.setClickable(false);
        checkBox.setClickable(false);
        checkBox3.setClickable(false);
        count3 += 1;
        datCuoc.setVisibility(View.INVISIBLE);
        randomXucSac();
        tinhTien();
        xiu.setClickable(false);
        tai.setClickable(false);
        if (money <= 0) {
            money = 0;
            tVMoney.setText("$: " + money);
        }
    }

    public void tinhTien() {
        money -= moneyPlus;
        tVMoney.setText("$: " + money);
    }

    public void eventTai() {
        scoreTai += 1;
        tai.setVisibility(View.INVISIBLE);
        checkBox.setClickable(false);
        checkBox2.setClickable(false);
        checkBox3.setClickable(false);
        showToast("Tài");
        count2 = 1;
        xiu.setClickable(false);
        if (!(checkBox.isChecked() || checkBox2.isChecked() || checkBox3.isChecked())) {
            showToast("Chưa đặt cược ");
        } else if (count4 >= 1) {
            datCuoc.setVisibility(View.VISIBLE);
        }
    }

    public void eventXiu() {
        scoreXiu += 1;
        xiu.setVisibility(View.INVISIBLE);
        checkBox.setClickable(false);
        checkBox2.setClickable(false);
        checkBox3.setClickable(false);
        showToast("Xỉu");
        tai.setClickable(false);
        count2 = 1;
        if (!(checkBox.isChecked() || checkBox2.isChecked() || checkBox3.isChecked())) {
            showToast("Chưa đặt cược ");
        } else if (count4 >= 1) {
            datCuoc.setVisibility(View.VISIBLE);
        }
    }

    int score;

    public void tinhDiem() {
        if (scoreTai >= 1) {
            score = number + number2 + number3;
            if (score >= 11 && score <= 17) {
                int money3 = moneyPlus * 2;
                showToast("Tài\n+" + money3);
                money += money3;
                tVMoney.setText("$: " + money);
            } else if (score == 11) {
                int money3 = moneyPlus * 2;
                showToast("Tài\n+" + money3);
                money += money3;
                tVMoney.setText("$: " + money);
            } else if (score > 4 && score < 10) {
                showToast("Xỉu\n-" + moneyPlus);
                money -= moneyPlus;
                tVMoney.setText("$: " + money);
                if (money <= 0) {
                    money = 0;
                    tVMoney.setText("$: " + money);
                }
            }
        } else if (scoreXiu >= 1) {
            score = number + number2 + number3;
            if (score >= 11 && score <= 17) {
                showToast("Tài\n-" + moneyPlus);
                money -= moneyPlus;
                tVMoney.setText("$: " + money);
                if (money <= 0) {
                    money = 0;
                    tVMoney.setText("$: " + money);
                }
            } else if (score == 11) {
                showToast("Tài\n-" + moneyPlus);
                money -= moneyPlus;
                tVMoney.setText("$: " + money);
                if (money <= 0) {
                    money = 0;
                    tVMoney.setText("$: " + money);
                }
            } else if (score > 4 && score < 10) {
                int money4 = moneyPlus * 2;
                showToast("Xỉu\n+" + money4);
                money += money4;
                tVMoney.setText("$: " + money);
            }
        }
    }
}