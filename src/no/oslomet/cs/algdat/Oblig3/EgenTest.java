package no.oslomet.cs.algdat.Oblig3;

import java.util.Comparator;

public class EgenTest {

    public static void main (String[] args) {

        //Oppgave 0 og 1
        no.oslomet.cs.algdat.Oblig3.ObligSBinTre<Integer> tre =
                new ObligSBinTre<>(Comparator.naturalOrder());

        tre.leggInn(6);
        System.out.println(tre.toString());
        tre.fjern(6);

        System.out.println(tre.toString());

        int[] a = {6, 3, 9, 1, 5, 7, 10, 2, 4, 8, 11, 6, 8};
        for (int verdi : a) tre.leggInn(verdi);

        System.out.println(tre.toString());
        tre.fjern(11);
        System.out.println(tre.toString());

        String s;

        tre.fjern(12);
        System.out.println(tre.toString());
        System.out.println("test");
        tre.fjern(2);

        System.out.println(tre.toString());
        tre.fjernAlle(6);
        System.out.println(tre.toString());
    }
}
