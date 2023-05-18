
package mephi.JFrameMain;

import org.apache.commons.io.FilenameUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class XMLReader extends FileReader{
    
    private final DataStorage ds;
    private Reactor reactor;


    public XMLReader() {
        this.ds = new DataStorage();
    }
    
    @Override
    public DataStorage getDs() {
        return ds;
    }

    @Override
    public FileReader createAndRead(String filename) {
        if(FilenameUtils.getExtension(filename).equals("xml")){
            XMLReader xmlReader = new XMLReader();
            xmlReader.readFile(filename);
            return xmlReader;
        } else if (next != null){
            return next.createAndRead(filename);
        }
        return null;
    }

    @Override
    public void readFile(String path) {
        ds.setSource("xml");
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try {
            XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(path));
            while (reader.hasNext())
            {
                XMLEvent xmlEvent = reader.nextEvent();
                if (xmlEvent.isStartElement()) {
                    StartElement startElement = xmlEvent.asStartElement();
                    switch (startElement.getName().getLocalPart()) {
                        case "Reactor" -> reactor = new Reactor();
                        case "type" -> {
                            xmlEvent = reader.nextEvent();
                            reactor.setType(xmlEvent.asCharacters().getData());
                        }
                        case "burnup" -> {
                            xmlEvent = reader.nextEvent();
                            reactor.setBurnup(Double.parseDouble(xmlEvent.asCharacters().getData()));
                        }
                        case "kpd" -> {
                            xmlEvent = reader.nextEvent();
                            reactor.setKpd(Double.parseDouble(xmlEvent.asCharacters().getData()));
                        }
                        case "enrichment" -> {
                            xmlEvent = reader.nextEvent();
                            reactor.setEnrichment(Double.parseDouble(xmlEvent.asCharacters().getData()));
                        }
                        case "thermal_capacity" -> {
                            xmlEvent = reader.nextEvent();
                            reactor.setThermal_capacity(Double.parseDouble(xmlEvent.asCharacters().getData()));
                        }
                        case "electrical_capacity" -> {
                            xmlEvent = reader.nextEvent();
                            reactor.setElectrical_capacity(Double.parseDouble(xmlEvent.asCharacters().getData()));
                        }
                        case "life_time" -> {
                            xmlEvent = reader.nextEvent();
                            reactor.setLife_time(Integer.parseInt(xmlEvent.asCharacters().getData()));
                        }
                        case "first_load" -> {
                            xmlEvent = reader.nextEvent();
                            reactor.setFirst_load(Double.parseDouble(xmlEvent.asCharacters().getData()));
                        }
                        default -> {
                        }
                    }
                }
                if (xmlEvent.isEndElement()) {
                    EndElement endElement = xmlEvent.asEndElement();
                    if (endElement.getName().getLocalPart().equals("Reactor")) {
                        ds.getReactors().add(reactor);
                    }
                }
            }
        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }
    }

    @Override
    public DefaultMutableTreeNode buildTree() {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Reactors");
        for (Reactor reactor : ds.getReactors()) {
            rootNode.add(reactor.getNode());
        }
        return rootNode;
    }
}