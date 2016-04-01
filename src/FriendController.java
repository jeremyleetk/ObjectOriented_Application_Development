import java.util.*;

/** The FriendController class handles the operational processes of Unfriend, Accept, Reject and Request Friend. */

public class FriendController{

    /**
     * Computes the number of Friends in the Friend csv datafile and Returns a message regarding the number of Friend.
     * @param   friendDAO  Friend Data Access Object which initializes and manages String lists of Friend and PendingFriend of the current Farmer. 
     * @return  Message regarding the number of Friend.   
     */
	public String computeFriendList(FriendDAO friendDAO){
		String message="My Friends:\n";
		ArrayList<String> friendList = friendDAO.getFriendList();
		int counter=0;
		for(int count=0;count<friendList.size();count++){
			++counter;
			message+=counter+". "+friendList.get(count)+"\n";
		}
		return message;
	}
	
    /**
     * Computes the number of Pending Friend Requests from PendingFriend datafile and Returns a message regarding the number of Pending Friend Requests.
     * @param   friendDAO  Friend Data Access Object which initializes and manages String lists of Friend and PendingFriend of the current Farmer. 
     * @return  Message regarding the number of Pending Friend Request.   
     */
	//this method will compute the pending list message
	public String computePendingList(FriendDAO friendDAO){
        ArrayList<String> pendingList = friendDAO.getPendingList();
		String message="\nMy Requests:\n";
		int counter =friendDAO.getFriendList().size();
		for(int count=0;count<pendingList.size();count++){
			++counter;
			message+=counter+". "+pendingList.get(count)+"\n";		
		}
		return message;
	}
	
    /**
     * Unfriends a Friend of the Current Farmer and Returns a message regarding the remove of a Friend.
     * @param   userChoice  the index number of The Friend that the Current Farmer chose. 
     * @param   friendDAO  Friend Data Access Object which initializes and manages String lists of Friend and PendingFriend of the current Farmer. 
     * @param   currentFarmer  the Current Player (or Farmer).
     * @return  Message regarding the remove of a Friend from the Current Farmer FriendList.
     */
	//this method will unfriend a friend that the user has :(
	public String unfriend(String userChoice,FriendDAO friendDAO,Farmer currentFarmer){
		String message="";
		try{
            ArrayList<String> friendList = friendDAO.getFriendList();
            int unfriendChoice = Integer.parseInt(userChoice.charAt(1)+"");
            //validate if the unfriend choice is within the friendlist choice
            if(unfriendChoice> friendList.size() || unfriendChoice<=0){
                return"Sorry! Please unfriend within a valid range!\n";
            }else{
                String tempFriend = friendList.get(unfriendChoice-1);
                message="You have unfriend "+tempFriend;
                
                //remove the other party as a friend
                ArrayList<String> tempFriendList=friendDAO.getFriendList(tempFriend);
                //loop through the arraylist locate the friend and remove
                for(int count=0;count<tempFriendList.size();count++){
                    if(tempFriendList.get(count).equals(currentFarmer.getUsername())){
                        tempFriendList.remove(count);
                        break;
                    }
                }
                
                friendDAO.updateTempArraylistFarmer(tempFriend,tempFriendList);
                friendList.remove(unfriendChoice-1);
            }
		//finally update the user friend file
            friendDAO.updateFriendList();
		}catch(NumberFormatException e){
			return"Sorry the unfriend option u chose was invalid!\n";
		}catch(IndexOutOfBoundsException e){
			return"You did not specify which friend!\n";
		}
		return message;
	}
	
	
	/**
     * Requests to be friend
     * @param   friendRequest  the username of the friend who the Current Farmer sent Friend Request to.
     * @param   friendDAO  Friend Data Access Object which initializes and manages String lists of Friend and PendingFriend of the current Farmer. 
     * @param   farmerDAO  Farmer Data Access Object which initializes and manages list of Farmer objects in FarmCity Game.
     * @param   currentUser  current player (or Farmer).
     * @return  Message regarding the state of the Request.
     */	
	//this method is done when the user requests for a new friend
	public String request(String friendRequest,FriendDAO friendDAO,FarmerDAO farmerDAO,Farmer currentFarmer){
		
		Farmer tempFarmer=null;
		//we have to check if user exists first
		ArrayList<Farmer> farmerList = farmerDAO.getFarmerList();
		for(int count=0;count<farmerList.size();count++){
			Farmer tempFarmer2=farmerList.get(count);
			if(tempFarmer2.getUsername().equals(friendRequest)){
				tempFarmer=tempFarmer2;
			}
		}
		//user exists we need to check if is already friend or is already a pending user
		if(tempFarmer==null){
			return"Sorry! "+friendRequest+" is not an existing user in farm ville!\n";
		}else{
			//check for own friend list if friend
			ArrayList<String> friendList=friendDAO.getFriendList();
			for(int count=0;count<friendList.size();count++){
				if(friendList.get(count).equals(friendRequest)){
					return"You are already his friend!\n";
				}
			}
			ArrayList<String> tempFriendPendingList = friendDAO.getPendingList(friendRequest);
			boolean tempPendingList=false;
			for(int count=0;count<tempFriendPendingList.size();count++){
				if(tempFriendPendingList.get(count).equals(currentFarmer.getUsername())){
					tempPendingList=true;
				}
			}
			if(tempPendingList){
				return"You have already send a previous pending request!\n";
			}
		}
		//finally write to that user's pending list
		friendDAO.addNewPending(friendRequest);
		return"A friend request is sent to "+friendRequest+"\n";
	}
	
