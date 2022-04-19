package com.example.App;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.example.Services.Service;
import com.example.SnapShot.CareTaker;
import com.example.SnapShot.SnapShot;
import com.example.organize.Organize;

class Machine implements Runnable {

    List<String> takeNotify;
    List<String> take;
    String put;
    int time;
    int index;
//    public static boolean dispatch = true;
    Queue<String> current = new LinkedList<>();
    String TakeQ;

    public Machine(List<String> take, String put, int time) {
        this.take = take;
        this.put = put;
        this.time = time;
    }

    public boolean check() {
        for (int i = 0; i < take.size(); i++) {
        	try {
        		if (!(Organize.allQ.get(take.get(i)).isEmpty())) {
                    current = Organize.allQ.get(take.get(i));
                    TakeQ = take.get(i);
                    index = i;
                    return true;
                }
        	}catch(Exception e){
        	}
        }
        return false;
    }

    // private CareTaker careTaker = new CareTaker();
    private String consume() throws InterruptedException {
        // wait if the queue is empty
        // this.lastQueue();
        while (!check()) {
            synchronized (Organize.allQ) {
                Organize.allQ.wait();
            }
        }
        // Otherwise consume element and notify the waiting producer
        synchronized (this) {
            String item = current.remove();

            System.out
                    .println(
                            "Consumed: " + item + ":by thread:" + Thread.currentThread().getName() + " it's time = "
                                    + time
                                    + " Time now "
                                    + LocalTime.now());

            String currentConsume[] = { TakeQ, Thread.currentThread().getName(), item, Integer.toString(time) };
            //if(dispatch)
            Service.disPatch(currentConsume);
            SnapShot snap = new SnapShot(item, TakeQ, Thread.currentThread().getName(), time,
                    System.currentTimeMillis());
            // snap.print();
            CareTaker careTaker = new CareTaker();
            careTaker.saveSnapShot(snap);
            // this.lastQueue();
            Thread.sleep(time);
            return item;
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                String item = consume();
                produce(item);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void produce(String item) throws InterruptedException {
        // producing element and notify consumers
        synchronized (Organize.allQ) {
            // this.lastQueue();
            Organize.allQ.get(put).add(item);
            System.out
                    .println("Produced:" + item + " by thread:" + Thread.currentThread().getName() + " Time now "
                            + LocalTime.now());
            Organize.printAllQ();
            String[] currentProduce = { Thread.currentThread().getName(), put, "", "" };
            //if (dispatch)
            Service.disPatch(currentProduce);
            // System.out.println(Organize.LastQ);
            // System.out.println(Organize.allQ.get(Organize.LastQ).size());

            SnapShot snap = new SnapShot("", Thread.currentThread().getName(), put, 0,
                    System.currentTimeMillis());
            // snap.print();
            CareTaker careTaker = new CareTaker();
            careTaker.saveSnapShot(snap);

            // System.out.println(Organize.allQ.get(Organize.LastQ).size() ==
            // Organize.numberOfItems);
            // Control.replayControl();

            // this.lastQueue();
            Organize.allQ.notifyAll();
        }
    }

    // private void lastQueue() {
    // String lastQueue = "Q"+Organize.numberOfQueues+1;
    // if (put.equals(lastQueue)) {
    // System.out.println("yeeeeeeeeeeeeeeeeeeeeeees");
    // try {
    // Organize.allQ.get(lastQueue).remove();
    // }catch (Exception e){
    // System.out.println();
    // };
    // }
    // }

}
/*
 * private void checkThread() {
 * if (Thread.currentThread().getName().equals("M1")) {
 * System.out.println("Thread M1 is alive: ");
 * }
 * if (Thread.currentThread().getName().equals("M2")) {
 * System.out.println("Thread M2 is alive: ");
 * }
 * if (Thread.currentThread().getName().equals("M3")) {
 * System.out.println("Thread M3 is alive: ");
 * }
 * if (Thread.currentThread().getName().equals("M4")) {
 * System.out.println("Thread M4 is alive: ");
 * }
 * if (Thread.currentThread().getName().equals("M5")) {
 * System.out.println("Thread M5 is alive: ");
 * }
 * }
 */

/*
 * private static boolean f = true;
 * private static String pre = "";
 * private void changeState() throws Exception{
 * if(f)
 * {
 * pre = Thread.currentThread().getName();
 * f = false;
 * }
 * while((Thread.currentThread().getName().equals(pre))) {
 * wait();
 * }
 * pre = Thread.currentThread().getName();
 * f = true;
 * }
 */