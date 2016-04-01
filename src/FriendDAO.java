import java.util.*;
import java.io.*;

/**
 * The FriendDAO class stores information related to a Data Manager of Friend objects of the current farmer. 
 */
 
public class FriendDAO{
	private Farmer currentFarmer;
	private String friendFileName="friends.csv";
	private String pendingFileName="pending.csv";
	private ArrayList<String> friendList;
	private ArrayList<String> pendingList;

    /**
     * Constructs a FriendDAO object which initializes and manages a list of Friends and a list of Pending Friends of the current Farmer.
     * @param   currenfarmer  current player (or Farmer).
     */	
	public FriendDAO(Farmer currentFarmer){
		this.currentFarmer=currentFarmer;
		this.friendList= new ArrayList<String>();
		this.pendingList= new ArrayList<String>();
		loadFriends();
	}
	
	/**
     * Returns the String ArrayList of the Friend Names of the Current Farmer.
     * @return  a String ArrayList of Friend Names of the Current Farmer.
     */
	//returns the list of friends
	public ArrayList<String> getFriendList(){
		return this.friendList;
	}
	
    /**
     * Returns the String ArrayList of the Pending Friend Names of the Current Farmer.
     * @return  a String ArrayList of the Pending Friend Names of the Current Farmer.
     */
	//return the list of pending friends
	public ArrayList<String> getPendingList(){
		return this.pendingList;
	}
	
    /**
     * Adds username of the Current Farmer to his friend PendingFriend List csv datafile when he makes a Friend Request to that person.
     * @param   tempFarmer  the username of the Friend who the Current Farmer is sending Friend Request to.
     */
	//assuming that all checks are done, we will add new pending friend request
	public void addNewPending(String tempFarmer){
		PrintStream writer = null;
		//it will just write a new friend request to the end of the file of the specific user 
		try{
			writer = new PrintStream(new FileOutputStream(".\\data\\"+tempFarmer+pendingFileName,true));
			writer.print(","+currentFarmer.getUsername());
			writer.flush();
		}catch(IOException e){
			System.out.println("There was a problem updating the pending list file for temp farmer!");
		}finally{
			writer.close();
		}
	}
	
	/**
     * Adds username of the Current Farmer to his friend Friend csv datafile when his Friend Request is accepted.
     * @param   tempFarmer  the username of the Friend that the Friend Request of the Current Farmer is accepted.
     */ 
	//when you accept a pending friend request, you need to update the other user's friend list 
	public void updateTempFriendList(String tempFarmer){
		PrintStream writer = null;
		//it will just write a new friend  to the end of the file of the specific user 
		try{
			writer = new PrintStream(new FileOutputStream(".\\data\\"+tempFarmer+friendFileName,true));
			writer.print(","+currentFarmer.getUsername());
			writer.flush();
		}catch(IOException e){
			System.out.println("There was a problem updating the friend file for temp farmer!");
		}finally{
			writer.close();
		}
	
	}
	
    /**
     * Updates the String ArrayList of Friend Names of Farmer to his csv Friend datafile.
     * @param   tempFarmer  the username of the Farmer whose datafile is going to be saved
     * @param   tempFriendList  String ArrayList of Friend Names.
     */
	//this method takes in a temp farmer name and his array and we will write to the file
	public void updateTempArraylistFarmer(String tempFarmer,ArrayList<String> tempFriendList){
		PrintStream writer2 = null;

		try{
			writer2 = new PrintStream(new FileOutputStream(".\\data\\"+tempFarmer+friendFileName));
			for(int count=0;count<tempFriendList.size();count++){
				String tempPending = tempFriendList.get(count);
				writer2.print(tempPending);
				if(count!=tempFriendList.size()-1){
					writer2.print(",");
				}
			}
		}catch(IOException e){
			System.out.println("There was a problem updating the friend file for temp farmer!");
		}finally{
			writer2.close();
		}
		
	}
	
	/**
     * Removes the username of the Current Farmer from the PendingFriend List of his Friend whose Friend Request got accepted.
     * @param   tempFarmer  the name of the friend whose the Friend Request got Accepted.
     */ 
	//when you accept a pending friend request ,update the other friend's pending list
	public void updateTempPendingList(String tempFarmer){
		//after adding to his friend list we need to remove the pending friend request
		
		//need to check if the temp friend has a pending request
		ArrayList<String> tempList = getPendingList(tempFarmer);
		for(int count=0;count<tempList.size();count++){
			if(tempList.get(count).equals(currentFarmer.getUsername())){
				tempList.remove(count);
				break;
			}
		}
	//now we will update the temp friend pending list
		PrintStream writer2 = null;

		try{
			writer2 = new PrintStream(new FileOutputStream(".\\data\\"+tempFarmer+pendingFileName));
			for(int count=0;count<tempList.size();count++){
				String tempPending = tempList.get(count);
				writer2.print(tempPending);
				if(count!=tempList.size()-1){
					writer2.print(",");
				}
			}
		}catch(IOException e){
			System.out.println("There was a problem updating the pending file for temp farmer!");
		}finally{
			writer2.close();
		}
	}
	
