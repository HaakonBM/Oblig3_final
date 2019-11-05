package no.oslomet.cs.algdat.Oblig3;

import java.util.Comparator;

public class EgenTest {

    public static void main (String[] args) {

        //Oppgave 0 og 1
        ObligSBinTre<Character> en = new ObligSBinTre<>(Comparator.naturalOrder());
        System.out.println(en.antall());
        ObligSBinTre<Integer> to = new ObligSBinTre<>(Comparator.naturalOrder());
        System.out.println(to.antall());



        Integer[] a = {4,7,2,9,5,10,8,1,3,6};
        ObligSBinTre<Integer> tre = new ObligSBinTre<>(Comparator.naturalOrder());
        for (int verdi : a) {
            tre.leggInn(verdi);
        }
        System.out.println(tre.inneholder(999));

        System.out.println(tre.inneholder(3));

    }
}
