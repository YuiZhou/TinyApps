import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

abstract class DownloadAlbum {
	protected static Downloader downloader;
	
	DownloadAlbum() {
		downloader = Downloader.getInstance();
	}

	/**
	 * Download all photos in the given album
	 * 
	 * @param url
	 *            the url of album need to be download
	 * @return the path of directory of all photos
	 */
	String download(String url) {
		/* Download the album file and parse it */
		String filename = downloader.download(url);
		downloader.setDir(parseDir(filename));
		try {
			parseFile(filename);
			File f = new File(filename);
			f.deleteOnExit();
		} catch (FileNotFoundException e) {
			System.err.println("Download error");
		}
		return filename;
	}

	private String parseDir(String filename) {
		System.out.println("filename is "+filename);
		int index = filename.lastIndexOf('.');
		if( index < 2)
			return filename;
		return filename.substring(0, index) + "/";
	}

	/**
	 * parse the source file to find out the url of photos and download them.
	 * 
	 * @param filename
	 *            the source file of album
	 * @throws FileNotFoundException 
	 */
	abstract void parseFile(String filename) throws FileNotFoundException;
}
