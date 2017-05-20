package com.meditec.resources;

import java.util.ArrayList;

public class Chat {

    private static ArrayList<String> list = new ArrayList<>();

    public Chat(ArrayList<String> list){
        this.list = list;
    }

    public static ArrayList<String> getList(){
        return list;
    }
}
