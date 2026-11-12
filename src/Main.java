import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static Nodo raiz = null;


    // Parte 2: utilidades de altura y factor de equilibrio

    static int altura(Nodo n) {
        return (n == null) ? 0 : n.altura;
    }


    static void actualizar(Nodo n) {
        n.altura = 1 + Math.max(altura(n.izq), altura(n.der));
    }

    // Factor de equilibrio = altura(izquierdo) - altura(derecho)
    static int factorEquilibrio(Nodo n) {
        return (n == null) ? 0 : altura(n.izq) - altura(n.der);
    }

    // vuelve a calcular las alturas del árbol (de abajo hacia arriba)
    static void recalcularAlturas(Nodo n) {
        if (n == null) return;
        recalcularAlturas(n.izq);
        recalcularAlturas(n.der);
        actualizar(n);
    }

    // Parte 3: construcción del árbol


    static void construirArbol() {
        System.out.print("Raíz: ");
        String valor = sc.nextLine().trim();
        if (valor.isEmpty()) {
            System.out.println("La raíz no puede estar vacía.");
            return;
        }
        raiz = new Nodo(valor);

        Queue<Nodo> cola = new LinkedList<>();
        cola.add(raiz);

        while (!cola.isEmpty()) {
            Nodo actual = cola.poll();

            System.out.print("Hijo izquierdo de " + actual.dato + " (Enter si no tiene): ");
            String izq = sc.nextLine().trim();
            if (!izq.isEmpty()) {
                actual.izq = new Nodo(izq);
                cola.add(actual.izq);
            }

            System.out.print("Hijo derecho de " + actual.dato + " (Enter si no tiene): ");
            String der = sc.nextLine().trim();
            if (!der.isEmpty()) {
                actual.der = new Nodo(der);
                cola.add(actual.der);
            }
        }
        recalcularAlturas(raiz);
        System.out.println("\nÁrbol construido correctamente.");
    }


    // Parte 4: Grafica en consola

    static int contador;
    static int anchoCelda;
    static char[][] lienzo;

    static String etiqueta(Nodo n, boolean conFE) {
        if (conFE) {
            int fe = factorEquilibrio(n);
            return n.dato + "(" + (fe > 0 ? "+" : "") + fe + ")";
        }
        return n.dato;
    }

    static int maxEtiqueta(Nodo n, boolean conFE) {
        if (n == null) return 0;
        return Math.max(etiqueta(n, conFE).length(),
                Math.max(maxEtiqueta(n.izq, conFE), maxEtiqueta(n.der, conFE)));
    }

    static int contarNodos(Nodo n) {
        return (n == null) ? 0 : 1 + contarNodos(n.izq) + contarNodos(n.der);
    }

    // Dibuja el nodo y devuelve la columna
    static int dibujar(Nodo n, int nivel, boolean conFE) {
        if (n == null) return -1;

        int colIzq = dibujar(n.izq, nivel + 1, conFE);

        int centro = contador * anchoCelda + anchoCelda / 2;
        contador++;
        String et = etiqueta(n, conFE);
        int inicio = centro - et.length() / 2;
        int fila = nivel * 2;

        for (int i = 0; i < et.length(); i++) {
            lienzo[fila][inicio + i] = et.charAt(i);
        }

        int colDer = dibujar(n.der, nivel + 1, conFE);

        // Línea hacia el hijo izquierdo
        if (colIzq != -1) {
            for (int c = colIzq + 1; c < inicio; c++) lienzo[fila][c] = '_';
            lienzo[fila + 1][colIzq] = '/';
        }
        // Línea hacia el hijo derecho
        if (colDer != -1) {
            for (int c = inicio + et.length(); c < colDer; c++) lienzo[fila][c] = '_';
            lienzo[fila + 1][colDer] = '\\';
        }
        return centro;
    }

    static void mostrarArbol(Nodo n, boolean conFE) {
        if (n == null) {
            System.out.println("El árbol está vacío. Use la opción 1 para construirlo.");
            return;
        }
        anchoCelda = maxEtiqueta(n, conFE) + 2;
        int filas = altura(n) * 2 - 1;
        int columnas = contarNodos(n) * anchoCelda + 2;

        lienzo = new char[filas][columnas];
        for (char[] f : lienzo) java.util.Arrays.fill(f, ' ');

        contador = 0;
        dibujar(n, 0, conFE);

        for (char[] f : lienzo) {
            System.out.println(new String(f).replaceAll("\\s+$", ""));
        }
    }


    // PASO 5: recorridos

    // Raíz -> Izquierdo -> Derecho
    static void preorden(Nodo n) {
        if (n == null) return;
        System.out.print(n.dato + " ");
        preorden(n.izq);
        preorden(n.der);
    }

    // Izquierdo -> Raíz -> Derecho
    static void inorden(Nodo n) {
        if (n == null) return;
        inorden(n.izq);
        System.out.print(n.dato + " ");
        inorden(n.der);
    }

    // Izquierdo -> Derecho -> Raíz
    static void posorden(Nodo n) {
        if (n == null) return;
        posorden(n.izq);
        posorden(n.der);
        System.out.print(n.dato + " ");
    }


    // Parte 6: factores de equilibrio

    static void listarFactores(Nodo n) {
        if (n == null) return;
        listarFactores(n.izq);
        int fe = factorEquilibrio(n);
        String estado = (Math.abs(fe) > 1) ? "  <-- DESBALANCEADO" : "";
        System.out.println("  Nodo " + n.dato + ": altura izq = " + altura(n.izq)
                + ", altura der = " + altura(n.der) + ", FE = " + fe + estado);
        listarFactores(n.der);
    }


    // PASO 7: balanceo AVL

    static Nodo rotarDerecha(Nodo y) {
        Nodo x = y.izq;
        y.izq = x.der;
        x.der = y;
        actualizar(y);
        actualizar(x);
        return x;
    }


    static Nodo rotarIzquierda(Nodo x) {
        Nodo y = x.der;
        x.der = y.izq;
        y.izq = x;
        actualizar(x);
        actualizar(y);
        return y;
    }


    static Nodo balancear(Nodo n) {
        if (n == null) return null;

        n.izq = balancear(n.izq);
        n.der = balancear(n.der);
        actualizar(n);

        int fe = factorEquilibrio(n);

        if (fe > 1) {
            if (factorEquilibrio(n.izq) >= 0) {
                System.out.println("  Nodo " + n.dato + " (FE = " + fe
                        + "): Rotación simple a la derecha (caso Izquierda-Izquierda)");
            } else {
                System.out.println("  Nodo " + n.dato + " (FE = " + fe
                        + "): Rotación doble Izquierda-Derecha (primero izquierda sobre "
                        + n.izq.dato + ", luego derecha sobre " + n.dato + ")");
                n.izq = rotarIzquierda(n.izq);
            }
            n = rotarDerecha(n);
            return balancear(n);
        }

        if (fe < -1) {
            if (factorEquilibrio(n.der) <= 0) {
                System.out.println("  Nodo " + n.dato + " (FE = " + fe
                        + "): Rotación simple a la izquierda (caso Derecha-Derecha)");
            } else {
                System.out.println("  Nodo " + n.dato + " (FE = " + fe
                        + "): Rotación doble Derecha-Izquierda (primero derecha sobre "
                        + n.der.dato + ", luego izquierda sobre " + n.dato + ")");
                n.der = rotarDerecha(n.der);
            }
            n = rotarIzquierda(n);
            return balancear(n);
        }

        return n;
    }

    static void buscarDesbalanceados(Nodo n, List<String> lista) {
        if (n == null) return;
        buscarDesbalanceados(n.izq, lista);
        buscarDesbalanceados(n.der, lista);
        int fe = factorEquilibrio(n);
        if (Math.abs(fe) > 1) lista.add(n.dato + " (FE = " + fe + ")");
    }

    static void balancearArbol() {
        if (raiz == null) {
            System.out.println("El árbol está vacío. Use la opción 1 para construirlo.");
            return;
        }

        recalcularAlturas(raiz);
        System.out.println("\n--- 1. ÁRBOL ORIGINAL (con factor de equilibrio) ---\n");
        mostrarArbol(raiz, true);

        List<String> malos = new ArrayList<>();
        buscarDesbalanceados(raiz, malos);
        System.out.println("\n--- 2. NODOS QUE REQUIEREN BALANCEO ---");
        if (malos.isEmpty()) {
            System.out.println("  Ninguno. El árbol ya cumple la propiedad AVL.");
            return;
        }
        for (String s : malos) System.out.println("  " + s);


        System.out.println("\n--- 3. ROTACIONES APLICADAS ---");
        raiz = balancear(raiz);
        recalcularAlturas(raiz);

        // 4. Mostrar el árbol después del balanceo
        System.out.println("\n--- 4. ÁRBOL BALANCEADO (AVL) ---\n");
        mostrarArbol(raiz, true);
        System.out.println();
        listarFactores(raiz);
    }

    // PASO 8: menú principal

    static void mostrarMenu() {
        System.out.println("\n========== MENÚ ==========");
        System.out.println("1. Construir árbol");
        System.out.println("2. Mostrar árbol");
        System.out.println("3. Recorrido Preorden");
        System.out.println("4. Recorrido Inorden");
        System.out.println("5. Recorrido Posorden");
        System.out.println("6. Mostrar factores de equilibrio");
        System.out.println("7. Balancear árbol (AVL)");
        System.out.println("8. Salir");
        System.out.println("==========================");
        System.out.print("Seleccione una opción: ");
    }

    public static void main(String[] args) {
        int opcion = 0;

        while (opcion != 8) {
            mostrarMenu();
            String linea = sc.nextLine().trim();
            try {
                opcion = Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                System.out.println("Opción inválida. Escriba un número del 1 al 8.");
                opcion = 0;
                continue;
            }

            switch (opcion) {
                case 1:
                    construirArbol();
                    System.out.println();
                    mostrarArbol(raiz, false);
                    break;
                case 2:
                    mostrarArbol(raiz, false);
                    break;
                case 3:
                    if (raiz == null) { System.out.println("El árbol está vacío."); break; }
                    System.out.print("Preorden: ");
                    preorden(raiz);
                    System.out.println();
                    break;
                case 4:
                    if (raiz == null) { System.out.println("El árbol está vacío."); break; }
                    System.out.print("Inorden: ");
                    inorden(raiz);
                    System.out.println();
                    break;
                case 5:
                    if (raiz == null) { System.out.println("El árbol está vacío."); break; }
                    System.out.print("Posorden: ");
                    posorden(raiz);
                    System.out.println();
                    break;
                case 6:
                    if (raiz == null) { System.out.println("El árbol está vacío."); break; }
                    recalcularAlturas(raiz);
                    System.out.println();
                    mostrarArbol(raiz, true);
                    System.out.println();
                    listarFactores(raiz);
                    break;
                case 7:
                    balancearArbol();
                    break;
                case 8:
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción inválida. Escriba un número del 1 al 8.");
            }
        }
    }
}

// Parte 1: el nodo del árbol

class Nodo {
    String dato;
    Nodo izq;
    Nodo der;
    int altura;

    Nodo(String dato) {
        this.dato = dato;
        this.altura = 1;
    }
}