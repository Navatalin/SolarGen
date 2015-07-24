public class Asteroid
{
    private double[] Location;
    private double size;
    
    public Asteroid(double size)
    {
        this.size = size;
    	
        Location = new double[2];
    }
    
    public double[] getLoc()
    {
        return Location;
    }
    public void setLoc(double X, double Y)
    {
    	Location[0] = X;
    	Location[1] = Y;
    }
    public void setSize(double size)
    {
    	this.size = size;
    }
    public double getSize()
    {
    	return size;
    }    
}