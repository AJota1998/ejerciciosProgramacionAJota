package EX_EJEMPLO.EJ2;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class RecorridoStax {

    public static void mostrarLibroMasPaginas() {
        try {

            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            XMLEventReader xmlReader =
                    xmlInputFactory.createXMLEventReader(new FileInputStream("ficheros/ejercicio2.xml"));

            String lmpTitulo = "";
            String lmpAutor = "";
            String lmpEditorial = "";
            int lmpPaginas = 0;

            String aTitulo = "";
            String aAutor = "";
            String aEditorial = "";
            int aPaginas = 0;

            String tagActual = "";

            while (xmlReader.hasNext()) {

                XMLEvent xmlEvent = xmlReader.nextEvent();

                if (xmlEvent.isStartElement()) {

                    StartElement startTag = xmlEvent.asStartElement();

                    tagActual = startTag.getName().getLocalPart();

                } else if (xmlEvent.isCharacters()) {

                    Characters texto = xmlEvent.asCharacters();

                    if (!texto.getData().contains("\n")) {
                        if (!tagActual.equals("")) {

                            switch (tagActual) {
                                case "titulo":
                                    aTitulo = texto.getData();
                                    break;
                                case "autor":
                                    aAutor = texto.getData();
                                    break;
                                case "editorial":
                                    aEditorial = texto.getData();
                                    break;
                                case "paginas":
                                    aPaginas = Integer.parseInt(texto.getData());
                                    break;
                            }
                        }
                    }
                } else if (xmlEvent.isEndElement()) {

                    EndElement endtag = xmlEvent.asEndElement();

                    if (endtag.getName().getLocalPart().equals("libro")) {

                        if (aPaginas > lmpPaginas) {

                            lmpTitulo = aTitulo;
                            lmpAutor = aAutor;
                            lmpEditorial = aEditorial;
                            lmpPaginas = aPaginas;
                        }

                        aTitulo = "";
                        aAutor = "";
                        aEditorial = "";
                        aPaginas = 0;
                    }
                }
            }

            System.out.println("Datos del libro con más páginas:");
            System.out.println("Titulo: " + lmpTitulo);
            System.out.println("Autor: " + lmpAutor);
            System.out.println("Editorial: " + lmpEditorial);
            System.out.println("Páginas: " + lmpPaginas);

        } catch (FileNotFoundException | XMLStreamException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void mostrarNumLibrosEditorial(String edit) {

        Integer contador = 0;

        try {

            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            XMLEventReader xmlReader =
                    xmlInputFactory.createXMLEventReader(new FileInputStream("ficheros/ejercicio2.xml"));

            while (xmlReader.hasNext()) {
                XMLEvent xmlEvent = xmlReader.nextEvent();

                if (xmlEvent.isCharacters()) {

                    Characters texto = xmlEvent.asCharacters();

                    if (texto.getData().equalsIgnoreCase(edit)) {
                        contador++;
                    }
                }
            }

            System.out.println("Hay " + contador + " libros de la edit " + edit + ".");

        } catch (FileNotFoundException | XMLStreamException e) {
            System.out.println(e.getMessage());
        }
    }
}
