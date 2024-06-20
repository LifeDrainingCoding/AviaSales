package com.pracktic.aviasales;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class DeleteAdapter extends RecyclerView.Adapter<DeleteAdapter.ViewHolder> {
    private OnCheckedListener callback;
    private ArrayList<AviaLine> aviaLines;
    public DeleteAdapter(ArrayList<AviaLine> aviaLines, OnCheckedListener callback) {
        this.callback = callback;
        this.aviaLines = aviaLines;
    }
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.check_boxes_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DeleteAdapter.ViewHolder v, int i) {
        AviaLine aviaLine = aviaLines.get(i);
        v.checkBox.setText(String.valueOf(i));
        v.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            callback.onChecked(aviaLine, v.getAbsoluteAdapterPosition(), isChecked);

        });
        v.textView.setText(String.valueOf(aviaLine.getAviaLineId()));


    }

    @Override
    public int getItemCount() {
        return aviaLines.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView textView;
        public ViewHolder(@NonNull @NotNull View v) {
            super(v);
            checkBox = v.findViewById(R.id.checkboxId);
            textView = v.findViewById(R.id.avialine_name_txt);
        }
    }
}
