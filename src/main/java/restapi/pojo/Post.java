package restapi.pojo;

import javax.validation.constraints.NotNull;

public class Post {
	@NotNull
	private int postid;
	@NotNull
	private String uri;
	
	public Post(){};
	public Post(int postid, String uri){
		this.postid = postid;
		this.uri = uri;
	}
	public int getPostID(){
		return this.postid;
	}
	public String getUri(){
		return this.uri;
	}

}
