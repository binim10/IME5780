package renderer;

import elements.AmbientLight;
import elements.Camera;
import elements.PointLight;
import elements.SpotLight;
import geometries.Plane;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import scene.Scene;

/**
 * The type Soft shadow.
 */
public class SoftShadow {
    /**
     * Produce a picture of a sphere and triangle with point light and shade
     */
    @Test
    public void SphereTriangleInitial() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), //
                        60, new Point3D(0, 0, 200)), //
                new Triangle(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), //
                        new Point3D(-70, 40, 0), new Point3D(-40, 70, 0), new Point3D(-68, 68, 4)));

        scene.addLights(new PointLight(new Color(400, 240, 0),
                new Point3D(-100, 100, -200), 1, 1E-5, 1.5E-7).setRadius(15));

        ImageWriter imageWriter = new ImageWriter("sphereTriangleInitial", 200, 200, 400, 400);
        Render render = new Render(imageWriter, scene).setSuperSampling(500).setMultithreading(3).setDebugPrint();

        render.renderImage();
        render.writeToImage();
    }

    /**
     * magical room .
     */
    @Test
    public void Pyramid() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(-3800, -3100, -3000), new Vector(2, 2, 2), new Vector(1, -2, 1)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries( //
                new Plane(new Material(0.2, 0.2, 60, 0.3, 0.2), new Color(java.awt.Color.GRAY),
                        new Point3D(0, 400, 100), new Vector(0, -1, 0)),
                new Polygon(Color.BLACK, new Material(0.2, 0.2, 200, 0.5, 0),
                        new Point3D(-1, -300, 500), new Point3D(-1, -140, 500), new Point3D(1, -140, 500), new Point3D(1, -300, 500)),
                new Sphere(new Color(java.awt.Color.yellow), new Material(0.2, 0.2, 200, 0, 0.2), // )
                        80, new Point3D(-1, -120, 500)),
                new Polygon(Color.BLACK, new Material(0.2, 0.2, 200, 0.9, 0),
                        new Point3D(-150, -150, 1999), new Point3D(-150, 200, 1999), new Point3D(150, 200, 1999), new Point3D(150, -150, 1999)),
                new Sphere(new Color(800, 0, 0), new Material(0.5, 0.5, 200, 0.1, 0), // )
                        140, new Point3D(260, 260, 500)),
                new Sphere(new Color(0, 0, 200), new Material(0.25, 0.25, 20, 0, 0.5), // )
                        140, new Point3D(-260, 260, 0)),
                new Sphere(new Color(700, 20, 20), new Material(0.5, 0.5, 200, 0.5, 0), // )
                        100, new Point3D(-300, 300, 1500)),
                new Triangle(new Color(java.awt.Color.green), new Material(0.5, 0.9, 100, 0, 0), new Point3D(-140, 400, 0), new Point3D(40, 400, 0), new Point3D(-50, 200, 10)),
                new Triangle(new Color(java.awt.Color.green), new Material(0.5, 0.9, 200, 0, 0), new Point3D(-50, 400, 0), new Point3D(-20, 400, 50), new Point3D(-50, 200, 10)),
                new Triangle(new Color(java.awt.Color.green), new Material(0.5, 0.9, 100, 0, 0), new Point3D(-140, 400, 0), new Point3D(-20, 400, 50), new Point3D(-50, 200, 10)));

        scene.addLights(new SpotLight(new Color(java.awt.Color.white), //
                        new Point3D(0, 0, -1500), 1, 4E-5, 2E-7, new Vector(0, 0, 1)).setRadius(3),
                new PointLight(new Color(java.awt.Color.white), new Point3D(0.001, -100, 499), 1, 4E-5, 2E-7).setRadius(3));

        ImageWriter imageWriter = new ImageWriter("The magical room- Piramida without SS", 200, 200, 800, 800);
        Render render = new Render(imageWriter, scene).setSuperSampling(0).setMultithreading(3).setDebugPrint().setBVHImprove(true);

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
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //Right triangle
                        new Point3D(-150, 150, 115), new Point3D(150, 150, 135), new Point3D(75, -75, 150)), //
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-150, 150, 115), new Point3D(-70, -70, 140), new Point3D(75, -75, 150)), //
                new Sphere(new Color(java.awt.Color.BLUE), new Material(0.2, 0.2, 30, 0, 0.3), // )
                        30, new Point3D(60, -50, 50)),
                new Triangle(Color.BLACK, new Material(0.3, 0.3, 30), new Point3D(-30, 20, 100),
                        new Point3D(-15, 30, 95), new Point3D(17, 87, 122)));

        scene.addLights(new SpotLight(new Color(700, 400, 400), //
                new Point3D(60, -50, 0), 1, 4E-5, 2E-7, new Vector(0, 0, 1)).setRadius(12));

        ImageWriter imageWriter = new ImageWriter("soft shadow without transparency + triangle between -  500Ray 12Radius", 200, 200, 600, 600);
        Render render = new Render(imageWriter, scene).setSuperSampling(500).setMultithreading(3).setDebugPrint();

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
                new Plane(new Material(0.4, 0.1, 60, 0, 0), new Color(java.awt.Color.DARK_GRAY),
                        new Point3D(0, 400, 100), new Vector(0, -1, 0)),
                new Polygon(Color.BLACK, new Material(0.2, 0.3, 200, 0.5, 0),
                        new Point3D(-1, -300, 500), new Point3D(-1, -140, 500), new Point3D(1, -140, 500), new Point3D(1, -300, 500)),
                new Sphere(new Color(java.awt.Color.yellow), new Material(0.2, 0.2, 200, 0, 0.6), // )
                        80, new Point3D(-1, -120, 500)),
                new Polygon(Color.BLACK, new Material(0.2, 0.2, 200, 0.9, 0),
                        new Point3D(-150, -150, 1999), new Point3D(-150, 200, 1999), new Point3D(150, 200, 1999), new Point3D(150, -150, 1999)),
                new Sphere(new Color(800, 0, 0), new Material(0.3, 0.5, 200, 0.2, 0), // )
                        140, new Point3D(300, 260, 600)),
                new Sphere(new Color(0, 0, 200), new Material(0.25, 0.25, 20, 0, 0.25), // )
                        140, new Point3D(-260, 260, 0)),
                new Sphere(new Color(400, 20, 20), new Material(0.2, 0.5, 200, 0, 0.3), // )
                        100, new Point3D(-600, 300, 1300)),
                new Triangle(new Color(100, 300, 100), new Material(0.25, 0.5, 100, 0.25, 0),
                        new Point3D(-100, 400, 150), new Point3D(100, 400, 350), new Point3D(0, 200, 250)));

        scene.addLights(new SpotLight(new Color(700, 400, 400), //no. 1
                        new Point3D(0, 0, -1500), 1, 4E-5, 2E-7, new Vector(0, 0, 1)).setRadius(15),
                new PointLight(new Color(200, 400, 200), new Point3D(0.001, -100, 499), 1, 4E-5, 2E-7).setRadius(15),//no.2
                new PointLight(new Color(200, 200, 400), new Point3D(0.001, -50, 1000), 1, 4E-5, 2E-7).setRadius(25));//no.3

        ImageWriter imageWriter = new ImageWriter("The magical room moving camera to right - soft shadow 5", 200, 200, 1000, 1000);
        Render render = new Render(imageWriter, scene).setSuperSampling(400).setMultithreading(3).setDebugPrint();

        render.renderImage();
        render.writeToImage();
    }

}
