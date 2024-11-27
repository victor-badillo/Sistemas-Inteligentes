package es.udc.intelligentsystems;

public class Node implements Comparable<Node> {
    /*
    private State state;
    private Node parent;
    private Action action;

    public Node(State state, Node parent, Action action){
        this.state = state;
        this.parent = parent;
        this.action = action;
    }

    public Node create_node(State state, Node node, Action action){
        Node createdNode = new Node(state, node, action);
        return createdNode;
    }

    public Node getParent(){
        return this.parent;
    }

    public State getState(){
        return this.state;
    }
    public Action getAction(){
        return this.action;
    }

    @Override
    public String toString(){
        return "NODE -> " + state + ", (" + parent + ") , " + action;
    }
    */
    Node parent;
    State state;
    Action action;
    float cost;
    float f;

    public Node(Node parent, State state, Action action, Heuristic heuristic) {
        this.parent = parent;
        this.state = state;
        this.action = action;
        if(parent != null){
            this.cost = parent.cost + action.getCost();
            if(heuristic != null)
                this.f = this.cost + heuristic.evaluate(state);
        }
    }

    @Override
    public String toString() {
        return "(" + state +
                ", " + action +
                ", " + cost +
                ", " + f +
                ')';
    }

    @Override
    public int compareTo(Node nodo) {
        // el orden se basa en la funcion f
        return nodo.f < this.f ? 1 : -1;
    }
    public State getState(){
        return this.state;
    }

}
