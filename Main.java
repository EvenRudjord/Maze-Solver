import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java Main <filename>");
            return;
        }

        Labyrint labyrint = new Labyrint(args[0]);
        readUserInput(labyrint);
    }

    private static void readUserInput(Labyrint labyrint) {
        Scanner tastatur = new Scanner(System.in);
        System.out.println("Skriv inn startkoordinater <rad> <kolonne> ('-1' for å avslutte)");

        while (true) {
            String linje = tastatur.nextLine().trim();
            if (linje.equals("-1")) break;

            int[] koordinater = lesKoordinater(linje);
            if (koordinater == null) {
                System.out.println("Skriv to tall: <rad> <kolonne>");
                continue;
            }

            
            labyrint.finnUtveiFra(koordinater[0], koordinater[1]);
            System.out.println(labyrint);

            System.out.println("\nSkriv inn nye startkoordinater ('-1' for å avslutte)");
        }
        tastatur.close();
    }

    private static int[] lesKoordinater(String linje) {
        String[] deler = linje.split(" ");
        if (deler.length != 2) return null;
        try {
            return new int[] {Integer.parseInt(deler[0]), Integer.parseInt(deler[1])};
        } catch (NumberFormatException e) {
            return null;
        }
    }
}