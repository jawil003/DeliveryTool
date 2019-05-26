package Model.Kasse;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.util.Matrix;
import org.jetbrains.annotations.NotNull;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.List;

public class BonCreator {

    private Kassenverwaltung verw;
    private PDDocument doc;
    private ObservableList<PDPage> pages;
    private PDPageContentStream cont;

    /**
     * @param verw
     * @param gesamterPreis
     * @throws IOException
     */
    public BonCreator(Kassenverwaltung verw, double gesamterPreis) throws IOException {


        this.verw = verw;

        doc = new PDDocument();
        pages = FXCollections.observableArrayList();

        pages.addListener(new ListChangeListener<PDPage>() {
            @Override
            public void onChanged(Change<? extends PDPage> c) {
                while (c.next()) {
                    final List<? extends PDPage> addedSubList = c.getAddedSubList();
                    for (PDPage e : addedSubList) {
                        doc.addPage(e);
                    }
                }
            }
        });

        PDPage page = new PDPage();
        pages.add(page);
        cont = new PDPageContentStream(doc, page);
        cont.beginText();
        //addCenteredText("Rechnung", PDType1Font.TIMES_BOLD, 16, cont, page, new Point2D.Float());
        cont.setFont(PDType1Font.TIMES_ROMAN, 12);
        cont.setLeading(14.5f);
        cont.setTextMatrix(Matrix.getTranslateInstance(25, 700));
    }

    /**
     * Adding a List of Pizza Entries to the current PDF Page
     *
     * @param pizzen
     * @throws IOException
     */
    public void addPizzas(List<BestelltePizza> pizzen) throws IOException {
        for (BestelltePizza e : pizzen) {
            cont.showText(e.toString());
            cont.newLine();
        }
    }

    /**
     * Need to be called when no action of the BoxCreator is needed anymore
     *
     * @throws IOException
     */
    public void close() throws IOException {
        cont.endText();
        cont.close();
        doc.save("Rechnung.pdf");
        doc.close();
    }

    /**
     * Adding text centered on a page (Cover like style)
     *
     * @param text
     * @param font
     * @param fontSize
     * @param content
     * @param page
     * @param offset
     * @throws IOException
     */
    private void addCenteredText(
            @NotNull String text,
            @NotNull PDFont font,
            int fontSize,
            @NotNull PDPageContentStream content,
            @NotNull PDPage page,
            @NotNull Point2D.Float offset)
            throws IOException {
        content.setFont(font, fontSize);
        //content.beginText();

        // Rotate the text according to the page orientation
        boolean pageIsLandscape = isLandscape(page);
        Point2D.Float pageCenter = getCenter(page);

        // We use the text's width to place it at the center of the page
        float stringWidth = getStringWidth(text, font, fontSize);
        if (pageIsLandscape) {
            float textX = pageCenter.x - stringWidth / 2F + offset.x;
            float textY = pageCenter.y - offset.y;
            // Swap X and Y due to the rotation
            content.setTextMatrix(Matrix.getRotateInstance(Math.PI / 2, textY, textX));
        } else {
            float textX = pageCenter.x - stringWidth / 2F + offset.x;
            float textY = pageCenter.y + offset.y;
            content.setTextMatrix(Matrix.getTranslateInstance(textX, textY));
        }

        content.showText(text);
    }

    /**
     * @param page
     * @return true, if PdPage is in landscape mode, else false
     */
    private boolean isLandscape(@NotNull PDPage page) {
        int rotation = page.getRotation();
        final boolean isLandscape;
        if (rotation == 90 || rotation == 270) {
            isLandscape = true;
        } else if (rotation == 0 || rotation == 360 || rotation == 180) {
            isLandscape = false;
        } else {
      /*LOG.warn(
          "Can only handle pages that are rotated in 90 degree steps. This page is rotated {} degrees. Will treat the page as in portrait format",
          rotation);*/
            isLandscape = false;
        }
        return isLandscape;
    }

    @NotNull
    Point2D.Float getCenter(@NotNull PDPage page) {
        PDRectangle pageSize = page.getMediaBox();
        boolean rotated = isLandscape(page);
        float pageWidth = rotated ? pageSize.getHeight() : pageSize.getWidth();
        float pageHeight = rotated ? pageSize.getWidth() : pageSize.getHeight();

        return new Point2D.Float(pageWidth / 2F, pageHeight / 2F);
    }

    float getStringWidth(@NotNull String text, @NotNull PDFont font, int fontSize) throws IOException {
        return font.getStringWidth(text) * fontSize / 1000F;
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
