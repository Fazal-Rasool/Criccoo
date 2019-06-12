package com.salamlahore.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RM_WinnerPredictionNew {


    @SerializedName("error")
    @Expose
    private boolean error;


    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("team_1")
    @Expose
    private Data team_1;

    @SerializedName("team_2")
    @Expose
    private Data team_2;


    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getTeam_1() {
        return team_1;
    }

    public void setTeam_1(Data team_1) {
        this.team_1 = team_1;
    }

    public Data getTeam_2() {
        return team_2;
    }

    public void setTeam_2(Data team_2) {
        this.team_2 = team_2;
    }

    public class Data{
        @SerializedName("prediction")
        @Expose
        private String prediction;

        @SerializedName("total_count")
        @Expose
        private String total_count;

        public String getPrediction() {
            return prediction;
        }

        public void setPrediction(String prediction) {
            this.prediction = prediction;
        }

        public String getTotal_count() {
            return total_count;
        }

        public void setTotal_count(String total_count) {
            this.total_count = total_count;
        }
    }
}
