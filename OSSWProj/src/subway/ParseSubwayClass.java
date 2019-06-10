package subway;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.Charset;

/**
 * ParseSubwayClass
 */
public class ParseSubwayClass {
	private final String currentPath = System.getProperty("user.dir");

	private final String fileName = currentPath + "\\OSSWProj\\Subway\\지하철혼잡도및소요시간.csv";	
	
	private ArrayList<ArrayList<String>> csvFilevalues;

	private SubwayInfoClass subwayInfo = SubwayInfoClass.getInstance();

	public ParseSubwayClass() {
		System.out.println(currentPath);
		this.csvFilevalues = new ArrayList<ArrayList<String>>();
		this.readCSV();
		this.saveInfo();
	}

	private void readCSV() {
		BufferedReader br = null;
		String seporator = ",";

		try {
			br = Files.newBufferedReader(Paths.get(this.fileName));
			Charset.forName("UTF-8");
			String line = "";
			//line = br.readLine();
			while ((line = br.readLine()) != null) {
				String[] splittedList = line.split(seporator);
				ArrayList<String> tmpList = new ArrayList<String>();
				for (String str : splittedList) {
					tmpList.add(str);
				}
				this.csvFilevalues.add(tmpList);
			}
		}
		catch (FileNotFoundException e) {
			//TODO: handle exception
			e.printStackTrace();
		}
		catch (IOException e){
			e.printStackTrace();
		}	
		finally{
			try{
				if (br != null){
					br.close();
				}
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}

		for(int i=0; i<this.csvFilevalues.size(); i++) {
			for (int j = 0; j < this.csvFilevalues.get(i).size(); j++) {
				System.out.print(this.csvFilevalues.get(i).get(j)+ " ");
			}
			System.out.println("");
		}
	}

	private void saveInfo(){
		// 0 : line number
		// 1 : direction
		// 2 : stationName
		// 3 : duration
		// 4~end : congestion, 00~23, no data about 01~04
		
		// to subwayInfo save data per two row ==> row % 2 == 0
		// line : in arraylist, stationName, direction, 
		// station : in arraylist, stationName, nextStation
	}
}