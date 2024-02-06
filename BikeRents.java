package Objorient;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Bikes{
    private String BikeId;
    private String brand;
    private String model;
    private String Maxspeed;
    private double rentPricePerDay;
    private boolean isAvailableNow;

    public Bikes(String BikeId,String brand,String model,String Maxspeed,double rentPricePerDay){
        this.BikeId = BikeId;
        this.brand = brand;
        this.model = model;
        this.Maxspeed = Maxspeed;
        this.rentPricePerDay =rentPricePerDay;
        isAvailableNow = true;
    }

    public String getBikeId(){
        return BikeId;
    }
    public String getBrand() {
        return brand;
    }
    public String getModel() {
        return model;
    }

    public double getRentPricePerDay(int Daysrented) {
        return rentPricePerDay + Daysrented;
    }

    public String getMaxspeed(){
        return Maxspeed;
    }

    public boolean isAvailableNow() {
        return isAvailableNow;
    }
    public void rent(){
        isAvailableNow =false;
    }
    public  void returnTheBikeToTheCompany(){
        isAvailableNow = true;
    }
}

class Customers{
    private String CustomerId;
    private String name;
    public Customers(String CustomerId) {
        this.CustomerId = CustomerId;
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public String getCustomerId() {
        return CustomerId;
    }
}

class RentBikes{
    private Bikes bike; // yaha par jo bikes hai wo Strings hai kya? - nope ye upar jo class banaya hai uske hi hai.
    private Customers customers;  // Customers too. - same as above upar jo class hai uske hi hai
    private int days;
    RentBikes(Bikes bike, Customers customers, int days){
        this.bike = bike;
        this.customers = customers;
        this.days = days;
    }

    public Bikes getBike() {
        return bike;
    }

    public Customers getCustomers() {
        return customers;
    }

    public int getDays() {
        return days;
    }
}

class BikeRentSystem{
    private List<Bikes> bikes;
    private List<Customers> customers;
    private List<RentBikes> rentBikes;

    public BikeRentSystem(){
    bikes = new ArrayList<>();
    customers = new ArrayList<>();
    rentBikes = new ArrayList<>();
    }

    public void addBikes(Bikes bike){
        bikes.add(bike);
    }
    public void addCustomers(Customers customer){
        customers.add(customer);
    }
    public void rentBike(Bikes bike,Customers customer, int days){
        if (bike.isAvailableNow()){
            bike.rent();
            rentBikes.add(new RentBikes(bike,customer,days));
        }else{
            System.out.println("|Bikes| are not Available for Rent 'cause it's already /Rented/");
        }
    }
    public void returnTheBikeToTheCompany(Bikes bike){
        bike.returnTheBikeToTheCompany();
        RentBikes rentBikestoRemove = null;
        for(RentBikes rentBike:rentBikes){
            if(rentBike.getBike()==bike){
                rentBikestoRemove = rentBike;
                break;
            }
        }
        if(rentBikestoRemove != null){
            rentBikes.remove(rentBikestoRemove);
        }else {
            System.out.println("Car was not Rented");
        }
    }

