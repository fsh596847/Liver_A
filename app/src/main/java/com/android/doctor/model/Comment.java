package com.android.doctor.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * 评论实体类
 *
 */
@SuppressWarnings("serial")
public class Comment  implements Parcelable {

	private String portrait;

	private String content;

	private String author;

	private int authorId;

	private String pubDate;

	private int appClient;

	private List<Reply> replies = new ArrayList<Reply>();

	public Comment() {
	}

	@SuppressWarnings("unchecked")
	public Comment(Parcel source) {
		portrait = source.readString();
		author = source.readString();
		authorId = source.readInt();
		pubDate = source.readString();
		appClient = source.readInt();
		content = source.readString();

		replies = source.readArrayList(Reply.class.getClassLoader());

	}

	public static final Creator<Comment> CREATOR = new Creator<Comment>() {
		@Override
		public Comment createFromParcel(Parcel in) {
			return new Comment(in);
		}

		@Override
		public Comment[] newArray(int size) {
			return new Comment[size];
		}
	};

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(portrait);
		dest.writeString(author);
		dest.writeInt(authorId);
		dest.writeString(pubDate);
		dest.writeInt(appClient);
		dest.writeString(content);
		dest.writeList(replies);
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public int getAppClient() {
		return appClient;
	}

	public void setAppClient(int appClient) {
		this.appClient = appClient;
	}

	public List<Reply> getReplies() {
		return replies;
	}

	public void setReplies(List<Reply> replies) {
		this.replies = replies;
	}


	public static class Reply implements Serializable, Parcelable {

		public String rauthor;

		public String rpubDate;

		public String rcontent;
		
		public Reply() {
		}

		public Reply(Parcel source) {
			rauthor = source.readString();
			rpubDate = source.readString();
			rcontent = source.readString();
		}
		
		public String getRauthor() {
			return rauthor;
		}
		public void setRauthor(String rauthor) {
			this.rauthor = rauthor;
		}
		public String getRpubDate() {
			return rpubDate;
		}
		public void setRpubDate(String rpubDate) {
			this.rpubDate = rpubDate;
		}
		public String getRcontent() {
			return rcontent;
		}
		public void setRcontent(String rcontent) {
			this.rcontent = rcontent;
		}
		
		@Override
		public void writeToParcel(Parcel dest, int flags) {
			dest.writeString(rauthor);
			dest.writeString(rpubDate);
			dest.writeString(rcontent);
		}

		@Override
		public int describeContents() {
			return 0;
		}

		public static final Creator<Reply> CREATOR = new Creator<Reply>() {

			@Override
			public Reply[] newArray(int size) {
				return new Reply[size];
			}

			@Override
			public Reply createFromParcel(Parcel source) {
				return new Reply(source);
			}
		};
		
	}
	

}
