package game.wreckaz;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import com.bag.lib.Game;
import com.bag.lib.Screen;
import com.bag.lib.gl.SpriteBatcher;

public class AnimationHandler {

	private float sceneAlpha = 1.0f;
	private float sceneAngle = 0.0f;
	private Game  game;
	private ArrayList<UIButton> buttons;
	
	public AnimationHandler(Game g, ArrayList<UIButton> b)
	{
		this.game = g;
		this.buttons = b;
	}
	
	// Takes a Screen to transition to and a Game instance
	public void transitionToScreenWithRotateAnimation(Screen screen){
    	sceneAlpha -= 0.05f;
    	sceneAngle += 5;
    	if(sceneAlpha <= 0){
    		game.setScreen(screen);
    	}
	}
	
	// Render the menu assets
	public void renderAnimations(GL10 gl, SpriteBatcher batcher){
		
        gl.glColor4f(1, 1, 1, sceneAlpha);  
        
        // Select the assets batch and draw them
        batcher.beginBatch(Assets.mainMenuItems);
        batcher.drawSprite(400, 240, 800, 480, sceneAngle, Assets.mainMenuBackground);
        batcher.endBatch();
        batcher.beginBatch(Assets.tileMapItems);

        for(int idx = 0; idx < buttons.size(); idx++){
        	UIButton but = buttons.get(idx);
        	
        	if(but.state == UIButton.STATE_IDLE){
                batcher.drawSprite(but.position.x, but.position.y,but.R_width , but.R_height, 2.5f*sceneAngle, but.idleAsset);
        	} else if (but.state == UIButton.STATE_PRESSED){
                batcher.drawSprite(but.position.x, but.position.y,but.R_width , but.R_height, 2.5f*sceneAngle, but.pressedAsset);
        	}
        }
        
        batcher.endBatch();    

	}
}
