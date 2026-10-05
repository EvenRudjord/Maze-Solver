import java.util.ArrayList;

class Åpning extends HvitRute {
    public Åpning(int rad, int kolonne, Labyrint labyrint) {
        super(rad, kolonne, labyrint);
    }

    @Override 
    public void finn(Rute fra, int stegNr, ArrayList<Koordinat> sti) {
        this.antSteg = stegNr;
        this.besokt = true;
        sti.add(new Koordinat(radNr, kolNr));
        labyrint.leggTilUtvei(sti);
        System.out.println("Utvei funnet ved åpning: (" + radNr + ", " + kolNr + ") med " + stegNr + " steg.");
    }
}