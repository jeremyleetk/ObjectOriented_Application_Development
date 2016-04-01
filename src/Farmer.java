import java.util.*;
import java.text.*;

/**
 * The Farmer class stores information related to a Player (or Farmer) in FarmCity Game. 
 */

public class Farmer{
	private String username;
	private String name;
	private String password;
	private int xp;
	private String rank;
	private int giftCounts;
	private Date lastGiftDate;
	private int gold;
	private ArrayList<String> lastGiftFriends;
 
 /**
 * Default constructor that constructs a Farmer object with specified username, name and password.
 *
 * When a player first registers in the game, he will be automatically assigned Novice rank 
   with gold = 1000, XP = 0, available gifts to be sent daily = 5 
   and the date that a gift is last sent will be initialized to be a new day.
 */	
	//this constructor is called when the person registers 
	public Farmer(String username, String name ,String password){
		this.username=username;
		this.name=name;
		this.password=password;
		this.gold=1000;
		this.xp=0;
		this.rank="novice";
		this.giftCounts=5;
		this.lastGiftDate= new Date();
		this.lastGiftFriends=new ArrayList<String>();
	}

/**
 *  Constructs a Farmer object with specified username, name, password, amount of gold, rank, number of gifts available to be sent and the date the last gift was sent.
 */  
	//this constructor is called by the load class in the farmerdao when they attempt to read eveything from the farmer class and create new farmers and make new farmerLIST
	public Farmer(String username, String name ,String password,int gold,int xp,String rank,int giftCounts,Date lastGiftDate,ArrayList<String> lastGiftFriends){
		this.username=username;
		this.name=name;
		this.password=password;
		this.gold=gold;
		this.xp=xp;
		this.rank=rank;
		this.giftCounts=giftCounts;
		this.lastGiftDate=lastGiftDate;
		this.lastGiftFriends=lastGiftFriends;
	}

/**
 * Returns a list of Friends who received the Gift within specific day.
 * @return  String ArrayList of Friends who received the Gift within specific day.
 */
	//this returns the array list of friends who u last gift
	public ArrayList<String> getLastGiftFriends(){
		return this.lastGiftFriends;
	}
    
/**
 * Sets list of Friends who received the Gift within the day.
 * @param   ArrayList<String> lastGiftFriends  the String ArrayList of Friends who received the Gift within specific day.
 */	
	//this sets the array list of friends who u last gift
	public void setlastGiftFriends(ArrayList<String> lastGiftFriends){
		this.lastGiftFriends=lastGiftFriends;
	}
/**
 * Returns the Username of the farmer.
 * @return  the Username of the farmer.
 */	
	//this returns the id of the farmer for file writing
	public String getUsername(){
		return this.username;
	}
    
/**
 * Returns the Name of the farmer.
 * @return  the Name of the farmer.
 */		
	//this returns the name of the farmer for file writing
	public String getName(){
		return this.name;
	}

/**
 * Returns the Password of the farmer
 * @return  the Password of the farmer
 */	
	//return the password of the farmer for file writing
	public String getPassword(){
		return this.password;
	}

/**
 * Returns the amount of Gold of the farmer
 * @return  the amount of Gold of the farmer
 */	
	//returns the selected farmer gold
	public int getGold(){
		return this.gold;
	}

/**
 * Returns the XP of the farmer
 * @return  the XP of the farmer
 */	
	//returns the selected farmer xp
	public int getXp(){
		return this.xp;
	}
    
/**
 * Returns the Rank of the farmer
 * @return  the Rank of the farmer
 */	
	//returns the selected farmer's rank
	public String getRank(){
		return this.rank;
	}

/**
 * Returns the number of Gifts available to be sent daily of the farmer
 * @return  the number of Gifts available to be sent daily of the farmer
 */	
	//returns the selected farmers giftcount
	public int getGiftCounts(){
		return this.giftCounts;
	}

/**
 * Sets the number of Gifts available to be sent daily
 * @param   giftCounts  the number of Gifts available to be sent daily
 */	
	public void setGiftCounts(int giftCounts){
		this.giftCounts=giftCounts;
	}

/**
 * Returns the Date that the last gift was sent
 * @return  the Date that the last gift was sent
 */	
	//return the selected farmer last gift date
	public Date getLastGiftDate(){
		return this.lastGiftDate;
	}

/**
 * Sets the amount of Gold of the farmer
 * @param   gold  the amount of Gold to be set for the farmer
 */	
	//sets the gold for the usre
	public void setGold(int gold){
		this.gold=gold;		
	}

/**
 * Sets the XP of the farmer
 * @param xp  the XP to be set for the farmer
 */	
	//sets the xp for the user
	public void setXp(int xp){
		this.xp=xp;		
	}

/**
 * Sets the Rank of the farmer
 * @param   rank  the Rank to be set for the farmer
 */ 	
	public void setRank(String rank){
		this.rank=rank;		
	}

}