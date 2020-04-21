package com.example.paterson_kirsten_mdpcw;
//Kirsten Paterson S1828151
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CurrentActivity extends AppCompatActivity implements CListFrag.ItemSelected {

    private CListFrag listFrag;
    private CDetailFrag detailFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current);

        listFrag = new CListFrag();
        detailFrag = new CDetailFrag();

        //--------------FRAGMENT COMMUNICATION----------------
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frag_containerA, listFrag)
                .replace(R.id.frag_containerB, detailFrag).commit();



//-------------BOTTOM NAV -------------------------------
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_roadworks:
                        Intent intent1= new Intent(CurrentActivity.this,CurrentActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.nav_planned:
                        Intent intent2 = new Intent(CurrentActivity.this, PlannedActivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.nav_incidents:
                        Intent intent3 = new Intent(CurrentActivity.this, IncidentActivity.class);
                        startActivity(intent3);
                        break;

                }
                return false;
            }
        });

    }
    @Override
    public void onItemSelected(int index) {



    }
}
