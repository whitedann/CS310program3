package edu.sdsu.cs;

import edu.sdsu.cs.datastructures.DirectedGraph;

public class App
{
    public static void main( String[] args )
    {


        DirectedGraph test = new DirectedGraph();
        /**
        int previous = 0;
        test.add(previous);
        for (int current = 1; current < 100; current++) {
            test.add(current);
            test.connect(previous, current);
            previous = current;
        }
         **/

        test.add(0);
        test.add(10);
        test.add(11);
        test.add(12);
        test.add(200);
        test.add(500);
        test.add(30);
        test.connect(0, 10);
        test.connect(0, 11);
        test.connect(0, 12);
        test.connect(10, 200);
        test.connect(11, 200);
        test.connect(12, 200);
        test.connect(200, 500);
        test.connect(0, 30);
        test.connect(30, 12);

        test.shortestPath(0, 10);


    }
}
