package com.example.SnapShot;

import java.util.*;

public class CareTaker {
    public static List<SnapShot> list = new ArrayList<>();
    //private static LinkedHashMap<Integer, SnapShot> hashMap = new LinkedHashMap<>();
    // private static Queue<LinkedHashMap<Integer, SnapShot>> queue = new
    // LinkedList<>();
    public static Queue<SnapShot> queue = new LinkedList<>();

    public void saveSnapShot(SnapShot s) {
        // hashMap.put(hashMap.size(), s);
        queue.add(s);
        list.add(s);
        System.out.println("Item: " + s.getItem() + " Saved");
        System.out.println("All: " + queue.size());

        System.out.println("End\n");
    }

    public static void clear(){
        System.out.println("Clearing CareTaker");
        list.clear();
        queue.clear();
    }

    public SnapShot get(int index) {

        return list.get(index);
    }
    
    public static Queue<SnapShot> new_queue() {
    	Queue<SnapShot> q = new LinkedList<>();
    	q.addAll(queue);

    	return q;
    }
}