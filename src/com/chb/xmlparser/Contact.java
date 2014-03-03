package com.chb.xmlparser;

/**
 * Created by hempel on 14-2-28.
 */
public class Contact {
	private String name;
	private String birthday;
	private String birthdayMoon;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getBirthdayMoon() {
		return birthdayMoon;
	}

	public void setBirthdayMoon(String birthdayMoon) {
		this.birthdayMoon = birthdayMoon;
	}

	@Override
	public String toString() {
		return "name:" + name + ", birthday:" + birthday + ", birthdayMoon:" + birthdayMoon;
	}
}

