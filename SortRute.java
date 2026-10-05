import java.util.ArrayList;

class SortRute extends Rute {
    public SortRute(int rad, int kolonne, Labyrint labyrint) {
        super(rad, kolonne, labyrint);
    }

    @Override 
    public void finn(Rute fra, int stegNr, ArrayList<Koordinat> sti) {
        return;
    }

    @Override
    public String toString() {
        return " # ";
    }
}