package demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Utility {

	public HashMap<String, String> readConfigurationFile(String configFile) throws IOException {
		String line;
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		HashMap<String, String> valuesMap = new HashMap<String, String>();
		try {
			inputStreamReader = new InputStreamReader(getClass().getClassLoader().getResourceAsStream(configFile), "UTF-8");
			BufferedReader br = new BufferedReader(inputStreamReader);
			while ((line = br.readLine()) != null) {
				valuesMap.put(line.substring(0, line.indexOf('=')), line.substring(line.indexOf('=') + 1)); 
			}
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			if (inputStreamReader != null)
				inputStreamReader.close();
			if (inputStream != null)
				inputStream.close();
		}
		return valuesMap;
	}
	
	public File getObjFile() {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("objFile.txt").getFile());
		return file;
	}
}