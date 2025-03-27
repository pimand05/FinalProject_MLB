package utility;

public class InningKey {

    int inning;
    String equipo; //Local o Visitante [L,V];

    public InningKey(int inning, String equipo) {
        this.inning = inning;
        this.equipo = equipo;
    }

    @Override
    public String toString() {
        return inning+equipo;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof InningKey) {
            InningKey key = (InningKey) obj;
            return key.inning == this.inning && key.equipo.equals(this.equipo);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}
