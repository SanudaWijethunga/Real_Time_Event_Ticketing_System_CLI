public class Vendor implements Runnable{
    private final Configuration configurationObject;
    private final TicketPool ticketPoolObject;
    private final int vendorNum;

    //parameterised constructor for the Customer class
    public Vendor(Configuration configuration, TicketPool ticketPool, int vendorNum){
        this.configurationObject = configuration;
        this.ticketPoolObject = ticketPool;
        this.vendorNum =  vendorNum;
    }
    //overriding the run method to create the thread for vendor
    @Override
    public void run(){
        int ticketCapacity = configurationObject.getTotalTickets();
        int ticketReleaseRate = configurationObject.getTicketReleaseRate();
        boolean threadStop = false;
        while (!threadStop) {
            for (int i = 1; i <= ticketCapacity; i++) {
                ticketPoolObject.addTickets("Ticket",vendorNum);
                try {
                    Thread.sleep(ticketReleaseRate);
                    if (configurationObject.getTotalTickets() == 0) {
                        System.out.println("Vendor " + vendorNum + " added all the tickets to the ticket pool.");
                        //stop the thread
                        threadStop = true;
                        //exit from the loop and stop adding tickets to the ticket pool
                        break;
                    }
                } catch (InterruptedException e) {
                    System.out.println("Error when vendor adding tickets.");
                }
            }
        }
    }
}