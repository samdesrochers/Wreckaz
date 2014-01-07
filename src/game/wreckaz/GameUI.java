package game.wreckaz;

import java.util.ArrayList;

import com.bag.lib.gl.SpriteBatcher;

public class GameUI {

	SpriteBatcher batcher;
    ArrayList<UIButton> UIButtons;
	UIButton button1;
	UIButton button2;
	
	
	public GameUI(SpriteBatcher sBatcher)
	{
	    // UI Declaration
	    UIButtons = new ArrayList<UIButton>();
	    button1 = new UIButton(100, 50, 160, 35, Assets.blueTile, Assets.redTile);
	    button2 = new UIButton(400, 50, 160, 35, Assets.blueTile, Assets.redTile);
	    
	    UIButtons.add(button1);
	    UIButtons.add(button2);
	    
	    batcher = sBatcher;
	}
	
	public void draw()
	{	
		try {
			batcher.beginBatch(Assets.tileMapItems);
			for (UIButton u : this.UIButtons) {
				if(u.state == UIButton.STATE_IDLE)
					batcher.drawSprite(u.position.x, u.position.y, u.bounds.width, u.bounds.height, u.idleState);
				else
					batcher.drawSprite(u.position.x, u.position.y, u.bounds.width, u.bounds.height, u.pressedState);
			}
			batcher.endBatch();
		} catch(Exception e){}
	}
}
