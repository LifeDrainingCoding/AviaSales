package com.pracktic.aviasales;


import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Consts {
    public static final String USERS_DIR = "/users";
    static Consts instance;
    private String userName;
    private static final String TAG = "RecViewAdapterForListTabs";
    public static final String TYPE_COMMON = "Обычный";
    public static final String TYPE_ADVANCED = "Повышенной грузоподъемности";
    public static final int CHECKBOX_ACTION = 1;
    public static final int LIST_ACTION = 3;


    private String gender, category;
    public void setCategory(String category) {
        this.category = category;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCategory() {
        return category;
    }

    public String getGender() {
        return gender;
    }


    private Consts() {
    }


    static Consts getInstance() {
        if (instance == null) {
            instance = new Consts();
        }
        return instance;
    }



    void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
    public String getUserDir(){
        return USERS_DIR+"/"+userName;
    }
    public DatabaseReference getAviaLines(){
        return FirebaseDatabase.getInstance().getReference().child("avia_lines");
    }




        public String phoneticTranscriber (String s){
            String[] parts = s.split("");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < parts.length; i++) {
                switch (parts[i]) {
                    case "а":
                        parts[i] = "a";
                        break;
                    case "б":
                        parts[i] = "b";
                        break;
                    case "в":
                        parts[i] = "v";
                        break;
                    case "г":
                        parts[i] = "g";
                        break;
                    case "д":
                        parts[i] = "d";
                        break;
                    case "е":
                        parts[i] = "e";
                        break;
                    case "ё":
                        parts[i] = "e";
                        break;
                    case "ж":
                        parts[i] = "shz";
                        break;
                    case "з":
                        parts[i] = "z";
                        break;
                    case "и":
                        parts[i] = "i";
                        break;
                    case "й":
                        parts[i] = "i";
                        break;
                    case "ф":
                        parts[i] = "f";
                        break;
                    case "к":
                        parts[i] = "ck";
                        break;
                    case "л":
                        parts[i] = "l";
                        break;
                    case "м":
                        parts[i] = "m";
                        break;
                    case "н":
                        parts[i] = "n";
                        break;
                    case "о":
                        parts[i] = "o";
                        break;
                    case "п":
                        parts[i] = "p";
                        break;
                    case "р":
                        parts[i] = "r";
                        break;
                    case "с":
                        parts[i] = "s";
                        break;
                    case "т":
                        parts[i] = "t";
                        break;
                    case "щ":
                        parts[i] = "shch";
                        break;
                    case "ш":
                        parts[i] = "sh";
                        break;
                    case "ч":
                        parts[i] = "ch";
                        break;
                    case "э":
                        parts[i] = "e";
                        break;
                    case "ю":
                        parts[i] = "yu";
                        break;
                    case "я":
                        parts[i] = "ya";
                        break;
                    case "ь":
                        parts[i] = "";
                        break;
                    case "х":
                        parts[i] = "h";
                        break;
                    case "ъ":
                        parts[i] = "";
                        break;
                    case "у":
                        parts[i] = "u";
                        break;
                    case "ц":
                        parts[i] = "ts";
                        break;
                    case "ы":
                        parts[i] = "i";
                        break;
                    case "А":
                        parts[i] = "a";
                        break;
                    case "Б":
                        parts[i] = "b";
                        break;
                    case "В":
                        parts[i] = "v";
                        break;
                    case "Г":
                        parts[i] = "g";
                        break;
                    case "Д":
                        parts[i] = "d";
                        break;
                    case "Е":
                        parts[i] = "e";
                        break;
                    case "Ё":
                        parts[i] = "e";
                        break;
                    case "Ж":
                        parts[i] = "shz";
                        break;
                    case "З":
                        parts[i] = "z";
                        break;
                    case "И":
                        parts[i] = "i";
                        break;
                    case "Й":
                        parts[i] = "y";
                        break;
                    case "Ф":
                        parts[i] = "f";
                        break;
                    case "К":
                        parts[i] = "ck";
                        break;
                    case "Л":
                        parts[i] = "l";
                        break;
                    case "М":
                        parts[i] = "m";
                        break;
                    case "Н":
                        parts[i] = "n";
                        break;
                    case "О":
                        parts[i] = "o";
                        break;
                    case "П":
                        parts[i] = "p";
                        break;
                    case "Р":
                        parts[i] = "r";
                        break;
                    case "С":
                        parts[i] = "s";
                        break;
                    case "Т":
                        parts[i] = "t";
                        break;
                    case "Щ":
                        parts[i] = "shch";
                        break;
                    case "Ш":
                        parts[i] = "sh";
                        break;
                    case "Ч":
                        parts[i] = "ch";
                        break;
                    case "Э":
                        parts[i] = "e";
                        break;
                    case "Ю":
                        parts[i] = "yu";
                        break;
                    case "Я":
                        parts[i] = "ya";
                        break;
                    case "Ь":
                        parts[i] = "";
                        break;
                    case "Х":
                        parts[i] = "h";
                        break;
                    case "Ъ":
                        parts[i] = "";
                        break;
                    case "У":
                        parts[i] = "u";
                        break;
                    case "Ц":
                        parts[i] = "ts";
                        break;
                    case "Ы":
                        parts[i] = "i";
                        break;


                }


            }
            for (int i = 0; i < parts.length; i++) {
                sb.append(parts[i]);
            }

            return sb.toString();
        }
    public static boolean hasInvalidCharacters(String input) {

        String regex = "[^\\d,.]";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

    }
