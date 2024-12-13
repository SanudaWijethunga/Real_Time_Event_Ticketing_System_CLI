import java.util.InputMismatchException;
import java.util.Scanner;

public class Main{
    //Create an object from configuration class
    static Configuration configurationObject = new Configuration();
    public static void main(String[] args){
        //Creating an object form Scanner class
        Scanner input = new Scanner(System.in);
        //Display the starting of the system with menu
        System.out.println();
        System.out.println("""
                           ----Welcome to real time event ticketing system----
                           ---------------------------------------------------
                                                 -Menu-
                           ---------------------------------------------------
                           1 - Start a new event.
                           2 - Previous event.
                           3 - Exit.
                           ---------------------------------------------------
                           """);
        boolean corectInput = false;
        //get correct user menu option
        while (!corectInput){
            System.out.print("Select an option from the menu and enter here : ");
            String menuOption = input.nextLine().toLowerCase();
            switch (menuOption){
                case "1":
                    //start a new event
                    System.out.println();
                    System.out.println("Starting a new event");
                    //Call the method to start the system
                    getUserInputs(input);
                    corectInput = true;
                    break;
                case "2":
                    //load previous event
                    System.out.println();
                    //Call the method to load previous event
                    callPreviousEvent(input);
                    corectInput = true;
                    System.out.println();
                    break;
                case "3":
                    //exit from the loop
                    corectInput = true;
                    System.out.println("Exiting from the system.");
                    break;
                default:
                    System.out.println("You have entered and invalid option from the menu.");
                    break;
            }
        }
    }
    /**
     * This method gets all the inputs from the user to run the system.
     * @param input for get user inputs
     */
    private static void getUserInputs(Scanner input) {
        //Declare the main variables
        int totalTickets,ticketReleaseRate,customerRetrievalRate,maxTicketCapacity,cusTicketTotalCount;
        //Call the method to get correct total number of tickets
        totalTickets = getValidUserInputs(input, "Total number of tickets");
        //Call the method to get correct ticket release rate
        ticketReleaseRate = getValidUserInputs(input, "Ticket release rate (in m seconds)");
        //Call the method to get correct customer retrieval rate
        customerRetrievalRate = getValidUserInputs(input, "Customer retrieval rate (in m seconds)");
        //Call the method to get correct maximum ticket capacity
        maxTicketCapacity = getValidateCapacity(input, totalTickets, "Maximum ticket capacity");
        //Call the method to get correct number of tickets customer wants to buy
        cusTicketTotalCount = getValidateCapacity(input, totalTickets, "Number of tickets do customer want to purchase from");

        //Pass the user inputs to configuration class
        configurationObject.setTotalTickets(totalTickets);
        configurationObject.setTicketReleaseRate(ticketReleaseRate);
        configurationObject.setCustomerRetrievalRate(customerRetrievalRate);
        configurationObject.setMaxTicketCapacity(maxTicketCapacity);
        configurationObject.setCustomerTicketCount(cusTicketTotalCount);
        configurationObject.saveJsonToFile();
        configurationObject.saveToTextFile();
        //Call the method to run the threads
        callThreads(configurationObject);
    }
    /**
     * This method call the vendor and customer threads
     * @param configurationObject object of the configuration class
     */
    private static void callThreads(Configuration configurationObject){
        System.out.println();
        System.out.println("Starting the ticket system");
        System.out.println();
        //Create ticket pool object from TicketPool class
        TicketPool ticketPool = new TicketPool(configurationObject);

        //Create vendor object from Vendor class
        Vendor vendorOne = new Vendor(configurationObject,ticketPool,1);
        Thread threadVendorOne = new Thread(vendorOne);
        threadVendorOne.start();

        //Create vendor object from Vendor class
        Vendor vendorTwo = new Vendor(configurationObject,ticketPool,2);
        Thread threadVendorTwo = new Thread(vendorTwo);
        threadVendorTwo.start();

        //Create customer object from Customer class
        Customer customerOne = new Customer(configurationObject,ticketPool,1);
        Thread threadCustomerOne = new Thread(customerOne);
        threadCustomerOne.start();

        Customer customerTwo = new Customer(configurationObject,ticketPool,2);
        Thread threadCustomerTwo = new Thread(customerTwo);
        threadCustomerTwo.start();
    }
    /**
     * This method calls the configuration file to load the previous event details
     * @param input for get user inputs
     */
    private static void callPreviousEvent(Scanner input){
        Configuration configurationObj = new Configuration();
        try {
            //Save the loaded data to the object
            configurationObj = configurationObj.loadFromJsonFile();
            //Call the getter to get the total number of tickets
            int totalTickets = configurationObj.getTotalTickets();
            //Print the event details
            System.out.println("Previous event details");
            System.out.println("Total number of tickets : " + configurationObj.getTotalTickets());
            System.out.println("Ticket release rate     : " + configurationObj.getTicketReleaseRate());
            System.out.println("Customer retrieval rate : " + configurationObj.getCustomerRetrievalRate());
            System.out.println("Maximum ticket capacity : " + configurationObj.getMaxTicketCapacity());
            System.out.println();
            //Call the method to get correct number of tickets customer wants to buy
            int customerTicketCount = getValidateCapacity(input, totalTickets,"Number of tickets do customer want to purchase from");
            input.nextLine();
            configurationObj.setCustomerTicketCount(customerTicketCount);
            //Save updated the customer ticket count again
            configurationObj.saveJsonToFile();
            callThreads(configurationObj);
        } catch (NullPointerException e) {
            System.out.println("No previous event details available.Start a new event first.");
            getUserInputs(input);

        }
    }
    /**
     * This method returns the validated user inputs
     * @param input for get user input
     * @param message for displayed test
     * @return userInput
     */
    private static int getValidUserInputs(Scanner input, String message){
        boolean correctInput = false;
        int userInput = 0;
        while (!correctInput){
            System.out.print("Enter " + message + " here : ");
            try{
                userInput = input.nextInt();
                if (userInput <= 0){
                    System.out.println("You have entered an invalid answer. The " + message + " must be larger than 0.");
                }
                else {
                    correctInput = true;
                }
            }
            catch (InputMismatchException e){
                System.out.println("You have entered an invalid answer. The " + message + " must be a number.");
                input.nextLine();
            }
        }
        return userInput;
    }
    /**
     * This method validates the maximum ticket capacity and number of tickets customer buy.
     * @param input for get user inputs
     * @param totalTickets for total number of tickets
     * @param message for the displayed text
     * @return maxTicket
     */
    private static int getValidateCapacity(Scanner input, int totalTickets, String message){
        boolean correctInput = false;
        int maxTicket = 0;
        while (!correctInput){
            maxTicket = getValidUserInputs(input,message);
            if (maxTicket > totalTickets){
                System.out.println("You have entered an invalid answer. " + message + " must be greater than total number of tickets.");
            }
            else {
                correctInput = true;
            }
        }
        return maxTicket;
    }
}