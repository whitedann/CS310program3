package edu.sdsu.cs;

import edu.sdsu.cs.datastructures.DirectedGraph;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App
{
    public static void main( String[] args )
    {

/**
        DirectedGraph<String> test = new DirectedGraph<String>();

        String directory = "./";

        List<String> lines = new ArrayList<>();

        if(args.length > 0)
             directory = args[0];



        try {
            lines = Files.readAllLines(Paths.get(directory), Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(String line : lines){
            String[] tokens = line.split(",");

            if(tokens.length == 1 && !test.contains(tokens[0])) {
                test.add(tokens[0]);
            }
            if(tokens.length == 2){
                if(!test.contains(tokens[0]))
                    test.add(tokens[0]);
                if(!test.contains(tokens[1]))
                    test.add(tokens[1]);
                test.connect(tokens[0], tokens[1]);
            }

        }

        for(Object city : test.vertices()) {
            System.out.println("City: " + city.toString());
            System.out.println("\nNeighbors: ");
            for(Object neightbor : test.neighbors(city.toString()))
                System.out.println(neightbor.toString());
            System.out.println("\n");
        }

        **/

        DirectedGraph test = new DirectedGraph();
        Scanner input = new Scanner(System.in);
        test.add("A");
        test.add("B");
        test.add("C");
        test.add("D");
        test.connect("A", "B");
        test.connect("B", "C");
        test.connect("C", "D");
        test.connect("B", "D");
        System.out.println("Enter start city: ");
        String start = input.nextLine().trim();
        System.out.println("Enter end city: ");
        String dest = input.nextLine().trim();
        input.close();
        System.out.println("Shortest path is: ");



/**
        test.add("A");
        test.add("B");
        test.add("C");
        test.add("D");
        test.add("F");
        test.add("K");
        test.connect("A", "B");
        test.connect("B", "C");
        test.connect("C", "D");
        test.connect("B", "D");
        test.connect("F", "K");
 **/
    }
}
