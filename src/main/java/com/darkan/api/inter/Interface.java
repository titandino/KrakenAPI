package com.darkan.api.inter;

import kraken.plugin.api.Widgets;

public class Interface {
	
	private int id;
	
	public Interface(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public IFComponent[] getComponents() {
		IFComponent[] components = new IFComponent[Widgets.getGroupById(id).getWidgets().length];
		for (int i = 0;i < components.length;i++)
			components[i] = new IFComponent(id, i);
		return components;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("Interface: " + id + "\r\n");
		s.append("Components: ");
		for (IFComponent component : getComponents()) {
			try {
				s.append(component.toString());
			} catch (Exception e) {
				
			}
		}
		return s.toString();
	}
}
