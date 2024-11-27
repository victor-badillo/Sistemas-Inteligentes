package es.udc.intelligentsystems.example;

import es.udc.intelligentsystems.*;

import java.util.ArrayList;

public class Strategy4 implements SearchStrategy {

    public Strategy4() {
    }
    /*
    @Override
    public State solve(SearchProblem p) throws Exception{
        ArrayList<State> explored = new ArrayList<State>();
        State currentState = p.getInitialState();
        explored.add(currentState);

        int i = 1;

        System.out.println((i++) + " - Starting search at " + currentState);

        while (!p.isGoal(currentState)){
            System.out.println((i++) + " - " + currentState + " is not a goal");
            Action[] availableActions = p.actions(currentState);
            boolean modified = false;
            for (Action acc: availableActions) {
                State sc = p.result(currentState, acc);
                System.out.println((i++) + " - RESULT(" + currentState + ","+ acc + ")=" + sc);
                if (!explored.contains(sc)) {
                    currentState = sc;
                    System.out.println((i++) + " - " + sc + " NOT explored");
                    explored.add(currentState);
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
        return currentState;
    }*/


    @Override
    public Node[] solve(SearchProblem p) throws Exception{
        ArrayList<Node> explored = new ArrayList<Node>();
        State currentState = p.getInitialState();
         //
        Node currentNode = new Node(currentState,null,null); //
        explored.add(currentNode);
       // Node parent = currentNode;
        Node parent = new Node(currentState, null, null);

        int i = 1;

        System.out.println((i++) + " - Starting search at " + currentState);

        while (!p.isGoal(currentState)){
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
                    //parent = newExplored;
                    parent = new Node(currentState, parent, acc);
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
        return reconstruye_sol(parent);
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
