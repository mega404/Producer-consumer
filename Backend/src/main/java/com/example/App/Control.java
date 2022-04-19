package com.example.App;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

import com.example.Services.Service;
import com.example.SnapShot.CareTaker;
import com.example.SnapShot.SnapShot;
import com.example.organize.Organize;

public class Control {
    public static List<Thread> threads = new LinkedList<Thread>();
    static int numberOfThreads;

    public static void Start(Map<String, ArrayList<String>> map) {
        Organize.data = map;
        Organize.separate();
        int numberOfThreads = Organize.numberOfMachines;
        int numberOfQueues = Organize.numberOfQueues;

        numberOfQueues++;
        System.out.println("Number Of machines " + numberOfThreads);
        System.out.println("Number of Queues " + numberOfQueues);

        Random random = new Random();
        int minSeconds = 2;
        int maxSeconds = 7;

        for (Map.Entry<String, ArrayList<String>> entry : Organize.mapMachines.entrySet()) {
            threads.add(new Thread(new Machine(Organize.getTakeQueues(entry.getKey()),
                    Organize.getPutQueue(entry.getKey()), (random.nextInt(maxSeconds
                            - minSeconds + 1) + minSeconds) * 1000),
                    entry.getKey()));
        }

        for (int i = 0; i < numberOfThreads; i++)
            threads.get(i).start();
        
        for (int i = 0; i < numberOfThreads; i++)
            try {
                threads.get(i).join();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }


    public static void newSim() {
        System.out.println("Clearing Control");
        numberOfThreads = 0;
        threads = new LinkedList<>() ;
        CareTaker.clear();
        Organize.clear();

    }

    public static void replayControl() {
        Queue<SnapShot> queue = CareTaker.new_queue();
        while (queue.size() != 1) {
            // System.out.println(queue.size());
            SnapShot snap = queue.remove();

            String res[] = { snap.getFrom(), snap.getTo(), snap.getItem(), Integer.toString(snap.getDuration()) };
            System.out.println("Replay data!  " + res[0] + " " + res[1] + " " + res[2] + " " + res[3] + "\n");
            Service.disPatch(res);
            SnapShot second = queue.peek();
            // System.out.println(queue.size());
            try {

                Thread.sleep((second.getTime() - snap.getTime()));

            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        // System.out.println("*********************");
        // System.out.println(queue.size());
        SnapShot snap = queue.remove();

        String res[] = { snap.getFrom(), snap.getTo(), snap.getItem(), Integer.toString(snap.getDuration()) };
        System.out.println("Replay data!  " + res[0] + " " + res[1] + " " + res[2] + " " + res[3] + "\n");
        Service.disPatch(res);

        System.out.println("Finished replay!");

    }
    
    public static void stop() {
    	System.out.println("Clearing Control");
        numberOfThreads = 0;
        for (int i = 0; i < Control.threads.size(); i++) {
            try {
                System.out.println(Thread.activeCount());
                Control.threads.get(i).interrupt();

             } catch (Exception e) {
                System.out.println("Destroying!!!!!!");
               System.out.println(e.getMessage());
            }
    	
        }
        threads = new LinkedList<>() ;
        Organize.clear();

    }

}


/*
java.lang.NullPointerException: Cannot invoke "java.util.Queue.isEmpty()" because the return value of "java.util.Map.get(Object)" is null
at com.example.App.Machine.check(ProducerConsumer.java:32)
at com.example.App.Machine.consume(ProducerConsumer.java:46)
at com.example.App.Machine.run(ProducerConsumer.java:80)
at java.base/java.lang.Thread.run(Thread.java:833)
*/

