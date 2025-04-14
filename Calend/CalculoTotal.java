import java.util.ArrayList;

public class CalculoTotal { //Essa classe irá desaparecer e ser implementada em um controlador

    protected ArrayList<contasFixas> contas = new ArrayList<contasFixas>();
    protected int SomaTotal;

    public CalculoTotal(ArrayList<contasFixas> contas) {
        this.contas = contas;
    }

    public int getSomaTotal(){
        for (int i = 0; i < contas.size(); i++){
            SomaTotal += contas.get(i).getVAlor();
        }
        return SomaTotal;
    }


}
