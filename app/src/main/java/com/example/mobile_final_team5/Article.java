package com.example.mobile_final_team5;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import org.json.simple.JSONObject;

import java.util.Hashtable;

import com.example.mobile_final_team5.exceptions.ServerCommunicationError;
import com.google.gson.annotations.SerializedName;

public class Article extends ModelEntity{
	
	public String title;
	public String category;
	@SerializedName("abstract")
	private String abstractText;
	private String body;
	private String footerText;
	public String subtitle;
	private int idUser;
	private Image mainImage;
	private String imageDescription;
	@SerializedName("thumbnail_image")
	private String thumbnail;

	private String parseStringFromJson(JSONObject jsonArticle, String key, String def){
		Object in = jsonArticle.getOrDefault(key,def);
		return (in==null?def:in).toString();
	}

	@SuppressWarnings("unchecked")
	protected Article(ModelManager mm, JSONObject jsonArticle){
		super(mm);
		try{
			id = Integer.parseInt(jsonArticle.get("id").toString());
			idUser = Integer.parseInt(parseStringFromJson(jsonArticle,"id_user","0"));
			title = parseStringFromJson(jsonArticle,"title","").replaceAll("\\\\","");
			category = parseStringFromJson(jsonArticle,"category","").replaceAll("\\\\","");
			abstractText = parseStringFromJson(jsonArticle,"abstract","").replaceAll("\\\\","");
			body = parseStringFromJson(jsonArticle,"body","").replaceAll("\\\\","");
			footerText = parseStringFromJson(jsonArticle,"footer","").replaceAll("\\\\","");
			subtitle = parseStringFromJson(jsonArticle,"subtitle","").replaceAll("\\\\","");

			imageDescription = parseStringFromJson(jsonArticle,"image_description","").replaceAll("\\\\","");
			thumbnail = parseStringFromJson(jsonArticle,"thumbnail_image","").replaceAll("\\\\","");
			
			String imageData = parseStringFromJson(jsonArticle,"image_data","").replaceAll("\\\\","");
		
			if (imageData!=null && !imageData.isEmpty())
				mainImage = new Image(mm, 1, imageDescription, id, imageData);
		}catch(Exception e){
			Logger.log(Logger.ERROR, "ERROR: Error parsing Article: from json"+jsonArticle+"\n"+e.getMessage());
			throw new IllegalArgumentException("ERROR: Error parsing Article: from json"+jsonArticle);
		}
	}
	
	public Article(ModelManager mm, String category, String title, String subtitle,String abstractText, String body, String footer){
		super(mm);
		id = -1;
		this.category = category;
		idUser = Integer.parseInt(mm.getIdUser());
		this.abstractText = abstractText;
		this.title = title;
		this.body = body;
		this.footerText = footer;
		this.subtitle = subtitle;
		
	}
	
	public void setId(int id){
		if (id <1){
			throw new IllegalArgumentException("ERROR: Error setting a wrong id to an article:"+id);
		}
		if (this.id>0 ){
			throw new IllegalArgumentException("ERROR: Error setting an id to an article with an already valid id:"+this.id);
		}
		this.id = id;
	}
	
	public String getTitleText() {
		return title;
	}
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category= category;
	}
	public void setTitleText(String title) {
		this.title = title;
	}
	public String getAbstractText() {
		return abstractText;
	}
	public void setAbstractText(String abstractText) {
		this.abstractText = abstractText;
	}
	public String getBodyText() {
		return body;
	}
	public void setBodyText(String body) {
		this.body = body;
	}
	public String getFooterText() {
		return footerText;
	}
	public void setFooterText(String footerText) {
		this.footerText = footerText;
	}
	public String getSubtitleText() {return subtitle;}
	public void setSubtitleText(String subtitleText) {this.subtitle = subtitle;}
	
	public int getIdUser(){
		return idUser;
	}
	public Image getImage() throws ServerCommunicationError {
		if (thumbnail!=null && !thumbnail.isEmpty()){
			return new Image(mm,1,"",getId(),thumbnail);
		}
		return null;
	}
	public void setImage(Image image) {
		this.mainImage = image;
	}
	public String getThumbnailImage() {
		return thumbnail;
	} //i added this but idk if we should use getImage
	
	/*public Image addImage(String b64Image, String description) throws ServerCommunicationError{
		int order = 1;
		Image img =new Image(mm, order, description, getId(), b64Image);
		mainImage= img;
		return img;
	}*/
	public Image addImage(String b64Image, String description) throws ServerCommunicationError {
		int order = 1;
		Image img = new Image(mm, order, description, getId(), b64Image);
		mainImage = img;

		// Decode Base64 image data and set it to Bitmap
		byte[] decodedString = Base64.decode(b64Image, Base64.DEFAULT);
		Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
		mainImage.setBitmap(decodedBitmap);

		return img;
	}



	@Override
	public String toString() {
		return "Article [id=" + getId()
				//+ "isPublic=" + isPublic + ", isDeleted=" + isDeleted 
				+", title=" + title
				+", abstractText=" + abstractText
				+  ", body="	+ body + ", footerText=" + footerText
				+", subtitle=" + subtitle
				//+ ", publicationDate=" + Utils.dateToString(publicationDate) 
				+", image_description=" + imageDescription
				+", image_data=" + mainImage
				+", thumbnail=" + thumbnail
				+ "]";
	}
	
	public Hashtable<String,String> getAttributes(){
		Hashtable<String,String> res = new Hashtable<String,String>();
		//res.put("is_public", ""+(isPublic?1:0));
		//res.put("id_user", ""+idUser);
		res.put("category", category);
		res.put("abstract", abstractText);
		res.put("title", title);
		//res.put("is_deleted", ""+(isDeleted?1:0));
		res.put("body", body);
		res.put("subtitle", footerText);
		if (mainImage!=null){
			res.put("image_data", mainImage.getImage());
			res.put("image_media_type", "image/png");
		}
		
		if (mainImage!=null && mainImage.getDescription()!=null && !mainImage.getDescription().isEmpty())
			res.put("image_description", mainImage.getDescription());
		else if (imageDescription!=null && !imageDescription.isEmpty())
			res.put("image_description", imageDescription);

		//res.put("publication_date", publicationDate==null?null:Utils.dateToString(publicationDate));
		return res;
	}
}
