package com.satyamltd.braintest.mathquiz;

import java.util.List;
import java.util.concurrent.TimeUnit;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.satyamltd.braintest.mathquiz.utils.Constant;
import com.satyamltd.braintest.mathquiz.utils.CustomDialogClass;

public class QuestionActivity extends AppCompatActivity {
    List<Question> quesList;
    static int score = 0;
    int qid = 0;


    Question currentQ;
    TextView txtQuestion, times, scored;
    Button button1, button2, button3;

    public static Context contextstat;
    public static ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        String method = getIntent().getStringExtra("method");

        QuizHelper db = new QuizHelper(this);
        db.deleteTable();
        quesList = db.getAllQuestions(method);
        currentQ = quesList.get(qid);

        txtQuestion = (TextView) findViewById(R.id.txtQuestion);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);


        scored = (TextView) findViewById(R.id.score);


        times = (TextView) findViewById(R.id.timers);


        setQuestionView();
        times.setText("00:00:60");


        CounterClass timer = new CounterClass(Constant.time_inseconds * 1000, 1000);
        timer.start();
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("firstrun", true).apply();


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                getAnswer(button1.getText().toString());
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAnswer(button2.getText().toString());
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAnswer(button3.getText().toString());
            }
        });


        contextstat = QuestionActivity.this;
        pDialog = new ProgressDialog(QuestionActivity.this);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.setCancelable(false);

    }//oncreate


    public static int getMyValue() {
        return score;
    }


    public void getAnswer(String AnswerString) {
        if (currentQ.getANSWER().equals(AnswerString)) {


            score++;
            scored.setText(getString(R.string.score) + " " + score);
        } else {

            Intent intent = new Intent(QuestionActivity.this, ResultActivity.class);
            Bundle b = new Bundle();
            b.putInt("score", score);
            intent.putExtras(b);
            startActivity(intent);
            finish();
        }
        if (qid < Constant.questions_number) {


            currentQ = quesList.get(qid);
            setQuestionView();
        } else {


            Intent intent = new Intent(QuestionActivity.this, won.class);
            Bundle b = new Bundle();
            b.putInt("score", score);
            intent.putExtras(b);
            startActivity(intent);
            finish();
        }


    }


    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressLint("NewApi")
    public class CounterClass extends CountDownTimer {

        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            // TODO Auto-generated constructor stub
        }


        @Override
        public void onFinish() {

            if (Constant.show_banner_ad && ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null) {
                boolean firstrun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("firstrun", true);
                if (firstrun) {
                    getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("firstrun", false).apply();
                    pDialog.setMessage(getString(R.string.loading));


                    CustomDialogClass cdd = new CustomDialogClass(QuestionActivity.this);
                    cdd.setCanceledOnTouchOutside(false);
                    cdd.setCancelable(false);
                    if (!((Activity) QuestionActivity.this).isFinishing()) cdd.show();
                } else {
                    Intent intent = new Intent(QuestionActivity.this, ResultActivity.class);
                    Bundle b = new Bundle();
                    b.putInt("score", score);
                    intent.putExtras(b);
                    startActivity(intent);
                    finish();

                }
            } else {
                Intent intent = new Intent(QuestionActivity.this, ResultActivity.class);
                Bundle b = new Bundle();
                b.putInt("score", score);
                intent.putExtras(b);
                startActivity(intent);
                finish();

            }
        }//onfinish

        @Override
        public void onTick(long millisUntilFinished) {
            // TODO Auto-generated method stub

            long millis = millisUntilFinished;
            String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            System.out.println(hms);
            times.setText(hms);


        }


    }

    private void setQuestionView() {

        // the method which will put all things together
        txtQuestion.setText(currentQ.getQUESTION());
        button1.setText(currentQ.getOPTA());
        button2.setText(currentQ.getOPTB());
        button3.setText(currentQ.getOPTC());

        qid++;
    }


}

