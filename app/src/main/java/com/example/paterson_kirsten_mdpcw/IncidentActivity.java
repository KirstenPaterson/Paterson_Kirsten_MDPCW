package com.example.paterson_kirsten_mdpcw;

//Kirsten Paterson S1828151
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class IncidentActivity extends AppCompatActivity implements IListFrag.ItemSelected {


    private IListFrag listFrag;
    private IDetailFrag detailFrag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incident);



        listFrag = new IListFrag();
        detailFrag = new IDetailFrag();

        //--------------FRAGMENT COMMUNICATION---------------






       getSupportFragmentManager().beginTransaction()
               .replace(R.id.frag_containerA, listFrag)
               .replace(R.id.frag_containerB, detailFrag)
               .commit();








        //-------------------------BOTTOMNAV---------------------------
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_roadworks:
                        Intent intent1= new Intent(IncidentActivity.this,CurrentActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.nav_planned:
                        Intent intent2 = new Intent(IncidentActivity.this, PlannedActivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.nav_incidents:
                        Intent intent3 = new Intent(IncidentActivity.this, IncidentActivity.class);
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

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(getApplicationContext(),"portrait mode", Toast.LENGTH_SHORT).show();

        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            Toast.makeText(getApplicationContext(),"landscape mode", Toast.LENGTH_SHORT).show();
        }
    }
}
