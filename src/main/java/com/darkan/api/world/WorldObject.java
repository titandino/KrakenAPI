package com.darkan.api.world;

import com.darkan.api.entity.MyPlayer;
import com.darkan.api.util.Utils;
import com.darkan.cache.def.maps.Region;
import com.darkan.cache.def.objects.ObjectDef;

import kraken.plugin.api.Actions;

public class WorldObject extends WorldTile implements Interactable {
	private static final int[] MENU_OPS = { 
			Actions.MENU_EXECUTE_OBJECT1, Actions.MENU_EXECUTE_OBJECT2, Actions.MENU_EXECUTE_OBJECT3, 
			Actions.MENU_EXECUTE_OBJECT4, Actions.MENU_EXECUTE_OBJECT5, Actions.MENU_EXECUTE_OBJECT6 
		};
	
	protected int id;
	protected ObjectType type;
	protected int rotation;
	protected boolean validated;
	
	public WorldObject(int id, WorldTile tile) {
		super(tile);
		this.id = id;
		this.type = ObjectType.SCENERY_INTERACT;
	}

	public WorldObject(int id, ObjectType type, int rotation, WorldTile tile) {
		super(tile);
		this.id = id;
		this.type = type;
		this.rotation = rotation;
	}

	public WorldObject(int id, ObjectType type, int rotation, int x, int y, int plane) {
		super(x, y, plane);
		this.id = id;
		this.type = type;
		this.rotation = rotation;
	}

	public WorldObject(WorldObject object) {
		super(object);
		this.id = object.id;
		this.type = object.type;
		this.rotation = object.rotation;
	}
	
	public int getId() {
		return id;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}
	
	public ObjectType getType() {
		return type;
	}
	
	public void setType(ObjectType i) {
		this.type = i;
	}

	public int getRotation() {
		return rotation;
	}
	
	public int getSlot() {
		return type.slot;
	}
	
	public boolean interact(int action) {
		return interact(action, true);
	}
	
	public boolean interact(int action, boolean validate) {
		if (action < 0 || action >= MENU_OPS.length)
			return false;
		
		if (validate) {
			//TODO remove this once cracksmoke fixes his object coordinates
			boolean valid = Region.validateObjCoords(this);
			int x = (int) (valid ? getX() : getX() - Math.ceil(getDef().sizeX / 2));
			int y = (int) (valid ? getY() : getY() - Math.ceil(getDef().sizeY / 2));
			
			Actions.menu(MENU_OPS[action], getId(), x, y, Utils.random(0, Integer.MAX_VALUE));
		} else  {
			Actions.menu(MENU_OPS[action], getId(), getX(), getY(), Utils.random(0, Integer.MAX_VALUE));
		}
		return true;
	}
	
	public boolean interact(String action) {
		int op = getDef().getOpIdForName(action);
		if (op != -1) {
			interact(op);
			return true;
		}
		return false;
	}

	public ObjectDef getDef() {
		return ObjectDef.get(id, MyPlayer.getVars());
	}

	public String getName() {
		return getDef().name;
	}

	@Override
	public boolean hasOption(String string) {
		return getDef().containsOp(string);
	}
	
	@Override
	public String toString() {
		return "[" + id + " (" + getName() + "), " + type + ", " + rotation + ", " + super.toString() + " clipType: " + ObjectDef.get(id).clipType + "]";
	}

	@Override
	public String name() {
		return getName();
	}
}
