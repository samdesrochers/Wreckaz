package game.wreckaz;

import java.util.List;
import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

import com.bag.lib.Game;
import com.bag.lib.Input.TouchEvent;
import com.bag.lib.gl.Camera2D;
import com.bag.lib.gl.SpriteBatcher;
import com.bag.lib.impl.GLScreen;
import com.bag.lib.math.OverlapTester;
import com.bag.lib.math.Vector2;
import game.wreckaz.World.WorldListener;

public class GameScreen extends GLScreen {
	
	public static GameScreen instance;
	
	// States 
    static final int GAME_READY 	= 0;    
    static final int GAME_RUNNING 	= 1;
    static final int GAME_PAUSED 	= 2;
    static final int GAME_LEVEL_END = 3;
    static final int GAME_OVER 		= 4;
    
    // Touch States
    static final int STATE_TOUCH_IDLE = 10;
    static final int STATE_TOUCH_WEAPON_SELECTED = 11;
    static final int STATE_TOUCH_MEMBER_SELECTED = 12;
    
    // Screen size
    static final int SCREEN_WIDTH	= 800;
    static final int SCREEN_HEIGHT	= 480;

    int 			state;
    int 			touchState;
    
    Camera2D 		guiCam;
    Vector2 		touchPoint;

    SpriteBatcher 	batcher;
    GameUI			gameUI;
    
    World 			world;
    WorldListener 	worldListener;
    WorldRenderer 	renderer;
    
    float 			startTime;
    float 			elapsedTime;
    
	boolean 		gameOverTouch = false;
	
	public static void makeInstance(Game game)
	{
		if(GameScreen.instance == null) {
			GameScreen.instance = new GameScreen(game);
		}
	}
	
	public static GameScreen getInstance()
	{
		// Sketchy singleton pattern inherited from framework architecture and the need to pass a unique "game".
		if(instance == null){	// If null (SHOULD NEVER OCCUR) kill the game
			throw new RuntimeException("No instance for GameScreen - Aborting");
		}
		return instance;
	}
	
    public GameScreen(Game game) {
        super(game);
        
        state = GAME_READY;
        touchState = STATE_TOUCH_IDLE;
        
        guiCam = new Camera2D(glGraphics, SCREEN_WIDTH, SCREEN_HEIGHT);
        touchPoint = new Vector2();
        
        // Create a worldListener, to trigger events on the world
        worldListener = new WorldListener() {		
			public int getTime() {
				return (int)elapsedTime;
				
			}
        };
        
        batcher = new SpriteBatcher(glGraphics, 5000);
        gameUI = new GameUI(batcher);
        
        world = new World(worldListener, gameUI);
        renderer = new WorldRenderer(glGraphics, batcher, world);
        
        // Variables
        startTime = System.currentTimeMillis();
        elapsedTime = 0;
    }
    
	/**************************************************
	 * 
	 * 	GAMESCREEN UPDATE SECTION
	 * 
	 **************************************************/
	@Override
	public void update(float deltaTime) {
	    
/*		if(deltaTime > 0.1f)
	        deltaTime = 0.1f;*/
	    
	    switch(state) {
	    case GAME_READY:
	        updateReady();
	        break;
	    case GAME_RUNNING:
	        updateRunning(deltaTime);
	        break;
	    case GAME_PAUSED:
	        updatePaused();
	        break;
	    case GAME_OVER:
	        updateGameOver();
	        break;
	    }
	}
	
	// Update when state is READY
	private void updateReady() {
		// First touch 
	    //if(game.getInput().getTouchEvents().size() > 0) {
	        state = GAME_RUNNING;
	    //}
	}
	
	// Update when state is RUNNING
	private void updateRunning(float deltaTime) {
	    
		// Check Touch Events
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
	    int len = touchEvents.size();
	    for(int i = 0; i < len; i++) {
	        TouchEvent event = touchEvents.get(i);
	        touchPoint.set(event.x, event.y);
	        guiCam.touchToWorld(touchPoint);
	        
	        UITouchHandler(event, touchPoint);
	        worldTouchHandler(event, touchPoint);
	        
	    }    
	    elapsedTime += deltaTime;
	    
	    // Update World
	    world.update(deltaTime);
	}
	
	private void updatePaused() {
		// game.setScreen(new MainMenuScreen(game));
	}	
	
	private void updateGameOver() {
	    List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
	    int len = touchEvents.size();
	    for(int i = 0; i < len; i++) {      
	        TouchEvent event = touchEvents.get(i);
	        touchPoint.set(event.x, event.y);
	        guiCam.touchToWorld(touchPoint);
	    }
	}
	
