package com.codexpert;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;

public class Questions implements Parcelable {
    String lienImage;
    ArrayList<String> question;
    ArrayList<String> reponses;
    int[] solutions;

    public Questions(ArrayList<String> question, ArrayList<String> reponses, int[] sol, String img) {
        this.lienImage = img;
        this.question = question;
        this.reponses = reponses;
        this.solutions = sol;
    }

    protected Questions(Parcel in) {
        lienImage = in.readString();
        question = in.createStringArrayList();
        reponses = in.createStringArrayList();
        solutions = in.createIntArray();
    }

    public static final Creator<Questions> CREATOR = new Creator<Questions>() {
        @Override
        public Questions createFromParcel(Parcel in) {
            return new Questions(in);
        }

        @Override
        public Questions[] newArray(int size) {
            return new Questions[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(lienImage);
        parcel.writeStringList(question);
        parcel.writeStringList(reponses);
        parcel.writeIntArray(solutions);
    }
}
