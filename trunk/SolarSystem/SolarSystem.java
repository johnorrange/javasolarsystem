

import java.applet.Applet; 
import java.awt.BorderLayout; 
import com.sun.j3d.utils.applet.MainFrame; 
import com.sun.j3d.utils.behaviors.mouse.*;
import com.sun.j3d.utils.behaviors.keyboard.*;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.*; 
import javax.media.j3d.*; 
import javax.vecmath.*;

public class SolarSystem extends Applet{ 
	private HeavenlyBody sun;
	private HeavenlyBody mercury;
	private HeavenlyBody venus;
	private HeavenlyBody earth;
	private HeavenlyBody mars;
	private HeavenlyBody jupiter;
	private HeavenlyBody saturn;
	private HeavenlyBody uranus;
	private HeavenlyBody neptune;
	
	private BranchGroup objRoot;
	private TransformGroup transformGroup;
	private BoundingSphere bounds;
	private Color3f lightColor;
	private Point3f attenuation;
	private PointLight upperLight, middleLight, lowerLight, leftLight, rightLight, forthLight, backLight;
    	
    public BranchGroup createSceneGraph(Canvas3D c) { 
        objRoot = new BranchGroup();
        
        bounds = new BoundingSphere(
        		new Point3d(0.0f, 0.0f, 0.0f), 420.0f);

        Transform3D smallScale = new Transform3D();
        smallScale.setScale(0.2);
        Transform3D originalScale = new Transform3D();
        
        transformGroup = new TransformGroup();
        transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        
        //transformGroup.setTransform(smallScale);
        
        objRoot.addChild(transformGroup);
        
        // set stars background
        TextureLoader bgLoader = new TextureLoader(getClass().getResource("/image/stars.jpg"), c);
        /*
        bgLoader.getTexture().setEnable(true);
        Appearance bgAppearance = new Appearance();
        PolygonAttributes pa = new PolygonAttributes();
        pa.setCullFace(PolygonAttributes.CULL_FRONT);
        bgAppearance.setPolygonAttributes(pa);
        bgAppearance.setTexture(bgLoader.getTexture());
        Sphere bgSphere = new Sphere(419.0f, Sphere.GENERATE_NORMALS_INWARD|Sphere.GENERATE_TEXTURE_COORDS, bgAppearance);
        //bgSphere.setBounds(bounds);
        objRoot.addChild(bgSphere);
        */
        Background background = new Background();
        background.setImage(bgLoader.getImage());
        background.setApplicationBounds(bounds);
        objRoot.addChild(background);
        
        // set color properties
        lightColor = new Color3f(1.0f, 1.0f, 1.0f);
        attenuation = new Point3f(1.0f, 0.0f, 0.0f);
        
        setPointLight(upperLight, new Point3f(0.0f, 109.0f, 0.0f));
        setPointLight(lowerLight, new Point3f(0.0f, -109.0f, 0.0f));
        setPointLight(leftLight, new Point3f(-109.0f, 0.0f, 0.0f));
        setPointLight(rightLight, new Point3f(109.0f, 0.0f, 0.0f));
        setPointLight(middleLight, new Point3f());
        setPointLight(forthLight, new Point3f(0.0f, 0.0f, -109.0f));
        setPointLight(backLight, new Point3f(0.0f, 0.0f, 109.0f));
        
        // create heavenly bodies
        sun = new Star(109.0f, getClass().getResource("/image/sunmap.jpg"), 0.0, 27275000, 0, new Vector3d(0.0, 0.0, 0.0), c);
        
        mercury = new Planet(0.38f, getClass().getResource("/image/mercurymap.jpg"), -0.002, 586000, 240852, new Vector3d(113.9, 0.0, 0.0), c);
        venus = new Planet(0.95f, getClass().getResource("/image/venusmap.jpg"), -3.096, 2430185, 615000, new Vector3d(117.2, 0.0, 0.0), c);
        earth = new Planet(1.0f, getClass().getResource("/image/earthmap1k.jpg"), -0.409, 9973, 1000000, new Vector3d(120.0, 0.0, 0.0), c);
        mars = new Planet(0.53f, getClass().getResource("/image/marsmap1k.jpg"), -0.440, 10260, 1880000, new Vector3d(125.0, 0.0, 0.0), c);
        jupiter = new Planet(11.0f, getClass().getResource("/image/jupitermap.jpg"), -0.054, 4135, 11860000, new Vector3d(162.0, 0.0, 0.0), c);
        saturn = new Planet(9.5f, getClass().getResource("/image/saturnmap.jpg"), -0.467, 4440, 29460000, new Vector3d(205.0, 0.0, 0.0), c);
        uranus = new Planet(4.0f, getClass().getResource("/image/uranusmap.jpg"), -1.708, 7183, 84010000, new Vector3d(302.0, 0.0, 0.0), c);
        neptune = new Planet(3.9f, getClass().getResource("/image/neptunemap.jpg"), -0.516, 6713, 164790000, new Vector3d(411.0, 0.0, 0.0), c);
        
        /*addHB(transformGroup, sun);
        addHB(transformGroup, mercury);
        addHB(transformGroup, venus);
        addHB(transformGroup, earth);
        addHB(transformGroup, mars);
        addHB(transformGroup, jupiter);
        addHB(transformGroup, saturn);
        addHB(transformGroup, uranus);
        addHB(transformGroup, neptune);*/
        
        transformGroup.addChild(sun.getTransformGroup());
        transformGroup.addChild(mercury.getTransformGroup());
        transformGroup.addChild(venus.getTransformGroup());
        transformGroup.addChild(earth.getTransformGroup());
        transformGroup.addChild(mars.getTransformGroup());
        transformGroup.addChild(jupiter.getTransformGroup());
        transformGroup.addChild(saturn.getTransformGroup());
        transformGroup.addChild(uranus.getTransformGroup());
        transformGroup.addChild(neptune.getTransformGroup());
        
        // set rotate behavior
        MouseRotate rotateBehavior = new MouseRotate();
        rotateBehavior.setTransformGroup(transformGroup);
        rotateBehavior.setSchedulingBounds(bounds);
        
        // set translate behavior
        MouseTranslate translateBehavior = new MouseTranslate();
        translateBehavior.setTransformGroup(transformGroup);
        translateBehavior.setSchedulingBounds(bounds);
        
        // set scale behavior
        MouseZoom scaleBehavior = new MouseZoom();
        scaleBehavior.setTransformGroup(transformGroup);
        scaleBehavior.setSchedulingBounds(bounds);
        
        // add mouse behavior to the scene
        objRoot.addChild(rotateBehavior);
        objRoot.addChild(translateBehavior);
        objRoot.addChild(scaleBehavior);
        
        // set key navigator behavior
        KeyNavigatorBehavior keyNavigatorBehavior = new KeyNavigatorBehavior(transformGroup);
        keyNavigatorBehavior.setSchedulingBounds(bounds);
        objRoot.addChild(keyNavigatorBehavior);
        
        Clip clip = new Clip(840.0);
        clip.setApplicationBounds(bounds);
        objRoot.addChild(clip);
        
        objRoot.compile(); 
        return objRoot; 
    }
    
