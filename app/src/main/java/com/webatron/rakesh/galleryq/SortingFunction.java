package com.webatron.rakesh.galleryq;

import java.util.Comparator;
import java.util.HashMap;

/**
 * Created by rakesh on 3/4/18.
 */

public class SortingFunction implements Comparator<HashMap<String,String>> {

    private final String key;
    private final String order;

    public SortingFunction(String key, String order) {
        this.key = key;
        this.order = order;
    }

    @Override
    public int compare(HashMap<String, String> first, HashMap<String, String> second) {
        String firstvalue = first.get(key);
        String secondvalue = second.get(key);

        if(this.order.toLowerCase().contentEquals("asc")){
            return  firstvalue.compareTo(secondvalue);
        }else
        return secondvalue.compareTo(firstvalue);
    }
}
