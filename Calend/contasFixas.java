public class contasFixas extends Contas {

    protected contasFixas(int valor, String nome, String dataVencimento){
        super(valor,nome,dataVencimento);
    }

    protected int getValor(){
        return valor;
    }

    protected String getNome(){
        return nome;
    }

    protected String getDataVencimento(){
        return dataVencimento;
    }

    private void setNome(String valor){
        this.nome = nome;
    }

    private void setValor(int valor){ //Edição de valor caso necessite
        this.valor = valor;
    }

    private void setdataVencimento(String dataVencimento){ //Edição de data caso necessite
        this.dataVencimento = dataVencimento;
    }

}
