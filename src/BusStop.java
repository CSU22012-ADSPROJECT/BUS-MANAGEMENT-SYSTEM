
public class BusStop {

	int stop_id;
	int stop_code;
	String stop_name;
	String stop_desc;
	double stop_lat;
	double stop_lon;
	String zone_id;
	String stop_url;
	String location_type;
	BusStop parent_station;

	public int getStopID() {
		return stop_id;
	}

	public void setStopID(int x) {
		this.stop_id = x;
	}

	public int getStopCode() {
		return stop_code;
	}

	public void setStopCode(int x) {
		this.stop_code = x;
	}

	public String getStopName() {
		return stop_name;
	}

	public void setStopName(String x) {
		this.stop_name = x;
	}

	public String getStopDesc() {
		return stop_desc;
	}

	public void setStopDesc(String x) {
		this.stop_desc = x;
	}

	public double getStopLat() {
		return stop_lat;
	}

	public void setStopLat(double x) {
		this.stop_lat = x;
	}

	public double getStopLon() {
		return stop_lon;
	}

	public void setStopLon(double x) {
		this.stop_lon = x;
	}

	public String getZoneID() {
		return zone_id;
	}

	public void setZoneID(String x) {
		this.zone_id = x;
	}

	public String getStopURL() {
		return zone_id;
	}

	public void setStopURL(String x) {
		this.zone_id = x;
	}

	public String getLocationType() {
		return zone_id;
	}

	public void setLocationType(String x) {
		this.zone_id = x;
	}
	
	public BusStop getParent() {
		return parent_station;
	}

	public void setParent(BusStop x) {
		this.parent_station = x;
	}
}
