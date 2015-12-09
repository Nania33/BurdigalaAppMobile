package com.enseirb.pfa.bastats.pdf;


import android.content.Context;

import com.enseirb.pfa.bastats.data.DAO.DBJoueurDAO;
import com.enseirb.pfa.bastats.data.DAO.DBMatchDAO;
import com.enseirb.pfa.bastats.data.DAO.DBStatDAO;
import com.enseirb.pfa.bastats.data.model.Joueur;
import com.enseirb.pfa.bastats.data.model.Match;
import com.enseirb.pfa.bastats.data.model.Stat;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;


public class PdfCreator {

    private static final String IMAGE = "C:\\Users\\Charlie Brown\\Desktop\\bastats.png";

    /**
     * Creates a PDF with five tables.
     * @param    filename the name of the PDF file that will be created.
     * @throws    DocumentException
     * @throws    IOException
     */
    public void createPdf(String filename, String team1, String team2, int matchId, String arbitre1, String arbitre2, int score1,int score2,Context mCtx)
            throws IOException, DocumentException {

        // step 1
        Document document = new Document();
        // step 2
        PdfWriter.getInstance(document, new FileOutputStream(filename));
        // step 3
        document.open();
        // step 4
        addHeaderTable(document, team1, team2,score1,score2);

        DBMatchDAO tableMatch = new DBMatchDAO(mCtx);
        Match match = new Match(tableMatch.getWithId(matchId));
        int formationA = match.getFormationEquipeA();
        int formationB = match.getFormationEquipeB();
        //step 5
        PdfPTable table = createTable1(team1, matchId, formationA, mCtx);
        table.setSpacingBefore(150);
        table.setSpacingAfter(50);
        document.add(table);

        PdfPTable table1 = createTable1(team2, matchId, formationB, mCtx);
        table1.setSpacingBefore(50);
        table1.setSpacingAfter(200);
        document.add(table1);
        //step 8
        createInformation(document, arbitre1, arbitre2);
        // step 9
        document.close();
    }

    /**
     * put the logo
     * @throws BadElementException
     * @throws IOException
     * @throws DocumentException
     */
    public PdfPCell addImage()
            throws BadElementException, IOException, DocumentException {


        Image img = Image.getInstance(BrowserFile.path+"/"+"bastats.png");

        //img.setAbsolutePosition(40f, 700f);
        img.scaleAbsolute(50f, 50f);


        PdfPCell pdfPCell = new PdfPCell(img);
        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPCell.setBackgroundColor(new BaseColor(231, 115, 40));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        return pdfPCell;

    }

    /**
     * create the footer
     * @param document
     * @param arbitre1
     * @param arbitre2
     * @throws DocumentException
     */
    public void createInformation(Document document, String arbitre1, String arbitre2 )
            throws DocumentException {

        Paragraph p = new Paragraph();
        //p.setFont(FilmFonts.NORMAL);

        p.add(new Phrase("Arbitre 1 : " + arbitre1));
        // p.add(PojoToElementFactory.getOriginalTitlePhrase(movie));
        p.add(" ");
        p.add(new Phrase("Abitre 2 : "+arbitre2));

        document.add(p);
    }


    /**
     * Put the Header
     * @param document
     * @param team1
     * @param team2
     * @return
     * @throws DocumentException
     */
    public float addHeaderTable(Document document, String team1, String team2,int score1,int score2)
            throws DocumentException, IOException {

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(" dd/MM/yyyy HH:mm");

        PdfPTable header = new PdfPTable(3);
        header.setWidthPercentage(100);
        header.getDefaultCell().setBackgroundColor(BaseColor.BLACK);
        Font font = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);


        PdfPCell p = new PdfPCell(new Phrase(team1+" "+String.valueOf(score1)+" - " + String.valueOf(score2)+" "+team2, font));
        p.setColspan(3);
        p.setBorder(Rectangle.NO_BORDER);
        p.setHorizontalAlignment(Element.ALIGN_CENTER);
        p.setBackgroundColor(new BaseColor(231, 115, 40));
        header.addCell(p);

        p.setPhrase(new Phrase(sdf.format(date), font));
        p.setColspan(1);
        p.setBorder(Rectangle.NO_BORDER);
        p.setHorizontalAlignment(Element.ALIGN_CENTER);
        p.setVerticalAlignment(Element.ALIGN_CENTER);
        //p.setBackgroundColor(BaseColor.BLACK);
        header.addCell(p);

        /*p = new PdfPCell(this.addImage());
        p.setVerticalAlignment(Element.ALIGN_CENTER);
        header.addCell(p);*/

