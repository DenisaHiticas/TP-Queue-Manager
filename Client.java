public class Client implements Comparable<Client> {
    private int id;
    private int timpSOSIRE; //timpu de sosire
    private int timpSERVICIU; // timpu de service
//constructor cu acelasi nume ca si clasa
    public Client(int id, int timpSOSIRE, int timpSERVICIU) {
        this.id = id;
        this.timpSOSIRE = timpSOSIRE;
        this.timpSERVICIU = timpSERVICIU;
    }

    //AM DOAR GETTERE SI SETTERE
    public int getId() {
        return id;
    }

    public int getArrivalTime() {
        return timpSOSIRE;
    }

    public int getTimpSERVICIU() {
        return timpSERVICIU;
    }

    public void decrementareTIMPSERVICIU() {
        timpSERVICIU--;
    }
//COMPARA TIMPU DE SOSIRE DE LA UN CLIENT CU ALTUL
    @Override
    public int compareTo(Client o) {
        if (this.timpSOSIRE < o.timpSOSIRE) {
            return -1;
        } else if (this.timpSOSIRE > o.timpSOSIRE) {
            return 1;
        } else {
            return Integer.compare(this.id, o.id);
        }
    }
}