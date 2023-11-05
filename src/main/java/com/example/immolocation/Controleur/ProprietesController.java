package com.example.immolocation.Controleur;



import com.example.immolocation.Model.Bailleur;
import com.example.immolocation.Model.Locataire;
import com.example.immolocation.Model.Proprietes;
import com.example.immolocation.Service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Controller
public class ProprietesController {


	private String userConnect;
	private  Bailleur bailleur;
	private Long nId;



	@Value("${uploadDir}")
	private String uploadFolder;

	@Autowired
	IBailleurServices ibailleurServices;

	@Autowired
	IProprietesServices iProprietesServices;

	@Autowired
	ILocataireServices iLocataireServices;
	private final Logger log = LoggerFactory.getLogger(this.getClass());



	@GetMapping("/public")
	public String page(Model model){
		List<Proprietes> proprietes=iProprietesServices.findAllFreePropriete();
		model.addAttribute("propriete",proprietes);

		return "publication";
	}


	@GetMapping("/GestionProprietes")
	public String pageGestionPropriete(Model model,HttpServletRequest httpServletRequest){
		HttpSession httpSession= httpServletRequest.getSession();
		SecurityContext securityContext= (SecurityContext)
				httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
		String login =securityContext.getAuthentication().getName();
		this.bailleur=ibailleurServices.rechercherBailleur(login);//recuperation du bailleur connecté
		System.out.println(bailleur.getLogin());
		List<Proprietes> propriete=iProprietesServices.listProprieteparBailleur(this.bailleur);
		//List<Proprietes> propriete = iProprietesServices.proprieteLibreParBailleur(this.bailleur);
		model.addAttribute("propriete", propriete);
		model.addAttribute("bailleur",this.bailleur);
		model.addAttribute("propriete", propriete);
		return "propriete/GestionProprietes";
	}

	/*
	renvoie le formulaire pour l'ajout d'une propriete
	 */
	@GetMapping("/AjouterPropriete")
	public String formulairePropriete(Model model){
		model.addAttribute("bailleur",bailleur);
		model.addAttribute("propriete",new Proprietes());
		return "propriete/AjouterPropriete";
	}

