import java.util.PriorityQueue;

public class QueueLogics {
    private PriorityQueue<Client>[] queues;
    private int simulationInterval;
    private int numClients;
    private int numQueues;

    private int maxServiceTime;

    //fiecare coadă este sortată în funcție de timpul de sosire și timpul de servire a clienților
    public QueueLogics(int numClients, int numQueues, int simulationInterval, int maxServiceTime) {
        this.numClients = numClients;
        this.numQueues = numQueues;
        this.simulationInterval = simulationInterval;
        this.maxServiceTime = maxServiceTime;
        queues = new PriorityQueue[numQueues];
        for (int i = 0; i < numQueues; i++) {
            queues[i] = new PriorityQueue<Client>((a, b) -> a.getArrivalTime() != b.getArrivalTime()
                    ? a.getArrivalTime() - b.getArrivalTime()
                    : a.getTimpSERVICIU() - b.getTimpSERVICIU());
        }
    }

//metoda runSimulation
    public void runSimulation() {
        int currentTime = 0;
        while (currentTime < simulationInterval) {
            // Check if any clients can leave the queues
            for (int i = 0; i < numQueues; i++) {
                PriorityQueue<Client> queue = queues[i];
                while (!queue.isEmpty() && queue.peek().getTimpSERVICIU() <= 0) {
                    Client client = queue.poll();
                    System.out.println("Clientul " + client.getId() + " a parasit coada " + i + ".");
                }
            }

            //adaug un nou client la coada scurta
            int arrivalTime = (int) (Math.random() * simulationInterval);
            int serviceTime = (int) (Math.random() * maxServiceTime);
            Client newClient = new Client(numClients + 1, arrivalTime, serviceTime);
            addClientToShortestQueue(newClient);

            // Decrease service time for all clients in the queues
            //scad timpu de service pentru toti clientii in QUEUE
            for (int i = 0; i < numQueues; i++) {
                PriorityQueue<Client> queue = queues[i];
                for (Client client : queue) {
                    client.decrementareTIMPSERVICIU();
                }
            }
            //astept o secunda si incrementez timpu curent
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Thread-ul a fost întrerupt.");
            }
            currentTime++;
        }
    }

    private void addClientToShortestQueue(Client client) {
        int shortestQueueIndex = 0;
        int shortestQueueSize = queues[0].size();
        for (int i = 1; i < numQueues; i++) {
            int queueSize = queues[i].size();
            if (queueSize < shortestQueueSize) {
                shortestQueueIndex = i;
                shortestQueueSize = queueSize;
            }
        }
        queues[shortestQueueIndex].add(client);
        System.out.println("Clientul " + client.getId() + " a ajuns la coada " + shortestQueueIndex + " la timpul " + client.getArrivalTime() + ".");
    }

    //adaugă un client în coada cu cel mai mic timp total de așteptare
    public void addClient(Client client) {

        //caut queue cu cel mai mic total timp de asteptare
        PriorityQueue<Client> shortestQueue = queues[0];
        int shortestQueueIndex = 0;
        int shortestQueueTime = getTotalWaitingTime(shortestQueue);
        for (int i = 1; i < queues.length; i++) {
            int queueTime = getTotalWaitingTime(queues[i]);
            if (queueTime < shortestQueueTime) {
                shortestQueue = queues[i];
                shortestQueueIndex = i;
                shortestQueueTime = queueTime;
            }
        }

        // Adaug clientul in cea mai scurta queue la tumpu meu
        shortestQueue.offer(client);
        System.out.println(String.format("Client %d added to queue %d at time %d", client.getId(), shortestQueueIndex,
                client.getArrivalTime()));
    }
//calculează timpul total de așteptare pentru o coadă dată.
    private int getTotalWaitingTime(PriorityQueue<Client> queue) {
        int total = 0;
        for (Client client : queue) {
            total += Math.max(0, simulationInterval - (client.getArrivalTime() + total));
        }
        return total;
    }
}
