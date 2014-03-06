import org.omg.IOP.CodecPackage.FormatMismatch;

public class Timer {

	/**
	 * @description
	 * this class will fix a timestamp in format hour:min:sec,ms with a fixed offset
	 */
	private static int offset;
	Timer(int offset){
		this.offset = offset;
	}
	
	String fixTime(String timeStr){
		String fixTime = "";
		int off = offset;
		int[] timeArr;
		try {
			timeArr = tokenize(timeStr);
		
		
			/*
			 * timeArr[0] - hour
			 * timeArr[1] - min
			 * timeArr[2] - sec
			 * timeArr[3] - ms
			 */
			for(int i = 2; i >= 0; i--){
				timeArr[i] += off;
				if(timeArr[i] >= 60){
					/* offset > 0 */
					timeArr[i] -= 60;
					off = 1;
				}else if(timeArr[i] < 0){
					/* offset < 0 */
					timeArr[i] += 60;
					off = -1;
				}else
					off = 0;
			}
			
			fixTime += getStr(timeArr[0])+":"+getStr(timeArr[1])+":"+getStr(timeArr[2])+","+timeArr[3];
		} catch (FormatMismatch e) {
			System.err.println("Wrong format for \n"+timeStr);
		}
		return fixTime;
	}

	private String getStr(int i) {
		String str = "";
		if(i < 10)
			str = "0" + i;
		else
			str += i;
		return str;
	}

	private int[] tokenize(String timeStr) throws FormatMismatch {
		int[] timeArr = new int[4];
		/* hour:min:sec,ms */
		for(int i = 0; i < 2; i++){
			int id = timeStr.indexOf(':');
			if(id < 0)
				throw new FormatMismatch();
			String timeChip = timeStr.substring(0, id);
			timeStr = timeStr.substring(id + 1);
			timeArr[i] = Integer.parseInt(timeChip);
		}
		
		int id = timeStr.indexOf(',');
		if(id < 0)
			throw new FormatMismatch();
		String timeChip = timeStr.substring(0, id);
		timeStr = timeStr.substring(id + 1);
		timeArr[2] = Integer.parseInt(timeChip);
		timeArr[3] = Integer.parseInt(timeStr);
		
		return timeArr;
	}

}
