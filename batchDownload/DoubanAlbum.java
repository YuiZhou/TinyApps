import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class DoubanAlbum extends DownloadAlbum {
	private static final String prefix = "http://www.douban.com/photos/photo/";
	private static final String photoPre = "http://img3.douban.com/view/photo/photo/public/p";
	public DoubanAlbum() {
		super();
	}
	void parseFile(String filename) throws FileNotFoundException {
		byte[] buf = new byte[2048];
		String line = "";
		BufferedReader br = new BufferedReader(new FileReader(filename));
		try {
			while((line = br.readLine()) != null){
				int index = line.indexOf(prefix);
				if(index >= 0){
					int fromIndex = index + prefix.length();
					String photoid = line.substring( fromIndex, line.indexOf('/', fromIndex));
					downloader.download(photoPre + photoid + ".jpg");
				}
			}
			br.close();
		}catch(IOException e){}
	}
}