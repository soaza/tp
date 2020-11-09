---
layout: page
title: Yeoh Hui Qing's Project Portfolio Page
---

## Project: Archangel

Archangel is a desktop address book application used for managing patient data and appointment scheduling for medical professionals.
The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 10 kLoC.

Given below are my contributions to the project.

* **New Feature**: Added code foundation for a new model - Appointment.
  * What it does: allows users to be able to use Appointment-type commands (which my team have implemented).
  * Justification: This feature is fundamental to the objective of our product since Archangel focuses on appointments scheduling.
  * Highlights: This feature affects almost all other commands that works on Appointment-type features.
  It required an in-depth analysis of design alternatives - to mimic Patient model implementation or if changes are necessary.
  The implementation too was challenging as it required retrieval of Patient data that exists in Archangel instead of simply parsing primitve-type inputs to existing commands.
  * Credits: *Original CS2103T AddressBook implementation of `Person` model was heavily referenced for the implementation of `Appointment` model.*

* **New Feature(s)**: Added basic functionalities for Appointment - schedule, delete and edit appointment commands.
  * What it does: allows users to schedule, delete and edit Appointments.
  * Justification: The add and delete features are fundamental to the objective of our product since Archangel focuses on appointments scheduling.
  The edit feature is a good-to-have feature to increase user convenience when manging their appointments.
  * Highlights: These features are essential for the modelling of Archangel as an appointment scheduling application.
  * Credits: *Original CS2103T AddressBook implementation of `AddCommand`, `DeleteCommand` and `EditCommand` was heavily referenced for the implementation of Appointment-type commands.*

<div style="page-break-after: always;"></div>

* **New Feature**: Added ability to delete/edit appointments if patient in appointment is deleted/edit respectively.
  * What it does: allows users to manage their appointments easily if changes are made to patients in the appointments.
  * Justification: This feature ensures appointment and patient data are synced at any point in time and removes obsolete data entries in Archangel.
  * Highlights: This feature improves the usability of Archangel as it reduces hassle for user to manage outdated appointments.

* **New Feature**: Added ability to sort appointments chronologically.
  * What it does: allows users to view appointments in chronological order.
  * Justification: This feature improves ease of accessing appointments by making it more intuitive for users to search for appointments.
  * Highlights: This feature improves the usability of Archangel as it reduces hassle for user to manually search/filter appointments.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=yeohhq)

* **Project management**:
  * In charge of implementing core Appointment model and logic
  * In charge of overall documentation of project
  * Ensure that project deliverables are delivered on time

* **Enhancements to existing features**:
  * Wrote tests for Appointment-type command (Pull requests [\#51](https://github.com/AY2021S1-CS2103T-W11-1/tp/pull/51), [\#66](https://github.com/AY2021S1-CS2103T-W11-1/tp/pull/66), [\#67](https://github.com/AY2021S1-CS2103T-W11-1/tp/pull/67))
  * Created all the test builders for the Appointment class.
  
* **Documentation**:
  * User Guide:
    * Added documentation for the features `a-schedule`, `a-delete` and `a-edit`: [\#98](https://github.com/AY2021S1-CS2103T-W11-1/tp/pull/98), [\#126](https://github.com/AY2021S1-CS2103T-W11-1/tp/pull/126)
    * Did cosmetic tweaks to existing documentation of features for patient-type commands: [\#84](https://github.com/AY2021S1-CS2103T-W11-1/tp/pull/84)
    * Added all the step by step photos in the UG

  * Developer Guide:
    * Added implementation details of the `a-schedule`, `a-delete`, `a-edit` features with relevant diagrams: [\#102](https://github.com/AY2021S1-CS2103T-W11-1/tp/pull/102), [\#243](https://github.com/AY2021S1-CS2103T-W11-1/tp/pull/243)
    * Edited implementation details of the `p-add`, `p-delete`, `p-edit` features: [\#102](https://github.com/AY2021S1-CS2103T-W11-1/tp/pull/102)
    3
* **Community**:
  * Reported bugs and suggestions for other teams in the class (examples: [1](https://github.com/yeohhq/ped/issues/6), [2](https://github.com/yeohhq/ped/issues/2), [3](https://github.com/yeohhq/ped/issues/3))
  * Most parts of the Appointment model I have implemented is used by other members for their implementations
