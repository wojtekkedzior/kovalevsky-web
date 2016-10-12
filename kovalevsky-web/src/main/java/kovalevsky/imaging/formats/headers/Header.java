package kovalevsky.imaging.formats.headers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Header {
	protected List<Map<String, String>> headerComponents;
	
	public Header() {
		headerComponents = new ArrayList<Map<String, String>>();
	}
	
	protected void addHeaderComponent(String name, String value, int offSet, int bytes) {
		Map<String, String> component = new HashMap<String, String>();
		component.put("name", name);
		component.put("value", value);
		component.put("offset", offSet + "");
		component.put("bytes", bytes + "");
		headerComponents.add(component);
	}
	
	public void modifyHeaderComponent(String name, String newValue) {
		
	}
	
	public List<Map<String, String>> getHeaderComponents() {
		return headerComponents;
	}
	
	public abstract int getWidth();
	public abstract int getHeight();

	public abstract void setSize(int size); //size without the header
	public abstract void setWidth(int i);
	public abstract void setHeight(int i);
	
}
