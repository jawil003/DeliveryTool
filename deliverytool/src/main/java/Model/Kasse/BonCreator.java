/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Model.Kasse;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.apache.pdfbox.multipdf.Overlay;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

public class BonCreator<T> {

    private Kassenverwaltung verw;
    private PDDocument doc;
    private ObservableList<PDPage> pages;
    private PDPageContentStream cont;
    private String path;

    private int durchlaeufe = 0;
    private PDRectangle mediaBox;
    private PDFont fontÜberschrift;
    private PDFont fontNormal;
    private int fontSizeÜberschrift;
    private int fontSizeNormal;

    /**
     * @param verw
     * @param gesamterPreis
     * @throws IOException
     */
    public BonCreator(Kassenverwaltung verw, double gesamterPreis, String path) throws IOException, URISyntaxException {


        this.verw = verw;
        doc = new PDDocument();
        pages = FXCollections.observableArrayList();
        this.path = path;

        pages.addListener((ListChangeListener<PDPage>) c -> {
            while (c.next()) {
                final List<? extends PDPage> addedSubList = c.getAddedSubList();
                for (PDPage e : addedSubList) {
                    doc.addPage(e);
                }
            }
        });

        PDPage page = new PDPage();

        /**
         *@todo Set background image
         *@body Fix the setting of a background image
         */
        //setBackgroundImage(doc, getClass().getResource("Classdependencies/BonCreator/pizza-3007395_960_720.jpg").toURI().getPath());
        mediaBox = page.getMediaBox();
        pages.add(page);
        cont = new PDPageContentStream(doc, page);
        fontÜberschrift = PDType1Font.HELVETICA_BOLD;
        fontNormal = PDType1Font.TIMES_ROMAN;
        fontSizeÜberschrift = 20;
        fontSizeNormal = 12;
        cont.setFont(fontNormal, fontSizeNormal);
        cont.setLeading(14.5f);
    }

    /**
     * Adding a List of Pizza Entries to the current PDF Page
     *
     * @param datalist
     * @throws IOException
     */
    public void addPizzas(ObservableList<T> datalist, double gesamterPreis) throws IOException {
        centerText(cont, fontSizeÜberschrift, 30, pages.get(0), "Rechnung");
        centerListAsText(cont, fontSizeNormal, 30, pages.get(0), datalist);
        centerText(cont, fontSizeNormal, 30, pages.get(0), "-----------------------------------------");
        centerText(cont, fontSizeNormal, 30, pages.get(0), "Gesamt: " + String.format("%.2f", gesamterPreis) + "€");

    }


    /**
     * Need to be called when no action of the BoxCreator is needed anymore
     *
     * @throws IOException
     */
    public void close(String path) throws IOException {
        cont.endText();
        cont.close();
        doc.save(path);
        doc.close();
    }

    /**
     * Center Listentries in a PDPage in a PDDocument (each Entry in a new Line)
     *
     * @param stream
     * @param fontSize
     * @param marginTop
     * @param page
     * @param list
     * @throws IOException
     */
    private void centerListAsText(PDPageContentStream stream, int fontSize, int marginTop, PDPage page, List<T> list) throws IOException {
        PDRectangle mediaBox = page.getMediaBox();
        stream.setFont(fontNormal, fontSize);

        for (T data : list) {
            stream.beginText();
            float titleWidth = fontNormal.getStringWidth(data.toString()) / 1000 * fontSize;
            float titleHeight = fontNormal.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontSize;

            float startX = (mediaBox.getWidth() - titleWidth) / 2;
            float startY = mediaBox.getHeight() - marginTop - titleHeight;
            stream.newLineAtOffset(startX, startY - durchlaeufe * (5 + titleHeight));
            stream.showText(data.toString());
            durchlaeufe++;
            stream.endText();


        }


    }

    /**
     * Adding a centered single Line Text to the PDPage with a fontSize a margin from the top (only relevant for the first line on a page)
     * @param stream
     * @param fontSize
     * @param marginTop
     * @param page
     * @param text
     * @throws IOException
     */
    private void centerText(PDPageContentStream stream, int fontSize, int marginTop, PDPage page, String text) throws IOException {
        PDRectangle mediaBox = page.getMediaBox();
        stream.setFont(fontNormal, fontSize);


        stream.beginText();
        float titleWidth = fontNormal.getStringWidth(text) / 1000 * fontSize;
        float titleHeight = fontNormal.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontSize;

        float startX = (mediaBox.getWidth() - titleWidth) / 2;
        float startY = mediaBox.getHeight() - marginTop - titleHeight;
        stream.newLineAtOffset(startX, startY - durchlaeufe * (5 + titleHeight));
        stream.showText(text);
        durchlaeufe++;
        stream.endText();

    }

    /**
     * @param realDoc
     * @param imagePath
     */
    private void setBackgroundImage(PDDocument realDoc, String imagePath) throws IOException {
        HashMap<Integer, String> overlayGuide = new HashMap<Integer, String>();
        for (int i = 0; i < realDoc.getNumberOfPages(); i++) {
            overlayGuide.put(i + 1, imagePath);
        }

        Overlay overlay = new Overlay();
        overlay.setInputPDF(realDoc);
        overlay.setOverlayPosition(Overlay.Position.BACKGROUND);
        overlay.overlay(overlayGuide);
    }

    /**
     * @return the PDDocument which the class creates
     */
    public PDDocument getDoc() {
        return doc;
    }

    /**
     * @param doc
     */
    public void setDoc(PDDocument doc) {
        this.doc = doc;
    }

    /**
     * @return ObservableList with all pages from top to bottom (e.g get(0) is first page)
     */
    public ObservableList<PDPage> getPages() {
        return pages;
    }

    /**
     * @param pages
     */
    public void setPages(ObservableList<PDPage> pages) {
        this.pages = pages;
    }

    /**
     * @return the ContentStream used by the class
     */
    public PDPageContentStream getCont() {
        return cont;
    }

    /**
     * @param cont
     */
    public void setCont(PDPageContentStream cont) {
        this.cont = cont;
    }
}
