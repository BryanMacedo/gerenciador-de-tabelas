package model.entities;

public enum TypeDLC {
    NAO_TEM("Não tem"),
    TERMINEI("Terminei"),
    NAO_JOGUEI("Não joguei"),
    E_DLC("É DLC");

    private String strDLC;

    TypeDLC(String strDLC){
        this.strDLC = strDLC;
    }

    public String getStrDLC() {
        return strDLC;
    }
}
