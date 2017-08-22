//Bruno Dourado Miranda 
//--Algoritmos de Busca em Profundidade e Busca em Largura--
//Data: 02/2015

//Uma análise de custo será feita a partir deste código, buscando descobrir o custo genérico 
//de cada método.

// O vetor v é o vetor que armazena os vértices do grafo.
package fila;

import javax.swing.JOptionPane;

class Vertice {//Cria variável e atribui valor
    String nome = null;//Custo O(2)
    Vertice adjacente = null;//Custo O(2)          --Custo total do método: O(4)--
}

class ControleFila {//Cria variável
    Vertice inicio;//Custo O(1)
    Vertice fim;//Custo O(1)                        --Custo total do método: O(2)--
}

class FilaEncadeada {

    ControleFila add(ControleFila q, String nome) {
        //Caso Fila Não Vazia,custo: Ω(6); Caso Fila Vazia, custo: O(7);
        Vertice novoElemento = new Vertice();//Custo O(1)
        novoElemento.nome = nome;//Custo O(1)

        if (q.inicio != null) {//Custo O(1) tanto para if quanto p/ else
            q.fim.adjacente.adjacente = novoElemento;//Custo O(1)
            q.fim.adjacente = novoElemento;//Custo O(1)
        } else {
            q.inicio = new Vertice();//Custo O(1)
            q.fim = new Vertice();//Custo O(1)
            q.inicio.adjacente = novoElemento;//Custo O(1)
            q.fim.adjacente = novoElemento;//Custo O(1)
        }

        return q;//Custo O(1)       --Custo total do método: O(7)--
    }

    ControleFila remove(ControleFila q) {
        //Apenas compara se tem elementos na fila, se houver, remove. Para tal operação O(2).
        if (q.inicio != null) {//Custo O(1)
            q.inicio.adjacente = q.inicio.adjacente.adjacente;//Custo O(1)
        }
        return q;//Custo O(1)           --Custo total do método: O(2)--
    }

    boolean isEmpty(ControleFila q) {//Compara e retorna true caso Fila Vazia --custo total do método: O(2)--
        return q.inicio.adjacente == null;
    }

    String peek(ControleFila q) {//Retorna o primeiro elemento da fila, 
        if (q.inicio != null) {//Custo O(1)
            return q.inicio.adjacente.nome;//Custo O(1)
        } else {
            return null;//Custo O(1)    --Custo total do método: O(2)--       
        }
    }
}

class BuscaEmLargura {

    int[] bfs(Vertice vetor[], int indice) {

        ControleFila Q = new ControleFila();//Custo O(1)
        FilaEncadeada f = new FilaEncadeada();//Custo O(1)
        int distancia[] = new int[vetor.length];//Custo O(1)
        String color[] = new String[vetor.length];//Custo O(1)
        int pi[] = new int[vetor.length];//Custo O(1)
        int i = 0, u = 0, v = 0;//Custo O(3)
        Vertice aux = new Vertice();//Custo O(1)

        for (i = 0; i < vetor.length; i++) {//Custo O(v)
            color[i] = "white";
            distancia[i] = 999;
            pi[i] = -1;
        }
        color[indice] = "gray";//Custo O(1)
        distancia[indice] = 0;//Custo O(1)
        Q = f.add(Q, Integer.toString(indice));//Custo O(7)

        while (!f.isEmpty(Q)) {// Custo do laço: O(v+e)
            u = Integer.valueOf(f.peek(Q));
            aux = vetor[u];
            while (aux.adjacente != null) {//Custo do laço: O(e)
                v = retornaPosicaoVertice(vetor, aux.adjacente.nome);//Custo O(v)
                if (color[v].equals("white")) {
                    color[v] = "gray";
                    distancia[v] = distancia[u] + 1;
                    pi[v] = u;
                    Q = f.add(Q, Integer.toString(v));
                }
                aux = aux.adjacente;
            }
            Q = f.remove(Q);
        }     
        return distancia;//Custo O(1)  --Custo do método: 18 + v + (v+e)+ e*(v+2) + retorno = (2v+18) + ( e * v ) ---logo Custo total=  O( e * v )--- 
    }

    int retornaPosicaoVertice(Vertice vetor[], String nome) {
        int i = 0;//Custo O(1)
        while (!vetor[i].nome.equals(nome)) {
            i++;//Custo O(v)
        }
        return i;//Custo O(1)       --Custo do método: O(v+2)--
    }

}

class BuscaEmProfundidade {

    public String color[];//Custo O(1)
    public int distancia[];//Custo O(1)
    public int pi[];//Custo O(1)
    public int vez;//Custo O(1)

