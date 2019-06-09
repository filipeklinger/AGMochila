package agmochila;

import java.util.ArrayList;

/**
 *
 * @author filipe
 */
public class Evolucao {
    private ArrayList<Mochila> filhos;
    private ArrayList<Mochila> pais;
    Populacao pop;
    private Fabrica f;
    private final int taxaDeMutacao;
    
    /**
     * 
     * @param p objeto populacao
     * @param f objeto fabrica de itens
     * @param m int taxa de mutacao
     */
    public Evolucao(Populacao p,Fabrica f,int m){
        this.pais = p.getPopulacao();
        this.pop = p;
        this.f = f;
        this.taxaDeMutacao = m;
        
    }
    
    public void recombinacao(){
        filhos = new ArrayList<>();//importante zerar os filhos a cada recombinacao
        System.out.println("------->Ordenando por fitness");
        this.ordenaPorFitness();
        System.out.println("------->criando roleta");
        double[] chancesDeSelecao = this.criaRoleta();
        
        //aqui criamos filhos enquanto nao obtemos a mesma quantidade de individuos
        //da geracao anterior
        System.out.println("------->Gerando filhos");
        while(filhos.size()<pais.size()){
            int[] individuosSelecionados = this.selecao(chancesDeSelecao);
            Mochila[] f = this.criafilhos(individuosSelecionados);
            filhos.add(f[0]);
            filhos.add(f[1]);
        }
        //agora que criamos todos os filhos vamos substituir a populacao
        pop.setNewPopulacao(filhos);
    }
    
    private void ordenaPorFitness(){
        ArrayList<Mochila> OrdenadoFit = new ArrayList<>();
        //Aqui organizamos os Individuos por fitness em Outro vetor
            do{
                //vamos descobrir qual tem a menor distancia total percorrida
                //para isso comecamos com o maior valor possivel (Infinito)
                int maisValioso = 0;
                int escolhido = 0;
                for(int j=0;j < pais.size();j++){
                    if(pais.get(j).getValorTotal() > maisValioso){//a cada iteração o vetor diminui
                        maisValioso = pais.get(j).getValorTotal();//atualizamos o valor do comparador
                        escolhido = j;//pegamos o indice do mais apto
                    } 
                }
                OrdenadoFit.add(pais.get(escolhido));//adicionamos a lista de aptos
                pais.remove(escolhido);//removemos o mais apto para encontrar o proximo mais apto
                
            }while(pais.size() > 0);
            pais.addAll(OrdenadoFit);
    }
    
    /**
     * Cria uma roleta com base no fitness
     * @return vetor contento a porcentagem de cada individuo que que se identifica com seu indice
     */
    private double[] criaRoleta(){
        //calculando somatorio do fitness total
        double somatorio = 0;
        for(int i=0;i<pais.size();i++){
            somatorio += pais.get(i).getValorTotal();
        }
        //calculando proporcao de cada individuo na roleta
        double porcentagemDoIndividuo[] = new double[pais.size()];
        System.out.println("--------------------------------------------------");
        System.out.println("Porcentadem de cada individuo na geracao "+pop.getGeracao());
        double acm = 0;
        for(int i=0;i<pais.size();i++){
            porcentagemDoIndividuo[i] = (Double.valueOf(pais.get(i).getValorTotal()*100)/somatorio);
            acm+=porcentagemDoIndividuo[i];
            System.out.println("Individuo: "+i+" porcent: "+porcentagemDoIndividuo[i]+" Acumulado:"+acm);
        }
        System.out.println("--------------------------------------------------");
        
        return porcentagemDoIndividuo;
    }
    
