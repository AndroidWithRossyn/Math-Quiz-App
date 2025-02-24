package com.satyamltd.braintest.mathquiz.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.satyamltd.braintest.mathquiz.QuestionActivity;
import com.satyamltd.braintest.mathquiz.R;
import com.satyamltd.braintest.mathquiz.ResultActivity;

public class CustomDialogClass extends Dialog implements android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button yes, no;


    public CustomDialogClass(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        yes = (Button) findViewById(R.id.btn_yes);
        no = (Button) findViewById(R.id.btn_no);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_yes) {
            dismiss();

            QuestionActivity.pDialog.show();
        } else if (id == R.id.btn_no) {
            dismiss();
            // QuestionActivity.closeQ();
            Intent intent = new Intent(c, ResultActivity.class);
            Bundle b = new Bundle();
            b.putInt("score", QuestionActivity.getMyValue());
            intent.putExtras(b);
            c.startActivity(intent);
            c.finish();
        }


    }
}