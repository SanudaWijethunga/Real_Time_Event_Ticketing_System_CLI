public class Customer implements Runnable{
    private final Configuration configurationObject;
    private final TicketPool ticketPoolObject;
    private final int customerNum;

    //parameterised constructor for the Customer class
    public Customer(Configuration configuration, TicketPool ticketPool, int customerNum){
        this.configurationObject = configuration;
        this.ticketPoolObject = ticketPool;
        this.customerNum = customerNum;
    }
    //overriding the run method to create the thread for customer
    @Override
    public void run(){
        //return necessary user input values and store to variables
        int customerTicketCount = configurationObject.getCustomerTicketCount();
        int retrievalRate = configurationObject.getCustomerRetrievalRate();

        for (int i = 1; i <= customerTicketCount; i++) {
            ticketPoolObject.removeTicket(customerNum);
            try {
                Thread.sleep(retrievalRate);
            } catch (InterruptedException e) {
                System.out.println("Error when customer purchasing tickets.");
            }
        }
    }
}