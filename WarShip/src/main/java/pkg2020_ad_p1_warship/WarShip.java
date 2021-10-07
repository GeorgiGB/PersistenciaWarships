package pkg2020_ad_p1_warship;

import java.io.*;
import java.util.Properties;
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author joange
 */
public class WarShip {

    /**
     * @param args the command line arguments
     */
    static int MAX_JUGADAS = 100;

    private Random r;
    private Board board;
    private WarShip ws;

    public WarShip() {
        r = new Random(System.currentTimeMillis());
        board = new Board();
        board.initBoats();
    }

    //TODO Fichero para guardar la configuracion
    public static void saveConfi() {
        Properties confi = new Properties();
        confi.setProperty("Tamaño_Tablero", String.valueOf(Board.BOARD_DIM));
        confi.setProperty("Num_Barcos", String.valueOf(Board.BOARD_BOATS_COUNT));
        confi.setProperty("Num_Jugadas", String.valueOf(MAX_JUGADAS));

        try {
            confi.store(new FileOutputStream("warship.properties"), "Fichero de configuracion");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TODO: 07/10/2021 Fichero para leer la configuracion

    public static void leerConfi() {
        Properties confi = new Properties();
        try {
            confi.load(new FileInputStream("warship.properties"));
            Board.BOARD_DIM = Integer.parseInt(confi.getProperty("Tamaño_Tablero"));
            Board.BOARD_BOATS_COUNT = Integer.parseInt(confi.getProperty("Num_Barcos"));
            MAX_JUGADAS = Integer.parseInt(confi.getProperty("Num_Jugadas"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TODO: 07/10/2021 Modificar menu para que pueda leer un juego ya cargado
    private void importJoc() {
        FileReader fr = null; //recordar no poner 0
        try {
            //leer el txt
            File f = new File("moves_in.txt");
            fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);
            while (bfr.ready()) {
                //Para leer linea por linea
                String linea = bfr.readLine();
                //Identificar cada campo con un ";"
                String[] items = linea.split(";");
                System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "Jugada: " + items[0]);
                int columna, fila;
                do {
                    fila = Integer.parseInt(items[1]);
                    columna = Integer.parseInt(items[2]);
                } while (board.fired(fila, columna));
                if (board.shot(fila, columna) != Cell.CELL_WATER) {
                    board.paint();
                } else {
                    System.out.println("(" + fila + "," + columna + ") --> AGUA");
                }
                if (board.getEnd_Game()) {
                    System.out.printf("Joc acabat amb %2d jugades\n", items[0]);
                    break;
                }
            } catch(FileNotFoundException e){
                e.printStackTrace();
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }


    private void autoPlay() {

        board.paint();

        // Vamos a realizar 50 jugadas aleatorias ...
        for (int i = 1; i <= MAX_JUGADAS; i++) {
            System.out.println(ConsoleColors.GREEN_BRIGHT + "JUGADA: " + i);

            int fila, columna;
            do {
                fila = r.nextInt(Board.BOARD_DIM);
                columna = r.nextInt(Board.BOARD_DIM);
            } while (board.fired(fila, columna));

            if (board.shot(fila, columna) != Cell.CELL_WATER) {
                board.paint();
            } else {
                System.out.println("(" + fila + "," + columna + ") --> AGUA");
            }

            if (board.getEnd_Game()) {
                System.out.printf("Joc acabat amb %2d jugades\n", i);
                break;
            }
        }
    }

    private void play() {
        int num_jugadas = 0;
        boolean rendit = false;

        String jugada;
        int fila = -1, columna = -1;
        do {
            do {
                jugada = Leer.leerTexto("Dime la jugada en dos letras A3, B5... de A0 a J9: ").toUpperCase();
                if (jugada.equalsIgnoreCase("00")) {
                    System.out.println("Jugador rendit");
                    rendit = true;
                    break;
                }
                if (jugada.length() == 0 || jugada.length() > 2) {
                    System.out.println("Format incorrecte.");
                    continue;
                }

                fila = jugada.charAt(0) - 'A';
                columna = jugada.charAt(1) - '0';

            } while (board.fired(fila, columna));

            // acaba el joc
            if (rendit) {
                break;
            }

            num_jugadas++;

            if (board.shot(fila, columna) != Cell.CELL_WATER) {
                board.paintGame();
            } else {
                System.out.println("(" + fila + "," + columna + ") --> AGUA");
            }

            if (board.getEnd_Game()) {
                System.out.printf("Joc acabat amb %2d jugades\n", num_jugadas);
                break;
            }

        } while (num_jugadas < MAX_JUGADAS);

    }


    public static void main(String[] args) {
        // TODO code application logic here
        WarShip ws = new WarShip();

        int opcio = 0;
        do {
            System.out.println(ConsoleColors.GREEN + "--    Escollir   --");
            System.out.println(ConsoleColors.GREEN + "1. Joc automàtic...");
            System.out.println(ConsoleColors.GREEN + "2. Joc manual......");
            System.out.println(ConsoleColors.GREEN + "3. Importar Joc....");
            opcio = Leer.leerEntero(ConsoleColors.CYAN + "Indica el tipus de joc que vols: " + ConsoleColors.RESET);
        } while (opcio < 1 || opcio > 2);

        switch (opcio) {
            case 1:
                ws.autoPlay();
                break;
            case 2:
                ws.play();
                break;
            case 3:
                ws.importJoc();
        }
        saveConfi();
    }
}
