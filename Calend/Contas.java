public class Contas {
    protected int valor;
    protected String dataVencimento;
    protected String nome;

    public Contas(int valor, String dataVencimento, String nome){
        this.valor = valor;
        this.dataVencimento = dataVencimento;
        this.nome = nome;
    }

    private void setdataVencimento(String dataVencimento){
        this.dataVencimento = dataVencimento
    }

    private void setnome (String nome){
        this.nome = nome;
    }

    private void setValor(String valor){
        this.valor = valor;
    }

    protected String getDataVencimento(){
        return dataVencimento;
    }

    protected String getNome(){
        return getNome;
    }

    protected int getValor(){
        return valor;
    }

}