/** Daniel White (CSSC0721) Mario Shamhon (CSSC0781) **/
/** CS310 Fall 2018, Shawn Healey **/

package edu.sdsu.cs.datastructures;

import java.util.*;

public class DirectedGraph<V> implements IGraph<V>{

    private List<Vertex> vertices;
    private List<Edge> edges;

    private class Vertex implements Comparable{
        private final V id;

        private Vertex(V id) {
            this.id = id;
        }

        /** Help from http://www.vogella.com/tutorials/
         * JavaAlgorithmsDijkstra/article.html **/
        @Override
        public boolean equals(Object O){
            if(this == O) {
                return true;
            }
            if(O == null) {
                return false;
            }
            Vertex other = (Vertex) O;
            if(this.id == null){
                if(other.id != null)
                    return false;
            } else if(!this.id.equals(other.id))
                return false;
            return true;
        }

        public String toString(){
            return this.id.toString();
        }

        @Override
        public int compareTo(Object o) {
            if(this.equals(o))
                return 0;
            else
                return 1;
        }
    }

    private class Edge{
        private final Vertex source;
        private final Vertex destination;
        private final int weight;

        private Edge(Vertex source, Vertex destination, int weight){
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }

        @Override
        public boolean equals(Object O){
            if(this == O) {
                return true;
            }
            if(O == null)
                return false;
            Edge toCheck = (Edge) O;
            if(this.source.id == null){
                if(toCheck.source.id != null)
                    return false;
            }
            else if(!this.source.id.equals(toCheck.source.id))
                return false;
            else if(!this.destination.id.equals(toCheck.destination.id))
                return false;
            return true;
        }

        private int getWeight(){
            return this.weight;
        }

        public String toString(){
            return this.source.toString() + " leads to " +
                    this.destination.toString() + " weight: " + this.weight;
        }
    }

    public DirectedGraph(){
        this.vertices = new LinkedList<>();
        this.edges = new LinkedList<>();
    }

    @Override
    public void add(V vertexName) {
        Vertex newVertex = new Vertex(vertexName);
        if(this.vertices.contains(newVertex)) {
            return;
        }
        else
            this.vertices.add(newVertex);
    }

    @Override
    public void connect(V start, V destination) {
        Vertex source = new Vertex(start);
        Vertex dest = new Vertex(destination);
        if(!this.vertices.contains(source))
            throw new NoSuchElementException("Source not found");
        if(!this.vertices.contains(dest))
            throw new NoSuchElementException("Destination not found");
        Edge newEdge = new Edge(source, dest, 1);
        if(!this.edges.contains(newEdge)) {
            this.edges.add(newEdge);
        }
    }

    @Override
    public void clear() {
        this.vertices = new LinkedList<>();
        this.edges = new LinkedList<>();
    }

    @Override
    public boolean contains(V label) {
        Vertex toCheck = new Vertex(label);
        if(this.vertices.contains(toCheck))
            return true;
        return false;
    }

    @Override
    public void disconnect(V start, V destination) {
        Vertex src = new Vertex(start);
        Vertex dest = new Vertex(destination);
        if(!this.vertices.contains(src))
            throw new NoSuchElementException("Source not found");
        if(!this.vertices.contains(dest))
            throw new NoSuchElementException("Destination not found");
        Edge toCheck = new Edge(src, dest, 0);
        if(edges.contains(toCheck))
            edges.remove(toCheck);
        else
            System.out.println("No connection exists between nodes " +
            start.toString() + " and " + destination.toString());
    }

    @Override
    public boolean isConnected(V start, V destination) {
        List<Vertex> toCheck = new LinkedList<>();
        List<Vertex> finishedChecking = new LinkedList<>();
        Vertex src = new Vertex(start);
        Vertex dest = new Vertex(destination);
        if(!this.vertices.contains(src))
            throw new NoSuchElementException(
                    "The source node does not exist in the graph");
        if(!this.vertices.contains(dest))
            throw new NoSuchElementException(
                    "The destination node does not exist in the graph");

        /** https://stackoverflow.com/questions/
         * 354330/how-to-determine-if-two-nodes-are-connected **/
        toCheck.add(src);
        while(!toCheck.isEmpty()){
            Vertex currentVertex = toCheck.remove(0);
            finishedChecking.add(currentVertex);
            for(Edge e : this.edges){
                if(e.source.equals(currentVertex)){
                    if(e.destination.equals(dest))
                        return true;
                    if(!finishedChecking.contains(e.destination))
                        toCheck.add(e.destination);
                }
            }
        }
        return false;
    }

