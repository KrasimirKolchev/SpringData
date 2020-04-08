package com.productsshopxml.utils.xmlParser;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public interface XMLParser {
    <T> T importFromXML(Class<T> clazz, String filePath) throws JAXBException, FileNotFoundException;

    <T> void exportToXML(T object, String filePath) throws JAXBException;

}
