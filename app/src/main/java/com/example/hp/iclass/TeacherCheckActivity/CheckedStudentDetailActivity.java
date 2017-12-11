package com.example.hp.iclass.TeacherCheckActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;

import com.example.hp.iclass.HttpFunction.Function.Common_Function.Fun_QuaryStudentScore;
import com.example.hp.iclass.HttpFunction.Function.Teacher_Function.Fun_UpdateStudentScore;
import com.example.hp.iclass.OBJ.CheckOBJ;
import com.example.hp.iclass.OBJ.SubjectOBJ;
import com.example.hp.iclass.OBJ.TeacherOBJ;
import com.example.hp.iclass.R;

import java.lang.reflect.Method;

public class CheckedStudentDetailActivity extends AppCompatActivity {
    private SubjectOBJ subjectOBJ = new SubjectOBJ();
    private TeacherOBJ teacherOBJ = new TeacherOBJ();
    private CheckOBJ checkOBJ = new CheckOBJ();
    private Toolbar toolbar;
    private RatingBar ratingBar;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checked_student_detail);
        Intent intent = getIntent();
        checkOBJ = (CheckOBJ) intent.getSerializableExtra("checkOBJ");
        teacherOBJ = (TeacherOBJ) intent.getSerializableExtra("teacherOBJ");
        subjectOBJ = (SubjectOBJ) intent.getSerializableExtra("subjectOBJ");
        checkOBJ.setSubject_th(subjectOBJ.getSubject_th());
        checkOBJ.setSubject_id(subjectOBJ.getSubject_id());
        try {
            score = Fun_QuaryStudentScore.http_QuaryStudentScore(checkOBJ);
        } catch (InterruptedException e) {
            e.printStackTrace();
            score = 0;
        }
        checkOBJ.setScore(score);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setTitle(checkOBJ.getStudent_id() + " : " + checkOBJ.getStudent_name());
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goback();
            }
        });
        ratingBar.setRating((float) score);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                try {
                    Fun_UpdateStudentScore.http_UpdateStudentScore(checkOBJ, (int) rating);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if(menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")) {
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_only_fresh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_refresh) {
            try {
                score = Fun_QuaryStudentScore.http_QuaryStudentScore(checkOBJ);
            } catch (InterruptedException e) {
                e.printStackTrace();
                score = 0;
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void goback() {
        finish();
    }

    public void onBackPressed() {
        finish();
    }

}
