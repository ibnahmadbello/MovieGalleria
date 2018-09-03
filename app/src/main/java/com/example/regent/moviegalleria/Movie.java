package com.example.regent.moviegalleria;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable{

    private String movieName;
    private String image;
    private String overView;
    private String rating;
    private String date;

    public String getMovieName() {
        return movieName;
    }

    public Movie(){}

    public Movie(String movieName, String image, String overView, String rating, String date){
        this.movieName = movieName;
        this.image = image;
        this.overView = overView;
        this.rating = rating;
        this.date = date;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString(){
        return movieName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(movieName);
        parcel.writeString(image);
        parcel.writeString(overView);
        parcel.writeString(rating);
        parcel.writeString(date);

    }

    public static final Parcelable.Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }
    };

    public Movie(Parcel parcel){
        movieName = parcel.readString();
        image = parcel.readString();
        overView = parcel.readString();
        rating = parcel.readString();
        date = parcel.readString();
    }
}
