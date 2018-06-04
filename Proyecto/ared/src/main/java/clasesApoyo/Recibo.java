/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasesApoyo;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import static com.itextpdf.kernel.geom.PageSize.A5;
import modelo.Cliente;
import modelo.Ingreso;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.test.annotations.WrapToTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Maestro;
import modelo.PagoAlumno;
import modelo.PagoAlumnoExterno;
import modelo.PagoMaestro;
import modelo.PagoRenta;
import modelo.Persona;
import org.dom4j.DocumentException;

/**
 *
 * @author mau
 */
public class Recibo {

  public static final String RESOURCEDIR = "src/main/resources";
  public static final String DEST = System.getProperty("user.home") + "/Desktop/";
  public static final String REGULAR = "/fonts/OpenSans-Regular.ttf";
  public static final String BOLD = "/fonts/OpenSans-Bold.ttf";
  public static final String ITALIC = "/fonts/OpenSans-Italic.ttf";
  public static final String NEWLINE = "\n";

  public static File crearReciboPagoIngreso(String ruta, String nombrearchivo,
          Ingreso ingreso, Persona cliente, Persona emisor)
          throws FileNotFoundException, DocumentException, IOException {

    PdfFont bold = PdfFontFactory.createFont(RESOURCEDIR + BOLD, true);
    PdfFont italic = PdfFontFactory.createFont(RESOURCEDIR + ITALIC, true);

    String rutaDeArchivo = ruta + nombrearchivo + ".pdf";
    File file = new File(rutaDeArchivo);
    PdfWriter writer = new PdfWriter(rutaDeArchivo);
    PdfDocument pdf = new PdfDocument(writer);
    try (Document doc = new Document(pdf, new PageSize(A5.rotate()))) {
      doc.add(new Paragraph()
              .setTextAlignment(TextAlignment.RIGHT)
              .setMultipliedLeading(1)
              .add(new Text(ingreso.toString())
                      .setFont(bold).setFontSize(13))
              .add(NEWLINE)
              .add(new Text(LocalDate.now().toString()).setFontSize(9)
              ));

      doc.add(crearTablaInvolucrados(bold, italic, cliente, emisor)
              .setBorder(Border.NO_BORDER)
              .setBorderBottom(Border.NO_BORDER)
              .setBorderLeft(Border.NO_BORDER)
      );

      doc.add(new Paragraph()
              .add(NEWLINE)
              .add(NEWLINE));

      doc.add(crearTablaConceptoIngreso(bold, italic, ingreso));

      doc.add(new Paragraph()
              .add(NEWLINE)
              .add(NEWLINE));
      doc.add(crearTablaDeFirmas("Firma de Conformidad", "Firma del Emisor"));
      agregarImagenFondo(pdf, "/imagenes/aredEspacioCompleto.png");

    }
    return file;
  }

  public static File crearReciboPagoAlumnoExterno(String ruta, String nombrearchivo,
          PagoAlumnoExterno pagoalumno, Persona cliente, Persona emisor) throws IOException {

    PdfFont bold = PdfFontFactory.createFont(RESOURCEDIR + BOLD, true);
    PdfFont italic = PdfFontFactory.createFont(RESOURCEDIR + ITALIC, true);

    String rutaDeArchivo = ruta + nombrearchivo + ".pdf";
    File file = new File(rutaDeArchivo);
    PdfWriter writer = new PdfWriter(rutaDeArchivo);
    PdfDocument pdf = new PdfDocument(writer);
    try (Document doc = new Document(pdf, new PageSize(A5.rotate()))) {
      doc.add(new Paragraph()
              .setTextAlignment(TextAlignment.RIGHT)
              .setMultipliedLeading(1)
              .add(new Text(pagoalumno.pkToString())
                      .setFont(bold).setFontSize(13))
              .add(NEWLINE)
              .add(new Text(LocalDate.now().toString()).setFontSize(9)
              ));

      doc.add(crearTablaInvolucrados(bold, italic, cliente, emisor)
              .setBorder(Border.NO_BORDER)
              .setBorderBottom(Border.NO_BORDER)
              .setBorderLeft(Border.NO_BORDER)
      );
      doc.add(new Paragraph()
              .add(NEWLINE));

      doc.add(crearTablaConceptoPagoAlumnoExterno(bold, italic, pagoalumno));

      doc.add(new Paragraph()
              .add(NEWLINE)
              .add(NEWLINE));
      doc.add(crearTablaDeFirmas("Firma de Conformidad", "Firma del Emisor"));
      agregarImagenFondo(pdf, "/imagenes/aredEspacioCompleto.png");

    }
    return file;
  }
  
