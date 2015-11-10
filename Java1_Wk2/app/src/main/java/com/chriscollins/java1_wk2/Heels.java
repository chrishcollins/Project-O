package com.chriscollins.java1_wk2;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by chriscollins on 11/9/15.
 */
public class Heels {

    public static void main(String args[]) {

        /*Declare hashmap */
        HashMap<String, Integer> hash = new HashMap<String, Integer>();

        /*Add to hashmap*/
        hash.put("Michael Jordan", 23);
        hash.put("Sam Perkins", 41);
        hash.put("George Lynch", 34);
        hash.put("Eric Montross", 00);
        hash.put("Jerry Stackhouse", 42);
        hash.put("Tyler Hansbrough", 50);
        hash.put("Harrison Barnes", 40);

        Set playerSet = hash.entrySet();
        Iterator iterator = playerSet.iterator();

        while (iterator.hasNext()) {
            Map.Entry mapEntry = (Map.Entry)iterator.next();
            System.out.print("Player " + mapEntry.getValue());
        }
    }


}
