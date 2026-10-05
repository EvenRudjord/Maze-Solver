import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class LabyrintGUI extends JFrame {
    private final Labyrint labyrint;
    private final KartPanel kart = new KartPanel();
    private final JLabel status = new JLabel("Klikk på en hvit rute for å velge start");

    private int startRad = -1;
    private int startKol = -1;
    private ArrayList<Koordinat> sti = null;

    public LabyrintGUI(String filnavn) {
        super("Labyrint");
        labyrint = new Labyrint(filnavn);

        JButton finnKnapp = new JButton("Finn korteste utvei");
        finnKnapp.addActionListener(e -> finnUtvei());

        JButton nullstillKnapp = new JButton("Nullstill");
        nullstillKnapp.addActionListener(e -> nullstill());

        JPanel topp = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topp.add(finnKnapp);
        topp.add(nullstillKnapp);
        topp.add(status);

        add(topp, BorderLayout.NORTH);
        add(kart, BorderLayout.CENTER);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    private void finnUtvei() {
        if (startRad < 0) {
            status.setText("Velg en startrute først");
            return;
        }
        labyrint.finnUtveiFra(startRad, startKol);
        sti = labyrint.korteste();

        if (sti == null) {
            status.setText("Ingen utvei fra (" + startRad + ", " + startKol + ")");
        } else {
            status.setText("Korteste utvei: " + sti.size() + " ruter");
        }
        kart.repaint();
    }

    private void nullstill() {
        startRad = -1;
        startKol = -1;
        sti = null;
        status.setText("Klikk på en hvit rute for å velge start");
        kart.repaint();
    }

    private class KartPanel extends JPanel {
        private static final int STANDARD_RUTE = 25;

        KartPanel() {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    velgRute(e.getX(), e.getY());
                }
            });
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(labyrint.antKolonner * STANDARD_RUTE,
                                 labyrint.antRader * STANDARD_RUTE);
        }

        private int rutestorrelse() {
            return Math.min(getWidth() / labyrint.antKolonner,
                            getHeight() / labyrint.antRader);
        }

        private void velgRute(int x, int y) {
            int s = rutestorrelse();
            int kol = (x - (getWidth() - s * labyrint.antKolonner) / 2) / s;
            int rad = (y - (getHeight() - s * labyrint.antRader) / 2) / s;

            if (rad < 0 || rad >= labyrint.antRader || kol < 0 || kol >= labyrint.antKolonner) return;

            if (labyrint.rute[rad][kol] instanceof SortRute) {
                status.setText("Du kan ikke starte i en sort rute");
                return;
            }
            startRad = rad;
            startKol = kol;
            sti = null;
            status.setText("Start: (" + rad + ", " + kol + "). Trykk på knappen.");
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int s = rutestorrelse();
            int offX = (getWidth() - s * labyrint.antKolonner) / 2;
            int offY = (getHeight() - s * labyrint.antRader) / 2;

            for (int rad = 0; rad < labyrint.antRader; rad++) {
                for (int kol = 0; kol < labyrint.antKolonner; kol++) {
                    Rute r = labyrint.rute[rad][kol];
                    if (r instanceof SortRute) g.setColor(Color.BLACK);
                    else if (r instanceof Åpning) g.setColor(new Color(150, 220, 150));
                    else g.setColor(Color.WHITE);

                    g.fillRect(offX + kol * s, offY + rad * s, s, s);
                    g.setColor(Color.LIGHT_GRAY);
                    g.drawRect(offX + kol * s, offY + rad * s, s, s);
                }
            }

            if (sti != null) {
                g.setColor(Color.RED);
                for (Koordinat k : sti) {
                    g.fillRect(offX + k.kolonne() * s + 1, offY + k.rad() * s + 1, s - 1, s - 1);
                }
            }

            if (startRad >= 0) {
                g.setColor(Color.BLUE);
                g.fillOval(offX + startKol * s + s / 4, offY + startRad * s + s / 4, s / 2, s / 2);
            }
        }
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java LabyrintGUI <filename>");
            return;
        }
        SwingUtilities.invokeLater(() -> new LabyrintGUI(args[0]).setVisible(true));
    }
}