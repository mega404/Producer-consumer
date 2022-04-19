package com.example.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.example.organize.Organize;
import com.example.App.Control;
import com.example.App.*;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class Service {

    public static List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @PostMapping("/map")
    public void GetAll(@RequestBody Map<String, ArrayList<String>> map) {
        Dummy(map); // remove it
        Control.Start(map);

    }

    @PostMapping("/stop")
    public void stop() {
    	Control.stop();
    }

    @PostMapping("/new")
    public void newSimulation() {
        System.out.println("in new!");
        Control.newSim();

    }

    @PostMapping("/items")
    public void numberOfItems(@RequestBody int number) {
        System.out.println("in numberOfItems!");
        Organize.numberOfItems = number;

    }

    @PostMapping("replay")
    public void replay() {
        System.out.println("in replay");
        Control.replayControl();

    }

    @RequestMapping(value = "/update", consumes = MediaType.ALL_VALUE)
    public SseEmitter update() {
        System.out.println("IN UPDATE");
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        try {
            sseEmitter.send(sseEmitter.event().name("INIT"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        sseEmitter.onCompletion(() -> emitters.remove(sseEmitter));
        emitters.add(sseEmitter);

        return sseEmitter;
    }

    public static void disPatch(String[] res) {

        System.out.println("Sent data!  " + res[0] + " " + res[1] + " " + res[2] + " " + res[3] + "\n");

        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event().name("last").data(res));
                emitter.send(SseEmitter.event().data(res));
            } catch (IOException e) {
                emitters.remove(emitter);
            }
        }
    }

    public void Dummy(Map<String, ArrayList<String>> map) {

        String filePath = "data.json";
        for (Map.Entry<String, ArrayList<String>> entry : map.entrySet()) {
            System.out.println("Key = " + entry.getKey() +
                    ", Value = " + entry.getValue());
        }

        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            File file = new File(filePath);
            mapper.configure(SerializationFeature.INDENT_OUTPUT, true).writeValue(file, map);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


	/*
	data = new HashMap<String, ArrayList<String>>();
    mapQueues = new HashMap<String, ArrayList<String>>();
    mapMachines = new HashMap<String, ArrayList<String>>();
    numberOfQueues = 0;
    numberOfMachines = 0;
    numberOfItems = 0;
    LastQ = "";
    allQ = new HashMap<String, Queue<String>>();
    */
