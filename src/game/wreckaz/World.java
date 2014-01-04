package game.wreckaz;

import java.util.ArrayList;

import com.bag.lib.math.Vector2;


public class World {
	
	// Interface, mostly used to access sound effects
    public interface WorldListener {
          //public void sound();
		  int getTime();
    }

    // World's size
    public static final float WORLD_WIDTH 			= 16;
    public static final float WORLD_HEIGHT 			= 9;
    
    // World's states
    public static final int WORLD_STATE_RUNNING 	= 0;
    public static final int WORLD_STATE_NEXT_LEVEL 	= 1;
    public static final int WORLD_STATE_GAME_OVER 	= 2;

    public ArrayList<Ship> ships;
    public final WorldListener listener;
    
    public int state;

    public World(WorldListener listener) {
    	
    	this.state = WORLD_STATE_RUNNING;
    	this.listener = listener;
    	
    	ships = new ArrayList<Ship>();
    	initializeShips();
    	
    }

	public void update(float deltaTime) {
		updateExplosions(deltaTime);
	}

//	private void updatePlayer(float deltaTime, float speed) {
//
////	    if(player.state == Player.PLAYER_STATE_HIT_WALL) {
////	    	explosion = new Explosion(50, (int)player.position.x, (int)player.position.y);
////	    	player.state = player.previousState;
////	    }
//	}

	private void updateExplosions(float deltaTime) {
//		try{	
//			explosion.update(deltaTime);
//		} catch(Exception e){}
	}
	
	private void initializeShips()
	{
		// Player Ship
		Vector2 pCenter = new Vector2(4, 4);
		
		// Rooms
		ArrayList<ShipRoom> rooms = new ArrayList<ShipRoom>();
		ShipRoom s1 = new ShipRoom(pCenter.x - 2, pCenter.y - 2, 2.2f, 2.0f);
		ShipRoom s2 = new ShipRoom(pCenter.x + 2, pCenter.y + 2, 2.2f, 2.0f);
		ShipRoom s3 = new ShipRoom(pCenter.x + 2, pCenter.y - 2, 2.2f, 2.0f);
		ShipRoom s4 = new ShipRoom(pCenter.x - 2, pCenter.y + 2, 2.2f, 2.0f);
		
		rooms.add(s1);
		rooms.add(s2);
		rooms.add(s3);
		rooms.add(s4);
		
		// Ship
		Ship playerShip = new Ship(pCenter.x, pCenter.y, 6.5f, 4.5f);
		playerShip.initialize(100, 100, rooms, null);
		
		ships.add(playerShip);
	}
}

