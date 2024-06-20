package com.pracktic.aviasales;

import android.icu.text.SimpleDateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;

public class ListAvialinesAdapter extends RecyclerView.Adapter<ListAvialinesAdapter.ViewHolder> {
    public ArrayList<AviaLine> aviaLines;
    public ListAvialinesAdapter(ArrayList<AviaLine> aviaLines) {
        this.aviaLines = aviaLines;
    }
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ListAvialinesAdapter.ViewHolder v, int i) {
        AviaLine aviaLine = aviaLines.get(i);
        String lineNumber, crews, planeType, startDate,availableSeats, seatsPrice;
                lineNumber = v.lineNumber.getText().toString() + aviaLine.getAviaLineId();
                crews = v.crews.getText().toString() + aviaLine.getCrews();
                planeType = v.planeType.getText().toString() + aviaLine.getAirPlaneType();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");
                Date date = new Date();
                date.setTime(aviaLine.getStartDate());

                startDate = v.startDate.getText().toString() + dateFormat.format(date);
                availableSeats = v.availableSeats.getText().toString() +"4 класса:"+ aviaLine.getAvailableEcoSeats()+", 3 класса:" +
                        aviaLine.getAvailablePremEcoSeats()+", 2 класса:"+aviaLine.getAvailableBusinessSeats()+", 1 класса:"+ aviaLine.getAvailableFirstSeats();
                seatsPrice = v.seatsPrice.getText().toString() +"4 класс:"+ aviaLine.getEcoSeatPrice()+", 3 класс:"+aviaLine.getPremEcoSeatPrice()+
                ", 2 класс:"+ aviaLine.getBusinessSeatPrice()+", 1 класс: "+aviaLine.getFirstSeatPrice();
                v.lineNumber.setText(lineNumber);
                v.crews.setText(crews);
                v.planeType.setText(planeType);
                v.startDate.setText(startDate);
                v.availableSeats.setText(availableSeats);
                v.seatsPrice.setText(seatsPrice);
    }

    @Override
    public int getItemCount() {
        return aviaLines.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView lineNumber,crews,planeType, startDate,availableSeats, seatsPrice;

        public ViewHolder(@NonNull @NotNull View v) {
            super(v);
            lineNumber = v.findViewById(R.id.line_name_txt);
            crews = v.findViewById(R.id.crews_txt);
            planeType =v.findViewById(R.id.airplane_type_txt);
            startDate = v.findViewById(R.id.start_date_txt);
            availableSeats = v.findViewById(R.id.available_seats_txt);
            seatsPrice = v.findViewById(R.id.seats_price_txt);
        }
    }
}
