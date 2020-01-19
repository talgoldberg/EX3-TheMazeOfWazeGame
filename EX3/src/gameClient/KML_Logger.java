package gameClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class KML_Logger {

	private static int time;

	/**
	 *create a KML file.
	 * */
	public static String createFile(String fileName, int scenarioNum)
	{
		time = scenarioNum % 2 == 1? 60000 : 30000;
		File kmlFile = new File(fileName);

		try 
		{ 
			kmlFile.createNewFile();
			BufferedWriter br = new BufferedWriter(new FileWriter(kmlFile));
			br.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"); ; 
			br.write("<kml xmlns=\"http://www.opengis.net/kml/2.2\">\r\n");
			br.write("<Document>\r\n");
			br.write("<Style id=\"AppleIcon\">\r\n");
			br.write("<Icon>\r\n");
			br.write("<href>http://www.pngall.com/wp-content/uploads/2016/04/Apple-Fruit-Download-PNG.png</href>\r\n");
			br.write("</Icon>\r\n");	
			br.write("</Style>\r\n");
			br.write("<Style id=\"BananaIcon\">\r\n");
			br.write("<Icon>\r\n");
			br.write("<href>http://www.pngall.com/wp-content/uploads/2016/04/Banana-Free-Download-PNG.png</href>\r\n");
			br.write("</Icon>\r\n");	
			br.write("</Style>\r\n");
			br.write("<Style id=\"RobotIcon\">\r\n");
			br.write("<Icon>\r\n");
			br.write("<href>https://d3m9l0v76dty0.cloudfront.net/system/photos/2435924/extra_large/f7c4010f051a379c0a3d25ec9d493fc2.jpg</href>\r\n");
			br.write("</Icon>\r\n");	
			br.write("</Style>\r\n\r\n");

			br.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}

		return fileName;
	}
	/**
	 * writes to KML file.
	 * */
	public static void write(String fileName, double x, double y, String type, long TimeToEnd)throws FileNotFoundException
	{
		long  t = (time - TimeToEnd)/1000;
		File file = new File(fileName);
		if(!file.exists())
			throw new FileNotFoundException("error file!");
		try 
		{
			BufferedWriter br = new BufferedWriter(new FileWriter(fileName,true));
			br.write("<Placemark>\r\n");
			br.write("<name>" + type + "</name>\r\n");
			br.write("<TimeSpan>\r\n<begin>" + t + "</begin>\r\n<end>" + (t+0.1) + "</end>\r\n</TimeSpan>\r\n");
			if(type=="Banana")
				br.write("<styleUrl>#BananaIcon</styleUrl>\r\n");
			if( type=="Apple")
				br.write("<styleUrl>#AppleIcon</styleUrl>\r\n");
			if (type=="Robot")
				br.write("<styleUrl>#RobotIcon</styleUrl>\r\n");

			br.write( "<TimeStamp>\r\n<when>"+ LocalDateTime.now() +"Z</when>\r\n</TimeStamp>\r\n");
			br.write( "<Point>\r\n");
			br.write( "<coordinates>"+ x + ", " + y + ", 0</coordinates>\r\n</Point>\r\n");
			br.write( "</Placemark>\r\n\r\n");
			br.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * closes file.
	 * */
	public static void closeFile(String fileName) throws IOException 
	{
		File file = new File(fileName);
		if(!file.exists())
			throw new FileNotFoundException("error file!");
		BufferedReader br = new BufferedReader(new FileReader(file));
		br.close();
		BufferedWriter bw = new BufferedWriter(new FileWriter(fileName,true));
		bw.write("</Document>\r\n");
		bw.write("</kml>");
		bw.close();
		file.setWritable(false);

	}

}
