/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agmochila;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author filipe
 */
public class Fabrica {
    //para criar itens complexos
    //itens possiveis
    ArrayList<Item> iP;
    static int pesoMaximo;
    
    public Fabrica(ArrayList<Item> itensPossiveis,int peso){
        this.iP = itensPossiveis;
        Fabrica.pesoMaximo = peso;
    }
    
    public static int sorteiaDentre(int max){
        Random r = new Random();
        //sorteamos um numero aleatorio dentro do range max da roleta
        int sorteado = r.nextInt((int) max)+1;
        return sorteado;
    }
    
    public Item getItemAleatorio(){
            int sorteado = this.sorteiaDentre(iP.size())-1;//menos 1 pois o indice comeca em zero
            return iP.get(sorteado);
    }
    
    /**
     * @param qtdItens Numero de itens na mochila
     * @return Mochila com itens aleatorios que respeita o peso maximo 
     */
    public Mochila getMochilaInicial(int qtdItens){
        //retorna uma mochila com N itens aleatorios iniciais
        Mochila m = new Mochila();
        for(int i=0;i< qtdItens;i++){
            m.setItem(this.getItemAleatorio());
        }
        //se for maior que peso maximo chama a funcao novamente
        if(m.calculaPeso() > pesoMaximo){
            return this.getMochilaInicial(qtdItens);
        }else{
            return m;    
        }
        
    }
    
}
