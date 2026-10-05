# Labyrint

Et lite objektorientert Java-prosjekt som leser en labyrint fra fil og finner alle utveier fra et gitt startpunkt. Den korteste utveien vises i utskriften, med antall steg per rute.

## Hvordan det fungerer

Labyrinten leses fra en tekstfil der `#` er en sort rute (vegg) og `.` er en hvit rute. Hvite ruter i ytterkanten er åpninger (utganger). Søket er en rekursiv dybde-først-søk der hver rute selv bestemmer hva som skjer når søket når den (polymorfi).

### Klassestruktur

```
Rute (abstract)
├── SortRute        – stopper søket
└── HvitRute        – utforsker naboene sine videre
    └── Åpning      – registrerer en funnet utvei
```

- **`Labyrint`** leser filen, bygger rutenettet, kobler sammen naboer og samler utveiene.
- **`Rute`** er abstrakt og definerer `finn(...)`, som hver subklasse implementerer på sin måte.
- **`Koordinat`** er en `record` som representerer en posisjon i labyrinten.
- **`Main`** tar imot filnavn og startkoordinater fra brukeren.
- **`LabyrintGUI`** er en enkel visualisering (se under).

## Kjøre programmet

Skriv inn startkoordinater som `<rad> <kolonne>`, for eksempel `1 1`. Skriv `-1` for å avslutte.

Det ligger også en større testlabyrint i `labyrintTest.txt`.

## Om bruk av AI

`LabyrintGUI.java` er generert med AI og er kun ment som visualisering av resultatet. Den er ikke en del av selve oppgaven. All logikk i `Labyrint`, `Rute`, `SortRute`, `HvitRute`, `Åpning`, `Koordinat` og `Main` er skrevet av meg.

- **Søket finner alle utveier**, ikke bare den korteste, så kjøretiden vokser eksponentielt med antall løkker og åpne flater. Et BFS-søk ville funnet korteste vei direkte og mye mer effektivt.
- Tilstand (`besokt`, `antSteg`) lagres på rutene og bør nullstilles mellom søk.
- Feltene bør gjøres `private` med tilgangsmetoder, og `Labyrint` bør kaste exceptions i stedet for å skrive ut og kalle `System.exit`.
- Mangler enhetstester.
