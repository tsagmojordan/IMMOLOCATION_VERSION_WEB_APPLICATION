package com.example.immolocation.Service;

import com.example.immolocation.Dao.BailleurRepository;
import com.example.immolocation.Dao.FactureRepository;
import com.example.immolocation.Dao.LocataireRepository;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;

@Service
public class pdfServicegeneral {

    @Autowired
    LocataireRepository locataireRepository;
    @Autowired
    FactureRepository factureRepository;
    @Autowired
    BailleurRepository bailleurRepository;
    @Autowired
    ServiceFacture serviceFacture;




    public void facture_pdfGeneral(HttpServletResponse response1, int  id_facture) throws IOException {

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response1.getOutputStream());
        document.open();

        Font font = FontFactory.getFont(FontFactory.TIMES_BOLD,37, Color.BLUE);
        Paragraph titreloyer = new Paragraph("FACTURE ",font);
        titreloyer.setAlignment(Element.ALIGN_CENTER);

        Paragraph motif= new Paragraph("Motif: LOYER DU LOCATAIRE");
        motif.setAlignment(Element.ALIGN_CENTER);


       String login = locataireRepository.recupererloginLocataire(id_facture);
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

        PdfPTable table2 = new PdfPTable(5);

        PdfPCell cellule1 = new PdfPCell(new Phrase("Montant mensuel à payer"));
        PdfPCell cellule2 = new PdfPCell(new Phrase("Dernier montant enregistré"));
        PdfPCell cellule3 = new PdfPCell(new Phrase("Avance de votre dernier montant_mensuel non complètement payé"));
        PdfPCell cellule4 = new PdfPCell(new Phrase("Dette Enregistrée"));
        PdfPCell cellule5 = new PdfPCell(new Phrase("Avance du prochain paiment Enregistrée "));
     String montant= String.valueOf(factureRepository.montant_enregistre_fac(id_facture));
     String avance = String.valueOf(factureRepository.avance_enregistre_fac(id_facture));
     String dette= String.valueOf(factureRepository.dette_enregistre_fac(id_facture));
     String surplus = String.valueOf(factureRepository.surplus_enregistre_fac(id_facture));

        System.out.println(montant);
        System.out.println(avance);
        System.out.println(dette);
        System.out.println(surplus);
        PdfPCell cellule6 = new PdfPCell(new Phrase(locataireRepository.montantMensuel(login)));
        PdfPCell cellule7 = new PdfPCell(new Phrase(montant));
        PdfPCell cellule8 = new PdfPCell(new Phrase(avance));
        PdfPCell cellule9 = new PdfPCell(new Phrase(dette));
        PdfPCell cellule10 = new PdfPCell(new Phrase(surplus));


        Paragraph moyen = new Paragraph("Moyen de paiment:CASH");
        moyen.setAlignment(Element.ALIGN_LEFT);
        Paragraph monnaie = new Paragraph("Monnaie: Francs CFA");
        monnaie.setAlignment(Element.ALIGN_LEFT);

        PdfPTable table3 = new PdfPTable(3);
        PdfPCell fait = new PdfPCell(new Phrase("Fait le "));
        PdfPCell date = new PdfPCell(new Phrase(factureRepository.date_enregistre_fac(id_facture)));
        PdfPCell lieu = new PdfPCell(new Phrase("à_________"));


        Paragraph signature= new Paragraph(" Signature du bailleur ");
        signature.setAlignment(Element.ALIGN_CENTER);

        Paragraph signature2= new Paragraph(locataireRepository.nom_Bailleur(login));
        signature2.setAlignment(Element.ALIGN_CENTER);

        cell1.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
        cell2.setBorder(com.lowagie.text.Rectangle.NO_BORDER);

        cell11.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
        cell12.setBorder(com.lowagie.text.Rectangle.NO_BORDER);

        cell21.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
        cell22.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
        cell31.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
        cell32.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
        fait.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
        date.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
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
        table2.addCell(cellule9);
        table2.addCell(cellule10);

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
}
