package Vergleiche;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import SQL.Mysql_connect;

public class file_handling {
	
	private ArrayList<String> aList = new ArrayList<String>();
	private ArrayList<String> bList = new ArrayList<String>();

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

	// Methode funktuniert nicht vollständig - besser 
	public void compareMyFiles(String f1, String f2){
//		aList = readFile(f1);
//		bList = readFile(f2);

		for(int i=1; i<aList.size();i++){
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
	
	public void writeInFileSQLStatement(String fn, String text){
		File f = new File(fn);
		try {
			System.out.println(text);
			BufferedWriter bw = new BufferedWriter(new FileWriter(f,true));
			bw.write(text);
			System.out.println("Write complete");
			bw.flush();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("resource")
	public ArrayList<String> getFileContent(String fn){
		ArrayList<String> list = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader( new File(fn)));
			if ( !br.ready()){
				throw new IOException(); // wenn nicht lesbar dann Exception schmeissen
			}
				String line = br.readLine();
				while(line != null){
//					if (line.isEmpty()) list.add("leereZeile");
					list.add(line);
					line = br.readLine();
				}
			br.close();	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return list;
	}
	
	@SuppressWarnings("resource")
	public String readFilesAndcompare(String fn, String fn2){
		aList = getFileContent(fn);
		bList = getFileContent(fn2);
		System.out.println("A ist: " + aList.size() + "\nB ist: " + bList.size());
		int asize = aList.size();
		int bsize = bList.size();
		int siz = 0;
		int diff = 0;
		int i=0;
		String info = "";
		
		// passe beide ArrayLists an Zeilenanzahl an
		if(asize > bsize){
			siz = asize; // a ist grösser
			diff = asize - bsize;
			while(i<diff){
				bList.add("leereZeile");
				i++;
			}
		}else{
			siz = bsize;  // b ist grösser
			diff = bsize - asize;
			while(i<diff){
				aList.add("leereZeile");
				i++;
			}	
		}
		// Anpassung beendet
		
		// Prüfung wird gestartet
//		System.out.println("A ist: " + aList.size() + "\nB ist: " + bList.size());
//		System.out.println("ich nehme: " + siz);
//		System.out.println("Differenz beträgt: " + diff);
		String[] s1 = new String[siz];
		String[] s2 = new String[siz];
		info += "Zeile: ";
		for(int j=0;j<siz;j++){
			s1[j] = aList.get(j).toString();
			s2[j] = bList.get(j).toString();
			if (!s1[j].equals(s2[j])){  // nur ungleiche sind relevant
				if( (s1[j].equals("")) || (s2[j].equals("")) ){
					j++;
				}else{
					info += (j+1)+", ";
					System.out.println("Datei1: " + s1[j]);
					System.out.println("Datei2: " + s2[j]);
				}
			}
		}
		info += "sind ungleich";
		return info;
	}
	
	public ArrayList<String> compareFileschar(String fn){
//		File f = new File(fn);
		String str = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader( new File(fn)));
//			BufferedReader br2 = new BufferedReader(new FileReader( new File(fn2)));
			int ch = br.read();
			String line = br.readLine();	
//			if (line.isEmpty()) continue;
				while(ch != -1){
					System.out.println((char)ch);
					line = formatString(line);
					str += line;
					ch = br.read();
				}
			aList.add(str);
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return aList;
	}
	
	public static void main(String[] args) {
		file_handling handling = new file_handling();
//		handling.compareFiles("f:\\test.txt", "f:\\testb.txt");
		String meinwort = "";
		ArrayList<String> aList = new ArrayList<String>();
		String text="";
		aList.add("dan");
		aList.add("iel");
//			handling.writeInFile("f:\\test.txt",aList);
			/*String user = "y9r106037";
			String pass = "basilius789063";
			String host = "134.0.26.187:3306/";
			String dbname = "y9r106037_usr_web27_2";
			Mysql_connect con = new Mysql_connect(host,dbname,user,pass);
			String content = con.getSelectStatement("Select * from revision");*/
			String meineungleichenzeilen = handling.readFilesAndcompare("f:\\test.txt","f:\\testb.txt");
			System.out.println(meineungleichenzeilen);
//			handling.writeInFileSQLStatement("f:\\testing.txt", content);
//			handling.compareFileschar("f:\\test.txt");
//		handling.compareMyFiles("f:\\test.txt","f:\\testb.txt");
	}
	
	/**
	 * @return the aList
	 */
	public ArrayList<String> getaList() {
		return aList;
	}

	/**
	 * @param aList the aList to set
	 */
	public void setaList(ArrayList<String> aList) {
		this.aList = aList;
	}

	/**
	 * @return the bList
	 */
	public ArrayList<String> getbList() {
		return bList;
	}

	/**
	 * @param bList the bList to set
	 */
	public void setbList(ArrayList<String> bList) {
		this.bList = bList;
	}

}
