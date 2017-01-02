package io.javabrains.data.entity;



public class Lesson {
	private String title;
	private String description;
	private String permalinkName;
	private String durationText;
	private String type;

	public Lesson() {
		
	}
	
	public Lesson(Lesson other) {
		this.title = other.title;
		this.description = other.description;
		this.permalinkName = other.permalinkName;
		this.durationText = other.durationText;
		this.type = other.type;
	}
	
	public Lesson(String permalinkName) {
		this.permalinkName = permalinkName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPermalinkName() {
		return permalinkName;
	}

	public void setPermalinkName(String permalinkName) {
		this.permalinkName = permalinkName;
	}

	public String getDurationText() {
		return durationText;
	}

	public void setDurationText(String durationText) {
		this.durationText = durationText;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((permalinkName == null) ? 0 : permalinkName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lesson other = (Lesson) obj;
		if (permalinkName == null) {
			if (other.permalinkName != null)
				return false;
		} else if (!permalinkName.equals(other.permalinkName))
			return false;
		return true;
	}
	
	

}