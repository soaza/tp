---
layout: page
title: Chew Hoa Shen's Project Portfolio Page
---

## Project: Archangel

Archangel is a desktop address book application used for managing patient data and appointment scheduling for medical professionals.
The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 10 kLoC.

Given below are my contributions to the project.

* **New Feature**: Added the basic function for viewing appointments in the AddressBook - listall appointment command.
  * What it does: Allows the user to view all the appointments stored in the AddressBook.
  * Justification: This is a fundamental and necessary feature for our product as Archangel is primarily an appointments scheduling application.
  * Highlights: This feature is essential for the viewing of Appointments in Archangel and set a standard for all following viewing-related features.

* **New Feature**: Added the ability to set Appointments as completed/missed - complete, new-misses appointment command.
  * What it does: Allows the user to set an Appointment as completed and missed.
  * Justification: The ability to mark appointments as completed ans missed increase the variety and quality of filtering options we can implement.
  * Highlights: This feature is essential for the implementation of filters like today, weekly, upcoming and completed.

* **New Feature**: Added the ability to filter appointment list - list, completed, missed appointment command.
  * What it does: Allows the user to view the appointments stored in AddressBook based on their completed and missed status.
  * Justification: These features enable better appointment organisation, which is one of the focuses of Archangel.

<div style="page-break-after: always;"></div>

* **New Feature**: Added the ability for Archangel to automatically check for newly missed appointments - TimerThread.
  * What it does: At intervals of 1 minute, scans through the appointment list and labels past uncompleted appointments as missed.
  * Justification: This feature improves the user experience significantly because it removes the need for users to manually find and label missed appointments.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=chshen1998&sort=groupTitle&sortWithin=title&since=2020-08-14&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&tabOpen=true&tabType=zoom&zFR=false&zA=chshen1998&zR=AY2021S1-CS2103T-W11-1%2Ftp%5Bmaster%5D&zACS=226.12238805970148&zS=2020-08-14&zFS=w11&zU=2020-11-05&zMG=false&zFTF=commit&zFGS=groupByRepos)

* **Project management**:
  * In charge of implementing AppointmentList, and various related filters and features.
  * In charge of implementing appointment test utilities and code coverage of various appointment features.
  * In charge of automating the interactivity between Appointment time, isMissed and isCompleted parameters.
  * Ensure user readability for appointment related sections in UG.

* **Enhancements to existing features**:
  * Wrote tests and test utilities for Appointment related commands. (Pull request [\#55](https://github.com/AY2021S1-CS2103T-W11-1/tp/pull/55))

* **Documentation**:
  * User Guide:
    * Added documentations for features `a-completed`, `a-missed`, `a-list`, `a-listall` and `a-complete`. (Pull request [\#85](https://github.com/AY2021S1-CS2103T-W11-1/tp/pull/85))
    * Improve UG to be more user centric. (Pull request [\#221](https://github.com/AY2021S1-CS2103T-W11-1/tp/pull/221))
  * Developer Guide:
    * Added implementation details for TimerThread. (Pull request [\#203](https://github.com/AY2021S1-CS2103T-W11-1/tp/pull/203))
    * Added implementation details for `a-completed`, `a-missed`, `a-list`, `a-listall` and `a-complete`. (Pull Request [\#85](https://github.com/AY2021S1-CS2103T-W11-1/tp/pull/85))

* **Community**:
  * Reported bugs and suggestions for other teams. (Example [1](https://github.com/chshen1998/ped/issues/5))