	private void worldTouchHandler(TouchEvent event, Vector2 point)
	{
		// Convert for world coordinate (ex 16:9 rather than 800x480)
		point.x = (point.x/SCREEN_WIDTH) * WorldRenderer.FRUSTUM_WIDTH;
		point.y = (point.y/SCREEN_HEIGHT) * WorldRenderer.FRUSTUM_HEIGHT;
		
		switch (touchState) {
		
		// No prior touch states detected
		case STATE_TOUCH_IDLE:
			if(event.type == TouchEvent.TOUCH_DRAGGED ||event.type == TouchEvent.TOUCH_DOWN){     
	         	
	        } else if(event.type == TouchEvent.TOUCH_UP){
	
	        }
			break;
			
		// User previously touched a ship member
		case STATE_TOUCH_MEMBER_SELECTED:
			if(event.type == TouchEvent.TOUCH_DRAGGED ||event.type == TouchEvent.TOUCH_DOWN){     
	         	
	        } else if(event.type == TouchEvent.TOUCH_UP){
	
	        }
			break;
			
		// User previously touched a weapon in the UI
		case STATE_TOUCH_WEAPON_SELECTED:
			
			// Touch needs to be in "enemy territory" (right side of screen)
			if(point.y >= 2 && point.x > 8) {
				if(event.type == TouchEvent.TOUCH_DRAGGED ||event.type == TouchEvent.TOUCH_DOWN){     
		         	
		        } else if(event.type == TouchEvent.TOUCH_UP){
		        	Log.d("Test", "State Touch - Shooting");
		        	world.playerShip.shoot(point);
		        	touchState = STATE_TOUCH_IDLE;
		        }
				break;
			}

		default:
			break;
		}
	}
	
	// Defines interaction with UI Elements according to different states
	private void UITouchHandler(TouchEvent event, Vector2 point)
	{
		switch (touchState) {
		
		// No prior touch states detected
		case STATE_TOUCH_IDLE:
			if(event.type == TouchEvent.TOUCH_DRAGGED ||event.type == TouchEvent.TOUCH_DOWN){     
	         	
	        } else if(event.type == TouchEvent.TOUCH_UP){
	        	if(OverlapTester.pointInRectangle(gameUI.button1.bounds, point)) {
	        		
	            	// If weapon is ready to fire, allow selection
	            	if(world.playerShip.selectWeapon(0)) {
	            		Log.d("Test", "State Touch - Weapon Selected");
	            		touchState = STATE_TOUCH_WEAPON_SELECTED;
	            		gameUI.button1.state = UIButton.STATE_PRESSED;
	            	} 
	            }	
	        }
			break;
			
		// User previously touched a ship member
		case STATE_TOUCH_MEMBER_SELECTED:
			if(event.type == TouchEvent.TOUCH_DRAGGED ||event.type == TouchEvent.TOUCH_DOWN){     
	         	
	        } else if(event.type == TouchEvent.TOUCH_UP){
	
	        }
			break;
			
		// User previously touched a weapon in the UI
		case STATE_TOUCH_WEAPON_SELECTED:
			if(event.type == TouchEvent.TOUCH_DRAGGED ||event.type == TouchEvent.TOUCH_DOWN){     
	         	
	        } else if(event.type == TouchEvent.TOUCH_UP){
	        	
	        	// Unselect if already selected. Select if not
	        	if(OverlapTester.pointInRectangle(gameUI.button1.bounds, point)) {

	            	// If weapon is ready to fire, allow selection
	            	if(world.playerShip.weapons.get(0).isSelected) {
	            		Log.d("Test", "State Touch - Weapon Unselected");
	            		world.playerShip.weapons.get(0).unselect();
	            		gameUI.button1.state = UIButton.STATE_READY;
	            		touchState = STATE_TOUCH_IDLE;
	            	} else {
	            		if(world.playerShip.selectWeapon(0)) {
		            		Log.d("Test", "State Touch - Weapon Selected");
		            		gameUI.button1.state = UIButton.STATE_IDLE;
		            		touchState = STATE_TOUCH_WEAPON_SELECTED;
		            	} 
	            	}
	            }
	        }
			break;

		default:
			break;
		}
	}

	/**************************************************
	 * 
	 * 	GAMESCREEN DRAWING SECTION
	 * 
	 **************************************************/
	
	@Override
	public void present(float deltaTime) {
		
		deltaTime /= 2; // to adjust framerate

		GL10 gl = glGraphics.getGL();
	    
		// Render Game objects
		for(int i = 0; i < 2; i++){
		    gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		    gl.glEnable(GL10.GL_TEXTURE_2D);
			renderer.render();
		}
	    
	    guiCam.setViewportAndMatrices();
	    gl.glEnable(GL10.GL_BLEND);
	    gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
	    gl.glColor4f(1, 1, 1, 1);
	    
	    //batcher.beginBatch(Assets.gameScreenItems);
	    
		// Draw the UI for current State
	    switch(state) {
	    case GAME_READY:
	        presentReady();
	        break;
	    case GAME_RUNNING:
	        presentRunning();
	        break;
	    case GAME_PAUSED:
	        presentPaused();
	        break;
	    case GAME_LEVEL_END:
	        presentLevelEnd();
	        break;
	    case GAME_OVER:
	        presentGameOver();
	        break;
	    }
	    //batcher.endBatch();
	    gl.glDisable(GL10.GL_BLEND);
		
	}
	
	private void presentReady() {
         // Draw here
		drawUI();
	}
	
	private void presentRunning() {
		// Draw here
		drawUI();
	}
	
	private void presentPaused() { 
		// Draw here
	}
	
	private void presentLevelEnd() {
		// Draw here
	}
	
	private void presentGameOver() {
		// Draw here
	}
	
	private void drawUI()
	{
		gameUI.draw();
	}

    @Override
    public void pause() {
    	
    }

    @Override
    public void resume() {        
    }

    @Override
    public void dispose() {       
    }
}


