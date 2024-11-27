package es.udc.intelligentsystems;

import java.util.ArrayList;
import java.util.Stack;

public class GraphSearchStrategy implements SearchStrategy{

   /*
    @Override
    public Node[] solve(SearchProblem p) throws Exception{
        ArrayList<Node> explored = new ArrayList<Node>();
        State currentState = p.getInitialState();
        //
        Stack<Node> frontier = new Stack<>();
        Node currentNode = new Node(currentState,null,null); //
        explored.add(currentNode);
        frontier.add(currentNode);
        Node parent = currentNode;

        int i = 1;

        System.out.println((i++) + " - Starting search at " + currentState);

        while (!p.isGoal(currentState)){
            if(frontier.isEmpty()){
                throw new Exception("There is no solution");
            }
            System.out.println((i++) + " - " + currentState + " is not a goal");
            Action[] availableActions = p.actions(currentState);
            boolean modified = false;
            for (Action acc: availableActions) {
                State sc = p.result(currentState, acc);
                System.out.println((i++) + " - RESULT(" + currentState + ","+ acc + ")=" + sc);
                if (!explored.contains(sc)) {
                    currentState = sc;
                    System.out.println((i++) + " - " + sc + " NOT explored");
                    Node newExplored = new Node(currentState, parent,acc );
                    parent = newExplored;
                    explored.add(newExplored);
                    modified = true;
                    System.out.println((i++) + " - Current state changed to " + currentState);
                    break;
                }
                else
                    System.out.println((i++) + " - " + sc + " already explored");
            }
            if (!modified) throw new Exception("No solution could be found");
        }
        System.out.println((i++) + " - END - " + currentState);
        return reconstruye_sol(currentNode);
    }*/


    @Override
    public Node[] solve(SearchProblem p) throws Exception{
        Stack<Node> frontier = new Stack<>(); //depth
        State currentState = p.getInitialState();
        Node actualNode = new Node(currentState , null, null);
        frontier.add(actualNode);
        ArrayList<State> explored = new ArrayList<>(); //at this point any node has been explored

        int i = 1;
        int nCreated = 1;
        System.out.println((i++) + " - Starting search at " + actualNode.getState());

        while(true) {
            if (frontier.isEmpty())
                throw new Exception("A solution couldn't be found");
            actualNode = frontier.pop(); //profundidad
            System.out.println((i++) + " ! Actual state changed to " + actualNode.getState());
            if (p.isGoal(actualNode.getState())) break;
            else {
                System.out.println((i++) + " - " + actualNode.getState() + " is not a goal");
                explored.add(actualNode.getState());
                Action[] availableActions = p.actions(actualNode.getState());
                for (Action acc : availableActions) {
                    State sc = p.result(actualNode.getState(), acc);
                    Node node = new Node(sc, actualNode, acc); nCreated++;
                    System.out.println((i++) + " - RESULT(" + actualNode.getState() + ","+ acc + ")=" +sc);
                    if (!frontier.contains(node) && !explored.contains(sc)) {
                        frontier.add(node);
                        System.out.println((i++) + " - " + sc + " NOT explored");
                        System.out.println((i++) + " - Node inserted in frontier " + node.toString());
                    }
                    else
                        System.out.println((i++) + " - " + sc + " already explored");
                }
            }
        }
        System.out.println((i) + " - FIN - " + actualNode);
        System.out.println("Expanded nodes: " + explored.size());
        System.out.println("Created nodes: " + nCreated);
        return reconstruye_sol(actualNode);
    }



    public Node[] reconstruye_sol(Node node){
        ArrayList<Node> solution = new ArrayList<>();
        Node actual = node;
        while(actual != null){
            solution.add(actual);
            actual = actual.getParent();
        }

        return solution.toArray(new Node[0]);
    }
}
