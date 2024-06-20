package com.pracktic.aviasales;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectClassFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectClassFragment extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnCheckedListener callback;
    private RecyclerView recyclerView;
    private MaterialButton closeButton;
    private onDissMissList list;
    private static final ArrayList<String> classes = new ArrayList<String>(){{
        add("4 класс");
        add("3 класс");
        add("2 класс");
        add("1 класс");
    }};

    public SelectClassFragment() {
        // Required empty public constructor
    }
    public SelectClassFragment(OnCheckedListener callback, onDissMissList list){
        this.callback = callback;
        this.list = list;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelectClassFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectClassFragment newInstance(String param1, String param2) {
        SelectClassFragment fragment = new SelectClassFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onDismiss(@NonNull @NotNull DialogInterface dialog) {
        super.onDismiss(dialog);
        list.onDissMiss();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_select_class, container, false);
        recyclerView = v.findViewById(R.id.list_classes);
        FragmentAdapter adapter =  new FragmentAdapter(classes, callback);
        recyclerView.setAdapter(adapter);
        closeButton =  v.findViewById(R.id.close_btn);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return  v;
    }
}