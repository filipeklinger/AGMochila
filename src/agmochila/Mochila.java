/*
 * O Cromossomo é a nossa mochila onde iremos inserir os objetos
 */
package agmochila;

import java.util.ArrayList;

/**
 *
 * @author filipe
 */
public class Mochila {
    ArrayList<Item> arrumacao;
    int valor;//nosso fitness

    public Mochila(){
        arrumacao = new ArrayList<>();
        valor = 0;
    }
    
    public ArrayList<Item> getArrumacao() {
        return arrumacao;
    }
    
    public String imprimeArrumacao(){
        String arr = "=>";
        for(Item arruItem: arrumacao){
            arr += arruItem.getNome()+" /";
        }
        return arr;
    }

    public void setItem(Item item) {
        this.arrumacao.add(item);
    }
    
    public int calculaPeso(){
        int pesoTotal = 0;
        for(Item arruItem: arrumacao){
            pesoTotal += arruItem.getPeso();
        }
        return pesoTotal;
    }
    
    public int getValorTotal() {
        return valor;
    }

    public void setValorTotal(int valor) {
        this.valor = valor;
    }
}
