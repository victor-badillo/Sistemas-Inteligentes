package es.udc.intelligentsystems;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

public class SearchStratyegyA implements InformedSearchStrategy{
    public SearchStratyegyA() {
    }

    @Override
    public Node[] solve(SearchProblem p, Heuristic h) throws Exception {
        Queue<Node> frontera = new PriorityQueue<>(); //profundidad
        Node actualNode = new Node(null, p.getInitialState(), null, h);
        frontera.add(actualNode);
        ArrayList<State> explored= new ArrayList<>();

        int i = 1;
        int nCreados = 1;
        System.out.println((i++) + " - Empezando búsqueda en " + actualNode.state);

        Node nodo;
        while (true) {
            if (frontera.isEmpty())
                throw new Exception("No se ha podido encontrar una solución");
            actualNode = frontera.remove(); //profundidad
            System.out.println((i++) + " ! Estado actual cambiado a " + actualNode.state);
            if (p.isGoal(actualNode.state)) break;
            else {
                System.out.println((i++) + " - " + actualNode.state + " no es meta");
                explored.add(actualNode.state);
                Action[] accionesDisponibles = p.actions(actualNode.state);
                for (Action acc : accionesDisponibles) {
                    State sc = p.result(actualNode.state, acc);
                    nodo = new Node(actualNode, sc, acc, h);
                    nCreados++;
                    System.out.println((i++) + " - RESULT(" + actualNode.state + "," + acc + ")=" + sc);
                    if (!explored.contains(sc)) {
                        if (!frontera.contains(nodo) && !explored.contains(sc)) {
                            frontera.add(nodo);
                            System.out.println((i++) + " - " + sc + " NO explorado");
                            System.out.println((i++) + " - Nodo anadido a frontera" + nodo);
                        } else
                            System.out.println((i++) + " - " + sc + " ya explorado");
                    }
                }
            }
        }
        System.out.println((i) + " - FIN - " + actualNode);
        System.out.println("Nodos expandidos: " + explored.size());
        System.out.println("Nodos creados: " + nCreados);
        return reconstruye_sol(actualNode);
    }

    /**
     * Calcula el camino para llegar a la solucion dado el nodo final
     * @param nodo nodo final
     * @return array con los nodos seguidos para llegar a la solucion
     */
    private Node[] reconstruye_sol(Node nodo) {
        ArrayList<Node> solucion = new ArrayList<Node>();
        Node actual = nodo;
        while(actual != null){
            solucion.add(actual);
            actual = actual.parent;
        }
        return solucion.toArray(new Node[0]);
    }
}
