package no.oslomet.cs.algdat.Oblig3;

import java.nio.charset.CharsetEncoder;
import java.util.Comparator;
import java.util.Iterator;

public class EgenTest {

    public static void main(String[] args) {

        //Oppgave 0 og 1
        no.oslomet.cs.algdat.Oblig3.ObligSBinTre<Character> tre =
                new ObligSBinTre<>(Comparator.naturalOrder());

        char [] verdier = "IATBHJCRSOFELKGDMPQN" .toCharArray();
        for ( char c : verdier) tre.leggInn(c);
        System. out .println(tre.høyreGren() + " " + tre.lengstGren());
        for (Character c : tre) System. out .print(c + " " ); // D G K N Q S


    }
}
