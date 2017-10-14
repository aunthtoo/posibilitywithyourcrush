package com.aunthtoo.posibilitywithyourcrush;

import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.wang.avi.AVLoadingIndicatorView;

public class Main extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    EditText you, crush;
    Button check;
    ViewGroup parent;
    View view;
    AVLoadingIndicatorView indicatorView;
    RelativeLayout ind, result;

    Runnable runnable;
    Handler handler;

    int index;

    //for animation
    RelativeLayout r1, r2;
    ImageView heartz;
    TextView title;
    int[] text = {R.string.t10, R.string.t20, R.string.t30, R.string.t40, R.string.t50, R.string.t60, R.string.t70, R.string.t80, R.string.t90, R.string.t100};

    //for result
    TextView resultTitle, resultDes;

    //for getting result
    int percent, indexForDes;
    boolean isSecondTime = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        toolbar=(Toolbar)findViewById(R.id.tool);
        toolbar.setNavigationIcon(R.drawable.ic_launcher);

        setSupportActionBar(toolbar);


        r1 = (RelativeLayout) findViewById(R.id.r1);
        r2 = (RelativeLayout) findViewById(R.id.r2);
        heartz = (ImageView) findViewById(R.id.heart);
        title = (TextView) findViewById(R.id.title);


        //edit text
        you = (EditText) findViewById(R.id.uname);
        crush = (EditText) findViewById(R.id.cname);


        check = (Button) findViewById(R.id.toCal);
        check.setOnClickListener(this);

        //get layout inflater
        LayoutInflater inflater = getLayoutInflater();

        //find parent of child view
        view = findViewById(R.id.forRes);
        parent = (ViewGroup) view.getParent();

        //for adding loading view
        ind = (RelativeLayout) inflater.inflate(R.layout.loading, parent, false);
        indicatorView = (AVLoadingIndicatorView) ind.findViewById(R.id.indicator);

        //index of child
        index = parent.indexOfChild(view);

        //for adding result view
        result = (RelativeLayout) inflater.inflate(R.layout.result, parent, false);
        resultTitle = (TextView) result.findViewById(R.id.resText);
        resultDes = (TextView) result.findViewById(R.id.resDescript);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {

                indicatorView.hide();
                parent.removeView(ind);

                parent.addView(result, index);


                resultTitle.setText(String.valueOf(percent) + " %");

                resultDes.setText(getResources().getString(text[indexForDes]));

                YoYo.with(Techniques.FadeInUp)
                        .duration(1000)
                        .playOn(resultTitle);

                YoYo.with(Techniques.FadeInUp)
                        .duration(1000)
                        .playOn(resultDes);


            }
        };


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toCal:

                if (!you.getText().toString().isEmpty() && !crush.getText().toString().isEmpty()) {
                    //hiding virtual keyboard
                    InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);

                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);


                    //adding loading view to parent relative layout
                    if (!isSecondTime) {
                        parent.addView(ind, index);
                        isSecondTime=true;

                    }
                    else
                    {
                        parent.removeView(result);
                        parent.addView(ind, index);

                    }


                    indicatorView.show();


                    //for calculating result
                    String[] u = you.getText().toString().split(" ");
                    String[] cru = crush.getText().toString().split(" ");

                    int resOne[] = new int[2];
                    int resTwo[] = new int[2];

                    if (u.length == 1) {
                        resOne[0] = (int) u[0].charAt(0);
                        resOne[1] = 20;
                    } else {
                        resOne[0] = (int) u[0].charAt(0);
                        resOne[1] = (int) u[1].charAt(1);
                    }


                    if (cru.length == 1) {
                        resTwo[0] = (int) cru[0].charAt(0);
                        resTwo[1] = 20;
                    } else {
                        resTwo[0] = (int) cru[0].charAt(0);
                        resTwo[1] = (int) cru[1].charAt(1);
                    }


                    int total = resOne[0] + resOne[1] + resTwo[0] + resTwo[1];

                    percent = (resOne[0] + resOne[1]) * 100 / total;

                    if (percent >= 0 && percent <= 10) {
                        indexForDes = 0;
                    } else if (percent >= 11 && percent <= 20) {
                        indexForDes = 1;
                    } else if (percent >= 21 && percent <= 30) {
                        indexForDes = 2;
                    } else if (percent >= 31 && percent <= 40) {
                        indexForDes = 3;
                    } else if (percent >= 41 && percent <= 50) {
                        indexForDes = 4;
                    } else if (percent >= 51 && percent <= 60) {
                        indexForDes = 5;
                    } else if (percent >= 61 && percent <= 70) {
                        indexForDes = 6;
                    } else if (percent >= 71 && percent <= 80) {
                        indexForDes = 7;
                    } else if (percent >= 81 && percent <= 90) {
                        indexForDes = 8;
                    } else if (percent >= 91 && percent <= 100) {
                        indexForDes = 9;
                    }


                    handler.postDelayed(runnable, 5000);
                } else
                    Toast.makeText(Main.this, "Please enter name in above edit texts !", Toast.LENGTH_SHORT).show();


                break;
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {
            YoYo.with(Techniques.BounceInLeft)
                    .duration(1000)
                    .playOn(r1);

            YoYo.with(Techniques.BounceInRight)
                    .duration(1000)
                    .playOn(r2);

            YoYo.with(Techniques.DropOut)
                    .duration(1000)
                    .playOn(heartz);

            YoYo.with(Techniques.StandUp)
                    .duration(1000)
                    .playOn(title);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.about)
        {
            AlertDialog.Builder builder=new AlertDialog.Builder(Main.this,R.style.Dialog);
            AlertDialog dialog=builder.create();
            dialog.setTitle("About Application");
            dialog.setMessage("\t\tThis app is just for fun :P :P :P\nEnjoy it\n\nDeveloped by Mg Ank Htoo...");
            dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        }
        return true;
    }
}
