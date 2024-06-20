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

public class FragmentAdapter extends RecyclerView.Adapter<FragmentAdapter.ViewHolder> {
    private OnCheckedListener callback;
    private ArrayList<String> classes;
    int checkedPosition = -1;
    public FragmentAdapter(ArrayList<String> classes, OnCheckedListener callback) {
        this.callback = callback;
        this.classes = classes;
    }
    @NonNull
    @NotNull
    @Override
    public FragmentAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.check_boxes_item, viewGroup, false);

        return new  ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder v, int i) {
        String seatClass  = classes.get(i);
        v.checkBox.setText(String.valueOf(i));

        if (!v.checkBox.isChecked()) {
            callback.onSelectedClass(seatClass,i, v.checkBox.isChecked()  );
        }

        v.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(checkedPosition!= v.getAbsoluteAdapterPosition()) {
                checkedPosition = v.getAbsoluteAdapterPosition();
                notifyDataSetChanged();
                callback.onSelectedClass(seatClass,v.getAbsoluteAdapterPosition(),isChecked);
            }else{
                checkedPosition = -1;
                callback.onSelectedClass(seatClass, v.getAbsoluteAdapterPosition(), isChecked);
                notifyDataSetChanged();
            }
        });
        v.textView.setText(seatClass);


    }

    @Override
    public int getItemCount() {
        return classes.size();
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
