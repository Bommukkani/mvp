
package com.example.application.model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("description")
    public String description;
    @SerializedName("name")
    public String name;
    @SerializedName("full_name")
    public String full_name;

    @SerializedName("id")
    public String id;
    @SerializedName("node_id")
    public String node_id;


    @SerializedName("owner")
    public owner owner;

    public class owner {
        @SerializedName("avatar_url")
        public String avatar_url;

        @SerializedName("type")
        public String type;

    }


}
