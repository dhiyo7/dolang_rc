package com.example.dolang.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ModelKategori implements Parcelable {

    @SerializedName("id") private int Id;
    @SerializedName("title") private String title;
    @SerializedName("category") private String category;
    @SerializedName("description") private String description;
    @SerializedName("region") private String region;
    @SerializedName("price") private String price;
    @SerializedName("address") private String address;
    @SerializedName("operational") private String operational;
    @SerializedName("image") private String image;
    @SerializedName("longitude") private String longitude;
    @SerializedName("latitude") private String latitude;
    @SerializedName("comment") private List<ModelKomentar> comments;
    @SerializedName("panorama") private List<ModelKategori> panorama;
    @SerializedName("panorama1") private String panorama1;
    @SerializedName("panorama2") private String panorama2;
    @SerializedName("panorama3") private String panorama3;
    @SerializedName("name") private String name;
    @SerializedName("message") private String messages;
    @SerializedName("tour_id") private int tour_id;

    public ModelKategori() {
    }

    public ModelKategori(int id, String title, String category, String description, String region, String price, String address, String operational, String image, String longitude, String latitude, List<ModelKomentar> comments, List<ModelKategori> panorama, String panorama1, String panorama2, String panorama3, String name, String messages, int tour_id) {
        Id = id;
        this.title = title;
        this.category = category;
        this.description = description;
        this.region = region;
        this.price = price;
        this.address = address;
        this.operational = operational;
        this.image = image;
        this.longitude = longitude;
        this.latitude = latitude;
        this.comments = comments;
        this.panorama = panorama;
        this.panorama1 = panorama1;
        this.panorama2 = panorama2;
        this.panorama3 = panorama3;
        this.name = name;
        this.messages = messages;
        this.tour_id = tour_id;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOperational() {
        return operational;
    }

    public void setOperational(String operational) {
        this.operational = operational;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public List<ModelKomentar> getComments() {
        return comments;
    }

    public void setComments(List<ModelKomentar> comments) {
        this.comments = comments;
    }

    public List<ModelKategori> getPanorama() {
        return panorama;
    }

    public void setPanorama(List<ModelKategori> panorama) {
        this.panorama = panorama;
    }

    public String getPanorama1() {
        return panorama1;
    }

    public void setPanorama1(String panorama1) {
        this.panorama1 = panorama1;
    }

    public String getPanorama2() {
        return panorama2;
    }

    public void setPanorama2(String panorama2) {
        this.panorama2 = panorama2;
    }

    public String getPanorama3() {
        return panorama3;
    }

    public void setPanorama3(String panorama3) {
        this.panorama3 = panorama3;
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

    protected ModelKategori(Parcel in) {
        Id = in.readInt();
        title = in.readString();
        category = in.readString();
        description = in.readString();
        region = in.readString();
        price = in.readString();
        address = in.readString();
        operational = in.readString();
        image = in.readString();
        longitude = in.readString();
        latitude = in.readString();
        if (in.readByte() == 0x01) {
            comments = new ArrayList<ModelKomentar>();
            in.readList(comments, ModelKomentar.class.getClassLoader());
        } else {
            comments = null;
        }
        if (in.readByte() == 0x01) {
            panorama = new ArrayList<ModelKategori>();
            in.readList(panorama, ModelKategori.class.getClassLoader());
        } else {
            panorama = null;
        }
        panorama1 = in.readString();
        panorama2 = in.readString();
        panorama3 = in.readString();
        name = in.readString();
        messages = in.readString();
        tour_id = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Id);
        dest.writeString(title);
        dest.writeString(category);
        dest.writeString(description);
        dest.writeString(region);
        dest.writeString(price);
        dest.writeString(address);
        dest.writeString(operational);
        dest.writeString(image);
        dest.writeString(longitude);
        dest.writeString(latitude);
        if (comments == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(comments);
        }
        if (panorama == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(panorama);
        }
        dest.writeString(panorama1);
        dest.writeString(panorama2);
        dest.writeString(panorama3);
        dest.writeString(name);
        dest.writeString(messages);
        dest.writeInt(tour_id);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ModelKategori> CREATOR = new Parcelable.Creator<ModelKategori>() {
        @Override
        public ModelKategori createFromParcel(Parcel in) {
            return new ModelKategori(in);
        }

        @Override
        public ModelKategori[] newArray(int size) {
            return new ModelKategori[size];
        }
    };
}