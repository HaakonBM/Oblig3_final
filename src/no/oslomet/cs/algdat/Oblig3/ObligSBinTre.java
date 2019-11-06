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

  
  public String[] grener()
  {if(tom())return new String[0];

    String[] tabell = new String[1];

    StringJoiner a;

    ArrayDeque<Node<T>> b = new ArrayDeque();
    ArrayDeque<Node<T>> c = new ArrayDeque();

    boolean listeEmpty = false;

    Node<T> p = rot;

    int i = 0;

    while(!listeEmpty){
      a = new StringJoiner(", ","[","]");

      while( p.venstre!=null || p.høyre!=null) {

        if( p.venstre!=null ) {

          if( p.høyre!=null ) b.add(p.høyre);

          p = p.venstre;

        }else if(p.høyre!=null){

          p = p.høyre;
        }
      }

      while(p!=null) {c.add(p);p=p.forelder;}

      while(!c.isEmpty())
        a.add(c.pollLast().toString());

      if(tabell[tabell.length-1]!=null)
        tabell = Arrays.copyOf(tabell, tabell.length+1);
      tabell[i++] = a.toString();

      if(!b.isEmpty()) p = b.pollLast();

      else listeEmpty = true;
    }
    return tabell;
  }
  
  public String bladnodeverdier()
  {
    throw new UnsupportedOperationException("Ikke kodet ennå!");
  }
  
  public String postString()
  {
    throw new UnsupportedOperationException("Ikke kodet ennå!");
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
    
    private BladnodeIterator()  // konstruktør
    {
      throw new UnsupportedOperationException("Ikke kodet ennå!");
    }
    
    @Override
    public boolean hasNext()
    {
      return p != null;  // Denne skal ikke endres!
    }
    
    @Override
    public T next()
    {
      throw new UnsupportedOperationException("Ikke kodet ennå!");
    }
    
    @Override
    public void remove()
    {
      throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

  } // BladnodeIterator

} // ObligSBinTre
