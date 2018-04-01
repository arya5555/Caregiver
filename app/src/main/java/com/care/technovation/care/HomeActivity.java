package com.care.technovation.care;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private String userEmail;

    ExpandableListView expandableListView;
    MyExpandableListAdapter myExpandableListAdapter;
    List<String> groupList;
    HashMap<String, List<String>> childMap;

    //boolean flag to know if main FAB is in open or closed state.
    private boolean fabExpanded = false;
    private FloatingActionButton fabAdd;

    //Linear layouts holding the fab submenus
    private LinearLayout layoutFabExistRecipient;
    private LinearLayout layoutFabFamily;
    private LinearLayout layoutFabNewRecipient;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //
                    return true;
                case R.id.navigation_newsfeed:
                    //
                    return true;
                case R.id.navigation_messages:
                    //
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = FirebaseAuth.getInstance();
        mTextMessage = (TextView) findViewById(R.id.message);
        user = auth.getCurrentUser();

        init();
        expandableListView = (ExpandableListView) findViewById(R.id.ExpandableList);
        myExpandableListAdapter = new MyExpandableListAdapter(this, groupList, childMap);
        expandableListView.setAdapter(myExpandableListAdapter);

        fabAdd = (FloatingActionButton) this.findViewById(R.id.FabMain);

        layoutFabExistRecipient = (LinearLayout) this.findViewById(R.id.layoutFabAddRecipient);
        layoutFabFamily = (LinearLayout) this.findViewById(R.id.layoutFabAddFamily);
        layoutFabNewRecipient = (LinearLayout) this.findViewById(R.id.layoutFabAddNewRecipient);

        //When main Fab is clicked, it expands if not expanded already.
        //Collapses if main FAB was open already.
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fabExpanded){
                    closeSubMenusFab();
                } else {
                    openSubMenusFab();
                }
            }
        });

        layoutFabNewRecipient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, NewRecipientActivity.class);
                startActivity(intent);
            }
        });

        //Only main FAB is visible in the beginning
        closeSubMenusFab();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void init() {
        groupList = new ArrayList<String>();
        childMap = new HashMap<String, List<String>>();

        List<String> groupList0 = new ArrayList<String>();
        groupList0.add("Grandpa");
        groupList0.add("Baby");
        groupList0.add("Dog");

        List<String> groupList1 = new ArrayList<String>();
        groupList1.add("Mom");
        groupList1.add("Brother");
        groupList1.add("Aunt");

        List<String> groupList2 = new ArrayList<String>();
        groupList2.add("Cathleen");
        groupList2.add("Jonah");

        groupList.add("Care Recipients");
        groupList.add("Family Members");
        groupList.add("Caregivers");

        childMap.put(groupList.get(0), groupList0);
        childMap.put(groupList.get(1), groupList1);
        childMap.put(groupList.get(2), groupList2);
    }

    private void closeSubMenusFab(){
        layoutFabNewRecipient.setVisibility(View.INVISIBLE);
        layoutFabFamily.setVisibility(View.INVISIBLE);
        layoutFabExistRecipient.setVisibility(View.INVISIBLE);
        fabAdd.setImageResource(R.drawable.ic_plus_white);
        fabExpanded = false;
    }

    //Opens FAB submenus
    private void openSubMenusFab(){
        layoutFabNewRecipient.setVisibility(View.VISIBLE);
        layoutFabFamily.setVisibility(View.VISIBLE);
        layoutFabExistRecipient.setVisibility(View.VISIBLE);
        //Change settings icon to 'X' icon
        fabAdd.setImageResource(R.drawable.ic_close_white);
        fabExpanded = true;
    }
}