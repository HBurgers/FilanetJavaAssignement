package com.Heinrich.JavaAssignment;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static sun.text.normalizer.Utility.escape;

//import static org.junit.Assert.assertEquals;
//
//import org.apache.commons.lang3.StringEscapeUtils;
//import org.junit.Test;
//
//import com.google.common.xml.XmlEscapers;

public class xmlWriter {
    /*
        Create file
        Setup xml
        Read txt file.
        Write to xml
     */
    private String fileName = "C:\\Users\\Heinrich\\IdeaProjects\\JavaAssignment\\src\\main\\java\\com\\Heinrich\\JavaAssignment\\input.txt";
    public xmlWriter() {
        createXML(fileName);
}

    public boolean createXML(String fileN) {
        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            BufferedReader br = null;
            FileReader fr = null;
            String date = "";
            String optionalInfo = "";
            try {

                //br = new BufferedReader(new FileReader(FILENAME));
                setFileName(fileN);
                fr = new FileReader(fileName);
                br = new BufferedReader(fr);

                String sCurrentLine;

                // root elements
                Document doc = docBuilder.newDocument();
                Element rootElement = doc.createElement("list");
                doc.appendChild(rootElement);
                

                while ((sCurrentLine = br.readLine()) != null) {
                    Element item = doc.createElement("item");
                    rootElement.appendChild(item);
                   sCurrentLine.trim();
                   if(!sCurrentLine.equals("")) {
                        date = sCurrentLine.substring(0,16);
                        //escapeString(date);

                        date = escapeString(date.trim());
                        if(!date.trim().equals("")) {
                            Text text = doc.createTextNode(date);
                            Element d = doc.createElement("date");
                            d.appendChild(text);
                            item.appendChild(d);
                        }

                       if(sCurrentLine.length() > 16) {
                           optionalInfo = sCurrentLine.substring(16);
                           optionalInfo =  escapeString(optionalInfo.trim());

                           if(!optionalInfo.trim().equals("")) {
                               //   System.out.println(optionalInfo);
                               Text text = doc.createTextNode(optionalInfo);
                               Element a = doc.createElement("lable");
                               a.appendChild(text);

                               item.appendChild(a);
                           }
                       }


                   }
                }

                // write the content into xml file
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File("C:\\Users\\Heinrich\\IdeaProjects\\JavaAssignment\\src\\main\\java\\com\\Heinrich\\JavaAssignment\\file.xml"));

                // Output to console for testing
                // StreamResult result = new StreamResult(System.out);

                transformer.transform(source, result);

                System.out.println("File saved!");
                return true;

            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } finally {
                try {

                    if (br != null) {
                        br.close();
                        // return false;
                    }

                    if (fr != null) {
                        fr.close();
                        //   return false;
                    }


                } catch (IOException ex) {

                    ex.printStackTrace();
                    return false;
                }
            }
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
            return false;
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
            return false;
        }

    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileN){
        fileName = fileN;
    }

    public String escapeString(String toEscape) {
        StringBuilder escapedXML = new StringBuilder();
        for (int i = 0; i < toEscape.length(); i++) {
            char c = toEscape.charAt(i);
            switch (c) {
                case '<':
                    escapedXML.append("&lt;");
                    break;
                case '>':
                    escapedXML.append("&gt;");
                    break;
                case '\"':
                    escapedXML.append("&quot;");
                    break;
                case '&':
                    escapedXML.append("&amp;");
                    break;
                case ' ':
                    escapedXML.append("&apos;");
                    break;
                default: if (c > 0x7e) { escapedXML.append("&#" + ((int) c) + ";"); } else escapedXML.append(c);
            }
        }
        return escapedXML.toString();
    }



}
