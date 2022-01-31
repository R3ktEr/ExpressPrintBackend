package com.iesfranciscodelosrios.model;

public class Document {
	private enum impressionsTypes {
		normal(1),
		twopages(2),
		twoslides(3),
		fourslides(4);
		
        private int icode;

        impressionsTypes(int icode) {
            this.icode = icode;
        }

        public int getICode() {
            return this.icode;
        }
    }
	
	private Long id;
	
	private int nCopies;
	private String color;
	private String size;
	private int thickness;
	private String impressionType;
	private String finishType;
	private impressionsTypes impressionPerSide;
	private String orientation;
	private String ringedPosition;
	
	private float copyPrice;
	private float colorPrice;
	private float sizePrice;
	private float thicknessPrice;
	private float impressionTypePrice;
	private float finishTypePrice;
	private float impressionPerSidePrice;
	private float ringedPositionPrice;
	
	private String comment;

	public Document(int nCopies, String color, String size, int thickness, String impressionType,
			String finishType, int impressionPerSide, String orientation, String ringedPosition, String comment) {
		super();
		this.id = -1L;
		this.nCopies = nCopies;
		this.color = color;
		this.size = size;
		this.thickness = thickness;
		this.impressionType = impressionType;
		this.finishType = finishType;
		this.impressionPerSide.icode=impressionPerSide;
		this.orientation = orientation;
		this.ringedPosition = ringedPosition;
		this.comment = comment;
	}

	public Document(int nCopies, String color, String size, int thickness, String impressionType,
			String finishType, int impressionPerSide, String orientation, String ringedPosition) {
		super();
		this.id = -1L;
		this.nCopies = nCopies;
		this.color = color;
		this.size = size;
		this.thickness = thickness;
		this.impressionType = impressionType;
		this.finishType = finishType;
		this.impressionPerSide.icode = impressionPerSide;
		this.orientation = orientation;
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

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public int getThickness() {
		return thickness;
	}

	public void setThickness(int thickness) {
		this.thickness = thickness;
	}

	public String getImpressionType() {
		return impressionType;
	}

	public void setImpressionType(String impressionType) {
		this.impressionType = impressionType;
	}

	public String getFinishType() {
		return finishType;
	}

	public void setFinishType(String finishType) {
		this.finishType = finishType;
	}

	public impressionsTypes getImpressionPerSide() {
		return impressionPerSide;
	}

	public void setImpressionPerSide(impressionsTypes impressionPerSide) {
		this.impressionPerSide = impressionPerSide;
	}

	public String getOrientation() {
		return orientation;
	}

	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}

	public String getRingedPosition() {
		return ringedPosition;
	}

	public void setRingedPosition(String ringedPosition) {
		this.ringedPosition = ringedPosition;
	}

	public float getCopyPrice() {
		return copyPrice;
	}

	public void setCopyPrice(float copyPrice) {
		this.copyPrice = copyPrice;
	}

	public float getColorPrice() {
		return colorPrice;
	}

	public void setColorPrice(float colorPrice) {
		this.colorPrice = colorPrice;
	}

	public float getSizePrice() {
		return sizePrice;
	}

	public void setSizePrice(float sizePrice) {
		this.sizePrice = sizePrice;
	}

	public float getThicknessPrice() {
		return thicknessPrice;
	}

	public void setThicknessPrice(float thicknessPrice) {
		this.thicknessPrice = thicknessPrice;
	}

	public float getImpressionTypePrice() {
		return impressionTypePrice;
	}

	public void setImpressionTypePrice(float impressionTypePrice) {
		this.impressionTypePrice = impressionTypePrice;
	}

	public float getFinishTypePrice() {
		return finishTypePrice;
	}

	public void setFinishTypePrice(float finishTypePrice) {
		this.finishTypePrice = finishTypePrice;
	}

	public float getImpressionPerSidePrice() {
		return impressionPerSidePrice;
	}

	public void setImpressionPerSidePrice(float impressionPerSidePrice) {
		this.impressionPerSidePrice = impressionPerSidePrice;
	}

	public float getRingedPositionPrice() {
		return ringedPositionPrice;
	}

	public void setRingedPositionPrice(float ringedPositionPrice) {
		this.ringedPositionPrice = ringedPositionPrice;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "Document [id=" + id + ", nCopies=" + nCopies + ", color=" + color + ", size=" + size + ", thickness="
				+ thickness + ", impressionType=" + impressionType + ", finishType=" + finishType
				+ ", impressionPerSide=" + impressionPerSide + ", orientation=" + orientation + ", ringedPosition="
				+ ringedPosition + ", copyPrice=" + copyPrice + ", colorPrice=" + colorPrice + ", sizePrice="
				+ sizePrice + ", thicknessPrice=" + thicknessPrice + ", impressionTypePrice=" + impressionTypePrice
				+ ", finishTypePrice=" + finishTypePrice + ", impressionPerSidePrice=" + impressionPerSidePrice
				+ ", ringedPositionPrice=" + ringedPositionPrice + ", comment=" + comment + "]";
	}
}
