
package org.example;

import java.io.File;

// Importer les classes nécessaires pour la validation XML avec XSD
import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

// Importer les classes nécessaires pour lire le fichier XML
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

// Importer les classes nécessaires pour utiliser XPath et rechercher des informations
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathConstants;

// Importer les classes pour manipuler les éléments du fichier XML
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

public class App {

    public static void main(String[] args) {

        try {

            // Etape 1 : Validation XML avec XSD

            // Créer les chemins des fichiers XML et XSD
            File xmlFile = new File("src/main/resources/cabinet.xml");
            File xsdFile = new File("src/main/resources/cabinet.xsd");

            // Créer une fabrique pour lire le fichier XSD
            SchemaFactory factory = SchemaFactory.newInstance(
                    XMLConstants.W3C_XML_SCHEMA_NS_URI);

            // Lire le fichier XSD, schema représente les règles de validation
            Schema schema = factory.newSchema(xsdFile);

            // Créer un validateur à partir du schéma XSD
            Validator validator = schema.newValidator();

            // Lancer la validation : donner le fichier XML au validateur
            // pour vérifier s'il respecte les règles définies dans le fichier XSD
            validator.validate(
                    new javax.xml.transform.stream.StreamSource(xmlFile));

            // Afficher un message si la validation réussit
            System.out.println("Le fichier XML est valide !");


            // Etape 2 : Lecture du fichier XML

            // Créer une fabrique pour préparer la lecture du fichier XML
            DocumentBuilderFactory dbFactory =
                    DocumentBuilderFactory.newInstance();

            // Créer un outil pour lire et analyser le fichier XML
            DocumentBuilder dBuilder =
                    dbFactory.newDocumentBuilder();

            // Lire le fichier XML et le charger dans un objet Document
            Document document = dBuilder.parse(xmlFile);


            // Etape 3 : Initialisation de XPath

            // Créer une fabrique pour initialiser XPath
            XPathFactory xpathFactory = XPathFactory.newInstance();

            // Créer un objet XPath pour rechercher des informations dans le XML
            XPath xpath = xpathFactory.newXPath();


            // Etape 4 : Afficher les patients actifs

            // Afficher le titre de la liste des patients actifs
            System.out.println("\n--- Patients actifs ---");

            // Préparer une expression XPath pour chercher les noms des patients actifs
            // @statut='actif' permet de sélectionner seulement les patients actifs
            String expressionPatients =
                    "/cabinet/patients/patient[@statut='actif']/identite/nom";

            // Exécuter l'expression XPath et récupérer les résultats dans une liste
            NodeList patients = (NodeList) xpath.evaluate(
                    expressionPatients,
                    document,
                    XPathConstants.NODESET);

            // Parcourir la liste des patients trouvés
            for (int i = 0; i < patients.getLength(); i++) {

                // Afficher le nom de chaque patient
                System.out.println(patients.item(i).getTextContent());
            }


            // Etape 5 : Rechercher les medecins par specialite

            // Afficher le titre de la recherche des médecins en Cardiologie
            System.out.println("\n--- Medecins en Cardiologie ---");

            // Préparer une expression XPath pour chercher les noms des médecins
            // dont la spécialité est Cardiologie
            String expressionMedecins =
                    "/cabinet/medecins/medecin[specialite='Cardiologie']/identite/nom";

            // Exécuter l'expression XPath et récupérer les médecins trouvés
            NodeList medecins = (NodeList) xpath.evaluate(
                    expressionMedecins,
                    document,
                    XPathConstants.NODESET);

            // Parcourir la liste des médecins trouvés
            for (int i = 0; i < medecins.getLength(); i++) {

                // Afficher le nom de chaque médecin
                System.out.println(medecins.item(i).getTextContent());
            }


            // Etape 6 : Afficher les rendez-vous

            // Afficher le titre de la liste des rendez-vous
            System.out.println("\n--- Liste des rendez-vous ---");

            // Récupérer tous les éléments qui portent le nom rendezvous_item
            NodeList rendezvous = document.getElementsByTagName(
                    "rendezvous_item");

            // Parcourir tous les rendez-vous trouvés
            for (int i = 0; i < rendezvous.getLength(); i++) {

                // Récupérer le rendez-vous actuel comme un élément XML
                Element rdv = (Element) rendezvous.item(i);

                // Récupérer l'identifiant et le statut du rendez-vous
                String id = rdv.getAttribute("id");
                String statut = rdv.getAttribute("statut");

                // Récupérer la date du rendez-vous
                String date = rdv.getElementsByTagName("date")
                        .item(0).getTextContent();

                // Récupérer l'heure du rendez-vous
                String heure = rdv.getElementsByTagName("heure")
                        .item(0).getTextContent();

                // Récupérer l'élément patient associé au rendez-vous
                Element patient = (Element)
                        rdv.getElementsByTagName("patient").item(0);

                // Récupérer l'élément médecin associé au rendez-vous
                Element medecin = (Element)
                        rdv.getElementsByTagName("medecin").item(0);

                // Récupérer la référence du patient et celle du médecin
                String patientId = patient.getAttribute("reference");
                String medecinId = medecin.getAttribute("reference");

                // Afficher toutes les informations du rendez-vous
                System.out.println(
                        id + " | " + date + " | " + heure
                                + " | Patient: " + patientId
                                + " | Medecin: " + medecinId
                                + " | Statut: " + statut);
            }

        } catch (Exception e) {

            // Capturer les erreurs et afficher le message correspondant
            System.out.println("Erreur : " + e.getMessage());

        }
    }
}
