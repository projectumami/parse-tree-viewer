package org.openjfx;

public class TreeTableNode 
{
	private int level;
	private int parentId;
	private int childId;
	private String data;
	
	public TreeTableNode()
	{
		
	}
	
	public TreeTableNode(int level, int parentId, int childId, String data) 
	{
		this.level = level;
		this.parentId = parentId;
		this.childId = childId;
		this.data = data;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getChildId() {
		return childId;
	}

	public void setChildId(int childId) {
		this.childId = childId;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	@Override
	public String toString()
	{
		return "(" + this.level + "," + this.parentId + "," + this.childId + "," + this.data + ")";
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}
