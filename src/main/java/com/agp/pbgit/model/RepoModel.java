package com.agp.pbgit.model;

import java.util.Date;
import java.util.Set;

public class RepoModel {
	public String repoName;
	public String masterBranch;
	public Date createdAt;
	public Date updatedAt;
	public String ownerName;
	public String url;
	
	public Set<RepoBranch> branches;
	
	public RepoModel() {}
	
	public RepoModel(String repoName, String masterBranch, Date createdAt, Date updatedAt, String ownerName,
			String url) {
		super();
		this.repoName = repoName;
		this.masterBranch = masterBranch;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.ownerName = ownerName;
		this.url = url;
	}

	public String getRepoName() {
		return repoName;
	}

	public void setRepoName(String repoName) {
		this.repoName = repoName;
	}

	public String getMasterBranch() {
		return masterBranch;
	}

	public void setMasterBranch(String masterBranch) {
		this.masterBranch = masterBranch;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	
	public Set<RepoBranch> getBranches() {
		return branches;
	}

	public void setBranches(Set<RepoBranch> branches) {
		this.branches = branches;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((branches == null) ? 0 : branches.hashCode());
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((masterBranch == null) ? 0 : masterBranch.hashCode());
		result = prime * result + ((ownerName == null) ? 0 : ownerName.hashCode());
		result = prime * result + ((repoName == null) ? 0 : repoName.hashCode());
		result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
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
		RepoModel other = (RepoModel) obj;
		if (branches == null) {
			if (other.branches != null)
				return false;
		} else if (!branches.equals(other.branches))
			return false;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
		if (masterBranch == null) {
			if (other.masterBranch != null)
				return false;
		} else if (!masterBranch.equals(other.masterBranch))
			return false;
		if (ownerName == null) {
			if (other.ownerName != null)
				return false;
		} else if (!ownerName.equals(other.ownerName))
			return false;
		if (repoName == null) {
			if (other.repoName != null)
				return false;
		} else if (!repoName.equals(other.repoName))
			return false;
		if (updatedAt == null) {
			if (other.updatedAt != null)
				return false;
		} else if (!updatedAt.equals(other.updatedAt))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RepoModel [repoName=" + repoName + ", masterBranch=" + masterBranch + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + ", ownerName=" + ownerName + ", url=" + url + ", branches=" + branches
				+ "]";
	}

	
	
}
