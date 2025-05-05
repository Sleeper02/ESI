public class Eletrodomestico{
    protected String Nome;
    protected int Watts;
    protected int TempodeUso; //Tempo em hora

    public Eletrodomestico(String nome, int Watts, int TempodeUso){
        this.Nome = nome;
        this.Watts = Watts;
        this.TempodeUso = TempodeUso;
    }

    private String setNome (String Nome){
        this.Nome = Nome;
    }

    private int setWatts (int Watts){
        this.Watts = Watts
    }

    private int setTempodeUso (int TempodeUso){
        this.TempodeUso = TempodeUso;
    }

    protected void getNome(){
        return Nome;
    }

    protected void getWatts(){
        return Watts;
    }

    protected void getTempodeUso(){
        return TempodeUso;
    }
}