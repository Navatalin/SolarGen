import org.apache.commons.math3.distribution.*;
import org.apache.commons.math3.random.*;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;

public class SolarGen
{
    public static String outputDir = "";
    public static void main(String args[])
    {
        ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();
        String[] configs = readConfig();
        
        double[] center = new double[2];
        center[0] = 0;
        center[1] = 0;
        
        try
        {
            //double maxRadius = 1500.0;
            double maxRadius = Double.parseDouble(configs[0]);
            //double randPosRatio = 0.75;
            double maxWeight = Double.parseDouble(configs[1]);
            //int steps = 1000000;
            double minWeight = Double.parseDouble(configs[2]);
            //int maxStars = 16000;
            int maxAsteroids = Integer.parseInt(configs[3]);

		    outputDir = configs[4];

            for(int i = 0; i < maxAsteroids; i++)
            {
                Asteroid a = genAsteroid(minWeight,maxWeight,maxRadius*0.2,maxRadius);
                asteroids.add(a);
            
            }
            writeOut(asteroids);
        }
        catch(Exception e)
        {
            System.out.println("Error loading config");
            System.out.println(e.getMessage());
            System.exit(1);
        }
        
    }
    public static double getRand(double low, double high)
    {
        RandomDataGenerator gen = new RandomDataGenerator();
        double res = gen.nextUniform(low,high);
        return res;
    }
    public static double randDist(double max, double min)
    {
        double mean = max / 2.0;
        double std = max / 4.0;
        NormalDistribution norm = new NormalDistribution(mean, std);
        
        double dist = 0;
        while(dist == 0)
        {
            dist = norm.sample();
            if(dist < min)
                dist = 0;
        }
        
        return dist;
    }
    public static double[] findLoc(double start[], double dist, double deg)
    {
        double[] res = new double[2];
        double rads = Math.toRadians(deg);
        
        res[0] = start[0] + dist * Math.cos(rads);
        res[1] = start[1] + dist * Math.sin(rads);
        
        return res;
    }
    public static double[] randLoc(double min, double max)
    {
        double[] res = new double[2];
        
        res[0] = getRand(min,max);
        res[1] = getRand(min,max);
        
        return res;
    }
    public static Asteroid genAsteroid(double low, double high, double min, double max)
    {
        double[] origin = {0.0,0.0};
        
        Asteroid res = new Asteroid(getRand(low, high));
        
        double[] randomLocation = findLoc(origin, randDist(max, min),getRand(1,360));
        
        //double[] randomLocation = randLoc(min, max);
        res.setLoc(randomLocation[0], randomLocation[1]);
        
    	return res;
    }
    public static void writeOut(ArrayList<Asteroid> asteroids)
    {
    	 try
        {
            PrintWriter writer = new PrintWriter(outputDir,"UTF-8");
        	
        	writer.println("X,Y,Mass,Class");
            for(Asteroid a : asteroids)
            {
                double[] Loc = a.getLoc();
                writer.println(Loc[0] + ", " + Loc[1] + " ," +  a.getSize() + " ," + "Asteroid");
                
            }
           
        	writer.println("END");
            writer.close();
            System.out.println("Number of Objects: " + asteroids.size());
        }
        catch(Exception e)
        {
            System.out.println("error");
        }
    }
    public static String[] readConfig()
	{
	    String[] configs = new String[5];
		String path = "config.cfg";
		BufferedReader br = null;
		String line = "";
		String comment = "#";
		
		try
		{
			br = new BufferedReader(new FileReader(path));
			while((line = br.readLine())!= null)
			{
			    if(!line.contains(comment))
			    {
				    if(line.contains("MaxRadius"))
				    {  
				        String[] t = line.split("=");
				        configs[0] = t[1].substring(1).trim();
				    }
				    else
				    if(line.contains("maxWeight"))
				    {   
				        String[] t = line.split("=");
				        configs[1] = t[1].substring(1).trim();
				    }
				    else
				    if(line.contains("minWeight"))
				    {   
				        String[] t = line.split("=");
				        configs[2] = t[1].substring(1).trim();
				    }
				    else
				    if(line.contains("MaxAsteroids"))
				    {   
				        String[] t = line.split("=");
				        configs[3] = t[1].substring(1).trim();
				    }
				    else
				    if(line.contains("OutputDir"))
				    {   
				        String[] t = line.split("=");
				        configs[4] = t[1].substring(1).trim();
				    }
				    
			    }
			}
			for(int i = 0; i < configs.length; i++)
		    {
		        System.out.println(configs[i]);
		    }
			
		}
		catch(Exception e)
		{
		    System.out.println("Error reading Config");
		    System.out.println(e.getMessage());
		}
		
		return configs;
		
	}
}