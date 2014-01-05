package game.wreckaz;

import java.util.ArrayList;

import com.bag.lib.DynamicGameObject;

public class Ship extends DynamicGameObject {
	
	// Ship specific Fields
	public ArrayList<ShipRoom> rooms;		// Different Rooms per Ship
	public ArrayList<ShipMember> members;	// Members aboard the Ship
	public float durability;			// HP of the Ship
	public float armor;					// Armor of the Ship
	
	public Ship(float x, float y, float width, float height) {
		super(x, y, width, height);
	}
	
	// Separate generic construction from specific fields
	public void initialize(float initArmor, float initDurability, ArrayList<ShipRoom> initRooms, ArrayList<ShipMember> initMembers)
	{
		this.rooms = initRooms;
		this.members = initMembers;
		this.durability = initDurability;
		this.armor = initArmor;
	}
	
	public void update(float dt)
	{
		
	}
}