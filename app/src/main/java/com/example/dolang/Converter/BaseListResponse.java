package com.example.dolang.Converter;

import com.google.gson.annotations.SerializedName;
import java.util.List;


//we need this class because response from the server is wrapped
public class BaseListResponse<T> {
    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private Boolean status;
    @SerializedName("data")
    private List<T> data;

    public BaseListResponse() {
        //empty constructor is a must
    }

    public BaseListResponse(String message, Boolean status, List<T> data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