	/*
	cette methode permet de sauvegarder une
	image dans la base de donnée en associant
	image et info image de la propriete
	 */
	@PostMapping("/image/saveImageDetails")
	public @ResponseBody ResponseEntity<?> createProduct(@RequestParam("name") String name,
														 @RequestParam("prix") int prix, @RequestParam("description") String description, @RequestParam("localisation") String localisation, Model model, HttpServletRequest request
			,final @RequestParam("image") MultipartFile file) {
		try {
			//String uploadDirectory = System.getProperty("user.dir") + uploadFolder;
			String uploadDirectory = request.getServletContext().getRealPath(uploadFolder);
			log.info("uploadDirectory:: " + uploadDirectory);
			String fileName = file.getOriginalFilename();
			String filePath = Paths.get(uploadDirectory, fileName).toString();
			log.info("FileName: " + file.getOriginalFilename());
			if (fileName == null || fileName.contains("..")) {
				model.addAttribute("invalid", "Sorry! Filename contains invalid path sequence \" + fileName");
				return new ResponseEntity<>("Sorry! Filename contains invalid path sequence " + fileName, HttpStatus.BAD_REQUEST);
			}
			String[] names = name.split(",");
			String[] descriptions = description.split(",");
			Date createDate = new Date();
			log.info("Name: " + names[0]+" "+filePath);
			log.info("description: " + descriptions[0]);
			log.info("prix: " + prix);
			log.info("localisation: " +localisation);
			try {
				File dir = new File(uploadDirectory);
				if (!dir.exists()) {
					log.info("Folder Created");
					dir.mkdirs();
				}
				// Save the file locally
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
				stream.write(file.getBytes());
				stream.close();
			} catch (Exception e) {
				log.info("in catch");
				e.printStackTrace();
			}
			byte[] imageData = file.getBytes();
			Proprietes propriete = new Proprietes();
			propriete.setName(names[0]);
			propriete.setImage(imageData);
			propriete.setPrix(prix);
			propriete.setDescription(descriptions[0]);
			propriete.setCreateDate(createDate); propriete.setLocalisation(localisation);
			iProprietesServices.ajouterProprieter(propriete,this.bailleur);
			log.info("HttpStatus===" + new ResponseEntity<>(HttpStatus.OK));
			return new ResponseEntity<>("Product Saved With File - " + fileName, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Exception: " + e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}


	/*
	cette methode permet d'afficher l'image
	d'une propriete dont on lui passe l'id
	sur la vue
	 */
	@GetMapping("/image/display/{id}")
	@ResponseBody
	void showImage(@PathVariable("id") Long id, HttpServletResponse response, Optional<Proprietes> propriete)
			throws ServletException, IOException {
		log.info("Id :: " + id);
		propriete = iProprietesServices.getProprieteById(id);
		response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
		response.getOutputStream().write(propriete.get().getImage());
		response.getOutputStream().close();
	}

	/*
	cette methode permet d'agrandir l'image
	d'une propriete en presentant tout les caracteristiques
	qui lui sont associées
	 */

	@GetMapping("/image/imageDetails")
	String showProductDetails(@RequestParam("id") Long id, Optional<Proprietes> propriete, Model model) {
		try {
			log.info("Id :: " + id);
			if (id != 0) {
				propriete = iProprietesServices.getProprieteById(id);
			
				log.info("products :: " + propriete);
				if (propriete.isPresent()) {
					model.addAttribute("id", propriete.get().getId());
					model.addAttribute("description", propriete.get().getDescription());
					model.addAttribute("name", propriete.get().getName());
					model.addAttribute("localisation", propriete.get().getLocalisation());
					model.addAttribute("prix", propriete.get().getPrix());
					System.out.println(this.bailleur.getNom_bailleur());
					return "imagedetails";
				}
				return "redirect:/GestionProprietes";
			}
		return "redirect:/GestionProprietes";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/GestionProprietes";
		}	
	}

	/*
    apres l'envoie du formulaire d'ajout si la propriete est occupé par un locataire,
    on demande au bailleur de renseigner les info du
     locataire concerné dans le cas contraire on lui
     retourne la page de gestion de propriete
      */
	@PostMapping("/SavePropriete")
	public String Save(Model model,Proprietes propriete,@RequestParam("etat") String etat){
		model.addAttribute("propriete",new Proprietes());
		propriete=iProprietesServices.setDisponibilite(etat,propriete);
		iProprietesServices.ajouterProprieter(propriete,this.bailleur);
		return "redirect:/GestionProprietes";
	}


	/*
    renvoie le dashbord de gestion de propriete,
     locataire et de facturation
     */
	@RequestMapping("/delete" )
	public String delete(Long id){
		Proprietes proprietes =iProprietesServices.consulterPropriete(id);//recherche de la proprieté a supprimer
		if(proprietes.isDisponible()==true){//virification si elle n'est pas occupée par un locataire on la supprime
			iProprietesServices.supprimerPropriete(id);
		}
		else{
			Locataire locataire=iLocataireServices.rechercherParPropriete(proprietes);//recherche du locataire qui ocuupe la proprieté
			List<Proprietes> proprietesListDuLocataire=locataire.getPropriete();//recupération de la liste de toutes les propriétés qu'il loue
			if(proprietesListDuLocataire.size()==1){//si il s'agit de la seul propriété qu'il loue alors on supprime la propriété et on le supprime en tant que locataire
				proprietesListDuLocataire.remove(proprietesListDuLocataire.get(0));
				iProprietesServices.supprimerPropriete(id);
				iLocataireServices.deleteLocatire(locataire);
			}
			else{
				for(int i=0;i<proprietesListDuLocataire.size();i++){//si il loue plusieurs propriétés
					if (proprietesListDuLocataire.get(i).equals(proprietes)) {//on recherche la propriété a supprimer parmis ces propriétés
						proprietesListDuLocataire.remove(proprietesListDuLocataire.get(i));//on la retire de la liste de ses propriétés
						locataire.setPropriete(proprietesListDuLocataire);
						iLocataireServices.modifierLocataire(locataire);//on enregistre l'operation faite sur les propriétées du locataire
						iProprietesServices.supprimerPropriete(id);//on supprime la propriété
					}
					else{
						System.out.println("erreur sur la suppression de la proprieté"+ proprietes.toString());
					}
				}
			}

		}

		return "redirect:/GestionProprietes";
	}

	/*
    permet de mettre a jour une propriete dont on lui passe l'id
    en parametre
     */
	@GetMapping("/update")
	public String Pageupdate( Long id,Model model){
		this.nId=id;
		Proprietes propriete=iProprietesServices.consulterPropriete(id);
		model.addAttribute("bailleur",this.bailleur);
		model.addAttribute("propriete",propriete);
		return "Bailleur/ModifierPropriete";

	}
	/*
	permet de sauvegarder les info de mise
	a jour d'une propriete
	 */
	@PostMapping("/SaveUpdate")
	public String upadate(Model model,Proprietes propriete){
		model.addAttribute("propriete",new Proprietes());
		iProprietesServices.modifierPropriete(this.nId,propriete);
		return"redirect:/GestionPropriete";
	}
	@GetMapping("/annuler")
	public String annuler(){
		return "redirect:/GestionPropriete";
	}

	@GetMapping("/proprieteLibre")
	public String proprieteLibre(Model model){
		List<Proprietes>proprieteList= iProprietesServices.proprieteLibreParBailleur(this.bailleur);
		model.addAttribute("bailleur",this.bailleur);
		model.addAttribute("propriete",proprieteList);
		return "propriete/GestionProprietes";
	}


}	

