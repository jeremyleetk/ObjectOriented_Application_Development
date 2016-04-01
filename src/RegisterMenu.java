import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

/**
 * The RegisterMenu class displays the Registration Menu
 */
 
public class RegisterMenu{

	/**
     * Displays Registration Menu and Takes in the Input from User for Registration operational process.
     * @param   farmerDAO  Farmer Data Access Object which initializes and manages a list of Farmer objects of the FarmCity Game
     */
	//displays the register menu for users to register
	public void process(FarmerDAO farmerDAO){
		FarmCityController fcController = new FarmCityController();
		Scanner console = new Scanner(System.in);
		//implement regular expression to check on username so that username is definitely alphanumeric
		boolean usernameStatus = false;
		String username,name,password;
        
		do{
            System.out.print("Enter your username > ");
            username = console.nextLine();
            //use regular expression to ensure that the username must be strictly alphanumeric before carrying on
            Pattern pattern = Pattern.compile("^[a-zA-Z0-9]*$");
            Matcher matcher = pattern.matcher(username);
            usernameStatus=matcher.matches();
                //if username is not alphanumeric i will not even check with the arraylist if there is a duplicate
            if(!usernameStatus){
                System.out.println("Username keyed in is not alphanumeric");
            }else{
                    //check for duplicate if duplicate is true set the status to be false so that the loop will keep continueing
                if(fcController.checkDuplicate(username,farmerDAO)){
                    usernameStatus=false;
                    System.out.println("Sorry, this username already exist! Please use another!");
                }
            }
                
		}while(!usernameStatus);
        
		System.out.print("Enter your full name > ");
		name = console.nextLine();
		boolean samePassword=false;
        
		do{
			System.out.print("Enter your password > ");
			password = console.nextLine();
			System.out.print("Confirm your password > ");
			String confirmPassword=console.nextLine();
			if(password.equals(confirmPassword)){
				samePassword=true;
			}else{
				System.out.println("The passwords do not match! Please try again!");
			}
		}while(!samePassword);
            fcController.addFarmer(username,name,password,farmerDAO);
            System.out.println("Hi, "+name+"! Your account is successfully created!");
    }
}