    public void setPointLight(PointLight pointLight, Point3f pos) {
    	pointLight = new PointLight(lightColor, pos, attenuation);
    	pointLight.setInfluencingBounds(bounds);
    	objRoot.addChild(pointLight);
    }
    
    public void addHB(TransformGroup transformGroup, HeavenlyBody hb) {
    	TransformGroup trans = hb.getTransformGroup();
    	
		Transform3D yAxis = new Transform3D();
		yAxis.rotZ(hb.getTiltAngle());
		Alpha rotationAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE,
										0, 0, hb.getRotationPeriod(), 0, 0, 0, 0, 0);
		RotationInterpolator rotationInterpolator = new RotationInterpolator(rotationAlpha, trans, yAxis, 0.0f, (float)Math.PI*2.0f);
		rotationInterpolator.setSchedulingBounds(bounds);
		trans.addChild(rotationInterpolator);
		
    	transformGroup.addChild(trans);
	}
    
    public SolarSystem() { 
        setLayout(new BorderLayout()); 
        Canvas3D c = new Canvas3D(SimpleUniverse.getPreferredConfiguration(), false); 
        add("Center", c); 
        
        Viewer viewer = new Viewer(c);
        Vector3d viewpoint = new Vector3d(160.0, 0.0, 120.0);
        Transform3D t = new Transform3D();
        t.set(viewpoint);
        ViewingPlatform vPlatform = new ViewingPlatform();
        vPlatform.getViewPlatformTransform().setTransform(t);
        
        BranchGroup scene = createSceneGraph(c); 
        SimpleUniverse u = new SimpleUniverse(vPlatform, viewer);
        u.getViewingPlatform();
        u.addBranchGraph(scene); 
    } 
    
    public static void main(String[] args) { 
        new MainFrame(new SolarSystem(), 800, 600); 
    }
} 

// end of SolarSystem.java 