---
layout: page
title: Gordon Foo's Project Portfolio Page
---

## Project: Archangel

Archangel is a desktop address book application used for managing patient data and appointment scheduling for medical professionals.
The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 10 kLoC.

Given below are my contributions to the project.

* **New Feature(s)**: Refactoring of Person class to Patient.
  * What it does: Changes the naming of person to patient.
  * Justification:  To standardise the terminologies between code, UG and DG to better suit our target audience.
  * Credits: *Original CS2103T AddressBook implementation is unchanged, just the naming is changed.*

* **New Feature(s)**: Added more fields for Patient - Gender, Birthdate and Bloodtype.
  * What it does: allows users to store the above information for their patients.
  * Justification: As Archangel is an app made for medical professionals to make storing and accessing data easier for them, being able to store such medical information is paramount to our app.
  * Highlights: These features are essential for the modelling of Archangel as an appointment scheduling and patient data storage application.
  * Credits: *Original CS2103T AddressBook implementation of `Address`, `Email` and `Phone` was heavily referenced for the implementation of additional Patient fields.*
  
* **New Feature(s)**: Added functionality for Patient - Remark command.
    * What it does: allows users to store exclusive information regarding the patient.
    * Justification: As service in the medical industry is very personalised, we allow for storing of more exclusive information such as allergies/preferences under the Remark section.
    * Highlights: These feature makes Archangel more flexible of an assistant to the medical professionals as it can store non-restrictive data.
    * Credits: *Original CS2103T tutorial for adding remarks into AddressBook was heavily referenced for the implementation of Remark command.*

<div style="page-break-after: always;"></div>

* **New Feature(s)**: Added basic functionalities for Appointment - schedule, delete and edit appointment commands. (worked together with hui qing)
  * What it does: allows users to schedule, delete and edit Appointments.
  * Justification: The add and delete features are fundamental to the objective of our product since Archangel focuses on appointments scheduling.
  The edit feature is a good-to-have feature to increase user convenience when manging their appointments.
  * Highlights: These features are essential for the modelling of Archangel as an appointment scheduling application.
  * Credits: *Original CS2103T AddressBook implementation of `AddCommand`, `DeleteCommand` and `EditCommand` was heavily referenced for the implementation of Appointment-type commands.*

* **New Feature**: First iteration of White-Blue themed UI
  * What it does: Cleaner and more user-friendly UI, which separated Archangel from the original AddressBook. Replaced original structure with a borderpane, and replaced DarkTheme with a WhiteBlueTheme.
  * Justification: The change in UI allowed us to display both Appointment and Patient lists side by side. This maximised the vertical space and made viewing information from both lists way easier.
  * Highlights: The application became more aesthetically pleasing and more practical as a data management app.
  * Credits: *Original CS2103T AddressBook UI was used as the back-bone.*
  <div style="page-break-after: always;"></div>
  Before:
        
    ![AddressBook3](https://raw.githubusercontent.com/gordonfgz/tp/branch-ppp/docs/images/Addressbook3.png)
  <div style="page-break-after: always;"></div>        
  After:
        
    ![FirstIteration](https://raw.githubusercontent.com/gordonfgz/tp/branch-ppp/docs/images/FirstIteration.png)
  <div style="page-break-after: always;"></div>
* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=gordonfgz)

* **Project management**:
  * In charge of overall code architecture of Appointment
  * In charge of functionality decisions of Archangel (e.g. delaying the time an appointment gets marked as missed)
  * In charge of testing    

* **Enhancements to existing features**:
  * Wrote tests for Patient-type fields
  * Wrote test for Patient-type parsers
  * Wrote tests for Appointment-type commands 
  * Wrote tests for Appointment-type parsers

* **Documentation**:
  * User Guide:
    * Added documentation for the patient-type commands: [\#225](https://github.com/AY2021S1-CS2103T-W11-1/tp/pull/225)
  * Developer Guide:
    * Added implementation details of the Patient command features with relevant diagrams: [\#92](https://github.com/AY2021S1-CS2103T-W11-1/tp/pull/92)
    * Editted diagrams to reflect the renaming of Person to Patient. [\#92](https://github.com/AY2021S1-CS2103T-W11-1/tp/pull/92)

* **Community**:
  * Reported bugs that are very severe for other teams in the class (examples: [1](https://github.com/gordonfgz/ped/issues/6))
  * Acted as the devil's advocate, frequently questioning functionalities to ensure features implemented are sound and practical
