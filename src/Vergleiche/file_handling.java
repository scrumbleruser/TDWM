package Vergleiche;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class file_handling {
	
	ArrayList<String> aList = new ArrayList<String>();
	ArrayList<String> bList = new ArrayList<String>();

	public void compareFiles(String f1, String f2){
		
		runSystemCommand("fc /N \""+f1+"\" \""+f2+"\"");
	}
	
	public void runSystemCommand(String command) {
        try {
            Process proc = Runtime.getRuntime().exec(command);
           
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            BufferedReader error = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
            
            String str = "";
            
            // reading outputstream
            while ((str = in.readLine()) != null) {
                System.out.println(str);
            }
            
            // reading errors
            while ((str = error.readLine()) != null) {
                System.out.println(str);
            }
           
        } catch (Exception e) {
           
            e.printStackTrace();
        }
    }
	
	public String formatString(String myString){
//		StringBuffer sb = new StringBuffer(myString);
		myString = myString.replaceAll(" ", "_");
		myString = myString.replaceAll("\\n", "");
		myString = myString.replaceAll("\\r", "");
		myString = myString.replaceAll("\\t", "");
		return myString;
	}
	
	public void compareMyFiles(String f1, String f2){
		aList = readFile(f1);
		bList = readFile(f2);

		for(int i=0; i<aList.size();i++){
				String a = aList.get(i).toString();
				String b = bList.get(i).toString();
				if (a.equals(b)){
					System.out.println("gleich");
					
				}
				if( aList.get(i).equalsIgnoreCase((bList.get(i).toString())) ){
					System.out.println("Beide listen haben gleichen String der lautet: " + aList.get(i));
				}
		}
	}
	
	public void writeInFile(String fn, ArrayList<String> aList){
		File f = new File(fn);
		int sz = aList.size();
		System.out.println("Size of Array: " + sz);
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(f,true));
			for(int i=0; i<sz;i++){
				bw.write(aList.get(i).toString() + "\n");
				System.out.println(aList.get(i) + " nr " + i);
				System.out.println("Write complet");
			}
			bw.flush();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("resource")
	public ArrayList<String> readFile(String fn){
		File f = new File(fn);
		String str = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
//			int ch = br.read(); - char
			if (!br.ready()){
				throw new IOException(); // wenn nicht lesbar dann Exception schmeissen
			}
			String line = br.readLine();
			while(line != null){
//				if (line.isEmpty()) continue;
//			while(ch != -1){ - char
//				System.out.println((char)ch); - char
//				line = formatString(line);
//				str += line;
				aList.add(line);
				System.out.println("Mein Wort lautet: " + line);
				if (line.equals("da")){
					System.out.println("gleich");
				}
//				ch = br.read(); - char
				line = br.readLine();
			}
//			aList.add(str);
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return aList;
	}
	
	public static void main(String[] args) {
		file_handling handling = new file_handling();
//		handling.compareFiles("C:\\f1.txt", "C:\\f2.txt");
		String meinwort = "";
		ArrayList<String> aList = new ArrayList<String>();
		aList.add("dan");
		aList.add("iel");
//			handling.writeInFile("f:\\test.txt",aList);

//			handling.readFile("f:\\test.txt");
		handling.compareMyFiles("f:\\test.txt","f:\\testb.txt");
	}

}
