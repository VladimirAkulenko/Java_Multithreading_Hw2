package com.company;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {
    final static String TEMPLATE = "RLRFR";
    final static int INSTRUCTIONS = 100;
    final static int THREADS = 1000;

    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) {

        for (int i = 0; i < THREADS; i++) {
            new Thread(() ->{
                String route = generateRoute(TEMPLATE, INSTRUCTIONS);
                int frequency = (int) route.chars().filter(ch -> ch == 'R').count();
                synchronized (sizeToFreq){
                    sizeToFreq.put(frequency,sizeToFreq.containsKey(frequency) ? sizeToFreq.get(frequency) + 1 : 1);
                }
            }).start();
        }
        Map.Entry<Integer,Integer> max = sizeToFreq.entrySet().stream().max(Map.Entry.comparingByValue()).get();

        System.out.println("Самое частое количество повторений " + max.getKey() +
                " (встретилось " + max.getValue() + " раз)");

        System.out.println("Другие размеры: ");
        sizeToFreq.entrySet().stream().sorted(Map.Entry.comparingByValue())
                .forEach(e -> System.out.println(" - " + e.getKey() + " (" + e.getValue() + " раз)"));
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}
