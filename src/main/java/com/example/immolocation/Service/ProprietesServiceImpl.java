package com.example.immolocation.Service;


import com.example.immolocation.Dao.BailleurRepository;
import com.example.immolocation.Dao.ProprietesRepository;
import com.example.immolocation.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ProprietesServiceImpl implements  IProprietesServices {

	//@Autowired
	//ILocataireServices iLocataireServices;

	@Autowired
	private BailleurRepository bailleurRepository;

	@Autowired
	private ProprietesRepository proprietesRepository;


	public Bailleur retourneBailleur(Proprietes propriete){
		Bailleur bailleur= propriete.getBailleur();
		return bailleur;
	}



	/*
	cette methode retourne la liste des proprietés
	du bailleur dont on lui passe en paramètre.elle
	permet donc d'afficher toute les proprietes
	libres d'un bailleur
	 */
	public List<Proprietes> proprieteLibreParBailleur(Bailleur bailleur){
		List<Proprietes> proprieteList= listProprieteparBailleur(bailleur);
		List<Proprietes> listPropriete=new ArrayList<>();
		for(int i=0;i<proprieteList.size();i++){
			if(proprieteList.get(i).isDisponible()==true){
				listPropriete.add(proprieteList.get(i));
			}
		}
		return listPropriete;
	}


	/*
	cette methode retourne la liste des proprietés
	sans tenir compte du bailleur qui possède cette
	proprieté.elle permet donc d'afficher toute les proprietes
	libres présentes dans la base de donnée et pourrait etre
	utilile pour la publication des proprietés libres en ligne
	 */
	@Override
	public List<Proprietes> findAllFreePropriete() {
		List<Proprietes> proprieteList=proprietesRepository.findAll();
		List<Proprietes> listPropriete=new ArrayList<>();
		for(int i=0;i<proprieteList.size();i++){
			if(proprieteList.get(i).isDisponible()==true){
				listPropriete.add(proprieteList.get(i));
			}
		}
		return listPropriete;
	}


	@Override
	public void ajouterProprieter(Proprietes propriete,Bailleur bailleur) {
		propriete.setBailleur(bailleur);
		proprietesRepository.save(propriete);
	}

	//********************la suppression d'une propriete occupé entraine la suppression de son locataire******************************
	public void supprimerPropriete(Long id) {
		Proprietes propriete=proprietesRepository.findById(id).get();
		if(propriete.isDisponible()== true){

			proprietesRepository.delete(propriete );
		}
		else{

			//Locataire locataire=iLocataireServices.rechercherParPropriete(propriete);

			//iLocataireServices.deleteLocatire(locataire);
			proprietesRepository.delete(propriete);
		}
	}


	public void modifierPropriete(Long id,Proprietes propriete) {
		Proprietes propriete1=proprietesRepository.findById(id).get();
		propriete1.setName(propriete.getName());
		propriete1.setDescription(propriete.getDescription());
		propriete1.setLocalisation(propriete.getLocalisation());
		//propriete1.setVille(propriete.getVille());
		propriete1.setDisponible(propriete.isDisponible());
		//propriete1.setDate(propriete.getDate());
		propriete1.setPrix((int) propriete.getPrix());
		proprietesRepository.save(propriete1);
	}




	public Proprietes consulterPropriete(Long id) {
		Proprietes propriete=proprietesRepository.findById(id).get();
		if(id<=0) throw new RuntimeException("Il n'existe pas de propriete avec cet identifiant");
		return propriete;
	}

	/*@Override
	public List<Proprietes> findByRegion(String Region) {
		List<Proprietes> proprieteList = proprietesRepository.finfAllByRegion(Region);
		return proprieteList;
	}*/

	@Override
	public Proprietes setDisponibilite(String etat,Proprietes propriete) {
		if(etat.equals("LA PROPRIETE N'EST PAS OCCUPEE PAR UN LOCATAIRE")){
			propriete.setDisponible(true);
		}
		else if(etat.equals("LA PROPRIETE EST OCCUPEE PAR UN LOCATAIRE")){
			propriete.setDisponible(false);
		}
		return propriete;
	}

	@Override
	public List<Proprietes> findByLocalisation(String localisation) {
		List<Proprietes> proprieteList = proprietesRepository.finfAllByLocalisation(localisation);
		return proprieteList;
	}

	@Override
	public List<Proprietes> findByPrix(Long prixiInf, Long prixSup) {
		List<Proprietes> proprieteList = proprietesRepository.finfAllByPrix(prixiInf,prixSup);
		return proprieteList;
	}

	@Override
	public List<Proprietes> listProprieteparBailleur(Bailleur bailleur) {
		List<Proprietes> proprieteList = proprietesRepository.findAllByBailleur(bailleur);
		return proprieteList;
	}

	@Override
	public Proprietes findByName(String name,Bailleur bailleur) {
		Proprietes propriete=new Proprietes();
		List<Proprietes> proprietes=proprieteLibreParBailleur(bailleur);
		for (int i=0; i<proprietes.size();i++){
			if (proprietes.get(i).getName().equals(name)){
				propriete=proprietes.get(i);
			}
			else {
				return propriete;
			}
		}

		   return proprietesRepository.findByName(name);
	}
	//////////////////////::::::encours de traitement!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!


	@Override
	public Optional<Proprietes> consulterProp(Long id) {
		return proprietesRepository.findById(id);
	}



	public void savePropriete(Proprietes propriete) {
		proprietesRepository.save(propriete);
	}

	/*
	cette methode retourne la liste de toute les proprietés
	présentes en base de donnée,occupé comme libre,tous deux
	confondu
	 */

	public List<Proprietes> getAllActivePropriete() {
		return proprietesRepository.findAll();
	}




	public Optional<Proprietes> getProprieteById(Long id) {
		return proprietesRepository.findById(id);
	}

	@Override
	public void supprimerProprieteOccupe(Long id) {
		Proprietes proprietes=consulterPropriete(id);

	}


}

