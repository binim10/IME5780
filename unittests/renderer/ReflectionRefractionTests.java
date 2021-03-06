/**
 *
 */
package renderer;

import geometries.Plane;
import geometries.Polygon;
import org.junit.Test;

import elements.*;
import geometries.Sphere;
import geometries.Triangle;
import primitives.*;
import scene.Scene;


/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 *
 * @author dzilb
 */
public class ReflectionRefractionTests {

	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */
	@Test
	public void twoSpheres() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

		scene.addGeometries(
				new Sphere(new Color(java.awt.Color.BLUE), new Material(0.4, 0.3, 100, 0, 0.9), 50,
						new Point3D(0, 0, 50)),
				new Sphere(new Color(java.awt.Color.RED), new Material(0.5, 0.5, 100), 25, new Point3D(0, 0, 50)));

		scene.addLights(new SpotLight(new Color(1000, 600, 0), new Point3D(-100, 100, -500), 1,
				0.0004, 0.0000006, new Vector(-1, 1, 2)));

		ImageWriter imageWriter = new ImageWriter("twoSpheres", 150, 150, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */
	@Test
	public void twoSpheresOnMirrors() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -10000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(10000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

		scene.addGeometries(
				new Sphere(new Color(0, 0, 100), new Material(0.25, 0.25, 20, 0, 0.5), 400, new Point3D(-950, 900, 1000)),
				new Sphere(new Color(100, 20, 20), new Material(0.25, 0.25, 20), 200, new Point3D(-950, 900, 1000)),
				new Triangle(new Color(20, 20, 20), new Material(0, 0, 0, 1, 0), new Point3D(1500, 1500, 1500),
						new Point3D(-1500, -1500, 1500), new Point3D(670, -670, -3000)),
				new Triangle(new Color(20, 20, 20), new Material(0, 0, 0, 0.5, 0), new Point3D(1500, 1500, 1500),
						new Point3D(-1500, -1500, 1500), new Point3D(-1500, 1500, 2000)));

		scene.addLights(new SpotLight(new Color(1020, 400, 400), new Point3D(-750, 750, 150), 1, 0.00001, 0.000005,
				new Vector(-1, 1, 4)));

		ImageWriter imageWriter = new ImageWriter("twoSpheresMirrored", 2500, 2500, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a two triangles lighted by a spot light with a partially transparent Sphere
	 * producing partial shadow
	 */
	@Test
	public void trianglesTransparentSphere() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.addGeometries( //
				new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
						new Point3D(-150, 150, 115), new Point3D(150, 150, 135), new Point3D(75, -75, 150)), //
				new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
						new Point3D(-150, 150, 115), new Point3D(-70, -70, 140), new Point3D(75, -75, 150)), //
				new Sphere(new Color(java.awt.Color.BLUE), new Material(0.2, 0.2, 30, 0, 0), // )
						30, new Point3D(60, -50, 50)));

		scene.addLights(new SpotLight(new Color(700, 400, 400), //
				new Point3D(60, -50, 0), 1, 4E-5, 2E-7, new Vector(0, 0, 1)).setRadius(3));

		ImageWriter imageWriter = new ImageWriter("shadow with transparency", 200, 200, 600, 600);
		Render render = new Render(imageWriter, scene).setSuperSampling(300);

		render.renderImage();
		render.writeToImage();
	}

