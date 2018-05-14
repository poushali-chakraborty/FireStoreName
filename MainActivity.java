package com.example.hp.firestorename;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.net.Uri;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.common.util.Strings;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    ListView listView;
    ArrayList<String> list=new ArrayList<>();
    ArrayList<String > keys=new ArrayList<>();
    private DatabaseReference mDatabase;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=(ListView)findViewById(R.id.listView);
/* firebase codes*/
        mDatabase = FirebaseDatabase.getInstance().getReference();

        final ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);

        listView.setAdapter(arrayAdapter);
        if(list.size()==0) {
            list.add("No Value");
        }

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value=dataSnapshot.getValue(String.class);
                list.add(value);
                String key=dataSnapshot.getKey();
                keys.add(key);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String value=dataSnapshot.getValue(String.class);
                String key=dataSnapshot.getKey();
                int i=keys.indexOf(key);
                list.set(i,value);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
/*end of Firebase codes*/

        }


    public void storeName(View view){
        EditText editName=(EditText)findViewById(R.id.editName);

        String str=editName.getText().toString().trim();
        if(str != null && !str.isEmpty())
        {



/* firebase*/
        /*create child
        * asign value to child*/
            mDatabase.push().setValue(str);

            editName.setText("");
            editName.setHint("enter a name");
            Toast.makeText(this, "Name added", Toast.LENGTH_SHORT).show();
        }
    }

    public void showNames(View view){

    }
}
