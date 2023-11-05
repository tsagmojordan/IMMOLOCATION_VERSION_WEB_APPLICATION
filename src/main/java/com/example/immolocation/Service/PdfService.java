package com.example.immolocation.Service;


import com.example.immolocation.Dao.BailleurRepository;
import com.example.immolocation.Dao.FactureRepository;
import com.example.immolocation.Dao.LocataireRepository;
import com.example.immolocation.Model.Facture;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Phaser;

@Service
@AllArgsConstructor
public class PdfService {
    @Autowired
LocataireRepository locataireRepository;
    @Autowired
FactureRepository factureRepository;
    @Autowired
BailleurRepository bailleurRepository;
    @Autowired
    ServiceFacture serviceFacture;




        public void facture_pdf (HttpServletResponse response1,String login) throws IOException {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, response1.getOutputStream());
            document.open();

            Font font =FontFactory.getFont(FontFactory.TIMES_BOLD,37, Color.BLUE);
                                        Paragraph titreloyer = new Paragraph("FACTURE ",font);
                                        titreloyer.setAlignment(Element.ALIGN_CENTER);

            Paragraph motif= new Paragraph("Motif: LOYER DU LOCATAIRE");
            motif.setAlignment(Element.ALIGN_CENTER);



                                                PdfPTable table = new PdfPTable(2);
            PdfPCell cell1 = new PdfPCell(new Phrase("Bailleur:"));
                                                                                PdfPCell cell2 = new PdfPCell(new Paragraph("Locataire:"));
            PdfPCell cell11 = new PdfPCell(new Phrase(locataireRepository.nom_Bailleur(login)));
                                                                                PdfPCell cell12 = new PdfPCell(new Paragraph(locataireRepository.non_locataire(login)));
            PdfPCell cell21 = new PdfPCell(new Phrase(locataireRepository.prenom_Bailleur(login)));
                                                                                 PdfPCell cell22= new PdfPCell(new Paragraph(locataireRepository.prefession_locataire(login)));
            PdfPCell cell31 = new PdfPCell(new Phrase(locataireRepository.contact_Bailleur(login)));
                                                                                    PdfPCell cell32 = new PdfPCell(new Paragraph(locataireRepository.contact_locataire(login)));


                                            Paragraph espace1= new Paragraph(" ");
                                            espace1.setAlignment(Element.ALIGN_CENTER);
                                            Paragraph espace2= new Paragraph(" ");
                                            espace2.setAlignment(Element.ALIGN_CENTER);

                                            Paragraph propriete_loue= new Paragraph("PROPRIETE LOUE: "+locataireRepository.description_propriete(login));
                                            propriete_loue.setAlignment(Element.ALIGN_CENTER);

                                            Paragraph lieu_de_la_propriété= new Paragraph("Localisation: "+locataireRepository.localisation_propriete(login));
                                            lieu_de_la_propriété.setAlignment(Element.ALIGN_CENTER);




             Paragraph etat_de_votre_paiment_= new Paragraph("Etat de votre paiment ");
            etat_de_votre_paiment_.setAlignment(Element.ALIGN_CENTER);

            PdfPTable table2 = new PdfPTable(4);

            PdfPCell cellule1 = new PdfPCell(new Phrase("Montant mensuel à payer"));
            PdfPCell cellule2 = new PdfPCell(new Phrase("Dernier montant enregistré"));
            PdfPCell cellule3 = new PdfPCell(new Phrase("Dette Enregistrée"));
            PdfPCell cellule4 = new PdfPCell(new Phrase("Avance du prochain paiment Enregistrée "));

            PdfPCell cellule5 = new PdfPCell(new Phrase(locataireRepository.montantMensuel(login)));
            PdfPCell cellule6 = new PdfPCell(new Phrase(factureRepository.dernier_montant_enregistre(login).get(0)));
            PdfPCell cellule7 = new PdfPCell(new Phrase(factureRepository.dernier_dette_enregistre(login).get(0)));
            PdfPCell cellule8 = new PdfPCell(new Phrase(factureRepository.dernier_surplus_enregistre(login).get(0)));


            Paragraph moyen = new Paragraph("Moyen de paiment:CASH");
            moyen.setAlignment(Element.ALIGN_LEFT);
            Paragraph monnaie = new Paragraph("Monnaie: Francs CFA");
            monnaie.setAlignment(Element.ALIGN_LEFT);

