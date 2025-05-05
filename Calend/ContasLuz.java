public class ContasLuz extends Contas{ //Dará uma estimativa do minimo da conta
    protected int Kwh; //Usuario terá que inserir o kwh para o calculo da conta de luz

    public ContasLuz(int valor, String nome, String dataVencimento, int Kwh, int TempodeUso){
        super(valor,nome,dataVencimento);
        this.Kwh = Kwh;
    }

    protected int Kwh(){
        return Kwh;
    }

    protected int TempodeUso(){
        return TempodeUso;
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

    private void setKwh(int Kwh){
        this.Kwh = Kwh
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