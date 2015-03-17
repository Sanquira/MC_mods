package bladder.source;

import net.minecraft.block.Block.SoundType;

public class SoundTypeEx extends SoundType {

	public SoundTypeEx(String arg0, float arg1, float arg2) {
		super(arg0, arg1, arg2);
	}

	@Override
	public String getBreakSound() {
		// TODO Auto-generated method stub
		return super.soundName;
	}

	@Override
	public String getStepResourcePath() {
		// TODO Auto-generated method stub
		return super.soundName;
	}

}
