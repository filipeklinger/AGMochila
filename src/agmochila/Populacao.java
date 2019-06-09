/*
 * Aqui criamos a populacao inicial e gerenciamos a quantidade
 */
package agmochila;

import java.util.ArrayList;

/**
 *
 * @author filipe
 */
public class Populacao {
    private ArrayList<Mochila> mochilas;
    private Fabrica f;
    private int tam,qtdItens,geracao;
    
    
    public Populacao(Fabrica f,int tamanhoDaPopulacao,int qtdItensNaMochila){
        this.mochilas = new ArrayList<>();
        this.f = f;
        this.tam = tamanhoDaPopulacao;
        this.qtdItens = qtdItensNaMochila;
        this.geracao = 1;
    }
    
    public void setNewPopulacao(ArrayList<Mochila> filhos){
        this.mochilas = filhos;
        this.geracao++;//aqui contaremos as geracoes
    }
    
    public int getGeracao(){
        return this.geracao;
    }
    
    public ArrayList<Mochila> getPopulacao(){
        return this.mochilas;
    }
    
    public void criaInicial(){
        //Criando populacao de mochilas 
        //um conjunto de possiveis arrumacoes
        for(int i=0;i<this.tam;i++){
            this.mochilas.add(f.getMochilaInicial(qtdItens));
        }
    }
    
    public String mostraPopulacao(){
        String p = "";
        
        for(Mochila moch: mochilas){
            p +=" "+ moch.imprimeArrumacao()+" Fitness: "+moch.getValorTotal()+" Peso: "+moch.calculaPeso()+"\n";
        }
        return p;
    }
    
    
}
