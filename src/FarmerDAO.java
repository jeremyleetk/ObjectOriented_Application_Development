import java.io.*;
import java.util.*;
import java.text.*;

/**
 * The FarmerDAO class stores information related to a Data Manager of Farmer object of the FarmCity Game.
 */
 
public class FarmerDAO{
	
	private ArrayList<Farmer> farmerList;
	private final String fileName=".\\data\\farmer.csv";
	
/**
 * Constructs a FarmerDAO object which initializes and manages a list of Farmer objects of the FarmCity Game.
 */
	//default constructor for farmerDAO
	public FarmerDAO(){
		this.farmerList = new ArrayList<Farmer>();
		this.loadFarmers();
	}

/**
 * Updates the account of Farmers in the FarmCity Game.
 */
 	
	//this will update the file with the latest contents in the arraylist
	public void update(){
		PrintStream writer = null;
		try{
			writer = new PrintStream(new FileOutputStream(fileName));
			for(int count=0;count<farmerList.size();count++){
				Farmer tempFarmer = farmerList.get(count);
				writer.print(tempFarmer.getUsername()+",");
				writer.print(tempFarmer.getName()+",");
				writer.print(tempFarmer.getPassword()+",");
				writer.print(tempFarmer.getGold()+",");
				writer.print(tempFarmer.getXp()+",");
				writer.print(tempFarmer.getRank()+",");
				writer.print(tempFarmer.getGiftCounts()+",");
				SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				writer.print(df.format(tempFarmer.getLastGiftDate())+",");
				ArrayList<String> lastGiftFriends = tempFarmer.getLastGiftFriends();
				for(int count2=0;count2<lastGiftFriends.size();count2++){
					writer.print(lastGiftFriends.get(count2));
					if(count2!=lastGiftFriends.size()-1){
						writer.print("#");
					}
				}
				writer.println();
			}
			writer.flush();
		}catch(IOException e){
			System.out.println("Problem with updating the farmer contents!");
		}finally{
		writer.close();
		}
	}
	
/**
 * Adds the data of new user to the datafile.
 * When there is a new user registered, the FarmCity Game will create 
   a Farmer datafile containing his information regarding Username, Name, Password, Gold, XP, Rank and Gift.
 * @param   farmer : The Farmer that is going to be added into the system.   
 */ 	
	//adds a new Farmer into the database
	public void addFarmer(Farmer farmer){
		//first we will add to the link list then write to file
		farmerList.add(farmer);
		//write farmer contents to file
		PrintStream writer = null;
		try{
		writer = new PrintStream(new FileOutputStream(fileName, true));
		writer.print(farmer.getUsername()+",");
		writer.print(farmer.getName()+",");
		writer.print(farmer.getPassword()+",");
		writer.print(farmer.getGold()+",");
		writer.print(farmer.getXp()+",");
		writer.print(farmer.getRank()+",");
		writer.print(farmer.getGiftCounts()+",");
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		writer.print(df.format(farmer.getLastGiftDate()));
		writer.println(",");
		writer.flush();
		}catch(IOException e){
			System.out.println("Problem with writing farmer contents to file!");
		}finally{
		writer.close();
		}
	}

/**
 * Checks whether the datafile of the farmer exists or not.
 * If datafile cannot be found, it will automatically create a default datafile for the farmer.
 * @param   fileName : the Name of the Datafile
 */
 //this method is required to check if file exist before doing anything to it. If file does not exist, it will create a new file.
	public void checkFileExist(String fileName){
		File file =new File(fileName);
		if(!file.exists()){
			PrintStream writer = null;
			try{
				writer = new PrintStream(new FileOutputStream(fileName, true));
			}catch(IOException e){
				System.out.println("Problem with creating a farmer  file!");
			}finally{
				writer.close();
			}
		}
	}
	
/**
 * Loads the datafile of the Farmer
 */	
	
	public void loadFarmers(){
		Scanner sc = null;
		checkFileExist(fileName);							
		try{
			sc = new Scanner(new File(fileName));
			sc.useDelimiter(",|\r\n");
		while(sc.hasNext()){
			String username = sc.next();
			String name = sc.next();
			String password = sc.next();
			int gold = Integer.parseInt(sc.next());
			int xp = Integer.parseInt(sc.next());
			String rank = sc.next();
			int giftCount = Integer.parseInt(sc.next());
			String textDate=sc.next();
			Date lastGiftDate = new SimpleDateFormat("dd/MM/yyyy").parse(textDate);
			//checking if 2 dates are similar, if the lastgiftdate is smaller than currentdate by 1 dd then we will make his giftcount to 5
			//for now not implemented when function works already then change :) 
			Calendar calender = Calendar.getInstance();
			calender.setTime(lastGiftDate);
			int storedDay = calender.get(Calendar.DAY_OF_YEAR); 
			int storedYear = calender.get(Calendar.YEAR);
			//how it works is i will give the day of year and year number 
			Date currentDate = new Date();
			Calendar calender2 = Calendar.getInstance();
			calender2.setTime(currentDate);
			int currentDay =calender2.get(Calendar.DAY_OF_YEAR); 
			int currentYear = calender2.get(Calendar.YEAR);
			String[] listOfFriends=sc.next().split("[#]+");
			ArrayList<String> lastGiftFriends = new ArrayList<String>();
				for(int count=0;count<listOfFriends.length;count++){
					lastGiftFriends.add(listOfFriends[count]);			
			}
			//the condition to fufil is that either the year is small or if same year , the day must be smaller!
			if(currentYear>storedYear || (currentYear==storedYear && storedDay<currentDay) ){
				giftCount=5;
				lastGiftDate=currentDate;
				lastGiftFriends = new ArrayList<String>();
			}
			//adds new farmer into the farmerlist
			farmerList.add(new Farmer(username,name,password,gold,xp,rank,giftCount,lastGiftDate,lastGiftFriends));
		}
		}catch(FileNotFoundException e){
			System.out.println("Sorry the file you are finding is not available");
		}catch(NumberFormatException e){
			System.out.println("One of the file data had problem namely the integer conversion");
		}catch(ParseException e){
			System.out.println("One of the file data had problem converting namely the date");
		}finally{
			sc.close();
		}
	}

/**
 * Returns the ArrayList of the Farmer objects in the FarmCity Game.
 * @return  an ArrayList of the Farmer objects in the FarmCity Game.
 */	
	//returns the list of farmers
	public ArrayList<Farmer> getFarmerList(){
		return this.farmerList;
	}
	

}