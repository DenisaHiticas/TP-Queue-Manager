import java.util.PriorityQueue;
//CLASA QUEUE
public class Queue {
    private int queueID;
    private int simulationTime;
    private int maxServiceTime;
    private int maxArrivalTime;
    private PriorityQueue<Client> clients;
//constructor cu acelasi nume ca si clasa
    public Queue(int queueID, int simulationTime, int maxServiceTime, int maxArrivalTime) {

        this.queueID = queueID;
        this.simulationTime = simulationTime;
        this.maxServiceTime = maxServiceTime;
        this.maxArrivalTime = maxArrivalTime;
        this.clients = new PriorityQueue<Client>();
    }
//gettere si settere
    public int getQueueID() {
        return queueID;
    }

    public PriorityQueue<Client> getClients() {
        return clients;
    }
//metoda run
    public void run() {
        try {
            int currentTime = 0;
            while (currentTime < simulationTime || !clients.isEmpty()) {
                if (!clients.isEmpty()) {
                    Client currentClient = clients.peek();
                    if (currentClient.getTimpSERVICIU() <= 0) {
                        clients.poll();
                        System.out.println("Clientul " + currentClient.getId() + " a parasit coada " + queueID + ".");
                    } else {
                        currentClient.decrementareTIMPSERVICIU();
                    }
                }

                if (currentTime < simulationTime) {
                    int arrivalTime = (int) (Math.random() * maxArrivalTime);
                    if (arrivalTime + currentTime <= simulationTime) {
                        int serviceTime = (int) (Math.random() * maxServiceTime);
                        Client newClient = new Client(clients.size() + 1, arrivalTime + currentTime, serviceTime);
                        clients.add(newClient);
                        System.out.println("Clientul " + newClient.getId() + " a ajuns la coada " + queueID + " la timpul " + (arrivalTime + currentTime) + ".");
                    }
                }

                Thread.sleep(1000);
                currentTime++;
            }
        } catch (InterruptedException e) {
            System.out.println("Thread-ul cozi " + queueID + " a fost întrerupt.");
        }
    }
}