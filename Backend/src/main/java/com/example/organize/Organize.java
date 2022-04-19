package com.example.organize;

import java.util.*;

public class Organize {
    public static Map<String, ArrayList<String>> data = new HashMap<String, ArrayList<String>>();
    public static Map<String, ArrayList<String>> mapQueues = new HashMap<String, ArrayList<String>>();
    public static Map<String, ArrayList<String>> mapMachines = new HashMap<String, ArrayList<String>>();

    public static int numberOfQueues, numberOfMachines;
    public static int numberOfItems;
    public static String LastQ;
    //
    public static Map<String, Queue<String>> allQ = new HashMap<String, Queue<String>>();
    //

    public static void separate() {
        for (Map.Entry<String, ArrayList<String>> data : data.entrySet()) {
            if (data.getKey().contains("Q")) {
                mapQueues.put(data.getKey(), data.getValue());
                allQ.put(data.getKey(), new LinkedList<String>());
            } else if (data.getKey().contains("M")) {
                mapMachines.put(data.getKey(), data.getValue());
            }
        }
        numberOfMachines = mapMachines.size();
        numberOfQueues = mapQueues.size();

        int qnumber = numberOfQueues + 1;
        LastQ = "Q" + qnumber;
        System.out.println(LastQ);
        allQ.put(LastQ, new LinkedList<String>());
        //numberOfItems = 2;
        getRandomColor(numberOfItems);

        System.out.println("Map of machines");
        print(mapMachines);
        System.out.println("\nMap of queues");
        print(mapQueues);
        System.out.println("\nMap of allQ");
        printAllQ();

    }

    public static void clear() {
        System.out.println("Clearing Organize");
        data = new HashMap<String, ArrayList<String>>();
        mapQueues = new HashMap<String, ArrayList<String>>();
        mapMachines = new HashMap<String, ArrayList<String>>();
        numberOfQueues = 0;
        numberOfMachines = 0;
        numberOfItems = 0;
        LastQ = "";
        allQ = new HashMap<String, Queue<String>>();
    }

    private static void getRandomColor(int n) {
        String letters[] = "0123456789ABCDEF".split("");
        String color = "#";
        for (int j = 0; j < n; j++) {
            color = "#";
            for (int i = 0; i < 6; i++) {
                color += letters[(int) Math.round(Math.random() * 15)];

            }
            allQ.get("Q1").add(color);
        }
    }

    public static List<String> getTakeQueues(String machine) {
        System.out.print("Getting take queues of " + machine + " ");
        List<String> list = new ArrayList<String>();
        for (Map.Entry<String, ArrayList<String>> data : mapQueues.entrySet()) {
            for (int i = 0; i < data.getValue().size(); i++) {
                if (data.getValue().get(i).contains(machine)) {
                    // String t = data.getKey().replace('Q', '0');
                    // queues.add(data.getKey());
                    System.out.print(data.getKey() + " ");
                    list.add(data.getKey());
                }
            }
        }
        System.out.println();
        return list;
    }

    public static String getPutQueue(String machine) {
        System.out.print("Getting put queue of " + machine + " ");
        String list = "";
        for (Map.Entry<String, ArrayList<String>> data : mapMachines.entrySet()) {
            if (data.getKey().equals(machine)) {
                System.out.println(data.getValue().get(0));
                list = data.getValue().get(0);
                break;
            }
        }
        return list;
    }

    public static void print(Map<String, ArrayList<String>> map) {
        for (Map.Entry<String, ArrayList<String>> entry : map.entrySet()) {
            System.out.println("Key = " + entry.getKey() +
                    ", Value = " + entry.getValue());
        }
    }

    public static void printAllQ() {

        for (Map.Entry<String, Queue<String>> entry : allQ.entrySet()) {
            System.out.println("Key = " + entry.getKey() +
                    ", Value = " + entry.getValue());
        }
    }
}