    public static File crearReciboPagoAlumno(String ruta, String nombrearchivo,
          PagoAlumno pagoalumno, Persona cliente, Persona emisor) throws IOException {

    PdfFont bold = PdfFontFactory.createFont(RESOURCEDIR + BOLD, true);
    PdfFont italic = PdfFontFactory.createFont(RESOURCEDIR + ITALIC, true);

    String rutaDeArchivo = ruta + nombrearchivo + ".pdf";
    File file = new File(rutaDeArchivo);
    PdfWriter writer = new PdfWriter(rutaDeArchivo);
    PdfDocument pdf = new PdfDocument(writer);
    try (Document doc = new Document(pdf, new PageSize(A5.rotate()))) {
      doc.add(new Paragraph()
              .setTextAlignment(TextAlignment.RIGHT)
              .setMultipliedLeading(1)
              .add(new Text(pagoalumno.toString())
                      .setFont(bold).setFontSize(13))
              .add(NEWLINE)
              .add(new Text(LocalDate.now().toString()).setFontSize(9)
              ));

      doc.add(crearTablaInvolucrados(bold, italic, cliente, emisor)
              .setBorder(Border.NO_BORDER)
              .setBorderBottom(Border.NO_BORDER)
              .setBorderLeft(Border.NO_BORDER)
      );
      doc.add(new Paragraph()
              .add(NEWLINE));

      doc.add(crearTablaConceptoPagoAlumno(bold, italic, pagoalumno));

      doc.add(new Paragraph()
              .add(NEWLINE)
              .add(NEWLINE));
      doc.add(crearTablaDeFirmas("Firma de Conformidad", "Firma del Emisor"));
      agregarImagenFondo(pdf, "/imagenes/aredEspacioCompleto.png");

    }
    return file;
  }

  private static void agregarImagenFondo(
          PdfDocument pdfDoc, String recursoImagen)
          throws MalformedURLException {
    PdfCanvas canvas = new PdfCanvas(pdfDoc.getFirstPage());
    ImageData image;

    image = ImageDataFactory.create("file:" + RESOURCEDIR + recursoImagen);
    canvas.saveState();
    PdfExtGState state = new PdfExtGState();
    state.setFillOpacity(0.4f);
    canvas.setExtGState(state);
    canvas.addImage(image, 0, 0, A5.rotate().getWidth(), false);
    canvas.restoreState();
  }

  private static Table crearTablaInvolucrados(PdfFont bold, PdfFont italic, Persona cliente, Persona emisor) {
    Table table = new Table(new UnitValue[]{
      new UnitValue(UnitValue.PERCENT, 50),
      new UnitValue(UnitValue.PERCENT, 50)})
            .setWidthPercent(100);
    table.setBorder(Border.NO_BORDER);
    table.setStrokeWidth(0);
    if (cliente == null) {
      cliente = new Cliente();
      cliente.setNombre("--------");
    }
    table.addCell(new Cell().add(new Paragraph()
            .addAll(Arrays.asList(
                    new Text("Para:").setFont(bold), new Text(NEWLINE),
                    new Text(
                            cliente.getNombre() + " "
                            + cliente.getApellidos())
                            .setFont(italic), new Text(NEWLINE),
                    new Text(cliente.getEmail()), new Text(NEWLINE),
                    new Text(cliente.getTelefono())
            ))).setFontSize(11)
            .setBorder(Border.NO_BORDER));

    table.addCell(new Cell().add(new Paragraph()
            .addAll(Arrays.asList(
                    new Text("Emite:").setFont(bold), new Text(NEWLINE),
                    new Text(
                            emisor.getNombre() + " "
                            + emisor.getApellidos())
                            .setFont(italic), new Text(NEWLINE),
                    new Text(emisor.getEmail()), new Text(NEWLINE),
                    new Text(emisor.getTelefono()
                    )))).setFontSize(11)
            .setBorder(Border.NO_BORDER));
    return table;
  }

