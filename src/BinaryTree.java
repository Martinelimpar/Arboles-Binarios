
public class BinaryTree {

    TreeNode root;

    public BinaryTree() {
        this.root = null;
    }

    public BinaryTree(TreeNode root) {
        this.root = root;
    }

    /*
    IMPORTANTE: respete EXACTAMENTE los nombres y firmas de los metodos que se
    piden abajo; la revision automatica los busca por nombre. Puede agregar
    todos los metodos auxiliares (privados) que necesite.
    */

    /*
    1. Construccion del arbol: implemente aqui

    public void insertRoot(Object data)
        Si el arbol esta vacio, crea la raiz con 'data'.

    public TreeNode search(Object data)
        Devuelve el nodo cuyo dato es igual (equals) a 'data', o null si no
        existe.

    public boolean insertLeft(Object parent, Object data)
        Agrega 'data' como hijo izquierdo del nodo cuyo dato es 'parent'.
        Devuelve false (y no modifica nada) si 'parent' no existe o si ya
        tiene hijo izquierdo; true en otro caso.

    public boolean insertRight(Object parent, Object data)
        Igual que insertLeft, pero con el hijo derecho.

    public void buildTree(Scanner scanner)
        Construye el arbol leyendo los datos con 'scanner' (en el main se le
        pasa new Scanner(System.in)). Primero se lee la raiz y luego, por
        niveles, el hijo izquierdo y el hijo derecho de cada nodo
        ("Hijo izquierdo de A: ", "Hijo derecho de A: ", ...). Una linea
        vacia o "-" significa que ese hijo no existe.
    */

    /*
    2. Visualizacion: implemente aqui

    public void printTree()
        Muestra el arbol en consola de forma grafica, indicando claramente
        la relacion entre padres e hijos.
    */

    /*
    3. Recorridos: implemente aqui. Cada recorrido debe ser una funcion
    RECURSIVA independiente (por ejemplo, un metodo privado que reciba un
    TreeNode). Los metodos publicos devuelven la secuencia separada por
    espacios, p.ej. "A B D H I E C F G".

    public String preorder()
    public String inorder()
    public String postorder()
    */

    /*
    4. Factores de equilibrio: implemente aqui

    public int height(TreeNode node)
        Altura del subarbol que empieza en 'node' (null tiene altura 0 y una
        hoja altura 1).

    public int balanceFactor(TreeNode node)
        Factor de equilibrio de 'node': height(node.right) - height(node.left).

    public boolean isBalanced()
        true si TODOS los nodos tienen factor de equilibrio -1, 0 o 1.

    public List<Object> unbalancedNodes()
        Lista con los datos de los nodos cuyo factor de equilibrio es menor
        que -1 o mayor que 1 (los que requieren balanceo).

    public void printBalanceFactors()
        Muestra el arbol en consola indicando en cada nodo su factor de
        equilibrio.
    */

    /*
    5. Balanceo AVL: implemente aqui. Cada rotacion recibe la raiz del
    subarbol desbalanceado y devuelve la NUEVA raiz de ese subarbol.

    public TreeNode rotateRight(TreeNode node)       rotacion simple a la derecha
    public TreeNode rotateLeft(TreeNode node)        rotacion simple a la izquierda
    public TreeNode rotateLeftRight(TreeNode node)   rotacion doble izquierda-derecha
    public TreeNode rotateRightLeft(TreeNode node)   rotacion doble derecha-izquierda

    public void balance()
        Identifica los nodos desbalanceados y aplica las rotaciones necesarias
        hasta que el arbol sea AVL (actualiza 'root' si cambia). Debe mostrar
        el arbol con sus factores de equilibrio antes y despues de balancear.
    */
}
