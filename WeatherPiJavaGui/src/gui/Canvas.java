package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
public class Canvas extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	double currentTempOutside = -273.15, currentTempInside = -273.15;
	Font roboto = new Font("Roboto", Font.PLAIN, 36);
	Timer t = new Timer(5,new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			pullLatestInfo();
			revalidate();
			repaint();
		}
	});
	public Canvas(){
		super();
		t.start();
	}
	public void pullLatestInfo(){
		String temps = readFile("temperatures.txt");
		String[] temps2 = temps.split(",[ ]*");
		try{
			currentTempOutside = Double.parseDouble(temps2[0]);
			currentTempInside = Double.parseDouble(temps2[1]);
		}catch(NumberFormatException e){
			e.printStackTrace();
		}finally{
			return;
		}
	}
	public String readFile(String fileName)
	{
		String retStr = "";
		String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                retStr+=line;
            }   

            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }
    
		return retStr;
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setFont(roboto);
		g.drawString("Current temp",75,175);
		g.drawString("Current temp", 375,175);
		g.drawString("outside", 75, 275);
		g.drawString("inside", 375, 275);
		g.drawString(Double.toString(currentTempOutside)+"\u00B0C" , 75, 75);
		g.drawString(Double.toString(currentTempInside)+"\u00B0C", 375, 75);
		revalidate();
		repaint();
		t.start();
	}
}
