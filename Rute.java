import java.util.ArrayList;

abstract class Rute {
    int radNr;
    int kolNr;
    Labyrint labyrint;
    Rute[] naboer;

    public Rute (int radNr, int kolNr, Labyrint labyrint) {
        this.radNr = radNr;
        this.kolNr = kolNr;
        this.labyrint = labyrint;
        this.naboer = new Rute[4]; // 0: opp, 1: høyre, 2: ned, 3: venstre
    }

    public void settNaboer(Rute[][] labyrint) {
        if (radNr > 0) naboer[0] = labyrint[radNr - 1][kolNr];                  // opp
        if (kolNr < labyrint[0].length - 1) naboer[1] = labyrint[radNr][kolNr + 1]; // høyre
        if (radNr < labyrint.length - 1) naboer[2] = labyrint[radNr + 1][kolNr];    // ned
        if (kolNr > 0) naboer[3] = labyrint[radNr][kolNr - 1];              // venstre
    }

    public abstract void finn(Rute fra, int stegNr, ArrayList<Koordinat> sti);
}