package com.pracktic.aviasales;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import org.jetbrains.annotations.NotNull;

import static com.pracktic.aviasales.Consts.*;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class SearchActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener , OnCheckedListener, onDissMissList {
    private Spinner spinner;
    private EditText searchBar;
    private MaterialButton searchButton,back;
    private int searchCode = -1;
    private int selectedClass = -1;
    private AviaLine aviaLine;
    private ArrayList<AviaLine> aviaLines;
    private RecyclerView recyclerView;


    private static final ArrayList<String> categories = new ArrayList<String>(){{
        add("По номеру рейса");
        add("По экипажу");
        add("По классу");
        add("По предельной цене");
        add("По классу и предельной цене");
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        spinner = findViewById(R.id.search_type_spinner);
        searchBar = findViewById(R.id.search_type_field);
        back =  findViewById(R.id.back_btn);
        searchButton =  findViewById(R.id.search_btn);
        recyclerView =  findViewById(R.id.list_result);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        loadAviaLines();

    }

    @Override
    protected void onStart() {
        super.onStart();
        back.setOnClickListener(v -> {
            Intent intent = new Intent(SearchActivity.this, ListActivity.class);
            startActivity(intent);
            finish();
        });
        spinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        searchCode = position;
        loadAviaLines();
        switch (searchCode) {
            case 0:
                showBar("Введите номер рейса");
               searchButton.setOnClickListener(v -> {
                   try {
                       int lineId = Integer.parseInt(searchBar.getText().toString());

                       aviaLines.removeIf(new Predicate<AviaLine>() {
                           @Override
                           public boolean test(AviaLine aviaLine) {
                               return !(aviaLine.getAviaLineId()==lineId);
                           }
                       }) ;
                       loadRecView(recyclerView, aviaLines);
                   }catch (Exception ex){
                       Toast.makeText(this, "Недопустимые символы в номере рейса", Toast.LENGTH_SHORT).show();
                   }
               });
                break;
            case 1:
                showBar("Введите имя члена экипажа");
                searchButton.setOnClickListener(v -> {
                    String crew = searchBar.getText().toString();
                    aviaLines.removeIf(new Predicate<AviaLine>() {
                        @Override
                        public boolean test(AviaLine aviaLine) {
                            return !(aviaLine.getCrews().contains(crew));
                        }
                    });
                    loadRecView(recyclerView, aviaLines);

                });


                break;
            case 2: showBar("Введите номер класса(4,3,2 или 1)");
            searchButton.setOnClickListener(v -> {
                try{
                int  classNumber = Integer.parseInt(searchBar.getText().toString());
                switch (classNumber) {
                    case 1:
                        aviaLines.removeIf(new Predicate<AviaLine>() {
                            @Override
                            public boolean test(AviaLine aviaLine) {
                                return aviaLine.getAvailableFirstSeats() == 0;
                            }
                        });
                        loadRecView(recyclerView, aviaLines);
                        break;
                    case 2:
                        aviaLines.removeIf(new Predicate<AviaLine>() {
                            @Override
                            public boolean test(AviaLine aviaLine) {
                                return aviaLine.getAvailableBusinessSeats() == 0;
                            }
                        });
                        loadRecView(recyclerView, aviaLines);
                        break;
                    case 3:
                        aviaLines.removeIf(new Predicate<AviaLine>() {
                            @Override
                            public boolean test(AviaLine aviaLine) {
                                return aviaLine.getAvailablePremEcoSeats()==0;
                            }
                        });
                        loadRecView(recyclerView, aviaLines);
                        break;
                    case 4:
                        aviaLines.removeIf(new Predicate<AviaLine>() {
                            @Override
                            public boolean test(AviaLine aviaLine) {
                                return aviaLine.getAvailableEcoSeats() == 0;
                            }
                        });
                        loadRecView(recyclerView, aviaLines);

                        break;
                    default:
                        Toast.makeText(this, "Введен номер несуществующего класса", Toast.LENGTH_SHORT).show();
                }
                }catch (Exception ex){
                    Toast.makeText(this, "Ошибка при конвертации номера класса", Toast.LENGTH_SHORT).show();
                }
            });
                break;
            case 3: showBar("Введите предельную цену");
            searchButton.setOnClickListener(v -> {
               try{
                   double  maxPrice = Double.parseDouble(searchBar.getText().toString());
                   aviaLines.removeIf(new Predicate<AviaLine>() {
                       @Override
                       public boolean test(AviaLine aviaLine) {
                           return aviaLine.getFirstSeatPrice()>maxPrice&& aviaLine.getBusinessSeatPrice()>maxPrice
                                   &&aviaLine.getPremEcoSeatPrice()>maxPrice&&aviaLine.getEcoSeatPrice()>maxPrice;
                       }
                   });
                   loadRecView(recyclerView,aviaLines);
               }catch (Exception ex){
                   Toast.makeText(this, "Ошибка при конвертации предельного значения", Toast.LENGTH_SHORT).show();
               }
            });
                break;
            case 4:searchButton.setOnClickListener(v -> {
                String[] parts = searchBar.getText().toString().split(",");
                if (parts.length == 2){
                    try {
                        double maxPrice = Double.parseDouble(parts[1]);
                        int classNumber = Integer.parseInt(parts[0]);
                        switch (classNumber) {
                            case 1:
                                aviaLines.removeIf(new Predicate<AviaLine>() {
                                    @Override
                                    public boolean test(AviaLine aviaLine) {
                                        return aviaLine.getAvailableFirstSeats() == 0 && aviaLine.getFirstSeatPrice()>maxPrice;
                                    }
                                });
                                loadRecView(recyclerView, aviaLines);
                                break;
                            case 2:
                                aviaLines.removeIf(new Predicate<AviaLine>() {
                                    @Override
                                    public boolean test(AviaLine aviaLine) {
                                        return aviaLine.getAvailableBusinessSeats() == 0&& aviaLine.getBusinessSeatPrice()>maxPrice;
                                    }
                                });
                                loadRecView(recyclerView, aviaLines);
                                break;
                            case 3:
                                aviaLines.removeIf(new Predicate<AviaLine>() {
                                    @Override
                                    public boolean test(AviaLine aviaLine) {
                                        return aviaLine.getAvailablePremEcoSeats() == 0&& aviaLine.getPremEcoSeatPrice()>maxPrice;
                                    }
                                });
                                loadRecView(recyclerView, aviaLines);
                                break;
                            case 4:
                                aviaLines.removeIf(new Predicate<AviaLine>() {
                                    @Override
                                    public boolean test(AviaLine aviaLine) {
                                        return aviaLine.getAvailableEcoSeats() == 0&& aviaLine.getEcoSeatPrice()>maxPrice;
                                    }
                                });
                                loadRecView(recyclerView, aviaLines);

                                break;
                            default:
                                Toast.makeText(this, "Введен номер несуществующего класса", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception ex) {
                        Toast.makeText(this, "Ошибка при конвертации номера класса", Toast.LENGTH_SHORT).show();
                    }
            }else{
                    Toast.makeText(this, "Неправильно введеные данные", Toast.LENGTH_SHORT).show();
                }
            });

                break;
            default:
                Toast.makeText(this, "Ничего не выбранно", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void loadRecView(RecyclerView recyclerView, ArrayList<AviaLine> aviaLines) {
        recyclerView.setAdapter(new SearchAdapter(aviaLines,this, getSupportFragmentManager(),this));

    }

    @Override
    public void onChecked(AviaLine aviaLine, int i, boolean isChecked) {
        this.aviaLine = aviaLine;
    }

    @Override
    public void onSelectedClass(String className, int i, boolean isChecked) {
        if(isChecked){
            selectedClass = i;
        }else {
            selectedClass = -1;
        }
    }


    private void loadAviaLines(){
        aviaLines = new ArrayList<>();
        ArrayList<DataSnapshot> snapshots = new ArrayList<>();
        DatabaseReference reference = getInstance().getAviaLines();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                dataSnapshot.getChildren().forEach(new Consumer<DataSnapshot>() {
                    @Override
                    public void accept(DataSnapshot dataSnapshot) {
                        snapshots.add(dataSnapshot);

                    }
                });
                for (int i = 0; i <snapshots.size() ; i++) {
                    aviaLines.add(snapshots.get(i).getValue(AviaLine.class));
                    if(i == snapshots.size()-1){
                        Toast.makeText(SearchActivity.this, "Успешно загруженны данные", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError databaseError) {

            }
        });
    }
    private void showBar(String hint){
        searchBar.setHint(hint);
        searchBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDissMiss() {
        DatabaseReference databaseReference =  getInstance().getAviaLines().child(String.valueOf(aviaLine.getAviaLineId()));
        switch(selectedClass){
            case 0: aviaLine.setAvailableEcoSeats(aviaLine.getAvailableEcoSeats()-1); break;
            case 1: aviaLine.setAvailablePremEcoSeats(aviaLine.getAvailablePremEcoSeats()-1); break;
            case 2: aviaLine.setAvailableBusinessSeats(aviaLine.getAvailableBusinessSeats()-1); break;
            case 3: aviaLine.setAvailableFirstSeats(aviaLine.getAvailableFirstSeats()-1);break;
        }
        databaseReference.setValue(aviaLine).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(SearchActivity.this, "Место в самолете зарезервированно!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SearchActivity.this, ListActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }
}