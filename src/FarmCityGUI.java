import java.util.*;

/**
 * The FarmCityGUI class shows the User Interface of the FarmCity Game. It displays the login screen for FarmCity and allows Users to register/login/exit.
 */

public class FarmCityGUI{
		
	//creates instance of farmcitygui 
	public FarmCityGUI(){
		}

    /**
     * Displays FarmCity Menu and Takes in the Input from User for FarmCity operational processes of Register, Login and Logout. 
     */
        
	//displays the login screen for farm city and allow users to register/login/exit
	public void displayLoginScreen(FarmerDAO farmerDAO){
		Scanner sc =  new Scanner(System.in);
		boolean status=true;
		do{
			System.out.println("== Farm City :: Welcome ==");
			System.out.println("Hi,");
			System.out.println("1. Register");
			System.out.println("2. Login");
			System.out.println("3. Exit");
			System.out.print("Enter your choice > ");
			String choice = sc.nextLine();
			try{
				int choiceInt = Integer.parseInt(choice);
				if(choiceInt>3 || choiceInt<1){
					throw new InputMismatchException();
				}
				executeChoice(choiceInt,farmerDAO);
				if(choiceInt==3){
				status=false;
			}
			}catch(InputMismatchException e){
				System.out.println("The input "+choice+" keyed in is not valid");
			}catch(NumberFormatException e){
				System.out.println("The input "+choice+" keyed in is not valid");
			}

		}while(status);
			sc.close();
	}
		
	/**
     * Executes choices of Planting, Clearing and Harvesting
     * @param   choice  choice input by Farmer
     * @param   farmerDAO  Farmer Data Access Object which initializes and manages list of Farmer objects in FarmCity Game    
     */	
	public void executeChoice(int choice,FarmerDAO farmerDAO){
		switch(choice){
			case 1:
			RegisterMenu rm = new RegisterMenu();
			rm.process(farmerDAO);
			break;
			case 2:
			LoginMenu lm = new LoginMenu();
			lm.process(farmerDAO);
			break;
			default:
			
		}
	}
		
		
}