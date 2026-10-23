 Gestion Cabinet Médical

 Description

This project is a Java application that manages the information of a medical cabinet using XML, XSD, XPath, and Maven.

 Project Structure

 1. cabinet.xml

This file contains all the medical cabinet data.

- Cabinet: contains the cabinet name and city.
- Patients: contains patient IDs, status, identity (name, first name, age), and contact information (phone number and email).
- Doctors: contains doctor IDs, identity (name and first name), and medical specialties with their codes.
- Appointments: contains appointment IDs, status, date, time, patient references, doctor references, and room numbers.

 2. cabinet.xsd

This file defines the structure and validation rules of cabinet.xml.

- Simple Types: define allowed values for patient status, appointment status, and phone type.
- Number Validation: checks the phone number format and ensures positive values for age and room number.
- Patients: defines the required patient information and attributes.
- Doctors: defines doctor information and specialty codes.
- Appointments: defines dates, times, patient and doctor references, and room numbers.
- IDs and References: use ID and IDREF to identify elements and connect appointments to patients and doctors.

 3. App.java

This is the main Java file that executes the project.

- XML Validation: checks the XML file using the XSD schema.
- XML Reading: loads and reads the XML document.
- XPath Initialization: prepares XPath to search the XML data.
- Active Patients: displays the names of patients whose status is active.
- Doctors by Specialty: searches for doctors in Cardiology.
- Appointments: displays appointment details, including ID, date, time, patient, doctor, and status.

 4. pom.xml

This file contains the Maven project configuration.

- Project Information: defines the group ID, artifact ID, version, and packaging type.
- Properties: defines the Java version and UTF-8 encoding.
- Dependencies: includes JUnit for testing.

 Technologies Used

- Java
- Maven
- XML
- XSD
- XPath

 Execution

Run the App.java file to validate the XML document and display the information.
<img width="845" height="570" alt="image" src="https://github.com/user-attachments/assets/99b5971e-d495-4f45-b79e-cabe8b76b51e" />
