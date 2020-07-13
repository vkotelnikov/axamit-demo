package kotelnikov.axamitdemo.tasks.post;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Department {

    private final String name;
    private AtomicInteger parcelsToSend;
    private volatile boolean isWorking;
    private int totalParcelsSent;
    private final int TIME_TO_SEND;
    private final int TIME_TO_RECEIVE;

    private Department rivalDepartment;

    private List<Thread> employees = new ArrayList<>();

    public Department( String name, int parcelsToSend, int timeToSend, int timeToReceive ) {
        this.name = name;
        this.isWorking = true;
        this.parcelsToSend = new AtomicInteger(parcelsToSend);
        this.TIME_TO_SEND = timeToSend;
        this.TIME_TO_RECEIVE = timeToReceive;
    }

    public void startWork() {
        System.out.println(this + " started working");
        for(Thread emp : employees ){
            emp.start();
        }
    }

    public void endWork(){
        for(Thread emp : employees ){
            emp.interrupt();
        }
    }

    public boolean isWorking(){
        return isWorking;
    }

    public void employNewEmployee(Employee employee) {
        employees.add( new Thread(employee) );
    }

    public void sendParcels(int parcelsToSend) {
        int parcels = this.parcelsToSend.addAndGet(-1 * parcelsToSend);
        totalParcelsSent += parcelsToSend;
        if (parcels <= 0) {
            System.out.println(this + " won!");
            isWorking = false; //под конец реализации понял, что не продумал, как остановить товарища по отделению,
            // если отделение вышло за рамки допустимых посылок. Поэтому возможен вариант, когда isWorking == false,
            // но товарищ успевает принять ещё несколько посылок и наоборот.
        }

    }

    public void receiveParcels( int parcelsToReceive ) {
        int parcels = this.parcelsToSend.addAndGet(parcelsToReceive);
        if (parcels >= 100 ){
            System.out.println(this + " lost!");
            isWorking = false;
        }
    }

    public AtomicInteger getParcelsToSend() {
        return parcelsToSend;
    }

    public int getTotalParcelsSent() {
        return totalParcelsSent;
    }

    public int getTimeToSend() {
        return TIME_TO_SEND;
    }

    public int getTimeToReceive() { return TIME_TO_RECEIVE; }

    public Department getRivalDepartment() {
        return rivalDepartment;
    }

    public void setRivalDepartment(Department rivalDepartment) {
        this.rivalDepartment = rivalDepartment;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
