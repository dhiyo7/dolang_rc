package com.example.dolang.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ModelKomentar implements Parcelable {
    @SerializedName("name") private String name;
    @SerializedName("message") private String messages;
    @SerializedName("tour_id") private int tour_id;
    @SerializedName("api_token") private String api_token;

    public ModelKomentar() {
    }

    public ModelKomentar(String name, String messages, int tour_id, String api_token) {
        this.name = name;
        this.messages = messages;
        this.tour_id = tour_id;
        this.api_token = api_token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public int getTour_id() {
        return tour_id;
    }

    public void setTour_id(int tour_id) {
        this.tour_id = tour_id;
    }

    public String getApi_token() {
        return api_token;
    }

    public void setApi_token(String api_token) {
        this.api_token = api_token;
    }

    protected ModelKomentar(Parcel in) {
        name = in.readString();
        messages = in.readString();
        tour_id = in.readInt();
        api_token = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(messages);
        dest.writeInt(tour_id);
        dest.writeString(api_token);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ModelKomentar> CREATOR = new Parcelable.Creator<ModelKomentar>() {
        @Override
        public ModelKomentar createFromParcel(Parcel in) {
            return new ModelKomentar(in);
        }

        @Override
        public ModelKomentar[] newArray(int size) {
            return new ModelKomentar[size];
        }
    };
}