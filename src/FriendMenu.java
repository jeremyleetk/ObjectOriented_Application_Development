import java.io.*;
import java.util.*;

/**
 * The FriendMenu class displays processes of Unfriend, Request, Accept and Reject friends of the Current Farmer.  
 */
 
public class FriendMenu{

	/**
     * Displays Friend Menu Takes in the Input from User for Friend operational processes.
     * @param   currentUser  current player (or Farmer).
     * @param   friendDAO  Friend Data Access Object which initializes and manages String lists of Friend and PendingFriend of the current Farmer.
     * @param   inventoryDAO  Inventory Data Access Object which initializes and manages list of InventorySlot of the current Farmer.
     * @param   farmerDAO  Farmer Data Access Object which initializes and manages a list of Farmer objects of the FarmCity Game.
     */ 
	public void display(Farmer currentUser,FriendDAO friendDAO,InventoryDAO inventoryDAO,FarmerDAO farmerDAO){
		Scanner console = new Scanner(System.in);
		do{
            System.out.println("== Farm City :: My Friends ==");
            System.out.println("Welcome, "+currentUser.getName()+"!\n");	
            FriendController  friendController = new FriendController();
            System.out.println(friendController.computeFriendList(friendDAO));
            System.out.println(friendController.computePendingList(friendDAO));		
            
            System.out.print("[M]ain | [U]nfriend | re[Q]uest | [A]ccept | [R]eject > ");
            String userChoice=console.nextLine();
            //if user types in M for Main it will quit the loop
            if(userChoice.equals("M")){
                return;
            }else{
                executeChoice(userChoice,friendDAO,inventoryDAO,currentUser,farmerDAO);
            }
		}while(true);
	}
	
    /**
     * Takes in and Executes choices of Unfriend, Request, Accept and Reject Friend from User.
     */    
	public void executeChoice(String userChoice,FriendDAO friendDAO,InventoryDAO inventoryDAO,Farmer currentFarmer,FarmerDAO farmerDAO){
		FriendController friendController = new FriendController();
		switch(userChoice.charAt(0)){
			case 'U':
                System.out.println(friendController.unfriend(userChoice,friendDAO,currentFarmer));
                break;
			case 'Q':
                Scanner console = new Scanner(System.in);
                System.out.print("Enter the username > ");
                String friendRequest = console.nextLine();
                //check if he is requesting to be friends with himself
                if(friendRequest.equals(currentFarmer.getUsername())){
                    System.out.println("Sorry! You cannot be friends with yourself!");
                    break;
                }
                System.out.println(friendController.request(friendRequest,friendDAO,farmerDAO,currentFarmer));
                break;
			case 'A':
                System.out.println(friendController.accept(userChoice,friendDAO));
                break;
			case 'R':
                System.out.println(friendController.reject(userChoice,friendDAO));
                break;
                default:
                System.out.println("Bad input keyed in!");		
		}
		
	}
		
}