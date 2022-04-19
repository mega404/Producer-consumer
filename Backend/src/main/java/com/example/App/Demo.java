package com.example.App;

import com.example.organize.Organize;
import java.util.*;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Demo {

    public static int numberOfThreads;
    public static List<Thread> threads = new LinkedList<Thread>();

    public static void Main() {
        Map<String, ArrayList<String>> Mymap = readFromJson();
        Organize.data = Mymap;
        Organize.separate();
        int numberOfThreads = Organize.numberOfMachines;
        int numberOfQueues = Organize.numberOfQueues;

        ///////// !/????????????????????? The last one;
        numberOfQueues++;
        System.out.println("Number Of machines " + numberOfThreads);
        System.out.println("Number of Queues " + numberOfQueues);
        Random random = new Random();
        int minSeconds = 4;
        int maxSeconds = 7;

        for (Map.Entry<String, ArrayList<String>> entry : Organize.mapMachines.entrySet()) {
            threads.add(new Thread(new Machine(Organize.getTakeQueues(entry.getKey()),
                    Organize.getPutQueue(entry.getKey()), (random.nextInt(maxSeconds
                            - minSeconds + 1) + minSeconds) * 1000),
                    entry.getKey()));
        }
        for (int i = 0; i < numberOfThreads; i++)
            threads.get(i).start();

        // for (int i = 0; i < numberOfThreads; i++)
        //     try {
        //         threads.get(i).join();
        //         System.out.println("***************");
        //     } catch (InterruptedException e) {
        //         // TODO Auto-generated catch block
        //         e.printStackTrace();
        //     }

    }

    public static Map<String, ArrayList<String>> readFromJson() {
        ObjectMapper mapper = new ObjectMapper();
        String path = "data.json";
        TypeReference<Map<String, ArrayList<String>>> typeReference = new TypeReference<Map<String, ArrayList<String>>>() {
        };
        Map<String, ArrayList<String>> hMap = new HashMap<String, ArrayList<String>>();
        File file = new File(path);
        try {
            hMap = mapper.readValue(file, typeReference);
            print(hMap);
            return hMap;
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void print(Map<String, ArrayList<String>> map) {
        for (Map.Entry<String, ArrayList<String>> entry : map.entrySet()) {
            System.out.println("Key = " + entry.getKey() +
                    ", Value = " + entry.getValue());
        }
    }

}
/*
 * for (Map.Entry<String , ArrayList<String>> map: System.mapQueues.entrySet())
 * {
 * List<String > list = System.getTakeQueues(map.getKey());
 * List<Queue<Integer>> queues1 = new ArrayList<>();
 * //for (int i=0; i<list.size(); i++)
 * 
 * /*Thread thread = new Thread(new Machine(System.getTakeQueues(map.getKey()),
 * System.getPutQueues(map.getKey()), (random.nextInt(maxSeconds - minSeconds +
 * 1)) * 1000, 1+1+""));
 * }
 */
/*
 * Thread prodThread1 = new Thread(new Machine(take, put, (random.nextInt(max -
 * min + 1) + min) * 1000), "1");
 * Thread prodThread2 = new Thread(new Machine(take, put, (random.nextInt(max -
 * min + 1) + min) * 1000), "2");
 * Thread prodThread3 = new Thread(new Machine(take, put, (random.nextInt(max -
 * min + 1) + min) * 1000), "3");
 * Thread prodThread4 = new Thread(new Machine(put, finalQ, (random.nextInt(max
 * - min + 1) + min) * 1000), "4");
 * Thread prodThread5 = new Thread(new Machine(put, finalQ, (random.nextInt(max
 * - min + 1) + min) * 1000), "5");
 * prodThread1.start();
 * prodThread2.start();
 * prodThread3.start();
 * prodThread4.start();
 * prodThread5.start();
 */

/*
 * Queue<Integer>[] take = new Queue[20];
 * for (int i = 0; i < 20; i++) {
 * take[i] = new LinkedList<Integer>();
 * }
 * take[0].add(1);
 * take[0].add(2);
 * take[0].add(3);
 * take[0].add(4);
 * 
 * take[1].add(10);
 * take[1].add(20);
 * take[1].add(30);
 * take[1].add(40);
 * 
 * take[2].add(50);
 * take[2].add(60);
 * take[2].add(70);
 * take[2].add(80);
 */

/*
 * Queue<Integer>[] put = new Queue[20];
 * for (int i = 0; i < 20; i++)
 * put[i] = new LinkedList<Integer>();
 * Queue<Integer>[] finalQ = new Queue[20];
 * for (int i = 0; i < 20; i++)
 * finalQ[i] = new LinkedList<Integer>();
 */
/*
 * for (int i=0; i<numberOfQueues; i++) {
 * queues.add(new ArrayList<>());
 * }
 */
/*
 * for (int i=0; i<numberOfQueues; i++) {
 * for (int j=0; j<numberOfQueues; j++)
 * queues.get(j).add(new LinkedList<>());
 * }
 */
// take /* Fake data*/
/*
 * for (int i=0; i<3; i++)
 * for (int j=1; j<13; j++)
 * queues.get(0).add(j);
 */
/*
 * The input of the first 3 threads is first queue and
 * output is the second queue
 */
/*
 * for (int i=0; i<numberOfThreads-2; i++){
 * threads.add(new Thread(new Machine(queues, queues, (random.nextInt(maxSeconds
 * - minSeconds + 1)) * 1000), i+1+""));
 * }
 * /*
 * The input of the next 2 threads is second queue and
 * output is the third queue
 */
/*
 * for (int i=3; i<numberOfThreads; i++){
 * threads.add(new Thread(new Machine(queues, queues, (random.nextInt(maxSeconds
 * - minSeconds + 1)) * 1000), i+1+""));
 * }
 * for (int i=0; i<numberOfThreads; i++)
 * threads.get(i).start();
 */
//
/*


 */