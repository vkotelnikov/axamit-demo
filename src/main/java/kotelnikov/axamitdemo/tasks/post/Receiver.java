package kotelnikov.axamitdemo.tasks.post;

public class Receiver extends Employee {

    public Receiver(String name, Department depart, int maxParcelsToProcess, int operationsToLunch, int lowerBound, int upperBound, int lunchTime, int increaseLunchBy, int decreaseLunchBy, int maxParcelsToCheat) {
        super(name, depart, maxParcelsToProcess, operationsToLunch, lowerBound, upperBound, lunchTime, increaseLunchBy, decreaseLunchBy, maxParcelsToCheat);
    }

    @Override
    protected void doWork() throws InterruptedException {
        Thread.sleep(depart.getTimeToReceive());
        int parcels = (int) ((Math.random() * 10) % MAX_PARCELS_TO_PROCESS) + 1;
        processParcels(parcels);
        operationsCompleted++;
    }

    @Override
    protected void processParcels( int parcels ){
        System.out.println(this + " received " + parcels + " parcels");
        depart.receiveParcels(parcels);
    }

}
