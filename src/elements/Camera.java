package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * The type Camera represent the rays/vectors the "camera" send to the view plane.
 */
public class Camera {
    private Point3D _p0;
    private Vector _vUp,_vTo,_vRight;

    /**
     * Instantiates a new Camera.
     *
     * @param p0  the p 0
     * @param vUp the v up
     * @param vTo the v to
     */
    public Camera(Point3D p0, Vector vTo, Vector vUp) {
        //check if they are vertical
        if(!isZero(vUp.dotProduct(vTo))){
            throw new IllegalArgumentException("the vectors up and to are not vertical");
        }
        this._p0 = p0;
        this._vUp = vUp.normalized();
        this._vTo = vTo.normalized();
        //create the vRight vector by using crossProduct that give vertical vector for both.
        this._vRight=new Vector(vUp.crossProduct(vTo)).normalized();
    }

    /**
     * Gets p0 the location of the start point.
     *
     * @return the p 0
     */
    public Point3D getP0() {
        return _p0;
    }

    /**
     * Gets v up.
     *
     * @return the v up
     */
    public Vector getVUp() {
        return _vUp;
    }

    /**
     * Gets v to.
     *
     * @return the v to
     */
    public Vector getVTo() {
        return _vTo;
    }

    /**
     * Gets v right.
     *
     * @return the v right
     */
    public Vector getVRight() {
        return _vRight;
    }

    /**
     * Construct ray through pixel ray.
     *
     * @param nX             the n x
     * @param nY             the n y
     * @param j              the j
     * @param i              the
     * @param screenDistance the screen distance
     * @param screenWidth    the screen width
     * @param screenHeight   the screen height
     * @return the ray
     */
    public Ray constructRayThroughPixel (int nX, int nY,
                                         int j, int i, double screenDistance,
                                         double screenWidth, double screenHeight){

        Point3D Pc = _p0.add(_vTo.scale(screenDistance));
        double Ry = screenHeight / nY;
        double Rx = screenWidth / nX;

        double yi = ((i - (nY / 2d)) * Ry + Ry / 2d);
        double xj = ((j - (nX / 2d)) * Rx + Rx / 2d);

        Point3D Pij=Pc;
        Vector temp=new Vector(getVTo()),temp2=new Vector(getVTo());//initialise for temp

        if (!isZero(xj)) {
            temp =_vRight.scale(xj);
            Pij=Pij.add(temp);
        }
        if (!isZero(yi)) {
            temp2 = _vUp.scale(yi);
            Pij=Pij.add(temp2);
        }
        if (!isZero(xj)&&!isZero(yi)){
            Pij=Pij.add(temp.subtract(temp2));
        }

        Vector Vij = Pij.subtract(_p0);

        return new Ray(_p0, Vij.normalized());

    }

}