package kotelnikov.axamitdemo.tasks.post;

public abstract class Employee implements Runnable {

    private final String name;
    protected final Department depart;
    protected final int MAX_PARCELS_TO_PROCESS;
    protected final int OPERATIONS_TO_LUNCH;
    protected final int LOWER_BOUND;
    protected final int UPPER_BOUND;
    protected final int LUNCH_TIME;
    protected final int INCREASE_LUNCH_BY;
    protected final int DECREASE_LUNCH_BY;
    protected final int MAX_PARCELS_TO_CHEAT;
    protected int operationsCompleted;

    public Employee(String name, Department depart, int maxParcelsToProcess, int operationsToLunch, int lowerBound, int upperBound, int lunchTime, int increaseLunchBy, int decreaseLunchBy, int maxParcelsToCheat) {
        this.name = name;
        this.depart = depart;
        this.MAX_PARCELS_TO_PROCESS = maxParcelsToProcess;
        this.OPERATIONS_TO_LUNCH = operationsToLunch;
        this.LOWER_BOUND = lowerBound;
        this.UPPER_BOUND = upperBound;
        this.LUNCH_TIME = lunchTime;
        this.INCREASE_LUNCH_BY = increaseLunchBy;
        this.DECREASE_LUNCH_BY = decreaseLunchBy;
        this.MAX_PARCELS_TO_CHEAT = maxParcelsToCheat;
    }

    @Override
    public void run() {
        System.out.println(this + " started working");
        try {
            while (!Thread.currentThread().isInterrupted() && depart.isWorking()) {
                if (isTimeToLunch()) {
                    doLunch();
                } else {
                    doWork();
                }
            }
        } catch (InterruptedException e) {
        }
    }

    abstract protected void doWork() throws InterruptedException;

    abstract protected void processParcels(int parcels);

    protected void doLunch() throws InterruptedException {
        int parcelsLeft = depart.getParcelsToSend().get();
        cheat();
        if (parcelsLeft < LOWER_BOUND) {
            System.out.println(this + " began long lunch");
            Thread.sleep(LUNCH_TIME + INCREASE_LUNCH_BY);
            System.out.println(this + " back from long lunch");
        } else if (parcelsLeft > UPPER_BOUND) {
            System.out.println(this + " began short lunch");
            Thread.sleep(LUNCH_TIME - DECREASE_LUNCH_BY);
            System.out.println(this + " back from short lunch");
        } else {
            System.out.println(this + " began normal lunch");
            Thread.sleep(LUNCH_TIME);
            System.out.println(this + " back from normal lunch");
        }

        operationsCompleted = 0;
    }

    private void cheat() {
        int parcels = (int) (Math.random() * 10 % MAX_PARCELS_TO_CHEAT);
        depart.sendParcels(parcels);
        depart.getRivalDepartment().receiveParcels(parcels);
        System.out.println(this + " cheated with " + parcels + " parcels!");
    }

    private boolean isTimeToLunch() {
        return operationsCompleted >= OPERATIONS_TO_LUNCH;
    }

    @Override
    public String toString() {
        return depart + " / " + this.name;
    }

}
