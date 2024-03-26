public class Main {
    public static void main(String[] args) {
            // crearea instanței GestionareCozi
            int numClients = 100;
            int numQueues = 5;
            int simulationInterval = 60;
            int maxServiceTime = 10;
            QueueLogics gestionareCozi = new QueueLogics(numClients, numQueues, simulationInterval, maxServiceTime);

            // rularea simulării cozilor
            gestionareCozi.runSimulation();
        }

}