            PdfPTable table3 = new PdfPTable(3);
            PdfPCell fait = new PdfPCell(new Phrase("Fait le "));
            PdfPCell date = new PdfPCell(new Phrase(factureRepository.dernier_facture_loc(login).get(0).getDate_Facturation()));
            PdfPCell lieu = new PdfPCell(new Phrase("à_________"));


            Paragraph signature= new Paragraph(" Signature du bailleur ");
            signature.setAlignment(Element.ALIGN_CENTER);

            Paragraph signature2= new Paragraph(locataireRepository.nom_Bailleur(login));
            signature2.setAlignment(Element.ALIGN_CENTER);

            cell1.setBorder(Rectangle.NO_BORDER);
            cell2.setBorder(Rectangle.NO_BORDER);

            cell11.setBorder(Rectangle.NO_BORDER);
            cell12.setBorder(Rectangle.NO_BORDER);

            cell21.setBorder(Rectangle.NO_BORDER);
            cell22.setBorder(Rectangle.NO_BORDER);
            cell31.setBorder(Rectangle.NO_BORDER);
            cell32.setBorder(Rectangle.NO_BORDER);
            fait.setBorder(Rectangle.NO_BORDER);
            date.setBorder(Rectangle.NO_BORDER);
            lieu.setBorder(Rectangle.NO_BORDER);

            lieu.setHorizontalAlignment(Element.ALIGN_RIGHT);

            cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell12.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell22.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell32.setHorizontalAlignment(Element.ALIGN_RIGHT);

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell11);
            table.addCell(cell12);
            table.addCell(cell21);
            table.addCell(cell22);
            table.addCell(cell31);
            table.addCell(cell32);

            table2.addCell(cellule1);
            table2.addCell(cellule2);
            table2.addCell(cellule3);
            table2.addCell(cellule4);
            table2.addCell(cellule5);
            table2.addCell(cellule6);
            table2.addCell(cellule7);
            table2.addCell(cellule8);

            table3.addCell(fait);
            table3.addCell(date);
            table3.addCell(lieu);






            document.add(titreloyer);
            document.add(espace1);
            document.add(espace2);

            document.add(motif);

            document.add(espace1);
            document.add(espace2);

            document.add(table);

            document.add(espace1);
            document.add(espace2);

            document.add(propriete_loue);
            document.add(lieu_de_la_propriété);

            document.add(espace1);
            document.add(espace2);

            document.add(etat_de_votre_paiment_);
            document.add(espace1);
            document.add(espace2);

            document.add(table2);

            document.add(espace2);
            document.add(espace2);
            document.add(moyen);
            document.add(monnaie);
            document.add(espace1);

            document.add(espace1);
            document.add(espace1);
            document.add(espace2);
            document.add(table3);

            document.add(espace1);

            document.add(espace1);
            document.add(espace2);

            document.add(signature);
            document.add(signature2);

            document.close();
        }


    private void corps_de_pdfContratDeBail(int id_facture) {

    }
        public void Contrat_de_baillePDF (HttpServletResponse response, String Salle) throws IOException {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();
            Paragraph para = new Paragraph("CONTRAT DE BAIL ");


           // corps_de_pdfContratDeBail(table, Salle);
            document.add(para);
          //  document.add(table);

            document.close();
        }

}
/*








            Font font1 =FontFactory.getFont(FontFactory.COURIER_OBLIQUE,15, Color.WHITE);
           Chunk infBailleur1 = new Chunk("Bailleur:                                                                                                                  Locataire:");
            infBailleur1.setFont(font1);
            infBailleur1.setBackground(Color.GRAY);
            infBailleur1.setUnderline(Color.MAGENTA, 1, 1/4, 2, 0, 1);

            Paragraph infBailleur2 = new Paragraph("                                                                                                                                                   nom_locataire");
            infBailleur2.setAlignment(Element.ALIGN_LEFT);



            Paragraph infBailleur4 = new Paragraph("contact");
            infBailleur4.setAlignment(Element.ALIGN_RIGHT);

            Paragraph infLocataire= new Paragraph(" ");
            infLocataire.setAlignment(Element.ALIGN_LEFT);


                //font.setSize(100);
         //   titreloyer.setFont(new Font(Font.HELVETICA, 11, Font.BOLD|Font.UNDERLINE));
*/