	/**
     * Accepts a Friend Request and Returns a message regarding the acception.
     * @param   userChoice  the index number of the Friend Request that the Current Farmer chose. 
     * @param   friendDAO  Friend Data Access Object which initializes and manages String lists of Friend and PendingFriend of the current Farmer. 
     * @return  Message regarding acception of the Friend Request.
     */ 
	//this method will accept a pending friend request
	public String accept(String userChoice,FriendDAO friendDAO){
		String tempFriend=null;
		String message="";
		try{
            int acceptChoice = Integer.parseInt(userChoice.charAt(1)+"");
            ArrayList<String> friendList = friendDAO.getFriendList();
            ArrayList<String> pendingList = friendDAO.getPendingList();
            int friendSize=friendList.size();
            //validate if the acceptChoice is within the pending list range
            if(acceptChoice>friendSize+pendingList.size() || acceptChoice<=friendSize){
                return"Sorry! Please accept a pending friend within the valid range!\n";
            }else{
                tempFriend = pendingList.get(acceptChoice-friendSize-1);
                pendingList.remove(acceptChoice-friendSize-1);
                friendList.add(tempFriend);
                message="You have added "+tempFriend+" as your new friend!";
            }		
            //finally update the user friend file and the pending request file
            friendDAO.updateFriendList();
            friendDAO.updatePendingList();
            //then update the accepted friend file's friend list!
            friendDAO.updateTempFriendList(tempFriend);
            friendDAO.updateTempPendingList(tempFriend);
		}catch(NumberFormatException e){
			return"Sorry the accept option u chose was invalid!\n";
		}catch(IndexOutOfBoundsException e){
			return"You did not specify which friend!\n";
		}
		return message;
	}
	
    /**
     * Rejects a Friend Request and Returns a message regarding the rejection.
     * @param   userChoice  the index number of the Friend Request that the Current Farmer chose. 
     * @param   friendDAO  Friend Data Access Object which initializes and manages String lists of Friend and PendingFriend of the current Farmer. 
     * @return  Message regarding rejection of the Friend Request.
     */
	//this method will remove a pending list friend request
	public String reject(String userChoice,FriendDAO friendDAO){
		String message="";
		try{
            int rejectChoice = Integer.parseInt(userChoice.charAt(1)+"");
            ArrayList<String> friendList =friendDAO.getFriendList();
            ArrayList<String> pendingList = friendDAO.getPendingList();
            int friendSize = friendList.size();
            //validate if the unfriend choice is within the friendlist choice
            if(rejectChoice> friendSize+pendingList.size() || rejectChoice<=friendSize){
                return"Sorry! Please reject within a valid range!";
            }else{
                message="You have removed "+pendingList.get(rejectChoice-friendSize-1)+"from your pending list.";
                pendingList.remove(rejectChoice-friendSize-1);
            }
            //finally update the pending list file
            friendDAO.updatePendingList();
		}catch(NumberFormatException e){
			return"Sorry the reject option u chose was invalid!";
		}catch(IndexOutOfBoundsException e){
			return"You did not specify which friend!";
		}
		return message;
	} 
}