    public void SystemMenu(){
        Scanner menu = new Scanner(System.in);
        while (true){
            System.out.println("=====  |||Sahil More Bike Rental System|||  =====");
            System.out.println("1. Rent a |Bike| of your choice.");
            System.out.println("2. Return the |Bike|");
            System.out.println("3. Exit the ||System||");
            System.out.print("Please Enter the |Bike| of your choice: ");

            int choice = menu.nextInt();
            menu.nextLine();

            if(choice == 1){
                System.out.println("\n //-----Here are the Available |Bikes|-----\\ \n");
                System.out.println("Register your Name : ");
                String customerName = menu.nextLine();

                System.out.println("\n Available |Bikes| for your Service : ");
                for(Bikes bike: bikes){
                    if(bike.isAvailableNow()){
                        System.out.println(bike.getBikeId() + " -- " + bike.getBrand() + " -- " + bike.getModel());
                    }
                }

                System.out.println("\n--Enter the |Bike| Id your want to Rent");
                String BikeId = menu.nextLine();

                System.out.println(" Enter the number of days you want our Services/Rent the |Bike| : ");
                int rentBikes = menu.nextInt();
                menu.nextLine();

                Customers newCustomers = new Customers("CUSTOMER 1 : " + (customers.size() + 1 ) + customerName);
                addCustomers(newCustomers);

                Bikes selectedBikes = null;
                for(Bikes bikes: bikes){
                    if(bikes.getBikeId().equals(BikeId) && bikes.isAvailableNow()){
                        selectedBikes =bikes;
                        break;
                    }
                }
                if (selectedBikes != null){
                    double TotalPrice = selectedBikes.getRentPricePerDay(rentBikes);
                    System.out.println("---Rental Information of our Services---");
                    System.out.println("Customer Id information : " + newCustomers.getCustomerId());
                    System.out.println("Customer's |Full Name| here : " + newCustomers.getName());
                    System.out.println("Bike model - " + selectedBikes.getBrand() + " " + selectedBikes.getModel());
                    System.out.println("Number of Days you want to Rent our |Bike| is "+ rentBikes + " days");
                    System.out.printf("Total Price: Rs %.2f%n", TotalPrice);

                    System.out.println("\n Confirm Renting a |Bike| from our System (Y/N) ? - ");
                    String confirm = menu.nextLine();

                    if(confirm.equalsIgnoreCase("Y")){
                        rentBike(selectedBikes,newCustomers,rentBikes); // the ones in blue are constructors.
                        System.out.println("\n |Bike| rented successfully");
                    }
                    else {
                        System.out.println("\n Rental cancelled");
                    }
                }else {
                    System.out.println("\nInvalid |Bike| selection or |Bike| not available for Rent Right Now.");
                }
            } else if (choice == 2) {
                System.out.println("\n== Return a Car ==\n");
                System.out.print("Enter the Bike number plate you want to return: ");
                String BikeId = menu.nextLine();

                Bikes bikestoreturn = null;
                for(Bikes bike: bikes){
                    if(bike.getBikeId().equals(BikeId) && !bike.isAvailableNow()){
                        bikestoreturn =bike;
                        break;
                    }
                }

                if (bikestoreturn != null){
                    Customers customers = null;
                    for(RentBikes rentBikes: rentBikes){
                        if (rentBikes.getBike() == bikestoreturn){ // Need to make different name gets
                            customers = rentBikes.getCustomers();   //a hell lot confusing.
                        }
                    }
                    if(customers != null){
                        returnTheBikeToTheCompany(bikestoreturn);
                        System.out.println("|Bike| returned successfully by : " + customers.getName());
                    } else {
                        System.out.println("Bike was not Rented or Rental Information of the |Bike| is Missing");
                    }
                }else {
                    System.out.println("Invalid |Bike| ID or |Bike| is not rented.");
                }

            }  else if (choice == 3) {
                break;
            } else {
                System.out.println("Invalid choice. Please enter a valid option here on your Screen.");
            }
        }
        System.out.println("\n----Thankyou for using our Bike Rental System----");
    }
}
    public class BikeRents {
    public static void main(String[] args) {
        BikeRentSystem rentSystem = new BikeRentSystem();
        Bikes bike1 = new Bikes("MH007","Royal Enfield", "Classic 350","250",4000);
        Bikes bike2 = new Bikes("MH008","Hero", "Splendor Plus","100",600);
        Bikes bike3 = new Bikes("MH009","TVS", "Raider","120",800);
        Bikes bike4 = new Bikes("MH100","Suzuki", "Access","150",1000);
        Bikes bike5 = new Bikes("MH108","Bajaj", "Pulsar","160",1200);
        Bikes bike6 = new Bikes("MH777","Honda", "Classic ","200",1500);
        Bikes bike7 = new Bikes("MH069","Harley davidson", "V-Twin","280",3000);
        Bikes bike8 = new Bikes("MH420","KTM", "Chapri-Kurla","250",200);
        Bikes bike9 = new Bikes("MH1010","Duke", "Chapri-Wadala","250",200);
        Bikes bike10 = new Bikes("MH1000","Bmw", "Supreme","300",8000);
        rentSystem.addBikes(bike1);
        rentSystem.addBikes(bike2);
        rentSystem.addBikes(bike3);
        rentSystem.addBikes(bike4);
        rentSystem.addBikes(bike5);
        rentSystem.addBikes(bike6);
        rentSystem.addBikes(bike7);
        rentSystem.addBikes(bike8);
        rentSystem.addBikes(bike9);
        rentSystem.addBikes(bike10);

        rentSystem.SystemMenu();

    }
}
