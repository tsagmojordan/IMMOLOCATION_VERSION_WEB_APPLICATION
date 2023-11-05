package com.example.immolocation.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class Proprietes {
/*
	private long id_propriete;
	private String name;
	private String description="non precisé";
	private String localisation="non precisé";
	private String ville ="non precisé";
	private boolean disponible=true;
	private int prix=0;
	private Date date;*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;
	private String Localisation;
	private boolean disponible=true;
	//private String ville;
	private int prix;
	//private boolean disponible;
	@Lob
    @Column(name = "Image", length = Integer.MAX_VALUE, nullable = true)
    private byte[] image;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", nullable = false)
    private Date createDate;
	@ManyToOne
	private Bailleur bailleur;

	public Proprietes() {}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Proprietes that = (Proprietes) o;
		return disponible == that.disponible && prix == that.prix && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(Localisation, that.Localisation) && Arrays.equals(image, that.image) && Objects.equals(createDate, that.createDate) && Objects.equals(bailleur, that.bailleur);
	}

	@Override
	public int hashCode() {
		int result = Objects.hash(id, name, description, Localisation, disponible, prix, createDate, bailleur);
		result = 31 * result + Arrays.hashCode(image);
		return result;
	}

	@Override
	public String toString() {
		return "Proprietes{" +
				"id=" + id +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", Localisation='" + Localisation + '\'' +
				", disponible=" + disponible +
				", prix=" + prix +
				", createDate=" + createDate +
				", bailleur=" + bailleur +
				'}';
	}
}


