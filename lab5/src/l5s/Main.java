package l5s;

import java.util.Arrays;
import java.util.Scanner;
import l5s.Commands.*;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0 ) System.exit(0);
        Command c = new Command();
        Scanner sc = new Scanner(System.in);
        System.out.println("Hello, my dear user!");
//        DOMTest domTest = new DOMTest();
//        domTest.domXml();

        do {
            System.out.print("> ");
            try {
                System.out.println(c.exec(sc.nextLine()));

            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        } while (true);
    }
}
