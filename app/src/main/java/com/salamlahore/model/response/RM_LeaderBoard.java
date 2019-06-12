package com.salamlahore.model.response;

import java.util.ArrayList;

public class RM_LeaderBoard {


    public ArrayList<user> user;
    public ArrayList<all_users> all_users;

    public class user{
        public String user_id = "";
        public String total_score = "";
        public String rank="";
    }


    public class all_users {
        public boolean error = false;
        public String u_username = "";
        public String name = "";
        public String total_score = "";
    }

}
