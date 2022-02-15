package com.iesfranciscodelosrios.model;

import java.io.Serial;
import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iesfranciscodelosrios.model.price.*;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="Document")
public class Document implements Serializable{
	@Serial
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "Id del documento",name="id",dataType = "Long",required=false,example="1")
	@Column(name="id")
	private Long id;
	
	@ApiModelProperty(value = "Precio de las copias documento",name="CopyPrice",dataType = "Copy",required=false)
	@OneToOne(fetch = FetchType.EAGER,targetEntity = Copy.class)
	private Copy CopyPrice; //Price per copy
	@ApiModelProperty(value = "Numero de copias del documento",name="nCopies",dataType = "int",required=false,example="5")
	@Column(name = "nCopies")
	private int nCopies;  //Number of copies
	@ApiModelProperty(value = "Indica si es en color o no y el precio",name="isColor",dataType = "Color",required=false)
	@OneToOne(fetch = FetchType.EAGER,targetEntity = Color.class)
	private Color isColor; //Color of the document. true color; false black and white;
	@ApiModelProperty(value = "Indica el tamaño del papel y el precio",name="size",dataType = "Size",required=false)
	@OneToOne(fetch = FetchType.EAGER,targetEntity = Size.class)
	private Size size; //Size of the paper
	@ApiModelProperty(value = "Indica el grosor del papel",name="thickness",dataType = "Thickness",required=false)
	@OneToOne(fetch = FetchType.EAGER,targetEntity = Thickness.class)
	private Thickness thickness; //Thickness of the paper
	@ApiModelProperty(value = "Indica si la copia se hace solo por delante o por delante y por atras",name="isTwoSides",dataType = "boolean",required=false,example="false")
	@Column(name="impressionType")
	private boolean isTwoSides; //One or two sides
	@ApiModelProperty(value = "Indica el acabado de las copias (grapado, encuadernado...)",name="finishType",dataType = "Ended",required=false)
	@OneToOne(fetch = FetchType.EAGER, targetEntity = Ended.class)
	private Ended finishType; //How the sheets will be arranged. Saddle (grapado) stitched (encuadernado)
	@ApiModelProperty(value = "Indica el numero de impresiones por cara. (1,2(paginas),2(diapositivas),4",name="impressionPerSide",dataType = "ImpressionPerSide",required=false)
	@OneToOne(fetch = FetchType.EAGER,targetEntity = ImpressionPerSide.class)
	private ImpressionPerSide impressionPerSide; //Number of impression of each side 1,2(pages),2(slides),4
	@ApiModelProperty(value = "Indica la disposicion de las copias (Vertical, Horizontal)",name="isVertical",dataType = "boolean",required=false, example="false")
	@Column(name="isVertical")
	private boolean isVertical; //Layout of the sheet. No price. True vertical, false horizontal
	@ApiModelProperty(value = "Indica la disposicion del anillado",name="ringedPosition",dataType = "boolean",required=false, example="false")
	@Column(name="ringedPosition") 
	private boolean ringedPosition; //Layout of the ringed. True vertical, false horizontal
	
	@JsonIgnoreProperties("documents")
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER, targetEntity = Order.class)
	private Order order;
	
	@ApiModelProperty(value = "El comentario del documento",name="comment",dataType = "String",required=false,example="Inserte comentario aqui")
	@Column(name="comment")
	private String comment;
	
	@ApiModelProperty(value = "La URL de la carpeta de Google Drive",name="url",dataType = "String",required=false,example="https://www.google.es")
	@Column(name="url")
	private String url;
	
	public Document() {
		this.id=-1L;
	}

	public Document(Copy CopyPrice, Color isColor, Size size, Thickness thickness, boolean isTwoSides,
			Ended finishType, ImpressionPerSide impressionPerSide, boolean isVertical, boolean ringedPosition, String comment,
			String url) {
		super();
		this.id=-1L;
		this.CopyPrice = CopyPrice;
		this.isColor = isColor;
		this.size = size;
		this.thickness = thickness;
		this.isTwoSides = isTwoSides;
		this.finishType = finishType;
		this.impressionPerSide = impressionPerSide;
		this.isVertical = isVertical;
		this.ringedPosition = ringedPosition;
		this.comment = comment;
		this.url = url;
	}
	
	public Document(Copy nCopies, Color isColor, Size size, Thickness thickness, boolean isTwoSides,
			Ended finishType, ImpressionPerSide impressionPerSide, boolean isVertical, boolean ringedPosition, String comment) {
		super();
		this.id=-1L;
		this.CopyPrice = nCopies;
		this.isColor = isColor;
		this.size = size;
		this.thickness = thickness;
		this.isTwoSides = isTwoSides;
		this.finishType = finishType;
		this.impressionPerSide = impressionPerSide;
		this.isVertical = isVertical;
		this.ringedPosition = ringedPosition;
		this.comment = comment;
	}
	
	public Document(Copy nCopies, Color isColor, Size size, Thickness thickness, boolean isTwoSides,
			Ended finishType, ImpressionPerSide impressionPerSide, boolean isVertical, boolean ringedPosition) {
		super();
		this.id=-1L;
		this.CopyPrice = nCopies;
		this.isColor = isColor;
		this.size = size;
		this.thickness = thickness;
		this.isTwoSides = isTwoSides;
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

	public Copy getnCopyPrice() {
		return CopyPrice;
	}

	public void setnCopyPrice(Copy nCopies) {
		this.CopyPrice = nCopies;
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

	public Thickness getThickness() {
		return thickness;
	}

	public void setThickness(Thickness thickness) {
		this.thickness = thickness;
	}

	public boolean isTwoSides() {
		return isTwoSides;
	}

	public void setTwoSides(boolean twoSides) {
		isTwoSides = twoSides;
	}

	public Ended getFinishType() {
		return finishType;
	}

	public void setFinishType(Ended finishType) {
		this.finishType = finishType;
	}

	public ImpressionPerSide getImpressionPerSide() {
		return impressionPerSide;
	}

	public void setImpressionPerSide(ImpressionPerSide impressionPerSide) {
		this.impressionPerSide = impressionPerSide;
	}

	public boolean isVertical() {
		return isVertical;
	}

	public void setVertical(boolean isVertical) {
		this.isVertical = isVertical;
	}

	public boolean getRingedPosition() {
		return ringedPosition;
	}

	public void setRingedPosition(boolean ringedPosition) {
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

	public Copy getCopyPrice() {
		return CopyPrice;
	}

	public void setCopyPrice(Copy copyPrice) {
		CopyPrice = copyPrice;
	}

	public int getnCopies() {
		return nCopies;
	}

	public void setnCopies(int nCopies) {
		this.nCopies = nCopies;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "Document [id=" + id + ", nCopies=" + CopyPrice + ", isColor=" + isColor + ", size=" + size
				+ ", thickness=" + thickness + ", impressionType=" + isTwoSides + ", finishType=" + finishType
				+ ", impressionPerSide=" + impressionPerSide + ", isVertical=" + isVertical + ", ringedPosition="
				+ ringedPosition + ", comment=" + comment + ", url=" + url + "]";
	}
}