  private static Table crearTablaConceptoIngreso(PdfFont bold, PdfFont italic, Ingreso pago) {
    String concepto = "SIN CONCEPTO";
    String monto;
    if (pago.getClass().toString().equals(PagoMaestro.class.toString())) {
      LocalDate date = pago.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
      concepto = "Pago por afiliación " + date.getMonth() + "/" + date.getYear();
    }
    if (pago.getClass().toString().equals(PagoRenta.class.toString())) {
      LocalDate date = pago.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
      concepto = "Renta de espacio para el día "
              + date.getDayOfMonth() + "/" + date.getMonth() + "/" + date.getYear();
    }
    monto = "$ " + Double.toString(pago.getMonto());
    return crearTablaConcepto(bold, italic, concepto, monto);
  }

  public static Paragraph firmaConformidad(String txtFirma) {
    Paragraph p = new Paragraph("                            ");
    p.setTextAlignment(TextAlignment.CENTER);
    SolidLine line = new SolidLine();
    LineSeparator separatorline = new LineSeparator(line);
    separatorline.setWidthPercent(50);
    p.setTextAlignment(TextAlignment.CENTER);
    p.add(separatorline);
    p.add(NEWLINE);
    p.add(new Text(txtFirma).setFontSize(10));
    return p;
  }

  private static Table crearTablaDeFirmas(String txtFirma1, String txtFirma2) {
    Table table = new Table(new UnitValue[]{
      new UnitValue(UnitValue.PERCENT, 50),
      new UnitValue(UnitValue.PERCENT, 50)})
            .setWidthPercent(100);
    table.addCell(new Cell().setBorder(Border.NO_BORDER).add(firmaConformidad(txtFirma1)));
    table.addCell(new Cell().setBorder(Border.NO_BORDER).add(firmaConformidad(txtFirma2)));
    return table;
  }

  private static Table crearTablaConceptoPagoAlumnoExterno(
          PdfFont bold, PdfFont italic, PagoAlumnoExterno pagoalumno) {
    String concepto = "Pago del alumno " + pagoalumno.getAlumno().toString()
            + " para " + pagoalumno.getMaestro().toString()
            + ", realizado el "
            + pagoalumno.getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString();
    String monto = "$ " + Double.toString(pagoalumno.getMonto());
    return crearTablaConcepto(bold, italic, concepto, monto);
  }
  
    private static Table crearTablaConceptoPagoAlumno(
          PdfFont bold, PdfFont italic, PagoAlumno pagoalumno) {
    String concepto = "Pago del alumno " + pagoalumno.getAlumno().toString()
            + " inscrito en el grupo " + pagoalumno.getGrupo().getNombre()
            + ", realizado el "
            + pagoalumno.getFechaPago().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString();
    String monto = "$ " + Double.toString(pagoalumno.getMonto());
    return crearTablaConcepto(bold, italic, concepto, monto);
  }

  private static Table crearTablaConcepto(PdfFont bold, PdfFont italic, String concepto, String monto) {
    Table table = new Table(new UnitValue[]{
      new UnitValue(UnitValue.PERCENT, 80),
      new UnitValue(UnitValue.PERCENT, 20)})
            .setWidthPercent(100);

    table.addHeaderCell(new Paragraph()
            .setTextAlignment(TextAlignment.CENTER)
            .setFont(bold)
            .add(new Text("Concepto")));
    table.addHeaderCell(new Paragraph()
            .setTextAlignment(TextAlignment.CENTER)
            .setFont(bold)
            .add(new Text("Importe")));

    table.addCell(new Paragraph()
            .setTextAlignment(TextAlignment.LEFT).setMultipliedLeading(3)
            .add(new Text(concepto).setFontSize(9))
    );
    table.addCell(new Paragraph()
            .setTextAlignment(TextAlignment.RIGHT).setMultipliedLeading(3)
            .add(new Text(monto))
    );
    table.addCell(new Cell(1, 2).add(
            new Paragraph().setTextAlignment(TextAlignment.RIGHT)
                    .add(new Text(monto)))
    );

    return table;
  }

}
