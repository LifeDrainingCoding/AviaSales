package com.pracktic.aviasales;

import android.content.Intent;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import org.jetbrains.annotations.NotNull;

import static com.pracktic.aviasales.Consts.*;
import java.util.ArrayList;
import java.util.function.Consumer;

public class ListActivity extends AppCompatActivity {
private DatabaseReference reference;
private RecyclerView recyclerView;
private MaterialButton back, add,del,query;
private boolean isShowed = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        recyclerView = findViewById(R.id.list_avialines);
        back = findViewById(R.id.back_btn);
        del = findViewById(R.id.delete_btn);
        add =findViewById(R.id.add_btn);
        query = findViewById(R.id.create_query_btn);


    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!isShowed){
            isShowed = true;
            loadRecView(recyclerView);
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this, AddActivity.class);
                startActivity(intent);
                finish();
            }
        });
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this, DeleteActivity.class);
                startActivity(intent);
                finish();
            }
        });
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this, SearchActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!isShowed){
            isShowed = true;
            loadRecView(recyclerView);
        }
    }

    private void loadRecView(RecyclerView recyclerView){
        reference =  getInstance().getAviaLines();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                ArrayList<DataSnapshot> snapshots = new ArrayList<>();
                ArrayList<AviaLine> aviaLines = new ArrayList<>();
                dataSnapshot.getChildren().forEach(new Consumer<DataSnapshot>() {
                    @Override
                    public void accept(DataSnapshot dataSnapshot) {
                        snapshots.add(dataSnapshot);
                    }
                });
                for (int i = 0; i < snapshots.size(); i++) {
                    AviaLine aviaLine = snapshots.get(i).getValue(AviaLine.class);
                    aviaLines.add(aviaLine);
                    if(i==snapshots.size()-1){
                     recyclerView.setAdapter(new ListAvialinesAdapter(aviaLines));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError databaseError) {

            }
        });
    }
}