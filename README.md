JSON2JAVA
==========

General idea is to generate the basic Java classes given a data-model in a JSON file.

### Status
[![Build Status](https://travis-ci.org/hoofmen/json2java.svg?branch=master)](https://github.com/hoofmen/json2java)

### Relevant Development Tools
* Java 8
* Spring boot 1.5.6.RELEASE
* Jackson 2.8.5

### File example of a data-model in JSON 
You will need to pass your data-model in a JSON file like the one below:

```json
{
  "Account": {
    "id": "1d4f35e5-ded5-fd9b-ed15-867d35ce21bf",
    "name": "troll_1991",
    "attempts": 3,
    "pi": 3.14,
    "active": true,
    "address": {
      "city": "San Francisco",
      "zip": 94107
    },
    "latest-posts": [
      {
        "id": 1,
        "title": "fake title 1"
      },
      {
        "id": 2,
        "title": "fake title 2"
      }
    ]
  }
}
```
Given the above JSON file, the following Java classes will be generated:
```java
public class Account {
	private List<LatestPosts> latestPosts;
	private boolean active;
	private Address address;
	private Integer attempts;
	private Double pi;
	private String id;
	private String name;

	public void setLatestPosts(List<LatestPosts> latestPosts) {
		this.latestPosts = latestPosts;
	}
	public List<LatestPosts> getLatestPosts() {
		return latestPosts;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public boolean getActive() {
		return active;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public Address getAddress() {
		return address;
	}
	public void setAttempts(Integer attempts) {
		this.attempts = attempts;
	}
	public Integer getAttempts() {
		return attempts;
	}
	public void setPi(Double pi) {
		this.pi = pi;
	}
	public Double getPi() {
		return pi;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
}
```
```java
public class Address {
	private Integer zip;
	private String city;

	public void setZip(Integer zip) {
		this.zip = zip;
	}
	public Integer getZip() {
		return zip;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCity() {
		return city;
	}
}
```
```java
public class LatestPosts {
	private String title;
	private Integer id;

	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		return title;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId() {
		return id;
	}
}

```

### How to use

```sbtshell
java -jar json2java.jar json_model.json
```