    /**
     * Returns the String ArrayList of PendingFriend Names of Farmer who sent Friend Request to the Current Farmer.
     * @param   farmerName  the username of Farmer who sent Friend Request to the Current Farmer.
     */		
	//this method will check if the user is already a pending user in the person's friend list
	public ArrayList<String> getPendingList(String farmerName){
		Scanner sc = null;
		ArrayList<String> tempPendingList = new ArrayList<String>();
		try{
			sc = new Scanner(new File(".\\data\\"+farmerName+pendingFileName));
			sc.useDelimiter(",|\r\n");
			while(sc.hasNext()){
				String tempFriend = sc.next();
				tempPendingList.add(tempFriend);
				}
			
		}catch(FileNotFoundException e){
			System.out.println("The temporary farmer pending list file is not available!");
		}
		return tempPendingList;
	}
    
	/**
     * Returns the String ArrayList of Friend Names of Farmer who is friend of the Current Farmer.
     * @param   farmerName  the username of Farmer who sent Friend Request to the Current Farmer.
     */
    //this method will return the friend's friendlist by his 
	public ArrayList<String> getFriendList(String farmerName){
		Scanner sc = null;
		ArrayList<String> tempFriendList = new ArrayList<String>();
		try{
			
			sc = new Scanner(new File(".\\data\\"+farmerName+friendFileName));
			sc.useDelimiter(",|\r\n");
			while(sc.hasNext()){
				String tempFriend = sc.next();
				tempFriendList.add(tempFriend);
				}
			
		}catch(FileNotFoundException e){
			System.out.println("The temporary farmer friend list file is not available!");
		}
		return tempFriendList;
	}
	
		
    /**
     * Loads Friend csv datafile and Pending Friend csv datafile of the Current Farmer.
     */
	//loadFriends will load both friend and pending friends of the currenfarmer
	public void loadFriends(){
		checkFileExist(".\\data\\"+currentFarmer.getUsername()+friendFileName,".\\data\\"+currentFarmer.getUsername()+pendingFileName);
		Scanner sc = null;
		try{
			sc = new Scanner(new File(".\\data\\"+this.currentFarmer.getUsername()+friendFileName));
			sc.useDelimiter(",|\r\n");
			while(sc.hasNext()){
				String tempFriend = sc.next();
				this.friendList.add(tempFriend);
			}
		}catch(FileNotFoundException e){
			System.out.println("The farmer friend list file is not available!");
		}
		
		Scanner sc2 = null;
		try{
			sc2 = new Scanner(new File(".\\data\\"+this.currentFarmer.getUsername()+pendingFileName));
			sc2.useDelimiter(",|\r\n");
			while(sc2.hasNext()){
				String tempPending = sc2.next();
				this.pendingList.add(tempPending);
			}
		}catch(FileNotFoundException e){
			System.out.println("The farmer pending list file is not available!");
		}
	}
	
    /**
     * Updates the Friend List of the Current Farmer to his Friend csv datafile.
     */ 
	//this update friendlist method will update the friendlist 
	public void updateFriendList(){
		PrintStream writer = null;
		//it will rewrite all the contents of the friend list in to the file 
		try{
			writer = new PrintStream(new FileOutputStream(".\\data\\"+currentFarmer.getUsername()+friendFileName));
			for(int count=0;count<friendList.size();count++){
				String tempFriend = friendList.get(count);
				writer.print(tempFriend);
				if(count!=friendList.size()-1){
					writer.print(",");
				}
			}
			writer.flush();
		}catch(IOException e){
			System.out.println("There was a problem updating the friend list file!");
		}finally{
			writer.close();
		}
		
	}
	
    /**
     * Updates the PendingFriend List of the Current Farmer to his PendingFriend csv datafile.
     */ 
	//this update pendinglist method will update the pendinglist
	public void updatePendingList(){
		PrintStream writer = null;
		//it will rewrite all the contents of the pending list in to the file 
		try{
			writer = new PrintStream(new FileOutputStream(".\\data\\"+currentFarmer.getUsername()+pendingFileName));
			for(int count=0;count<pendingList.size();count++){
				String tempPending = pendingList.get(count);
				writer.print(tempPending);
				if(count!=pendingList.size()-1){
					writer.print(",");
				}
			}
			writer.flush();
		}catch(IOException e){
			System.out.println("There was a problem updating the pending list file!");
		}finally{
			writer.close();
		}
		
	}
	
	
	/**
     * Checks whether FriendList csv datafile and PendingFriend List csv datafile exists.
     * @param   friendFileName  the name of the Friend csv datafile
     * @param   pendingFileName  the name of the PendingFriend csv datafile
     */ 
	//checkfileexist will check if the file exist,if it doesnt,create new file
	public void checkFileExist(String friendFileName,String pendingFileName){
			File friendFile =new File(friendFileName);
			File pendingFile =new File(pendingFileName);
		if(!friendFile.exists()){
			PrintStream writer = null;
			try{
				writer = new PrintStream(new FileOutputStream(friendFile, true));
			}catch(IOException e){
				System.out.println("Problem with creating a farmer friend list file!");
			}finally{
				writer.close();
			}
		}
		if(!pendingFile.exists()){
			PrintStream writer = null;
			try{
				writer = new PrintStream(new FileOutputStream(pendingFile, true));
			}catch(IOException e){
				System.out.println("Problem with creating a farmer pending friend list file!");
			}finally{
				writer.close();
			}
		}
	}
	
}