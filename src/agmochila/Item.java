/*
 * Item da mochila
 */
package agmochila;

/**
 *
 * @author filipe
 */
public class Item {
    String n;
    int p;
    int v;
    
    public Item(String nome,int peso,int valor){
        this.n = nome;
        this.p = peso;
        this.v = valor;
    }
    
    public String getNome() {
        return n;
    }

    public int getPeso() {
        return p;
    }

    public int getValor() {
        return v;
    }
    
}
