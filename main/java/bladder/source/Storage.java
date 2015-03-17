package bladder.source;

public class Storage {

	private static Storage instance = null;

	public static final String MODID = "better_ladder";
	public static final String MODNAME = "Better Ladder";
	public static final String MODVERSION = "0.0";

	protected Storage() {
	}

	public static Storage getInstance() {
		if (instance == null) {
			instance = new Storage();
		}
		return instance;
	}

	public String getTexturePath(String name) {
		return MODID + ":" + name.substring(5);
	}

}