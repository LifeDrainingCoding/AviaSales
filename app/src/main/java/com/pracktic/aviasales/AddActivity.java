package com.pracktic.aviasales;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import org.jetbrains.annotations.NotNull;

import static com.pracktic.aviasales.Consts.*;
import java.util.ArrayList;

public class AddActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private int year, month, day, hour, minute;
    private EditText lineNumber, crews, availableSeats, seatsPrice, startDate;
    private Spinner planeType;
    private String stringPlaneType = TYPE_COMMON;
    private ArrayAdapter<String> planeTypeAdapter;
    private MaterialButton back, add, choose;
    private Long date;
    private Calendar calendar;
    private DatabaseReference aviLineRef;
    private boolean isDateChosen = false;
    private static final String TAG = "AddActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        lineNumber =findViewById(R.id.line_number_field);
        crews = findViewById(R.id.crew_field);
        availableSeats = findViewById(R.id.avialable_seats_field);
        seatsPrice = findViewById(R.id.seat_price_field);
        startDate = findViewById(R.id.date_field);
        planeType = findViewById(R.id.plane_type_spinner);
        back = findViewById(R.id.back_to_list_btn);
        add = findViewById(R.id.add_line_btn);
        choose = findViewById(R.id.pick_date_btn);
        planeTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new ArrayList<String>(){{
            add(TYPE_COMMON);
            add(TYPE_ADVANCED);
        }});
        planeTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        datePickerDialog = new DatePickerDialog(this,this,2024, 6,1);
        timePickerDialog = new TimePickerDialog(this,this,12,30,true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        planeType.setAdapter(planeTypeAdapter);
        planeType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stringPlaneType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        back.setOnClickListener(v -> {
            Intent intent = new Intent(AddActivity.this, ListActivity.class);
            startActivity(intent);
            finish();
        });
        choose.setOnClickListener(v -> {
            datePickerDialog.show();
        });
        add.setOnClickListener(v -> {
            if (isDateChosen) {

            if (!lineNumber.getText().toString().isEmpty() && !crews.getText().toString().isEmpty() && !availableSeats.getText().toString().isEmpty() && !seatsPrice.getText().toString().isEmpty() && !startDate.getText().toString().isEmpty()) {
                if (!Consts.hasInvalidCharacters(availableSeats.getText().toString()) && !Consts.hasInvalidCharacters(seatsPrice.getText().toString())) {

                    String[] partsOfSeats = availableSeats.getText().toString().split(",");
                    String[] partsOfPrices = seatsPrice.getText().toString().split(",");
                    if (partsOfSeats.length == partsOfPrices.length && partsOfPrices.length == 4) {


                        int availableEcoSeats, availablePremEcoSeats, availableBusinessSeats, availableFirstSeats;
                        double ecoSeats, premEcoSeats, businessSeats, firstSeats;
                        try {
                            availableEcoSeats = Integer.parseInt(partsOfSeats[0]);
                            availablePremEcoSeats = Integer.parseInt(partsOfSeats[1]);
                            availableBusinessSeats = Integer.parseInt(partsOfSeats[2]);
                            availableFirstSeats = Integer.parseInt(partsOfSeats[3]);
                            ecoSeats = Double.parseDouble(partsOfPrices[0]);
                            premEcoSeats = Double.parseDouble(partsOfPrices[1]);
                            businessSeats = Double.parseDouble(partsOfPrices[2]);
                            firstSeats = Double.parseDouble(partsOfPrices[3]);
                            AviaLine aviaLine = new AviaLine(Integer.parseInt(lineNumber.getText().toString()), crews.getText().toString(),stringPlaneType, date,
                                    availableEcoSeats, availablePremEcoSeats,availableBusinessSeats,availableFirstSeats,ecoSeats,premEcoSeats,businessSeats,firstSeats);
                            aviLineRef = getInstance().getAviaLines();
                            aviLineRef.child(String.valueOf(aviaLine.getAviaLineId())).setValue(aviaLine).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(AddActivity.this, "Успешно загружено в базу данных!", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull @NotNull Exception e) {
                                    Log.e(TAG, "onFailure:  error during upload to DB!", e );
                                }
                            });
                        } catch (Exception e) {
                            Toast.makeText(AddActivity.this, "Ошибка при конвертировании чисел", Toast.LENGTH_LONG).show();
                        }


                    } else {
                        Toast.makeText(AddActivity.this, "Кол-во мест или цен не соотвествует 4", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(AddActivity.this, "Не валидно введены места для пасажиров или их цена", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(AddActivity.this, "Одно из полей пусто", Toast.LENGTH_SHORT).show();
            }
        }else {
                Toast.makeText(this, "Не выбрана дата", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        this.year = year;
        this.month = month;
        day = dayOfMonth;
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        this.hour = hourOfDay;
        this.minute = minute;
        String text= year+"."+month+"."+day+" "+hourOfDay+":"+minute;
        startDate.setText(text);
        calendar= Calendar.getInstance();
        calendar.set(year, month, day, hour, this.minute);
        date = calendar.getTimeInMillis();
        isDateChosen = true;
    }
}