package no.oslomet.cs.algdat.Oblig3;

////////////////// ObligSBinTre /////////////////////////////////

import java.util.*;

public class ObligSBinTre<T> implements Beholder<T>
{
  private static final class Node<T>   // en indre nodeklasse
  {
    private T verdi;                   // nodens verdi
    private Node<T> venstre, høyre;    // venstre og høyre barn
    private Node<T> forelder;          // forelder

    // konstruktør
    private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder)
    {
      this.verdi = verdi;
      venstre = v; høyre = h;
      this.forelder = forelder;
    }

    private Node(T verdi, Node<T> forelder)  // konstruktør
    {
      this(verdi, null, null, forelder);
    }

    @Override
    public String toString(){ return "" + verdi;}

  } // class Node

  private Node<T> rot;                            // peker til rotnoden
  private int antall;                             // antall noder
  private int endringer;                          // antall endringer

  private final Comparator<? super T> comp;       // komparator

  public ObligSBinTre(Comparator<? super T> c)    // konstruktør
  {
    rot = null;
    antall = 0;
    comp = c;
  }

  @Override
  public boolean leggInn(T verdi)  {
    Node<T> p = rot;
    Node<T> q = null;

  int cmp= 0;
  while (p!=null){
    q=p;
    cmp= comp.compare(verdi, p.verdi);
    p = cmp < 0 ? p.venstre : p.høyre;
  }

  p= new Node<> (verdi, null);
  if (q==null)
    rot=p;
  else if (cmp < 0)
     q.venstre= p;
  else
    q.høyre=p;

  if (q!=null)
    p.forelder=q;
  else
    p.forelder= q;

  antall++;
  return true;

  }

  @Override
  public boolean inneholder(T verdi)
  {
    if (verdi == null) return false;

    Node<T> p = rot;

    while (p != null)
    {
      int cmp = comp.compare(verdi, p.verdi);
      if (cmp < 0) p = p.venstre;
      else if (cmp > 0) p = p.høyre;
      else return true;
    }

    return false;
  }

  @Override
  public boolean fjern(T verdi) ////////////// Opg 5
  {
      if (verdi == null) {
          return false;
      }

      Node<T> p = rot;

      while (p != null) {
          int compare = comp.compare(verdi, p.verdi); {
              if (compare < 0) {
                  p = p.venstre;
              } else if (compare > 0) {
                  p = p.høyre;
              } else {
                  break;
              }
          }
      }
      if (p==null) {
          return false;
      }

      Node<T> b = rot;
      if (p.høyre == null || p.venstre == null) {
          if (p.venstre != null) {
              b = p.venstre;
          } else {
              b = p.høyre;
          }

          if (p == rot) {
              rot = b;
              if (b != null) {
                  b.forelder = null;
              }
          } else if (p == p.forelder.venstre) {
              if (b!= null) {
                  b.forelder = p.forelder;
                  p.forelder.venstre = b;
              }
          } else {
              if (b != null) {
                  b.forelder = p.forelder;
              }
              p.forelder.høyre = b;
          }
      } else {

          Node<T> r = p.høyre;
          while (r.venstre != null) {
              r = r.venstre;
          }
          p.verdi = r.verdi;

          if (r.forelder != p) {
              Node<T> q = r.forelder;
              q.venstre = r.høyre;
              if (q.venstre != null) {
                  q.venstre.forelder = q;
              }
          } else {
              p.høyre = r.høyre;
              if (p.høyre != null) {
                  p.høyre.forelder = p;
              }
          }
      }

      antall--;
      return true;

  }

  public int fjernAlle(T verdi) /////////////// Opg 5
  {
      int i = 0;
      boolean fjernet = true;
      while(fjernet!=false){
          if(fjern(verdi))
              i++;
          else
              fjernet = false;
      }
      return i;
  }

  @Override
  public int antall()
  {
    return antall;
  }

  
  public int antall(T verdi)
  {
    if (verdi==null)
      return 0;

    int n=0;
    Node<T> p = rot;
    while (p!=null){
      int cmp= comp.compare(verdi, p.verdi);
      if (cmp < 0)
        p= p.venstre;
      else if (cmp > 0)
        p= p.høyre;
      else {
        n++;
        p= p.høyre;
      }
  }
    return n;
  }
  
  @Override
  public boolean tom()
  {
    return antall == 0;
  }
  
  @Override
  public void nullstill() /////////// Opg 5
  {
    Node<T> p = rot, q = null; // q er forelder til p

    while ( rot != null) {
      p = rot; q= null;
      while (p.høyre != null || p.venstre != null) {
        if(p.høyre != null && p.venstre != null){
          p = p.venstre;
        }
        else if(p.høyre == null) {
          p = p.venstre;
        }
        else if (p.venstre == null){
          p = p.høyre;
        }
      }
      p.høyre = null;
      p.venstre = null;
      p.forelder = null;
      p.verdi = null;
    }
  }

  // Hvis p.høyre ikke er null, finn den minste indeks nederst til venstre side av treet.
  private static<T> Node<T> minVerdi(Node<T> p){
    if (p == null)
      return null;
    if (p.venstre != null)
      return minVerdi(p.venstre);
    return p;
  }
  
  private static <T> Node<T> nesteInorden(Node<T> p) {
    if (p == null)
      return null;
    // hvis p.høyre ikke er null
    if (p.høyre != null)
      return minVerdi(p.høyre);
    // else hvis p.høyre er null
    Node n = p.forelder;
    Node curr= p;
    while (n != null && curr == n.høyre) {
      curr= n;
      n = n.forelder;
    }
    return n;
  }
  
  @Override
  public String toString()
  {
      StringBuilder s = new StringBuilder();
      s.append('[');
      Node p = rot;
      if(p!=null){
          while(p.venstre != null)
              p= minVerdi(p);
          s.append(p);
          while(nesteInorden(p)!=null){
              p = nesteInorden(p);
              s.append(",").append(" ").append(p);
          }
      }
      s.append(']');
      return s.toString();
  }
  
  public String omvendtString()
  {
    StringBuilder s = new StringBuilder();
    TabellStakk tStack = new TabellStakk();
    s.append('[');

    Node p = minVerdi(rot);
    if(p != null){
        tStack.leggInn (p);
        while (nesteInorden(p) != null) {
        p = nesteInorden(p);
        tStack.leggInn (p);
      }
    }

    while ( !tStack.tom() )
    {
      s.append( tStack.taUt() );
      if(!tStack.tom())
        s.append(",").append(" ");
    }
    s.append(']');
    return s.toString();
  }

  public String høyreGren()
  {
    StringJoiner s = new StringJoiner(", ", "[", "]");

    if (!tom()) {
      Node<T> p = rot;

      while (true)
      {
        s.add(p.verdi.toString());
        if (p.høyre != null) {
          p = p.høyre;
        }

        else if (p.venstre != null) {
          p = p.venstre;
        }
        else break;
      }
    }
    return s.toString();
  }

  public String lengstGren()
  {
    if (tom()) return "[]";

    Kø<Node<T>> a = new TabellKø<>();
    a.leggInn(rot);

    Node<T> p = null;

    while (!a.tom())
    {
      p = a.taUt();
      if (p.høyre != null) {
        a.leggInn(p.høyre);
      }
      if (p.venstre != null) {
        a.leggInn(p.venstre);
      }
    }
    Stakk<T> s = new TabellStakk<>();
    while (p != null)
    {
      s.leggInn(p.verdi);
      p = p.forelder;
    }
    return s.toString();
  }


    // Testen for oppgaven står og kjører og kjører, hvis jeg setter en break der jeg har kommentert den bort får jeg en feil i oppgave 7d, så jeg tror feilen skjer i deloppgave 7e
  public String[] grener()
  {
      if(tom()){
          return new String[0];
      }
      int i =0;
      String[] stringTabell = new String[1];
      StringJoiner s;
      ArrayDeque<Node<T>> a = new ArrayDeque();
      ArrayDeque<Node<T>> b = new ArrayDeque();

      boolean listeEmpty = false;

      Node<T> p = rot;

      while(!listeEmpty){
          s = new StringJoiner(", ","[","]");

          while(p.venstre!=null || p.høyre!=null) {
              if(p.venstre!=null) {
                  if(p.høyre!=null) a.add(p.høyre);
                  p = p.venstre;
              }else {
                  p = p.høyre;
              }
              //break;  // hvis jeg setter en break her, får jeg feilmelding i oppgave 7d så problemet ligger i deloppgave 7e , skjønner ikke hvorfor den bare står og kjører
          }

          while(p!=null) {
              b.add(p);
              p = p.forelder;
          }
          while(!b.isEmpty()){
              s.add(b.pollLast().toString());
          }

          if(stringTabell[stringTabell.length-1]!=null)
              stringTabell = Arrays.copyOf(stringTabell, stringTabell.length+1);
          stringTabell[i++] = s.toString();

          if(!a.isEmpty()) p = a.pollLast();
          else listeEmpty = true;
      }

      return stringTabell;
  }
  
  public String bladnodeverdier()
  {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    Node p = rot;
    finnBladnode(p, sb);
    sb.append("]");
    return sb.toString();
  }// slutt bladnodeverdier

  private void finnBladnode(Node p, StringBuilder sb) {
    if (p == null) {
      return;
    }
    if(p.venstre == null && p.høyre == null){
      if(!sb.toString().equals("[")){
        sb.append(",").append(" ").append(p);
      } else {
        sb.append(p);
      }
    }
    finnBladnode(p.venstre, sb);
    finnBladnode(p.høyre, sb);
  }// slutt finnBladnode

    // hjlpemetoden
    private static <T> Node<T> førsteBladnode(Node<T> p)
    {
        while (true)
        {
            if (p.venstre != null) p = p.venstre;
            else if (p.høyre != null) p = p.høyre;
            else return p;
        }
    }

    // hjelpemetoden
    private static <T> Node<T> nesteBladnode(Node<T> p)
    {
        Node<T> f = p.forelder;
        while (f != null && (p == f.høyre || f.høyre == null))
        {
            p = f; f = f.forelder;
        }

        return f == null ? null : førsteBladnode(f.høyre);
    }
  
  public String postString() {

      if (tom()) return "[]";

      StringJoiner sj = new StringJoiner(", ", "[", "]");

      Node<T> p = førsteBladnode(rot);

      while (true)
      {
          sj.add(p.verdi.toString());

          if (p.forelder == null) break;

          Node<T> f = p.forelder;

          if (p == f.høyre || f.høyre == null) p = f;
          else p = førsteBladnode(f.høyre);
      }

      return sj.toString();
  }
  
  @Override
  public Iterator<T> iterator()
  {
    return new BladnodeIterator();
  }
  
  private class BladnodeIterator implements Iterator<T>
  {
    private Node<T> p = rot, q = null;
    private boolean removeOK = false;
    private int iteratorendringer = endringer;

    // konstruktør
    private BladnodeIterator()  {
        if (tom()) return;
        // ved bruk av en hjelpemetode
        p = førsteBladnode(rot);
        q = null;
        removeOK = false;
        iteratorendringer = endringer;
    }
   


    @Override
    public boolean hasNext() {

      return p != null;  // Denne skal ikke endres!
    }

    
    @Override
    public T next() {
        if (!hasNext()) throw new NoSuchElementException("Ikke flere bladnodeverdier!");

        if (endringer != iteratorendringer) throw new
                ConcurrentModificationException("Treet har blitt endret!");

        removeOK = true;
        // ved bruke av en hjelpemetode
        q = p; p = nesteBladnode(p);

        return q.verdi;

    }
    
    @Override
    public void remove() {
        if (!removeOK) throw
                new IllegalStateException("Ulovlig kall på remove()!");

        if (endringer != iteratorendringer) throw new
                ConcurrentModificationException("Treet har blitt endret!");

        removeOK = false;

        Node<T> f = q.forelder;

        if (f == null) rot = null;
        else if (q == f.venstre) f.venstre = null;
        else f.høyre = null;

        antall--;
        endringer++;
        iteratorendringer++;
    }// slutt remove

  } // BladnodeIterator

} // ObligSBinTre
