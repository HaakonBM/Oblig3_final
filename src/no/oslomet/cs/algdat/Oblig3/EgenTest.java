package no.oslomet.cs.algdat.Oblig3;

import java.util.Comparator;

public class EgenTest {

    public static void main (String[] args) {

        //Oppgave 0 og 1
        int [] a = {4,7,2,9,4,10,8,7,4,6,1};
        ObligSBinTre<Integer> tre = new ObligSBinTre<>(Comparator. naturalOrder ());
        for ( int verdi : a) tre.leggInn(verdi);

        System. out .println(tre); // [1, 2, 4, 4, 4, 6, 7, 7, 8, 9, 10]

        int [] b = {4,7,2,9,4,10,8,7,4,6,1};
        ObligSBinTre<Integer> treEn = new ObligSBinTre<>(Comparator. naturalOrder ());
        for ( int verdi : b) treEn.leggInn(verdi);
        System. out .println(treEn.omvendtString());

    }
}
