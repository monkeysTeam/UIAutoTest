package exception;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ErrorRespStatusException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4954101493084921990L;

	public ErrorRespStatusException(String msg) {
		super(msg);
	}
	public static void main(String[] args) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd/");
		String filepath="screen/"+df.format(new Date());
		File filedir=new File(filepath);
		System.out.println(filedir.getAbsolutePath());
	}
}
