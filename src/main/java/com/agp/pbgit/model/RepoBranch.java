package com.agp.pbgit.model;


public class RepoBranch {
	private String branch;
	private String owner;
	private String sha1;
	
	public RepoBranch() {}
	
	public RepoBranch(String branch, String owner, String sha1) {
		super();
		this.branch = branch;
		this.owner = owner;
		this.sha1 = sha1;
	}
	
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getSha1() {
		return sha1;
	}
	public void setSha1(String sha1) {
		this.sha1 = sha1;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((branch == null) ? 0 : branch.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result + ((sha1 == null) ? 0 : sha1.hashCode());
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
		RepoBranch other = (RepoBranch) obj;
		if (branch == null) {
			if (other.branch != null)
				return false;
		} else if (!branch.equals(other.branch))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		if (sha1 == null) {
			if (other.sha1 != null)
				return false;
		} else if (!sha1.equals(other.sha1))
			return false;

		return true;
	}

	@Override
	public String toString() {
		return "RepoBranch [branch=" + branch + ", owner=" + owner + ", sha1=" + sha1 + "]";
	}
	
	
	
}
