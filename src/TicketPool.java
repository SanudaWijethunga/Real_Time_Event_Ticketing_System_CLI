import java.util.ArrayList;
public class TicketPool {
    private final Configuration configurationObject;
    private final ArrayList<String> ticketsList;

    //parameterised constructor for the TicketPol class
    public TicketPool(Configuration configuration){
        this.configurationObject = configuration;
        this.ticketsList = new ArrayList<>();
    }
    public synchronized void addTickets(String ticket, int vendorNum) {
        int ticketPoolCapacity = configurationObject.getMaxTicketCapacity();
        try {
            //check that ticket pool is full and wait
            while (ticketsList.size() >= ticketPoolCapacity) {
                System.out.println("Ticket pool is full. Vendor " + vendorNum + " waiting to add tickets to the ticket pool.");
                wait();
            }
            //added to the ticket pool
            ticketsList.add("Ticket");
            configurationObject.setTotalTickets(configurationObject.getTotalTickets() - 1);
            int availableTotalTickets = configurationObject.getTotalTickets();
            int availableTicketPoolCount = ticketsList.size();
            System.out.println("Vendor " + vendorNum + " successfully added a " + ticket + " to the ticket pool. Available total tickets : " + availableTotalTickets + ". Available tickets in the pool : " + availableTicketPoolCount);
            notifyAll();

        }catch (Exception e){
            System.out.println("Error!");
        }
    }
    public synchronized void removeTicket(int customerNum){
        try {
            //check that ticket pool is empty and wait
            while (ticketsList.isEmpty()) {
                System.out.println("Ticket pool is empty. Customer " + customerNum + " waiting to purchase tickets.");
                wait();
            }
            //remove the ticket from the ticket list array
            ticketsList.remove("Ticket");
            System.out.println("Customer " + customerNum + " successfully purchased a ticket.");
            notifyAll();
        }
        catch (Exception e){
            System.out.println("Error");
        }
    }
}