    @Override
    public Iterable<V> neighbors(V vertexName) {
        Vertex target = new Vertex(vertexName);
        if(!this.vertices.contains(target))
            throw new NoSuchElementException("Node not present in graph");
        LinkedList<V> neighbors = new LinkedList<>();
        for(Edge e : this.edges){
            if(e.source.equals(target))
                neighbors.add(e.destination.id);
        }
        return neighbors;
    }

    @Override
    public void remove(V vertexName) {
        Vertex toRemove = new Vertex(vertexName);
        if(!this.vertices.contains(toRemove))
            throw new NoSuchElementException(
                    "That node isn't present in the graph");
        Iterator<Edge> iter = this.edges.iterator();
        while(iter.hasNext()){
            Edge e = iter.next();
            if(e.destination.equals(toRemove) || e.source.equals(toRemove))
                iter.remove();
        }
        this.vertices.remove(toRemove);
    }

    @Override
    public List<V> shortestPath(V start, V destination) {
        Vertex src = new Vertex(start);
        Vertex dest = new Vertex(destination);
        if(!this.vertices.contains(src))
            throw new NoSuchElementException("Source not found");
        if(!this.vertices.contains(dest))
            throw new NoSuchElementException("Destination not found");

        /** Create a list of unvisited nodes **/
        List<V> unvisitedNodes = new LinkedList<>();
        for(Vertex e : this.vertices){
            unvisitedNodes.add(e.id);
        }

        /** Initialize the list of nodes to return **/
        List<V> vistedNodes = new LinkedList<>();
        HashMap<V, V> previousVerticies = new HashMap<>();

        /** Initialize distances from start Map **/
        TreeMap<V, Integer> distances = new TreeMap<>();
        for(V id : unvisitedNodes) {
            if(!id.equals(start))
                distances.put(id, Integer.MAX_VALUE);
            previousVerticies.put(id, null);
        }
        distances.put(start, 0);

        /** Generate neighbors map **/
        HashMap<V, List<V>> neighborsMap = generateNeighborsMap();

        /** Loop **/
        V closest = start;
        while(!unvisitedNodes.isEmpty()){
            /** Finds closest unvisited node **/
            int minimumDist = Integer.MAX_VALUE;
            for(V id : unvisitedNodes){
                int distance = distances.get(id);
                if(distance <= minimumDist){
                    minimumDist = distance;
                    closest = id;
                }
            }

            /** Examine this node's unvisited neighbor and add to length **/
            for(V neighbor : neighborsMap.get(closest)){
                if(unvisitedNodes.contains(neighbor)){
                    int distanceFromStart;
                    if(distances.get(closest) == Integer.MAX_VALUE)
                        distanceFromStart = 1;
                    else
                        distanceFromStart = distances.get(closest) + 1;
                    if(distanceFromStart < distances.get(neighbor)){
                        previousVerticies.put(neighbor, closest);
                        distances.remove(neighbor);
                        distances.put(neighbor, distanceFromStart);

                    }
                }
             }
            unvisitedNodes.remove(closest);
        }
        V toPutIntoFinalList = destination;
        while(!toPutIntoFinalList.equals(start)){
            vistedNodes.add(toPutIntoFinalList);
            toPutIntoFinalList = previousVerticies.get(toPutIntoFinalList);
            if(toPutIntoFinalList == null)
                return vistedNodes;
        }
        vistedNodes.add(start);
        Collections.reverse(vistedNodes);
        return vistedNodes;
    }

    public HashMap<V, List<V>> generateNeighborsMap(){
        HashMap<V, List<V>> neighborsMap = new HashMap<>();
        for(Vertex e : this.vertices){
            List<V> neighbors = new LinkedList<>();
            for(Edge k : this.edges){
                if(k.source.compareTo(e) == 0)
                    neighbors.add(k.destination.id);
            }
            neighborsMap.put(e.id, neighbors);
        }
        return neighborsMap;
    }

    @Override
    public int size() {
        return this.vertices.size();
    }

    @Override
    public Iterable<V> vertices() {
        return (Iterable<V>) this.vertices;
    }

    @Override
    public IGraph<V> connectedGraph(V origin){
        IGraph<V> toReturn = new DirectedGraph();
        toReturn.add(origin);
        for(Vertex e : this.vertices){
            if(isConnected(origin, e.id))
                toReturn.add(e.id);
        }
        for(Edge e : this.edges){
            if(toReturn.contains(e.source.id)
                    && toReturn.contains(e.destination.id))
                toReturn.connect(e.source.id, e.destination.id);
        }
        return toReturn;
    }

    @Override
    public String toString(){
        String output = "";
        for(Vertex e : this.vertices) {
            output += String.format("Vertex: " + e.toString());
            output += String.format("\nNeighbors: ");
            for(V neightbor : this.neighbors(e.id)) {
                String toAdd = neightbor.toString();
                output += String.format(toAdd + ", ");
            }
            output += "\n\n";
        }
        return output;
    }

}
