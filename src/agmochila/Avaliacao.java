package agmochila;

import java.util.ArrayList;

/**
 *
 * @author filipe
 */
public class Avaliacao {
    //recebe uma populacao e avalia seu fitness
    ArrayList<Mochila> populacao;
    static private Mochila melhorIndividuo;
    static private int geracaoDoMelhor;
    
    public Avaliacao(){
        //this.populacao = populacao.getPopulacao();
        melhorIndividuo = new Mochila();//inicializamos com uma mochila vazia
        geracaoDoMelhor = 0;
    }
    
    public static void calculaFitness(Populacao p){
        for(Mochila m: p.getPopulacao()){
            //em cada mochila devemos percorrer todos os itens e obter a soma dos valores
            int valor = 0;
            for(Item i: m.getArrumacao()){
                valor+= i.getValor();//aqui estamos entrando no valor unitario de cada item
            }
            m.setValorTotal(valor);//aqui setamos o fitness do individuo
        }
        obtemMelhor(p);//apos calcular fitness verificamos se houve melhora
    }
    
    private static void obtemMelhor(Populacao p){
        
        for(Mochila m: p.getPopulacao()){
        //agora escolhemos o melhor individuo para nosso criterio de parada
            if(m.getValorTotal() > melhorIndividuo.getValorTotal() && m.calculaPeso() <= Fabrica.pesoMaximo){
                melhorIndividuo = m;//armazenamos o melhor 
                geracaoDoMelhor = p.getGeracao();//inserimos a geracao do melhor encontrado
            }
        }
        
    }
    
    /**
     * Aqui definimos quanto e o maximo de geracoes que devem ocorrer apos achar o melhor
     * @param maxGeracoesSemMelhora
     * @param geracaoAtual
     * @return 
     */
    public static boolean continuar(int maxGeracoesSemMelhora,int geracaoAtual){
        return (geracaoAtual - Avaliacao.geracaoDoMelhor) < maxGeracoesSemMelhora;
    }
    
    public String getMaisApto(){
        String mApto = "Geracao:"+Avaliacao.geracaoDoMelhor+" Arrumacao: "+Avaliacao.melhorIndividuo.imprimeArrumacao()+" Fitness: "+Avaliacao.melhorIndividuo.getValorTotal()+" Peso: "+Avaliacao.melhorIndividuo.calculaPeso();
        return mApto;
    }
    public static boolean qualidadeDesejada(int qualidade){
        return Avaliacao.melhorIndividuo.getValorTotal() < qualidade;
    }
    
    
    
}
