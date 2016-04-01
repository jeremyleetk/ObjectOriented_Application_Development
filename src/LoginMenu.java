import java.util.*;

/**
 * The LoginMenu class displays Login Menu.
 */
public class LoginMenu{

    /**
     * Displays the Login Menu and Takes in the Input from User for Login operational process.
     * @param   farmerDAO  Farmer Data Access Object which initializes and manages a list of Farmer objects of the FarmCity Game.
     */		
	public void process(FarmerDAO farmerDAO){
		Scanner console = new Scanner(System.in);
		
		System.out.print("Enter your username > ");
		String username = console.nextLine();
		System.out.print("Enter your password > ");
		String password = console.nextLine();
		FarmCityController fcController = new FarmCityController();
		Farmer currentFarmer =fcController.authenticate(username,password,farmerDAO);
		if(currentFarmer!=null){
			//when user has successfully logged in , display them the farmer options
			System.out.println("Welcome, you have been authenticated");
			FarmerMenu farmerMenu = new FarmerMenu(farmerDAO,currentFarmer);
			farmerMenu.display();
		}else{
			System.out.println("Wrong password or username!");
		}
		
	}

}