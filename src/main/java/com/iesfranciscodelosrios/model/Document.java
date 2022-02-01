package com.iesfranciscodelosrios.model;

import java.io.Serial;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Document")
public class Document implements Serializable{
	@Serial
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="copies")
	private int nCopies; //Number of copies
	@Column(name="color")
	private Color isColor; //Color of the document. true color; false black and white;
	@Column(name="size") 
	private Size size; //Size of the paper
	@Column(name="thickness")
	private PaperThickness thickness; //Thickness of the paper
	@Column(name="impressionType")
	private String impressionType; //One or two sides
	@Column(name="finishType") 
	private Ended finishType; //How the sheets will be arranged. Saddle (grapado) stitched (encuadernado)
	@Column(name="impressionPerSide")
	private String impressionPerSide; //Number of impression of each side 1,2(pages),2(slides),4 
	@Column(name="isVertical")
	private boolean isVertical; //Layout of the sheet. No price. True vertical, false horizontal
	@Column(name="ringedPosition") 
	private String ringedPosition; //Layout of the ringed.
	
	@Column(name="comment")
	private String comment;
	
	@Column(name="url")
	private String url;
	
	public Document() {
		this.id=-1L;
	}

	public Document(int nCopies, Color isColor, Size size, PaperThickness thickness, String impressionType,
			Ended finishType, String impressionPerSide, boolean isVertical, String ringedPosition, String comment,
			String url) {
		super();
		this.id=-1L;
		this.nCopies = nCopies;
		this.isColor = isColor;
		this.size = size;
		this.thickness = thickness;
		this.impressionType = impressionType;
		this.finishType = finishType;
		this.impressionPerSide = impressionPerSide;
		this.isVertical = isVertical;
		this.ringedPosition = ringedPosition;
		this.comment = comment;
		this.url = url;
	}
	
	public Document(int nCopies, Color isColor, Size size, PaperThickness thickness, String impressionType,
			Ended finishType, String impressionPerSide, boolean isVertical, String ringedPosition, String comment) {
		super();
		this.id=-1L;
		this.nCopies = nCopies;
		this.isColor = isColor;
		this.size = size;
		this.thickness = thickness;
		this.impressionType = impressionType;
		this.finishType = finishType;
		this.impressionPerSide = impressionPerSide;
		this.isVertical = isVertical;
		this.ringedPosition = ringedPosition;
		this.comment = comment;
	}
	
	public Document(int nCopies, Color isColor, Size size, PaperThickness thickness, String impressionType,
			Ended finishType, String impressionPerSide, boolean isVertical, String ringedPosition) {
		super();
		this.id=-1L;
		this.nCopies = nCopies;
		this.isColor = isColor;
		this.size = size;
		this.thickness = thickness;
		this.impressionType = impressionType;
		this.finishType = finishType;
		this.impressionPerSide = impressionPerSide;
		this.isVertical = isVertical;
		this.ringedPosition = ringedPosition;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Document other = (Document) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getnCopies() {
		return nCopies;
	}

	public void setnCopies(int nCopies) {
		this.nCopies = nCopies;
	}

	public Color getIsColor() {
		return isColor;
	}

	public void setIsColor(Color isColor) {
		this.isColor = isColor;
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	public PaperThickness getThickness() {
		return thickness;
	}

	public void setThickness(PaperThickness thickness) {
		this.thickness = thickness;
	}

	public String getImpressionType() {
		return impressionType;
	}

	public void setImpressionType(String impressionType) {
		this.impressionType = impressionType;
	}

	public Ended getFinishType() {
		return finishType;
	}

	public void setFinishType(Ended finishType) {
		this.finishType = finishType;
	}

	public String getImpressionPerSide() {
		return impressionPerSide;
	}

	public void setImpressionPerSide(String impressionPerSide) {
		this.impressionPerSide = impressionPerSide;
	}

	public boolean isVertical() {
		return isVertical;
	}

	public void setVertical(boolean isVertical) {
		this.isVertical = isVertical;
	}

	public String getRingedPosition() {
		return ringedPosition;
	}

	public void setRingedPosition(String ringedPosition) {
		this.ringedPosition = ringedPosition;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	
}