	/**
	 * beach.
	 */
	@Test
	public void beach() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.addGeometries( //
				new Plane(new Material(0.5, 0.5, 60, 0.5, 0), Color.BLACK, //
						new Point3D(-150, 200, 115), new Vector(0, 1, 0))/*, ////
				new Sphere(new Color(java.awt.Color.BLUE), new Material(0.2, 0.2, 30, 0.2, 0), // )
						80, new Point3D(180, -180, 2000))*/,
				new Sphere(new Color(200, 200, 0), new Material(0.5, 0.5, 200, 0, 0.2), // )
						100, new Point3D(190, -190, 1000)),
				new Sphere(new Color(java.awt.Color.BLACK), new Material(0, 0, 100, 0, 1), // )
						50, new Point3D(60, -50, -10000)),
				new Plane(new Material(0.2, 0.2, 70, 0, 0.5), new Color(100, 100, 500), //
						new Point3D(0, 0, 30000), new Vector(0, 0, 1)),
				new Triangle(Color.BLACK, new Material(0.8, 0.5, 60), new Point3D(0, 5, 300),//right shift
						new Point3D(0, -10, 300), new Point3D(6, 5, 305)),
				new Triangle(Color.BLACK, new Material(0.8, 0.5, 60), new Point3D(0, 5, 300),
						new Point3D(0, -10, 300), new Point3D(-6, 5, 305)),
				new Polygon(Color.BLACK, new Material(0.2, 0.5, 60), new Point3D(-10, 5, 300),
						new Point3D(-7, 10, 300), new Point3D(7, 10, 300), new Point3D(10, 5, 300)),
				new Triangle(Color.BLACK, new Material(0.8, 0.5, 60), new Point3D(-40, 5, 300),//left shift
						new Point3D(-40, -10, 300), new Point3D(-34, 5, 305)),
				new Triangle(Color.BLACK, new Material(0.8, 0.5, 60), new Point3D(-40, 5, 300),
						new Point3D(-40, -10, 300), new Point3D(-46, 5, 305)),
				new Polygon(Color.BLACK, new Material(0.2, 0.5, 60), new Point3D(-50, 5, 300),
						new Point3D(-47, 10, 300), new Point3D(-33, 10, 300), new Point3D(-30, 5, 300)),
				new Sphere(new Color(java.awt.Color.WHITE), new Material(0.5, 0.5, 200, 0, 0.2), // )
						25, new Point3D(-95, -150, 1000)),
				new Sphere(new Color(java.awt.Color.WHITE), new Material(0.5, 0.5, 200, 0, 0.2), // )
						25, new Point3D(-145, -130, 1000)),
				new Sphere(new Color(java.awt.Color.WHITE), new Material(0.5, 0.5, 200, 0, 0.2), // )
						24, new Point3D(-125, -130, 1000)),
				new Sphere(new Color(java.awt.Color.WHITE), new Material(0.5, 0.5, 200, 0, 0.2), // )
						25, new Point3D(-135, -170, 1000)),
				new Sphere(new Color(java.awt.Color.WHITE), new Material(0.5, 0.5, 200, 0, 0.2), // )
						24, new Point3D(-115, -170, 1000)),
				new Sphere(new Color(java.awt.Color.WHITE), new Material(0.5, 0.5, 200, 0, 0.2), // )
						25, new Point3D(-165, -150, 1000)));

		scene.addLights(new SpotLight(new Color(400, 400, 700), //
						new Point3D(100, -100, -1000), 1, 4E-5, 2E-7, new Vector(0, 0, 1)),
				new SpotLight(new Color(400, 700, 600), //
						new Point3D(60, -50, -20000), 1, 7E-5, 5E-7, new Vector(0, 0, 1)),
				new PointLight(new Color(java.awt.Color.yellow), new Point3D(190, -190, 1000), 1, 4E-5, 2E-7));