    public BuscaEmProfundidade(Vertice vetor[]) {// Custo da criação + atribuição para as variáeis
        color = new String[vetor.length];//Custo O(1)
        distancia = new int[vetor.length];//Custo O(1)
        pi = new int[vetor.length];//Custo O(1)
        this.vez = 0;//Custo O(1)                   --Custo do método: O(8)--
    }

    int[] dfs_Visit(Vertice vetor[], int u) {
        color[u] = "gray";//Custo O(1)
        vez = vez + 1;//Custo O(1)
        distancia[u] = vez;//Custo O(1)
        int v = 0;//Custo O(1)
        Vertice aux = new Vertice();//Custo O(1)
        aux = vetor[u];//Custo O(1)

        while (aux.adjacente != null) {//Custo O(e*v)
            v = retornaPosicaoVertice(vetor, aux.adjacente.nome);
            if (color[v].equals("white")) {
                pi[v] = u;
                distancia = dfs_Visit(vetor, v);
            }
            aux = aux.adjacente;
        }
        color[u] = "black";//Custo O(1)
        return distancia;//Custo O(1)               --Custo do método: O(e*v)--
    }

    int[] dfs(Vertice vetor[]) {
        int i = 0, v = 0;

        for (i = 0; i < vetor.length; i++) {//Custo : O(v)
            color[i] = "white";
            pi[i] = -1;
        }

        for (i = 0; i < vetor.length; i++) {//Custo O(v)
            if (color[i].equals("white")) {

                distancia = dfs_Visit(vetor, i);//Custo: O(e*v+2)
            }
        }
        return distancia;//Custo O(1)               --Custo do método: O(e*v)--
    }

    int retornaPosicaoVertice(Vertice vetor[], String nome) {
        int i = 0;//Custo O(1)
        while (!vetor[i].nome.equals(nome)) {//Custo O(v)
            i++;
        }
        return i;//Custo O(1)           --Custo do método: O(v+2)--
    }

}

public class Fila {

    public static void main(String[] args) {
        String str= JOptionPane.showInputDialog(null,"Insira a quantidade de vertices:");
        int qte= Integer.parseInt(str);
        Vertice vetor[] = new Vertice[qte];//Custo O(2)
        boolean thatsOk=false;
        int i=0,arestas=0;
        
        for (i = 0; i < qte; i++) {
            vetor[i] = new Vertice();
            vetor[i].nome = JOptionPane.showInputDialog(null, "Insira o nome do vértice:");
            str = JOptionPane.showInputDialog(null, "O vértice contem quantas arestas?");
            ControleFila controle = new ControleFila();
            FilaEncadeada Q = new FilaEncadeada();

            try {
                arestas = Integer.parseInt(str);
                thatsOk=true;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Numero invalido!");
                i--;
                arestas = 0;
            }

            while (arestas > 0) {
                str = JOptionPane.showInputDialog(null, "Insira o vértice adjacente:");
                Q.add(controle, str);
                arestas--;         
            }
            if(thatsOk==true){
                vetor[i].adjacente = controle.inicio.adjacente;
                thatsOk=false;
            }
        }    //---- Custo da construção da lista de adjacencias: O(e+v)
        
        str=JOptionPane.showInputDialog(null,"Insira a partir de qual vértice deseja percorrer o grafo:");
        i=0;  
        
        while(!vetor[i].nome.equals(str)){
            i++;
        }// Custo: O(v)
        
        
        BuscaEmLargura objBEL = new BuscaEmLargura();//Custo O(2)
        int d[] = new int[qte];//Custo O(2)
        BuscaEmProfundidade objBEMP = new BuscaEmProfundidade(vetor);//Custo O(2)
        int dBEMP[] = new int[qte];//Custo O(2)
        d = objBEL.bfs(vetor,i);// Custo O(e*v)
        dBEMP = objBEMP.dfs(vetor);//Custo O(e*v)

        
        String str2 = "", str3 = "";
        for (i = 0; i < d.length; i++) {
            str2 = str2 + Integer.toString(d[i]) + "  ";
            str3 = str3 + Integer.toString(dBEMP[i])+ "  ";
        }
        
        JOptionPane.showMessageDialog(null, "A distancia em largura do vértice escolhido é:\n" + str2
                + "\n\nA distância em profundidade do vértice escolhido é:\n" + str3);

        
        
        //Custo aproximado de ambas as buscas: 2 x[ O(e*v) ];
        // Custo aproximado da construção da lista de adjacencias: O(e+v)
        //Custo da amostra de buscar o vértice para buscar a distânica dele: O(v)
        //Custo da amostra das distancias: O(v)
        // Custo do algoritmo é da ordem de: O(e*v)
                
    }

}
