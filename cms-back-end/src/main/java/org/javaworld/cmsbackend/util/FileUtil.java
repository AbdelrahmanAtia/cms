package org.javaworld.cmsbackend.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;

public class FileUtil {

	public static String getUniqueFileName() {
		return new Date().getTime() + "" + (int) (Math.random() * Integer.MAX_VALUE);
	}

	/**
	 * saves  given base64 image to the given path <br>
	 * this method supports only jpg images
	 * @param path
	 * @param base64Image
	 * @return true if image saved successfully , false otherwise
	 */
	public static boolean saveImageToFileSystem(String path, String base64Image) {
		File file = new File(path);
		FileOutputStream fileOutputStream;
		try {
			fileOutputStream = new FileOutputStream(file);
			base64Image = base64Image.replace("data:image/jpeg;base64,", ""); // remove non base64 characters
			byte[] dataBytes = Base64.getDecoder().decode(base64Image);
			fileOutputStream.write(dataBytes);
			fileOutputStream.close();
			return  true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * deletes the file at the given path
	 * @param path the path of the file  that will be deleted
	 * @return  true if the file deleted successfully, false otherwise
	 */
	public static boolean deleteImageFromFileSystem(String path) {
		System.out.println("starting deleteImageFromFileSystem() with path = " + path);
		File file = new File(path);
		System.out.println("exists = " + file.exists());
		boolean deleted = file.delete();
		System.out.println("deleted = " + deleted);
		return deleted;
	}

	
}
