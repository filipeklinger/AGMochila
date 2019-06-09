/*
 * Esse Algoritmo genetico se Proproe resolver o problema da mochila
 * Maximizando o valor enquanto respeita a carga maxima.
 * Foi utilizado o operador de Cross Over "Um ponto", onde sao gerados 2 filhos por vez
 * A taxa de Mutação é de 5%
 */
package agmochila;

import java.util.ArrayList;

/**
 *
 * @author filipe
 */
public class AgMainClass {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<Item> itens = new ArrayList<>();
        //items possiveis na mochila
        //Item(String nome,int peso,int valor)
        itens.add(new Item("Notebook",8,100));
        itens.add(new Item("Caneta",1,2));
        itens.add(new Item("Lapis",1,1));
        itens.add(new Item("Celular",2,50));
        itens.add(new Item("Livro",4,60));
        itens.add(new Item("Caderno",3,15));
        itens.add(new Item("Casaco",3,40));
        itens.add(new Item("Carteira",2,60));
        //-----------------------Parametros-------------------------------------
        int taxaMutacao = 5;
        int PesoMaximo = 22;//em kilos
        int tamPopulacao = 4;//individuos vivos por geracao
        int qtdItensNaMochila = 5;//quantidade de genes em cada individuo (Funciona melhor de 4 a 6 itens)
        //----------------------Fim parametros----------------------------------
        
        
        Fabrica f = new Fabrica(itens,PesoMaximo);//Fabrica de Mochilas e itens aleatorios para mutacao
        //---------------------INICIO AG----------------------------------------
        //popula
        Populacao p = new Populacao(f, tamPopulacao, qtdItensNaMochila);
        p.criaInicial();
        
        
        //avalia
        Avaliacao av = new Avaliacao();
        Avaliacao.calculaFitness(p);
        System.out.println("Populacao inicial: \n"+p.mostraPopulacao());
        
        Evolucao e = new Evolucao(p, f,taxaMutacao);
        
        do{//reproduz enquanto
            e.recombinacao();
            Avaliacao.calculaFitness(p);
            System.out.println("---------------Geracao:"+p.getGeracao()+"-------------------");
            System.out.println(p.mostraPopulacao());
            
        }while(Avaliacao.continuar(450, p.getGeracao()));//opcao 1 stagnacao (melhor possivel parece ser 370)
        //}while(Avaliacao.qualidadeDesejada(350));//opcao 2 qualidade minima da solucao
        
        //-------------------Solucao AG-----------------------------------------
        System.out.println("-----------------------Resultado--------------------");    
        System.out.println("Populacao final, geracao:"+p.getGeracao()+": \n"+p.mostraPopulacao());
        System.out.println("Somente entra no mais apto de estiver dentro do peso maximo: "+Fabrica.pesoMaximo);
        System.out.println("Mais apto com "+qtdItensNaMochila+" itens:\n"+av.getMaisApto());
        
        
        
        
        
        
        
    }
    
}
