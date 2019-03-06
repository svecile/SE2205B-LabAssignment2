import java.lang.*;

import static java.lang.Math.abs;


public class QueueSimulator {
    public enum Event {ARRIVAL, DEPARTURE}

    private double currTime;
    private double arrivalRate;
    private double serviceTime;
    private double timeForNextArrival;
    private double timeForNextDeparture;
    private double totalSimTime;
    LinkedListQueue<Data> buffer = new LinkedListQueue<Data>();
    LinkedListQueue<Data> eventQueue = new LinkedListQueue<Data>();
    private Event e;

    public double getRandTime(double arrivalRate) {
        double num, time1, max = 1, min = 0, randNUM;
        randNUM = Math.random();
        time1 = (-1 / arrivalRate) * (Math.log(1 - randNUM));
        //System.out.println(time1);
        return time1;
    }

    public QueueSimulator(double aR, double servT, double simT) {
        arrivalRate = aR;
        serviceTime = servT;
        totalSimTime = simT;
        timeForNextArrival = getRandTime(aR);
        timeForNextDeparture = servT + timeForNextArrival;
    }

    public double calcAverageWaitingTime() {
        Data packet;
        double sum = 0, count = eventQueue.size();

        while (!eventQueue.isEmpty()) {
            packet = eventQueue.dequeue();
            sum += packet.getDepartureTime() - packet.getArrivalTime();
        }
        return sum / count;
        //(1 / arrivalRate) * ((arrivalRate * serviceTime) + ((0.5 * ((arrivalRate * serviceTime) * (arrivalRate * serviceTime))) / (1 - (arrivalRate * serviceTime))));
    }

    public double runSimulation() {
        /*Packets are enqueued into buffer in the form Node.
        The arrival time of the packet into buffer is recorded in the arrivalTime member of the Data variable
        which is the E member of the Node struct.
        When a packet is dequeued from buffer, its departure time is recorded in the departureTime member of the Data
        variable and this node is then enqueued into the eventQueue queue which record all packets departing the buffer queue

        When an event occurs, the simulator updates the current time variable currTime to the time at which the event occurs.*/

        while (currTime < totalSimTime) {
            Data packet = new Data();
            if (buffer.isEmpty() || timeForNextArrival < timeForNextDeparture) {
                //an arrival is happening
                currTime += timeForNextArrival;
                packet.setArrivalTime(currTime);
                buffer.enqueue(packet);
                timeForNextArrival = getRandTime(arrivalRate);

            } else {
                //a departure is happening
                currTime += timeForNextDeparture;
                packet = buffer.dequeue();
                packet.setDepartureTime(currTime);
                eventQueue.enqueue(packet);
            }
        }
        return calcAverageWaitingTime();
    }
}






