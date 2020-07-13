package kotelnikov.axamitdemo.tasks.post;

public class PostOfficeSimulator {

    public static boolean postIsRunning = false;
    public static long elapsedTime;
    public static int currentParcelsAtDept1;
    public static int currentParcelsAtDept2;

    public static void main(String[] args) {

        runPost();

    }

    public static void runPost() {
        postIsRunning = true;
        try {

            final long timeAvailable = 2 * 60 * 1000;
            elapsedTime = 0;

            Department depart1 = new Department("Depart 1", 50, 600, 500);

            depart1.employNewEmployee(new Sender("Sender 1", depart1, 3, 25, 25, 75, 6000, 1000, 1000, 5));
            depart1.employNewEmployee(new Receiver("Receiver 1", depart1, 5, 20, 20, 80, 5000, 1000, 1000, 5));

            Department depart2 = new Department("Depart 2", 55, 450, 550);

            depart2.employNewEmployee(new Sender("Sender 2", depart2, 4, 27, 23, 72, 5000, 1500, 500, 7));
            depart2.employNewEmployee(new Receiver("Receiver 2", depart2, 2, 23, 19, 79, 6000, 500, 2000, 7));

            depart1.setRivalDepartment(depart2);
            depart2.setRivalDepartment(depart1);

            depart1.startWork();
            depart2.startWork();

            try {
                while (elapsedTime <= timeAvailable && depart1.isWorking() && depart2.isWorking()) {
                    Thread.sleep(500);
                    elapsedTime += 500;
                    currentParcelsAtDept1 = depart1.getParcelsToSend().get();
                    currentParcelsAtDept2 = depart2.getParcelsToSend().get();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            depart1.endWork();
            depart2.endWork();

            if (elapsedTime > timeAvailable) {
                System.out.println((depart1.getTotalParcelsSent() > depart2.getTotalParcelsSent() ? depart1 : depart2) + " is winner!");
            }

            System.out.println(depart1 + " total = " + depart1.getTotalParcelsSent());
            System.out.println(depart2 + " total = " + depart2.getTotalParcelsSent());
        } catch ( Exception e ){

        } finally {
            postIsRunning = false;
        }

    }

}
