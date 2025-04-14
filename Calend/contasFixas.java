public class contasFixas {

    protected int valor;
    protected String nome;
    protected String dataVencimento;

    protected contasFixas(int valor, String nome, String dataVencimento){
        this.valor = valor;
        this.nome = nome;
        this.dataVencimento = dataVencimento;
    }

    protected int getVAlor(){
        return valor;
    }

    protected String getNome(){
        return nome;
    }

    protected String getDataVencimento(){
        return dataVencimento;
    }

    protected void setValor(int valor){ //Edição de valor caso necessite
        this.valor = valor;
    }

    protected void setdataVencimento(String dataVencimento){ //Edição de data caso necessite
        this.dataVencimento = dataVencimento;
    }

}
