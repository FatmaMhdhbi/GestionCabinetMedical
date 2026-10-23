
package org.example;

import java.io.File;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathConstants;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

public class App {

    public static void main(String[] args) {

        try {

            // Etape 1 : Validation XML avec XSD

            File xmlFile = new File("src/main/resources/cabinet.xml");
            File xsdFile = new File("src/main/resources/cabinet.xsd");

            SchemaFactory factory = SchemaFactory.newInstance(
                    XMLConstants.W3C_XML_SCHEMA_NS_URI);

            Schema schema = factory.newSchema(xsdFile);

            Validator validator = schema.newValidator();

            validator.validate(
                    new javax.xml.transform.stream.StreamSource(xmlFile));

            System.out.println("Le fichier XML est valide !");


            // Etape 2 : Lecture du fichier XML

            DocumentBuilderFactory dbFactory =
                    DocumentBuilderFactory.newInstance();

            DocumentBuilder dBuilder =
                    dbFactory.newDocumentBuilder();

            Document document = dBuilder.parse(xmlFile);


            // Etape 3 : Initialisation de XPath

            XPathFactory xpathFactory = XPathFactory.newInstance();

            XPath xpath = xpathFactory.newXPath();


            // Etape 4 : Afficher les patients actifs

            System.out.println("\n--- Patients actifs ---");

            String expressionPatients =
                    "/cabinet/patients/patient[@statut='actif']/identite/nom";

            NodeList patients = (NodeList) xpath.evaluate(
                    expressionPatients,
                    document,
                    XPathConstants.NODESET);

            for (int i = 0; i < patients.getLength(); i++) {
                System.out.println(patients.item(i).getTextContent());
            }


            // Etape 5 : Rechercher les medecins par specialite

            System.out.println("\n--- Medecins en Cardiologie ---");

            String expressionMedecins =
                    "/cabinet/medecins/medecin[specialite='Cardiologie']/identite/nom";

            NodeList medecins = (NodeList) xpath.evaluate(
                    expressionMedecins,
                    document,
                    XPathConstants.NODESET);

            for (int i = 0; i < medecins.getLength(); i++) {
                System.out.println(medecins.item(i).getTextContent());
            }


            // Etape 6 : Afficher les rendez-vous

            System.out.println("\n--- Liste des rendez-vous ---");

            NodeList rendezvous = document.getElementsByTagName(
                    "rendezvous_item");

            for (int i = 0; i < rendezvous.getLength(); i++) {

                Element rdv = (Element) rendezvous.item(i);

                String id = rdv.getAttribute("id");
                String statut = rdv.getAttribute("statut");

                String date = rdv.getElementsByTagName("date")
                        .item(0).getTextContent();

                String heure = rdv.getElementsByTagName("heure")
                        .item(0).getTextContent();

                Element patient = (Element)
                        rdv.getElementsByTagName("patient").item(0);

                Element medecin = (Element)
                        rdv.getElementsByTagName("medecin").item(0);

                String patientId = patient.getAttribute("reference");
                String medecinId = medecin.getAttribute("reference");

                System.out.println(
                        id + " | " + date + " | " + heure
                                + " | Patient: " + patientId
                                + " | Medecin: " + medecinId
                                + " | Statut: " + statut);
            }

        } catch (Exception e) {

            System.out.println("Erreur : " + e.getMessage());

        }
    }
}
