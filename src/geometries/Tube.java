package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class Tube extends RadialGeometry {
    Ray _axisRay;

    /**
     * Instantiates a new Tube.
     *
     * @param radius  the radius
     * @param axisRay the axisRay
     */
    public Tube(double radius, Ray axisRay) {
        super(radius);
        this._axisRay=axisRay;

    }

    /**
     * Gets axisRay.
     *
     * @return the axisRay
     */
    public Ray get_axisRay() {
        return _axisRay;
    }

    @Override
    public String toString() {
        return "Tube:" +
                "r=" + _axisRay +
                super.toString();
    }

    @Override
    public Vector getNormal(Point3D p) {
        double t = _axisRay.getDirection().dotProduct(p.subtract(_axisRay.getPOO()));
        Point3D center = _axisRay.getPOO().add(_axisRay.getDirection().scale(t));
        Vector v = p.subtract(center);
        return v.normalize();
    }
}
