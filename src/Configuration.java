import java.io.*;
import com.google.gson.Gson;
public class Configuration {
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;
    private int customerTicketCount;

    //getters and setters for the instance variables
    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public void setCustomerTicketCount(int customerTicketCount) {
        this.customerTicketCount = customerTicketCount;
    }

    public int getTotalTickets() {
        return this.totalTickets;
    }

    public int getTicketReleaseRate() {
        return this.ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return this.customerRetrievalRate;
    }

    public int getMaxTicketCapacity() {
        return this.maxTicketCapacity;
    }

    public int getCustomerTicketCount() {
        return this.customerTicketCount;
    }

    public void saveToTextFile() {
        try {
            //create the fileWriter object
            FileWriter fileWriter = new FileWriter("w2052741_20220982_ticket_details.txt");
            //write data to the text file
            fileWriter.write("Event details " + '\n');
            fileWriter.write("Total number of tickets: " + totalTickets + '\n');
            fileWriter.write("Ticket release rate    : " + ticketReleaseRate + '\n');
            fileWriter.write("Customer retrieval rate: " + customerRetrievalRate + '\n');
            fileWriter.write("Maximum ticket capacity: " + maxTicketCapacity + '\n');
            fileWriter.write("Number of tickets customer wants to purchase: " + customerTicketCount);
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("Error when saving to text file");
        }
    }
    /**
     * Method to save data to the w2052741_20220982_ticket_details.json file
     */
    public void saveJsonToFile() {
        String fileName = "w2052741_20220982_ticket_details.json";
        Gson gson = new Gson();
        try{
            //save data to the json file
            FileWriter fileWriter = new FileWriter(fileName);
            gson.toJson(this,fileWriter);
            System.out.println("successfully saved the data");
            fileWriter.close();
        }
        catch (IOException e){
            System.out.println("Error when saving the data to the json file.");
        }
    }
    /**
     * Method to load data from the w2052741_20220982_ticket_details.json file
     * @return configuration object
     */
    public Configuration loadFromJsonFile(){
        String fileName = "w2052741_20220982_ticket_details.json";
        Gson gson = new Gson();
        try (FileReader fileReader = new FileReader(fileName)){
            return gson.fromJson(fileReader,Configuration.class);
        }
        catch (IOException e){
            System.out.println("Error when loading previous event");
            return null;
        }
    }
}