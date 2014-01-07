package game.wreckaz;

import com.bag.lib.DynamicGameObject;
import com.bag.lib.gl.TextureRegion;

public class ShipWeapon extends DynamicGameObject {

	public TextureRegion bodyTexture;
	public TextureRegion iconTextureReady;
	public TextureRegion iconTextureLoading;
	
	public int damage;
	public float hitPercentage;
	public float projectileSpeed;
	public String name;
	
	public boolean isSelected;
	public boolean isFireReady;
	public boolean isFiring;
	
	private float reloadTime;
	private float time;
	
	public ShipWeapon(float x, float y, float width, float height) {
		super(x, y, width, height);
		reset();
	}
	
	public void reset()
	{
		isSelected = false;
		isFireReady = true; // DEBUG
		isFiring = false;
		time = 0.0f;
	}
	
	public void initialize(float fixedReloadTime, int dmg, float hit, float speed, String initName)
	{
		this.name = initName;
		this.reloadTime = fixedReloadTime;
		this.damage = dmg;
		this.hitPercentage = hit;
		this.projectileSpeed = speed;
	}
	
	public void assignAssets(TextureRegion bodyAsset, TextureRegion iconAssetOK , TextureRegion iconAssetLoad)
	{
		this.bodyTexture = bodyAsset;
		this.iconTextureReady = iconAssetOK;
		this.iconTextureLoading = iconAssetLoad;
	}
	
	public void update(float dt)
	{
		time += dt;
		
		if(time >= reloadTime) {
			time = 0.0f;
			isFireReady = true; // Set false when shooting
		}
	}

}
