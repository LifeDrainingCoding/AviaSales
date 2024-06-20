package com.pracktic.aviasales;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static com.pracktic.aviasales.Consts.getInstance;

public class DeleteActivity extends AppCompatActivity {
    private MaterialButton back,del;
    private boolean isShowed = false;
    private RecyclerView recyclerView;
    private LinkedHashMap<Integer,AviaLine> map = new LinkedHashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        recyclerView = findViewById(R.id.list_avialines_to_delete);
        back= findViewById(R.id.back_btn);
        del = findViewById(R.id.del_btn);
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
                Intent intent = new Intent(DeleteActivity.this, ListActivity.class);
                startActivity(intent);
                finish();
            }
        });
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSelectedItems();
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
        map.clear();
        DatabaseReference reference;
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
                        recyclerView.setAdapter(new DeleteAdapter(aviaLines, new OnCheckedListener() {
                            @Override
                            public void onChecked(AviaLine aviaLine, int i, boolean isChecked) {
                                if(isChecked){
                                    map.put(i, aviaLine);
                                }else {
                                    map.remove(i);
                                }
                            }

                            @Override
                            public void onSelectedClass(String className, int i, boolean isChecked) {

                            }
                        }));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError databaseError) {

            }
        });
    }
    private void deleteSelectedItems(){
        ArrayList<AviaLine> selectedAvialines = new ArrayList<>();
        map.forEach(new BiConsumer<Integer, AviaLine>() {
            @Override
            public void accept(Integer integer, AviaLine aviaLine) {
                selectedAvialines.add(aviaLine);
            }
        });
        for (final int[] i = {0}; i[0] <selectedAvialines.size() ; i[0]++) {
            AviaLine aviaLine = selectedAvialines.get(i[0]);
            DatabaseReference reference =  getInstance().getAviaLines().child(String.valueOf(aviaLine.getAviaLineId()));
            reference.removeValue().addOnSuccessListener(unused -> {
               if(i[0] == selectedAvialines.size()-1){
                   Toast.makeText(this, "Удалено выбранное", Toast.LENGTH_SHORT).show();
                   loadRecView(recyclerView);
               }
            });
        }
    }
}