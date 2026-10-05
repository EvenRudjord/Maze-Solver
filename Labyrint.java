import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

class Labyrint {
    int antRader;
    int antKolonner;

    Rute[][] rute;

    private ArrayList<ArrayList<Koordinat>> utveier = new ArrayList<>();

    public Labyrint(String filnavn) {
        ArrayList<String> linjer = lesLinjer(filnavn);

        antRader = linjer.size();
        antKolonner = linjer.get(0).length();
        rute = new Rute[antRader][antKolonner];

        lagRuter(linjer);
        settNaboer();

        System.out.println(this);
    }

    private ArrayList<String> lesLinjer(String filnavn) {
        ArrayList<String> linjer = new ArrayList<>();
        try {
            Scanner fil = new Scanner(new File(filnavn));
            while (fil.hasNextLine()) {
                String linje = fil.nextLine();
                if (!linje.isBlank()) linjer.add(linje);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filnavn);
            e.printStackTrace();
            System.exit(1);
        }
        return linjer;
    }

    private void lagRuter(ArrayList<String> linjer) {
        for (int rad = 0; rad < antRader; rad++) {
            String linje = linjer.get(rad);
            for (int kolonne = 0; kolonne < antKolonner; kolonne++) {
                char tegn = linje.charAt(kolonne);
                lagRute(rad, kolonne, tegn);
            }
        }
    }
    
    private void lagRute(int rad, int kolonne, char tegn) {
        if (tegn == '#') {
            rute[rad][kolonne] = new SortRute(rad, kolonne, this);
        } else if (tegn == '.') {
            if (erKant(rad, kolonne)) {
                rute[rad][kolonne] = new Åpning(rad, kolonne, this);
            } else {
                rute[rad][kolonne] = new HvitRute(rad, kolonne, this);
            }
        } else {
            throw new IllegalArgumentException("Ugyldig tegn i labyrinten: " + tegn);
        }
    }

    private boolean erKant(int rad, int kolonne) {
        return rad == 0 || rad == antRader - 1
            || kolonne == 0 || kolonne == antKolonner - 1;
    }

    private void settNaboer() {
        for (int rad = 0; rad < antRader; rad++) {
            for (int kolonne = 0; kolonne < antKolonner; kolonne++) {
                rute[rad][kolonne].settNaboer(rute);
            }
        }
    }

    public ArrayList<ArrayList<Koordinat>> finnUtveiFra(int rad, int kolonne) {
        utveier.clear(); 

        if (rad < 0 || rad >= antRader || kolonne < 0 || kolonne >= antKolonner) {
            System.out.println("Ugyldige koordinater: (" + rad + ", " + kolonne + ")");
            return utveier; 
        }
        if (rute[rad][kolonne] instanceof SortRute) {
            System.out.println("Startkoordinater er en sort rute: (" + rad + ", " + kolonne + ")");
            return utveier; 
        }

        rute[rad][kolonne].finn(null, 1, new ArrayList<Koordinat>());
        return utveier;
    }

    public ArrayList<Koordinat> korteste() {
        ArrayList<Koordinat> best = null;
        for (ArrayList<Koordinat> sti : utveier) {
            if (best == null || sti.size() < best.size()) best = sti;
        }
        return best; // null hvis ingen utvei
    }
    
    public void leggTilUtvei(ArrayList<Koordinat> sti) {
        utveier.add(sti);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < antRader; i++) {
            for (int j = 0; j < antKolonner; j++) {
                if (rute[i][j] != null) {
                    sb.append(rute[i][j].toString());
                } else {
                    sb.append(" ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}

