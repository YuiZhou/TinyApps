import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class RenrenAlbum extends DownloadAlbum {
	private static final String prefix = "xLarge:&#39;";
	public RenrenAlbum() {
		super();
	}
	void parseFile(String filename) throws FileNotFoundException {
		byte[] buf = new byte[2048];
		String line = "";
		BufferedReader br = new BufferedReader(new FileReader(filename));
		try {
			while((line = br.readLine()) != null){
				parseLine(line);
			}
			br.close();
		}catch(IOException e){}
	}
	
	private void parseLine(String line){
		int index = line.indexOf(prefix);
		if(index >= 0){
			int fromIndex = index + prefix.length();
			String photo = line.substring( fromIndex, line.indexOf('&', fromIndex));
			if(photo.length() == 0)
				parseLine(line, "large:&#39;");
			else 
				downloader.download(photo);
		}
	}
	private void parseLine(String line, String param) {
		int index = line.indexOf(param);
		if(index >= 0){
			int fromIndex = index + param.length();
			String photo = line.substring( fromIndex, line.indexOf('&', fromIndex));
			if(photo.length() > 0)
				downloader.download(photo);
		}
	}
}