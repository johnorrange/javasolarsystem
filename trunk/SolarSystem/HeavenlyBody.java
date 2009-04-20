

/**
	@author WANG Hongliang
    @time Mar 25, 2008
 */

import java.net.*;

import javax.media.j3d.*;
import javax.vecmath.*;

import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;

public abstract class HeavenlyBody extends Sphere {

	protected double tiltAngle;
	protected float shininess;
	protected TextureLoader textureLoader;
	protected TransformGroup transformGroup;
	protected Appearance appearance;
	protected Material material;
	protected Vector3d position;
	protected long rotationPeriod;
	protected long revolutionPeriod;
	
	public HeavenlyBody(float radius, URL imgFileName, double angle, long rotPrd, long revPrd, Vector3d pos, Canvas3D canvas) {
		super(radius, Sphere.GENERATE_NORMALS|Sphere.GENERATE_TEXTURE_COORDS, 60);
		
		setTiltAngle(angle);
		
		setPosition(pos);
		
		setRotationPeriod(rotPrd);
		
		setRevolutionPeriod(revPrd);
		
		// set texture loader
		textureLoader = new TextureLoader(imgFileName, canvas);
		textureLoader.getTexture().setEnable(true);
		
		setAppearance(getPreferredAppearance());
		
		transformGroup = getPreferredTransformGroup();

	}

	public double getTiltAngle() {
		return tiltAngle;
	}
	
	public float getShininess() {
		return shininess;
	}
	
	public abstract float getPreferredShininess();
	
	public TextureLoader getTextureLoader() {
		return textureLoader;
	}
	
	public TransformGroup getTransformGroup() {
		return transformGroup;
	}
	
	public Appearance getPreferredAppearance() {
		Appearance app = new Appearance();
		app.setMaterial(getPreferredMaterial());
		app.setTexture(getTextureLoader().getTexture());
		TextureAttributes textureAttributes = new TextureAttributes();
		textureAttributes.setTextureMode(TextureAttributes.MODULATE);
		app.setTextureAttributes(textureAttributes);
		return app;
	}
	
	public abstract Material getPreferredMaterial();
	
	public Material getMaterial() {
		return material;
	}
	
	public TransformGroup getPreferredTransformGroup() {
    	Transform3D t3d = new Transform3D();
        t3d.rotZ(getTiltAngle());
        t3d.setTranslation(getPosition());
        TransformGroup group = new TransformGroup(t3d);
        group.addChild(this);
        
        Transform3D rotAxis = new Transform3D();
        rotAxis.rotZ(getTiltAngle());
        rotAxis.setTranslation(getPosition());
        TransformGroup rotGroup = new TransformGroup();
        rotGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        rotGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        
        //Alpha rotAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE, 0, 0, 4000, 0, 0, 0, 0, 0);
        Alpha rotAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE, 0, 0, getRotationPeriod()/10, 0, 0, 0, 0, 0);
        RotationInterpolator rotRotator = new RotationInterpolator(rotAlpha, rotGroup, rotAxis, 0.0f, (float)Math.PI*2.0f);
        rotRotator.setSchedulingBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 320.0));
        rotGroup.addChild(rotRotator);
        rotGroup.addChild(group);
        
        //return rotGroup;
        
        Transform3D revAxis = new Transform3D();
        TransformGroup revGroup = new TransformGroup();
        revGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        revGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        
        Alpha revAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE, 0, 0, getRevolutionPeriod(), 0, 0, 0, 0, 0);
        RotationInterpolator revRotator = new RotationInterpolator(revAlpha, revGroup, revAxis, 0.0f, (float)Math.PI*2.0f);
        revRotator.setSchedulingBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 320.0));
        revGroup.addChild(revRotator);
        revGroup.addChild(rotGroup);
        
        return revGroup;
	}
	
	public Vector3d getPosition() {
		return position;
	}
	
	public long getRotationPeriod() {
		return rotationPeriod;
	}
	
	public long getRevolutionPeriod() {
		return revolutionPeriod;
	}

	public void setTiltAngle(double tiltAngle) {
		this.tiltAngle = tiltAngle;
	}
	
	public void setShininess(float shininess) {
		this.shininess = shininess;
	}
	
	public void setMaterial(Material material) {
		this.material = material;
	}
	
	public void setPosition(Vector3d position) {
		this.position = position;
	}
	
	public void setRotationPeriod(long rotationPeriod) {
		this.rotationPeriod = rotationPeriod;
	}
	
	public void setRevolutionPeriod(long revolutionPeriod) {
		this.revolutionPeriod = revolutionPeriod;
	}
}