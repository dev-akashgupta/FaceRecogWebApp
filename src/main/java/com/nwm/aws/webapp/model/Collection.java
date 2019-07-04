package com.nwm.aws.webapp.model;

import java.util.Date;

public class Collection {

	public static CollectionBuilder newBuilder() {
		Collection collection = new Collection();
		return collection.new CollectionBuilder(collection);
	}

	private Integer statusCode;
	private String arn;
	private String version;
	private Long count;
	private Date creationDate;
	
	private Collection() {}
	public Integer getStatusCode() {return statusCode;}
	public String getArn() {return arn;}
	public String getVersion() {return version;}
	public Long getCount() {return count;}
	public String getCreationDate() {return creationDate.toString();}
	public Collection copy() {
		Collection col = new Collection();
		col.statusCode = statusCode;
		col.arn = arn;
		col.version = version;
		col.count = count;
		col.creationDate = creationDate;
		return col;
	}
	public String toString() {
		StringBuilder res = new StringBuilder();
		res.append("StatusCode:").append(statusCode).append(";");
		res.append("ARN:").append(arn).append(";");
		res.append("Version:").append(version).append(";");
		res.append("FaceCount:").append(count).append(";");
		res.append("CreationDate:").append(creationDate).append(";");
		return res.toString();
	}

	public class CollectionBuilder {
		private final Collection col;
		private CollectionBuilder(Collection col) {
			this.col = col;
		}
		public Collection build() {
			return col.copy();
		}
		public CollectionBuilder setStatusCode(Integer val) {
			col.statusCode = val;
			return this;
		}
		public CollectionBuilder setArn(String val) {
			col.arn = val;
			return this;
		}
		public CollectionBuilder setVersion(String val) {
			col.version = val;
			return this;
		}
		public CollectionBuilder setCount(Long val) {
			col.count = val;
			return this;
		}
		public CollectionBuilder setDate(Date val) {
			col.creationDate = val;
			return this;
		}
	}
}
