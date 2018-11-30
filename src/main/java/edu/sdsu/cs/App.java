/** Daniel White (CSSC0721) Mario Shamhon (CSSC0781) **/
/** CS310 Fall 2018, Shawn Healey **/

package edu.sdsu.cs;

import edu.sdsu.cs.datastructures.DirectedGraph;
import edu.sdsu.cs.datastructures.IGraph;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class App
{
    public static void main( String[] args )
    {

        /** Initialize the Graph **/
        IGraph test = new DirectedGraph<>();

        /** Establish input Directory **/
        String directory = "./src/layout.csv";
        if(args.length > 0)
             directory = args[0];

        /** Break up input file into lines **/
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(directory),
                    Charset.defaultCharset());
        } catch (IOException e) {
            System.out.println("Error: Unable to open " + directory +
                    ". Verify the file exists, is accessible, " +
                    "and meets the syntax requirements.");
            return;
        }

        /** Builds the graph from the lines **/
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

        /** Prints out the graph **/
        System.out.println("Building a graph from file: " + directory + "\n");
        System.out.println(test.toString());

        /** Produces the shortest path between given the verticies **/
        Scanner input = new Scanner(System.in);
        System.out.println("Enter start vertex: ");
        String start = input.nextLine().trim();
        System.out.println("Enter end vertex: ");
        String dest = input.nextLine().trim();
        input.close();
        System.out.println("The shortest path between "
                + start + " and " + dest + " is: ");
        List<String> pathToTake = test.shortestPath(start, dest);
        if(start.equals(dest)) {
            System.out.println("The source and destination are the same!\n" +
                    "Total distance is 0.");
            return;
        }
        if(pathToTake.size() == 1){
            System.out.println(
                    "A path does not exist between these verticies!");
            return;
        }
        for(String e : pathToTake){
            if(pathToTake.indexOf(e) == pathToTake.size()-1)
                System.out.println(e.toString());
            else
                System.out.print(e.toString() + " to ");
        }
        System.out.println("Total distance is " +
                (pathToTake.size()-1) + " edges.");
    }
}
