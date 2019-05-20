package com.adaxiom.model.response;

public class RM_WinnerPrediction {
    public boolean error = false;
    public String message="";
    public Data team_1;
    public Data team_2;

    public class Data{
        public String prediction="";
        public String total_count="";
    }

//    public class teamTwo{
//        public String prediction="";
//        public String total_count="";
//    }
}