        p.setPhrase(new Phrase(String.format("page %d", 1), font));
        p.setBorder(Rectangle.NO_BORDER);
        p.setVerticalAlignment(Element.ALIGN_CENTER);
        p.setHorizontalAlignment(Element.ALIGN_CENTER);
        header.addCell(p);
        document.add(header);
        return header.getTotalHeight();
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    /**
     * Create the table with Team's stats
     * @param TeamName
     * @return
     * @throws DocumentException
     */
    public static PdfPTable createTable1(String TeamName, int matchId, int formationId, Context mCtx)
            throws DocumentException {

        PdfPTable table = new PdfPTable(new float[] { 2, 8, 3, 3, 3, 3, 2, 2, 2,  3, 2, 2, 2, 2, 2, 2, 3 });
        table.setWidthPercentage(110f);

        //table.setWidthPercentage(100);
        // t.setBorderColor(BaseColor.GRAY);
        // t.setPadding(4);
        // t.setSpacing(4);
        // t.setBorderWidth(1);

        table.getDefaultCell().setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.getDefaultCell().setUseAscender(true);
        table.getDefaultCell().setUseDescender(true);

        table.addCell("");
        //String team1 = formation1.getName();

        table.addCell(TeamName);
        for(int i = 0; i <15; ++i){
            table.addCell("");
        }

        // entete du tableau
        table.addCell("No");
        table.addCell("Player");
        table.addCell("MIN");
        table.addCell("FG");
        //table.addCell("FGA");
        table.addCell("3P");
        //table.addCell("3PA");
        table.addCell("Ft");
        table.addCell("+/-");
        table.addCell("Off");
        table.addCell("Def");
        table.addCell("Reb");
        table.addCell("A");
        table.addCell("To");
        table.addCell("Stl");
        table.addCell("Bs"); //Blocked shots
        table.addCell("Ba"); //Blocked against
        table.addCell("PF"); //Personnal fouls
        table.addCell("Pts");

        table.getDefaultCell().setBackgroundColor(null);


            //striped colored


            DBJoueurDAO joueurDAO = new DBJoueurDAO(mCtx);
            List<Joueur> joueur = new ArrayList<>(joueurDAO.getFormationMatch(formationId));
            //String stat = "stat";
            ListIterator<Joueur> joueursIt = joueur.listIterator();

        int i = 0;
            //remmplissage en ligne
            while(joueursIt.hasNext()){
                //ecritue des cases

                if(i%2 != 0)
                    table.getDefaultCell().setBackgroundColor(BaseColor.LIGHT_GRAY);
                else
                    table.getDefaultCell().setBackgroundColor(null);

                Joueur player = joueursIt.next();

                table.addCell(String.valueOf(player.getNumero()));
                table.addCell(String.valueOf(player.getNom()));

                DBStatDAO statDAO = new DBStatDAO(mCtx);
                Stat stat = statDAO.getStatFromJoueur(player, matchId);

                //table.addCell("No");
                //table.addCell("Player");
                table.addCell("00:00");

                String FG = stat.getNbShoot2S()+"-"+stat.getNbShoot2F();
                table.addCell(FG);
                //table.addCell("FGA");

                String threeP = stat.getNbShoot3S()+"-"+stat.getNbShoot3F();
                table.addCell(threeP);
                //table.addCell("3PA");

                String Ft = stat.getNbLfS()+"-"+stat.getNbLfF();
                table.addCell(Ft);

                //Plus minus
                table.addCell(String.valueOf(stat.calculerEvaluation()));


                String Off = String.valueOf(stat.getNbRebondOff());
                table.addCell(Off);

                String Def = String.valueOf(stat.getNbRebondDef());
                table.addCell(Def);

                String Reb = String.valueOf(stat.getNbRebondDef() + stat.getNbRebondOff());
                table.addCell(Reb);

                String A = String.valueOf(stat.getNbPasseDecisive());
                table.addCell(A);

                String To = String.valueOf(stat.getNbPerteBalle());
                table.addCell(To);

                String Stl = String.valueOf(stat.getNbInterception());
                table.addCell(Stl);

                String Bs = String.valueOf(stat.getNbContre());
                table.addCell(Bs); //Blocked shots
                table.addCell(Bs); //Blocked against

                String Pf = String.valueOf(stat.getNbFautes());
                table.addCell(Pf); //Personnal fouls

                String Pts = (stat.getNbShoot3S()*3+stat.getNbShoot2S()*2+stat.getNbLfS())+"";
                table.addCell(Pts);

                i++;
            }

        return table;
    }
}



