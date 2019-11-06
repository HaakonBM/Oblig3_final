package no.oslomet.cs.algdat.Oblig3;

import java.nio.charset.CharsetEncoder;
import java.util.Comparator;
import java.util.Iterator;

public class EgenTest {

    public static void main(String[] args) {

        //Oppgave 0 og 1
        no.oslomet.cs.algdat.Oblig3.ObligSBinTre<Integer> tre =
                new ObligSBinTre<>(Comparator.naturalOrder());

        tre.leggInn(6);
        System.out.println(tre.toString());
        tre.fjern(6);

        System.out.println(tre.toString());

        int[] a = {6, 3, 9, 1, 5, 7, 10, 2, 4, 8, 11, 6, 8};
        for (int verdi : a) tre.leggInn(verdi);



    }
}
