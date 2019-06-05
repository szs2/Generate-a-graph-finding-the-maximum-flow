// implement by : K.M.Senuri Samindi
//IIT No : 2017461
//UOW No : w1698415

import java.lang.*;
import java.util.LinkedList;

class CalculatingMaxFlow{

    private static int numOfVertice;         //Created a variable to assign number of vertices in the Graph
    private static int flowOfGraph;        //Created a variable to assign value of the random number of the Graph

    //Checking there is a path from source 's' to sink 't'
    //Store the path filling parent[]
    //return boolen value(true or false)
    boolean check(int ranGraph[][], int s, int t, int path[])
    {

        //Created the startPoint array to check the all vertices are visited or not
        boolean startPoint[] = new boolean[numOfVertice];
        for(int i=0; i<numOfVertice; ++i)
            startPoint[i]=false;

        //Create a row for enqueue source vertex and mark it visited
        LinkedList<Integer> row = new LinkedList<>();
        row.add(s);
        startPoint[s] = true;
        path[s]=-1;

        // Standard check Loop
        while (row.size()!=0)
        {
            int u = row.poll();

            for (int v=0; v<numOfVertice; v++)
            {
                if (startPoint[v]==false && ranGraph[u][v] > 0)
                {
                    row.add(v);
                    path[v] = u;
                    startPoint[v] = true;
                }
            }
        }

        // Check sink in check starting from source
        // return boolean value (true or false)
        return (startPoint[t] == true);
    }

    // Returns the maximum flow from sourse to sink in the auto generated Graph
    int fordFulkerson(int graph[][], int s, int t)
    {
        int u, v;

        //created a residual graph and fill it using autogenerated capacities

        int ranGraph[][] = new int[numOfVertice][numOfVertice];

        for (u = 0; u < numOfVertice; u++)
            for (v = 0; v < numOfVertice; v++)
                ranGraph[u][v] = graph[u][v];

        // This array is filled by check and to store path
        int parent[] = new int[numOfVertice];

        int max_flow = 0;

        // Augment the flow while there is path from s to t
        while (check(ranGraph, s, t, parent))
        {
            //find the minimum residual capacity of the edges and find the maximum flow through the choosen path.
            int path_flow = Integer.MAX_VALUE;
            for (v=t; v!=s; v=parent[v])
            {
                u = parent[v];
                path_flow = Math.min(path_flow, ranGraph[u][v]);
            }

            //Update residual Capacities of the edges and reverse edges along the path
            for (v=t; v != s; v=parent[v])
            {
                u = parent[v];
                ranGraph[u][v] -= path_flow;
                ranGraph[v][u] += path_flow;
            }

            //add path flow to overall flow
            max_flow += path_flow;
        }

        //return the overall flow
        return max_flow;
    }

    //Created main method to run the Program
    public static void main (String[] args)
    {
        long startingTime = System.nanoTime();     //Created variable to take Starting Time of the Progam
        int r =6;
        int c =12;

        numOfVertice = (int)(Math.random() * ((c-r) +1) +r);

        int [] array1= new int[numOfVertice];
        int [][] array2 = new int[numOfVertice][numOfVertice];

        //create a for loop for generate the graph
        for(int i=0; i<array1.length; i++){
            for(int j=0; j<array1.length; j++){

                int min= 5;
                int max= 20;

                flowOfGraph=(int) (Math.random() * ( (max - min) + 1)) + min;

                if (j == 0) {
                    flowOfGraph = 0;
                }else if(i==numOfVertice-1){
                    flowOfGraph=0;
                }else if(i==j){
                    flowOfGraph =0;
                }
                array1[j] =flowOfGraph;
            }


            array2 [i] = array1.clone();
        }

        //create the forloop for display the graph
        for(int i=0; i<array1.length; i++){
            for(int j=0; j<array1.length; j++){

                System.out.printf("%6d",array2 [i][j]);   // Display the graph
            }
            System.out.println();
        }

        CalculatingMaxFlow m = new CalculatingMaxFlow();

        System.out.println("Number of vertices : " + numOfVertice);
        System.out.println("The maximum possible flow is " +
                m.fordFulkerson(array2, 0, numOfVertice-1));

        long endingTime = System.nanoTime();       //Created variable to take end time of the Program
        long timeElapsed = endingTime - startingTime;      //Calculating the Exicusion time
        System.out.println("Executing time in nanoseconds : " + timeElapsed);       //Display the Exicusion time in nanoseconds
        System.out.println("Executing time in milliseconds : " + timeElapsed/1000000);       //Display the Exicusion time in milliseconds


    }
}

