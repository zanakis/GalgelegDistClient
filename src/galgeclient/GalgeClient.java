/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package galgeclient;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author zanakis
 */
public class GalgeClient {

    static Scanner in;
    static String username;
    static RESTClient connector;
    static String ordet;
    static ArrayList<String> brugteBogstaver = new ArrayList<>();
    static String synligtOrd;
    static int antalForkerteBogstaver;
    static boolean sidsteBogstavVarKorrekt;
    static boolean spilletErVundet;
    static boolean spilletErTabt;
    static int score;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        in = new Scanner(System.in);
        connector = new RESTClient();
        
        login();
        menu();
    }
    
    public static void spil() {
        brugteBogstaver = new ArrayList<>();
        ArrayList<Object> map = connector.init();
        breakdownInitStr(map);
//        check();
        String gæt;
        System.out.println("Ordet er: " + synligtOrd);
        while(!spilletErTabt && !spilletErVundet) {
            gæt = in.nextLine();
            for(int i = 0; i < gæt.length(); i++) {
                System.out.println("Der gættes på bogstavet: " + gæt.charAt(i)+ "\nDIN STARTSCORE ER: "+ score);
                if(!brugteBogstaver.contains(gæt.charAt(i)+"")) {
                    map = connector.guess(gæt.charAt(i) + "", ordet,
                            synligtOrd, antalForkerteBogstaver, sidsteBogstavVarKorrekt, score);
                    breakdownInitStr(map);
                    brugteBogstaver.add(gæt.charAt(i)+"");
                }
            }
//            map = connector.update();
//            breakdownInitStr(map);
            System.out.println("Ordet er: " + synligtOrd);
            System.out.println("Brugte bogstaver: " + brugteBogstaver);
            System.out.println("Din score er nu: " + score);
        }
        slutSpil();
    }
    
    public static void check() {
        ordet= "hejmed";
        brugteBogstaver = new ArrayList<>();
        brugteBogstaver.add("e");
        brugteBogstaver.add("a");
        brugteBogstaver.add("h");
        antalForkerteBogstaver=1;
        sidsteBogstavVarKorrekt = true;
        score = 110;
    }
    
    public static void breakdownInitStr(ArrayList<Object> map) {
        ordet = (String)map.get(0);
        synligtOrd = (String)map.get(1);
        antalForkerteBogstaver = Integer.parseInt((String)map.get(2));
        sidsteBogstavVarKorrekt = "true".equals((String)map.get(3));
        spilletErVundet = "true".equals((String)map.get(4));
        spilletErTabt = "true".equals((String)map.get(5));
        score = Integer.parseInt((String)map.get(6));
    }
    
    public static void slutSpil() {
        if(spilletErVundet) {
            System.out.println("Du har vundet");
            if(username!=null)
                connector.setHighscore(username, score);
        }
        else System.out.println("Du har tabt");
        System.out.println("spil igen? y/n");
        if(in.nextLine().startsWith("y"))
            spil();
        else menu();
    }
    
    public static void login() {
        String usr;
        while(true) {
            System.out.println("indtast brugernavn");
            usr = in.nextLine();
            System.out.println("indtast kodeord");
            String psw = in.nextLine();
            if(connector.login(usr, psw)) {
                username = usr;
                break;
            }
            System.out.println("Forkert brugernavn eller password.\nSpil uden at logge ind? y/n");
            if(in.nextLine().startsWith("y")) {
                username = null;
                break;
            }
        }
    }

    private static void menu() {
        System.out.println("Vælg funktion");
        System.out.println("1. spil\n2. se highscore");
        try {
            switch(Integer.parseInt(in.nextLine())) {
                case 1: spil();
                break;
                case 2: leaderBoard();
            }
        } catch(Exception e) {
            System.out.println("indtast et tal");
            menu();
        }
        menu();
    }

    private static void leaderBoard() {
        System.out.println(connector.getHighscore());
    }
}
