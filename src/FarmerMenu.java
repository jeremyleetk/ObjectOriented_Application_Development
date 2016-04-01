import java.util.*;
import java.io.*;

/**
 * The FarmerMenu class displays Farmer Menu. 
 */

public class FarmerMenu{

	private LandDAO landDAO;
	private Farmer currentUser;
	private InventoryDAO inventoryDAO;
	private FriendDAO friendDAO;
	private FarmerDAO farmerDAO;
	private RankDAO rankDAO;
	private SeedDAO seedDAO;

    /**
     * Constructs a FarmerMenu object 
     * @param   currentUser  current player (or Farmer)
     * @param   farmerDAO  Farmer Data Access Object which initializes and manages list of Farmer objects in FarmCity Game
     */	
	//default constructor that takes in the farmcitycontroller because inside holds the array list data
	public FarmerMenu(FarmerDAO farmerDAO,Farmer currentUser){
		this.farmerDAO=farmerDAO;
		this.currentUser = currentUser;
		this.rankDAO=new RankDAO();
		this.seedDAO= new SeedDAO();
		this.landDAO = new LandDAO(currentUser);
		this.inventoryDAO= new InventoryDAO(currentUser);
		this.friendDAO = new FriendDAO(currentUser);
	}
	
    /**
     * Displays Farm Menu and Takes in the Input from User for Farm operational processes.
     */
	//this is the display menu for the farmer
	public void display(){
		Scanner console2 = null;
		//this status boolean is used to keep looping this menu till user specifies to logout with 4
		boolean status=true;
		do{
			System.out.println("== Farm City :: Main Menu ==");
			System.out.println("Welcome, "+this.currentUser.getName()+"\n");
			System.out.println("1. My Friends");
			System.out.println("2. My Farm");
			System.out.println("3. My Inventory");
			System.out.println("4. Logout");
			System.out.print("Enter your choice >");
			console2 = new Scanner(System.in);
			String choice = console2.nextLine();
			try{
				int choiceInt = Integer.parseInt(choice);
				if(choiceInt>4 || choiceInt<1){
					throw new InputMismatchException();
				}
				executeFarmerChoice(choiceInt);
				if(choiceInt==4){
					status=false;
					System.out.println("Thanks for farming with us! See you again soon!");
				}
			}catch(InputMismatchException e){
				System.out.println("Sorry "+this.currentUser.getName()+"! The input "+choice+" keyed in is not valid");
			}catch(NumberFormatException e){
				System.out.println("Sorry "+this.currentUser.getName()+"! The input "+choice+" keyed in is not valid");
			}

		}while(status);
		//console2.close();
	}
	
	/**
     * Executes choices of Planting, Clearing and Harvesting
     * @param   choice  choice input by Farmer
     */
	public void executeFarmerChoice(int choice){
		switch(choice){
			case 1:
			FriendMenu friendMenu = new FriendMenu();
			friendMenu.display(currentUser,friendDAO,inventoryDAO,farmerDAO);
			break;
			case 2:
			FarmLandMenu farmLandMenu = new FarmLandMenu();
			farmLandMenu.display(currentUser,landDAO,seedDAO,rankDAO,inventoryDAO,farmerDAO);
			break;
			case 3:
			InventoryMenu inventoryMenu = new InventoryMenu();
			inventoryMenu.display(currentUser,friendDAO,inventoryDAO,farmerDAO,seedDAO);
			break;
		}
	}
	
	
	
}