		ImageWriter imageWriter = new ImageWriter("Beach", 200, 200, 600, 600);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}

	/**
	 * magical room .
	 */
	@Test
	public void magicalRoom() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -10000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.addGeometries( //
				new Plane(new Material(0.2, 0.2, 60, 0.3, 0.2), Color.BLACK,
						new Point3D(400, 0, 100), new Vector(-1, 0, 0)),
				new Plane(new Material(0.2, 0.2, 60, 0.3, 0.2), Color.BLACK,
						new Point3D(0, 400, 100), new Vector(0, -1, 0)),
				new Plane(new Material(0.2, 0.2, 60, 0.3, 0.2), Color.BLACK,
						new Point3D(-400, 0, 100), new Vector(1, 0, 0)),
				new Plane(new Material(0, 0, 0, 0, 0), Color.BLACK,
						new Point3D(0, 0, 3000), new Vector(0, 0, -1)),
				new Plane(new Material(0.2, 0.2, 60, 0.1, 1), Color.BLACK,
						new Point3D(0, -300, 500), new Vector(0, 1, 0)),
				new Polygon(Color.BLACK, new Material(0.2, 0.2, 200, 0.5, 0),
						new Point3D(-1, -300, 500), new Point3D(-1, -140, 500), new Point3D(1, -140, 500), new Point3D(1, -300, 500)),
				new Sphere(new Color(java.awt.Color.yellow), new Material(0.2, 0.2, 200, 0, 0.8), // )
						80, new Point3D(-1, -120, 500)),
				new Polygon(Color.BLACK, new Material(0.2, 0.2, 200, 0.9, 0),
						new Point3D(-150, -150, 1999), new Point3D(-150, 200, 1999), new Point3D(150, 200, 1999), new Point3D(150, -150, 1999)),
				new Sphere(new Color(800, 0, 0), new Material(0.5, 0.5, 200, 0.5, 0), // )
						140, new Point3D(260, 260, 500)),
				new Sphere(new Color(0, 0, 200), new Material(0.25, 0.25, 20, 0, 0.5), // )
                        140, new Point3D(-260, 260, 0)),
                new Sphere(new Color(700, 20, 20), new Material(0.5, 0.5, 200, 0.5, 0), // )
                        100, new Point3D(-300, 300, 1500)),
                new Triangle(new Color(100, 300, 100), new Material(0.5, 0.5, 100, 0.5, 0.5),
                        new Point3D(-100, 400, 150), new Point3D(100, 400, 350), new Point3D(0, 200, 250)));

        scene.addLights(new SpotLight(new Color(java.awt.Color.white), //
                        new Point3D(0, 0, -1500), 1, 4E-5, 2E-7, new Vector(0, 0, 1)).setRadius(10),
                new PointLight(new Color(java.awt.Color.white), new Point3D(0.001, -100, 499), 1, 4E-5, 2E-7).setRadius(10));

        ImageWriter imageWriter = new ImageWriter("The magical room with BVH algo", 200, 200, 600, 600);
        Render render = new Render(imageWriter, scene).setMultithreading(3).setDebugPrint().setBVHImprove(true).setSuperSampling(300);
        scene.getGeometries().buildHierarchyTree();
        render.renderImage();
        render.writeToImage();
    }

	/**
	 * bonus t move camera.
	 */
	@Test
	public void bonusTMoveCamera() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, -600, -9000), new Vector(0, 1, 10), new Vector(0, -10, 1)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.addGeometries( //
				new Plane(new Material(0.2, 0.2, 60, 0.3, 0.2), Color.BLACK,
						new Point3D(400, 0, 100), new Vector(-1, 0, 0)),
				new Plane(new Material(0.2, 0.2, 60, 0.3, 0.2), Color.BLACK,
						new Point3D(0, 400, 100), new Vector(0, -1, 0)),
				new Plane(new Material(0.2, 0.2, 60, 0.3, 0.2), Color.BLACK,
						new Point3D(-400, 0, 100), new Vector(1, 0, 0)),
				new Plane(new Material(0, 0, 0, 0, 0), Color.BLACK,
						new Point3D(0, 0, 3000), new Vector(0, 0, -1)),
				new Plane(new Material(0.2, 0.2, 60, 0.1, 1), Color.BLACK,
						new Point3D(0, -300, 500), new Vector(0, 1, 0)),
				new Polygon(Color.BLACK, new Material(0.2, 0.2, 200, 0.5, 0),
						new Point3D(-1, -300, 500), new Point3D(-1, -140, 500), new Point3D(1, -140, 500), new Point3D(1, -300, 500)),
				new Sphere(new Color(java.awt.Color.yellow), new Material(0.2, 0.2, 200, 0, 0.8), // )
						80, new Point3D(-1, -120, 500)),
				new Polygon(Color.BLACK, new Material(0.2, 0.2, 200, 0.9, 0),
						new Point3D(-150, -150, 1999), new Point3D(-150, 200, 1999), new Point3D(150, 200, 1999), new Point3D(150, -150, 1999)),
				new Sphere(new Color(800, 0, 0), new Material(0.5, 0.5, 200, 0.5, 0), // )
						140, new Point3D(260, 260, 500)),
				new Sphere(new Color(0, 0, 200), new Material(0.25, 0.25, 20, 0, 0.5), // )
						140, new Point3D(-260, 260, 0)),
				new Sphere(new Color(700, 20, 20), new Material(0.5, 0.5, 200, 0.5, 0), // )
						100, new Point3D(-300, 300, 1500)),
				new Triangle(new Color(100, 300, 100), new Material(0.5, 0.5, 100, 0.5, 0.5),
						new Point3D(-100, 400, 150), new Point3D(100, 400, 350), new Point3D(0, 200, 250)));

		scene.addLights(new SpotLight(new Color(java.awt.Color.white), //
						new Point3D(0, 0, -1500), 1, 4E-5, 2E-7, new Vector(0, 0, 1)),
				new PointLight(new Color(java.awt.Color.white), new Point3D(0.001, -100, 499), 1, 4E-5, 2E-7));

		ImageWriter imageWriter = new ImageWriter("The magical room moving camera", 200, 200, 600, 600);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}

	/**
	 * bonus test move camera 2.
	 */
	@Test
	public void bonusTMoveCamera2() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(3100, -3100, -2600), new Vector(-2, 2, 2), new Vector(-1, -2, 1)));
		scene.setDistance(600);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.addGeometries( //
				new Plane(new Material(0.2, 0.1, 60, 0.3, 0.2), Color.BLACK,
						new Point3D(400, 0, 100), new Vector(-1, 0, 0)),
				new Plane(new Material(0.2, 0.1, 60, 0.3, 0.2), Color.BLACK,
						new Point3D(0, 400, 100), new Vector(0, -1, 0)),
				new Plane(new Material(0.2, 0.1, 60, 0.3, 0.2), Color.BLACK,
						new Point3D(-400, 0, 100), new Vector(1, 0, 0)),
				new Plane(new Material(0, 0, 0, 0, 0), Color.BLACK,
						new Point3D(0, 0, 3000), new Vector(0, 0, -1)),
				new Plane(new Material(0.2, 0.1, 60, 0.1, 1), Color.BLACK,
						new Point3D(0, -300, 500), new Vector(0, 1, 0)),
				new Polygon(Color.BLACK, new Material(0.2, 0.2, 200, 0.5, 0),
						new Point3D(-1, -300, 500), new Point3D(-1, -140, 500), new Point3D(1, -140, 500), new Point3D(1, -300, 500)),
				new Sphere(new Color(java.awt.Color.yellow), new Material(0.2, 0.2, 200, 0, 0.8), // )
						80, new Point3D(-1, -120, 500)),
				new Polygon(Color.BLACK, new Material(0.2, 0.2, 200, 0.9, 0),
						new Point3D(-150, -150, 1999), new Point3D(-150, 200, 1999), new Point3D(150, 200, 1999), new Point3D(150, -150, 1999)),
				new Sphere(new Color(800, 0, 0), new Material(0.5, 0.5, 200, 0.5, 0), // )
						140, new Point3D(260, 260, 500)),
				new Sphere(new Color(0, 0, 200), new Material(0.25, 0.25, 20, 0, 0.5), // )
						140, new Point3D(-260, 260, 0)),
				new Sphere(new Color(700, 20, 20), new Material(0.5, 0.5, 200, 0.5, 0), // )
						100, new Point3D(-300, 300, 1500)),
				new Triangle(new Color(100, 300, 100), new Material(0.5, 0.5, 100, 0.5, 0.5),
						new Point3D(-100, 400, 150), new Point3D(100, 400, 350), new Point3D(0, 200, 250)));

		scene.addLights(new SpotLight(new Color(java.awt.Color.white), //
						new Point3D(0, 0, -1500), 1, 4E-5, 2E-7, new Vector(0, 0, 1)),
				new PointLight(new Color(java.awt.Color.white), new Point3D(0.001, -100, 499), 1, 4E-5, 2E-7));

		ImageWriter imageWriter = new ImageWriter("The magical room moving camera to right", 200, 200, 600, 600);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}

	/**
	 * bonus test move camera 3.
	 */
	@Test
	public void bonusTMoveCamera3() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(300, 396, -15000), new Vector(0, 1, 10), new Vector(0, -10, 1)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.addGeometries( //
				new Plane(new Material(0.2, 0.2, 60, 0.3, 0.2), Color.BLACK,
						new Point3D(400, 0, 100), new Vector(-1, 0, 0)),
				new Plane(new Material(0.2, 0.2, 60, 0.3, 0.2), Color.BLACK,
						new Point3D(0, 400, 100), new Vector(0, -1, 0)),
				new Plane(new Material(0.2, 0.2, 60, 0.3, 0.2), Color.BLACK,
						new Point3D(-400, 0, 100), new Vector(1, 0, 0)),
				new Plane(new Material(0, 0, 0, 0, 0), Color.BLACK,
						new Point3D(0, 0, 3000), new Vector(0, 0, -1)),
				new Plane(new Material(0.2, 0.2, 60, 0.1, 1), Color.BLACK,
						new Point3D(0, -300, 500), new Vector(0, 1, 0)),
				new Polygon(Color.BLACK, new Material(0.2, 0.2, 200, 0.5, 0),
						new Point3D(-1, -300, 500), new Point3D(-1, -140, 500), new Point3D(1, -140, 500), new Point3D(1, -300, 500)),
				new Sphere(new Color(java.awt.Color.yellow), new Material(0.2, 0.2, 200, 0, 0.8), // )
						80, new Point3D(-1, -120, 500)),
				new Polygon(Color.BLACK, new Material(0.2, 0.2, 200, 0.9, 0),
						new Point3D(-150, -150, 1999), new Point3D(-150, 200, 1999), new Point3D(150, 200, 1999), new Point3D(150, -150, 1999)),
				new Sphere(new Color(800, 0, 0), new Material(0.5, 0.5, 200, 0.5, 0), // )
						140, new Point3D(260, 260, 500)),
				new Sphere(new Color(0, 0, 200), new Material(0.25, 0.25, 20, 0, 0.5), // )
						140, new Point3D(-260, 260, 0)),
				new Sphere(new Color(700, 20, 20), new Material(0.5, 0.5, 200, 0.5, 0), // )
						100, new Point3D(-300, 300, 1500)),
				new Triangle(new Color(100, 300, 100), new Material(0.5, 0.5, 100, 0.5, 0.5),
						new Point3D(-100, 400, 150), new Point3D(100, 400, 350), new Point3D(0, 200, 250)));

		scene.addLights(new SpotLight(new Color(java.awt.Color.white), //
                        new Point3D(0, 0, -1500), 1, 4E-5, 2E-7, new Vector(0, 0, 1)),
                new PointLight(new Color(java.awt.Color.white), new Point3D(0.001, -100, 499), 1, 4E-5, 2E-7));

        ImageWriter imageWriter = new ImageWriter("The magical room moving camera to down", 200, 200, 600, 600);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void magicalRoomBVH() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -10000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries( //
                //right wall
                new Plane(new Material(0.2, 0.2, 60, 0.3, 0.2), Color.BLACK,
                        new Point3D(400, 0, 100), new Vector(-1, 0, 0)),
                //ceiling
                new Plane(new Material(0.2, 0.2, 60, 0.3, 0.2), Color.BLACK,
                        new Point3D(0, 400, 100), new Vector(0, -1, 0)),
                new Plane(new Material(0.2, 0.2, 60, 0.3, 0.2), Color.BLACK,
                        new Point3D(-400, 0, 100), new Vector(1, 0, 0)),
                new Plane(new Material(0, 0, 0, 0, 0), Color.BLACK,
                        new Point3D(0, 0, 3000), new Vector(0, 0, -1)),
                new Plane(new Material(0.2, 0.2, 60, 0.1, 1), Color.BLACK,
                        new Point3D(0, -300, 500), new Vector(0, 1, 0)),
                new Polygon(Color.BLACK, new Material(0.2, 0.2, 200, 0.5, 0),
                        new Point3D(-1, -300, 500), new Point3D(-1, -140, 500), new Point3D(1, -140, 500), new Point3D(1, -300, 500)),
                new Sphere(new Color(java.awt.Color.yellow), new Material(0.2, 0.2, 200, 0, 0.8), // )
                        80, new Point3D(-1, -120, 500)),
                new Polygon(Color.BLACK, new Material(0.2, 0.2, 200, 0.9, 0),
                        new Point3D(-150, -150, 1999), new Point3D(-150, 200, 1999), new Point3D(150, 200, 1999), new Point3D(150, -150, 1999)),
                new Sphere(new Color(800, 0, 0), new Material(0.5, 0.5, 200, 0.5, 0), // )
                        140, new Point3D(260, 260, 500)),
                new Sphere(new Color(0, 0, 200), new Material(0.25, 0.25, 20, 0, 0.5), // )
                        140, new Point3D(-260, 260, 0)),
                new Sphere(new Color(700, 20, 20), new Material(0.5, 0.5, 200, 0.5, 0), // )
                        100, new Point3D(-300, 300, 1500)),
                new Triangle(new Color(100, 300, 100), new Material(0.5, 0.5, 100, 0.5, 0.5),
                        new Point3D(-100, 400, 150), new Point3D(100, 400, 350), new Point3D(0, 200, 250)));

        scene.addLights(new SpotLight(new Color(java.awt.Color.white), //
                        new Point3D(0, 0, -1500), 1, 4E-5, 2E-7, new Vector(0, 0, 1)).setRadius(10),
                new PointLight(new Color(java.awt.Color.white), new Point3D(0.001, -100, 499), 1, 4E-5, 2E-7).setRadius(10));

        ImageWriter imageWriter = new ImageWriter("The magical room with BVH algo", 200, 200, 600, 600);
        Render render = new Render(imageWriter, scene).setMultithreading(3).setDebugPrint().setBVHImprove(true).setSuperSampling(300);
        scene.getGeometries().buildHierarchyTree();
        render.renderImage();
        render.writeToImage();
    }


}
