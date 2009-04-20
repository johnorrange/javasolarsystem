
import java.net.*;

import javax.media.j3d.*; 
import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

public class Star extends HeavenlyBody {
	public Star(float radius, URL imgFileName, double angle, long rotPrd, long revPrd, Vector3d pos, Canvas3D canvas) {
		super(radius, imgFileName, angle, rotPrd, revPrd, pos, canvas);
	}
	
	@Override
	public float getPreferredShininess() {
		return 128.0f;
	}
	
	@Override
	public Material getPreferredMaterial() {
		Color3f black = new Color3f();
		Color3f white = new Color3f(1.0f, 1.0f, 1.0f);
		Material mat = new Material(black, white, black, black, getPreferredShininess());
		mat.setLightingEnable(true);
		return mat;
	}
	
	@Override
	public TransformGroup getPreferredTransformGroup() {
		// TODO Auto-generated method stub
		TransformGroup group = new TransformGroup();
		group.addChild(this);
		return group;
	}
}

// end of Star.java