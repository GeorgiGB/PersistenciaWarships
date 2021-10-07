package pkg2020_ad_p1_warship;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

public class Visualizar {

    //Lista de Columnas
    public static void ListaColumnas(String[] filenames) {
        //Constante de 5
        final int MAX_FILES_BY_COLUMN = 5;
        int columnas = (filenames.length / MAX_FILES_BY_COLUMN) + 1;
        String[][] salida = new String[MAX_FILES_BY_COLUMN][columnas];

        for (int i = 0; i < filenames.length; i++) {
            salida[i % MAX_FILES_BY_COLUMN][i / MAX_FILES_BY_COLUMN] = filenames[i];
        }

        //bucle para mostrar salidas
        for (int i = 0; i < MAX_FILES_BY_COLUMN; i++) {
            for (int j = 0; j < columnas; j++) {
                System.out.println(salida[i][j] + " - ");
                System.out.println(" /");
            }
        }
    }

    //Listar una lista
    public static void Lista(String[] filenames) {
        int filas= filenames.length;
        String[] salida = new String[filas];

        for (int i = 0; i < filenames.length; i++) {
            System.out.println(salida[i]);
        }

    }

    // TODO: 07/10/2021 Hacer una tabla que muestre lal informacion de cada fichero: DFRWH NOMBRE TAMAÑO FECHA MODIFICACION


    /*
    class Tabla extends AbstractTableModel(){

    }*/
}
