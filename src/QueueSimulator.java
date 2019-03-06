import java.lang.*;

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
    }

    public double runSimulation() {

        while (currTime < totalSimTime) {

            Data packet = new Data();

            if (buffer.isEmpty() || timeForNextArrival < timeForNextDeparture) {
                //an arrival is happening
                currTime += timeForNextArrival;
                packet.setArrivalTime(currTime);
                buffer.enqueue(packet);

                timeForNextDeparture -= timeForNextArrival;
                timeForNextArrival = getRandTime(arrivalRate);

                if (timeForNextDeparture < 0) {
                    timeForNextDeparture = serviceTime;
                }

            } else {
                //a departure is happening
                currTime += timeForNextDeparture;
                packet = buffer.dequeue();
                packet.setDepartureTime(currTime);
                eventQueue.enqueue(packet);

                timeForNextArrival -= timeForNextDeparture;
                timeForNextDeparture = serviceTime;
            }
        }
        return calcAverageWaitingTime();
    }
}