    /**
     * Seleciona dois indices
     * @param porcentagemDoIndividuo vetor com as porcentagem de cada um ja calculadas
     * @return vetor com indice dos individuos a cruzarem
     */
    private int[] selecao(double[] porcentagemDoIndividuo){
        int indicePais[] = new int[2];    
        
        //sorteando um numero entre 0 e 100 para o Pai 1
        int sorteado = Fabrica.sorteiaDentre(100)-1;
        
        //buscando qual individuo esta dentro da faixa sorteada
        double acumulador = 0;
        for(int i=0;i<pais.size();i++){
            if(acumulador > sorteado) break;
            acumulador += porcentagemDoIndividuo[i];
            indicePais[0] = i;
        }
        //sorteando um numero entre 0 e 100 para o Pai 2
        sorteado = Fabrica.sorteiaDentre(100)-1;
        //buscando qual individuo esta dentro da faixa sorteada
        acumulador = 0;
        for(int i=0;i<pais.size();i++){
            if(acumulador > sorteado) break;
            acumulador += porcentagemDoIndividuo[i];
            indicePais[1] = i;
        }
        return indicePais;
    }
    
    public Mochila[] criafilhos(int[] individuosSelecionados){
        
        //selecionando ponto de corte
        int qtdItens = pais.get(0).getArrumacao().size();
        int corte = Fabrica.sorteiaDentre(qtdItens)-1;// -1 para ser de 0 a N-1
        //obtendo os pais
        Mochila pai1 = pais.get(individuosSelecionados[0]);
        Mochila pai2 = pais.get(individuosSelecionados[1]);
        
        //-----------------DEbug------------------------------------------------
        System.out.println("Pais selecionados"+" ( ponto de corte: "+corte+" ) "
                + "\n"+individuosSelecionados[0]+pai1.imprimeArrumacao()+" Fit: "+pai1.getValorTotal()+"\n"+individuosSelecionados[1]+pai2.imprimeArrumacao()+" Fit: "+pai2.getValorTotal());
        
        //----------------------------------------------------------------------
        Mochila filho1 = new Mochila();
        //obtendo os cromossomos dos pais de acordo com o ponto de corte 
        for(int i=0;i<qtdItens;i++){
            Item cromossomoAtual;
            if(i<corte){
                cromossomoAtual = pai1.getArrumacao().get(i);
            }else{
                cromossomoAtual = pai2.getArrumacao().get(i);
            }
            filho1.setItem(cromossomoAtual);
        }
        
        mutacao(filho1);
        
        Mochila filho2 = new Mochila();
        //obtendo os cromossomos dos pais de acordo com o ponto de corte 
        for(int i=0;i<qtdItens;i++){
            Item cromossomoAtual;
            if(i<corte){
                cromossomoAtual = pai2.getArrumacao().get(i);
            }else{
                cromossomoAtual = pai1.getArrumacao().get(i);
            }
            //enviando ao lab de mutacao
            //cromossomoAtual = this.mutacao(cromossomoAtual);
            filho2.setItem(cromossomoAtual);
        }
        mutacao(filho2);
        
        //-----------------DEbug-------------------
        //System.out.println("Filhos gerados:\n"+filho1.imprimeArrumacao()+"\n"+filho2.imprimeArrumacao());
        
        Mochila[] fs = {filho1,filho2};
     return  fs;
    }
    
    private void mutacao(Mochila filho){
        //busca aleatoriamente um indice de cromossomo a ser mutado
        int itemAserMutado = Fabrica.sorteiaDentre(filho.getArrumacao().size())-1;
        ArrayList<Item> its = filho.getArrumacao();
        
        //sorteia um numero e compara com a proabilidade da mutacao
        int roleta = Fabrica.sorteiaDentre(100)-1;
        
        if(roleta <= taxaDeMutacao){
            System.out.println("-------------->MUTACAO<----------------------");
            Item cromossoAtual = its.get(itemAserMutado);
            Item novo;
            do{
                novo = f.getItemAleatorio();
            }while(novo == cromossoAtual);//busca qualquer um que seja diferente do atual
            System.out.println(">>>>Mutado gene: "+cromossoAtual.getNome()+" Para: "+novo.getNome());
            filho.getArrumacao().set(itemAserMutado,novo);
        }
        
    }
    
}
