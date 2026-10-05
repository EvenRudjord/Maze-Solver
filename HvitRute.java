import java.util.ArrayList;
class HvitRute extends Rute {
    Boolean besokt;
    int antSteg;

    public HvitRute(int rad, int kolonne, Labyrint labyrint) {
        super(rad, kolonne, labyrint);
        this.besokt = false;
        this.antSteg = 0;
    }

    @Override 
    public void finn(Rute fra, int stegNr, ArrayList<Koordinat> sti) {
        Koordinat meg = new Koordinat(radNr, kolNr);
        if (sti.contains(meg)) return;

        this.antSteg = stegNr;
        this.besokt = true;
        ArrayList<Koordinat> nySti = new ArrayList<>(sti);
        nySti.add(meg);
        for (Rute nabo : naboer) {
            if (nabo != null && nabo != fra) {
                nabo.finn(this, stegNr + 1, nySti);
            }
        }
    }

    @Override
    public String toString() {
        ArrayList<Koordinat> best = labyrint.korteste();
        if (besokt && best != null && best.contains(new Koordinat(radNr, kolNr))) {
            return String.format("%2d ", antSteg);
        } else {
            return " . ";
        }
    }
}