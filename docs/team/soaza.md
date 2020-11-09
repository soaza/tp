---
layout: page
title: Kim Guan's Project Portfolio Page
---

## Project: Archangel

Archangel is an appointment management system designed for Psychiatrists. The project simulates an ongoing software
 project written in Java for a desktop application (called Archangel) used for managing appointment thru a CLI and a GUI
 built using JavaFX. It is written in OOP fashion. It provides a reasonably well-written code base bigger (around 6 KLoC) than what
students usually write in beginner-level SE modules, without being overwhelmingly big.
It comes with a reasonable level of user and developer documentation.

Given below are my contributions to the project.

* **New Feature**: Added the ability to undo/redo previous commands.
  * What it does: allows the user to undo all previous commands one at a time. Preceding undo commands can be reversed by using the redo command.
  * Justification: This feature improves the product significantly because a user can make mistakes in commands and the app should provide a convenient way to rectify them.
  * Highlights: This enhancement affects existing commands and commands to be added in future. It required an in-depth analysis of design alternatives. The implementation too was challenging as it required changes to existing commands.

* **New Feature**: Add a Calendar feature built using JFXtra's Agenda's extension,iCalendarAgenda.
  * What it does: Allows the user to view all the appointments that have been scheduled in an integrated surface.
  * Justification: This feature improves the product significantly because a user can have a clean overview of his/her upcoming schedule and keep track of it in an organised manner.
  * Highlights: This feature had to be integrated with the current data that is stored inside Archangel. It required one to think out of the box and make careful analysis of what resource
  was already available.
  * Credits: *Package used: JFxtras-agenda and thanks to [Taekwon](https://github.com/ktaekwon000) for his help in setting up the package*
![CalendarFeature](https://raw.githubusercontent.com/soaza/tp/branch-1.4-PPP/docs/images/CalendarTab.png)

* **New Feature**: Add a suggestion box feature to suggest the input format for users when typing their commands.
  * What it does: Allows the user to view all the possible commands that matches what the user has already typed.
  * Justification: This feature improves the user experience significantly because the user can use the application efficiently without the worry of having to memorise the available command.
  * Highlights: This feature had to be integrated with the commands that is available within Archangel. It required me to improvise upon what was already provided in the UI and analyse the product from the user's perspective. The implementation had some challenges in displaying what was to be suggested to the user but was later overcame with good knowledge of Java.
  * Credits: *Design built upon: [code reference](https://gist.github.com/floralvikings/10290131)*


* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=soaza&sort=groupTitle&sortWithin=title&since=2020-08-14&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&tabOpen=true&tabType=zoom&zA=soaza&zR=AY2021S1-CS2103T-W11-1%2Ftp%5Bmaster%5D&zACS=144.46666666666667&zS=2020-08-14&zFS=soaza&zU=2020-11-03&zMG=false&zFTF=commit&zFGS=groupByRepos&zFR=false)

* **Project management**: Team Lead
  * Assigned team members what functionalities each of us had to implement to prevent any conflict and provide a better workflow.
  * Managed the team every week on what was to be completed by upcoming deadlines.
  * Created milestones (`v1.2`-`v1.4`) to organise deadlines and work to be completed.
  * Managed releases `v1.2` - `v1.4` (3 releases) on GitHub

* **Enhancements to existing features**:
  * Updated the GUI to show Appointments (Pull requests [\#58](https://github.com/AY2021S1-CS2103T-W11-1/tp/pull/58))

* **Documentation**:
  * User Guide:
    * Added documentation for the features `undo` and `redo` (Pull request [\#69](https://github.com/AY2021S1-CS2103T-W11-1/tp/pull/69))
  * Developer Guide:
    * Added implementation details of the `undo` and `redo`. (Pull request [\#114](https://github.com/AY2021S1-CS2103T-W11-1/tp/pull/114))

* **Tools**:
  * Integrated a third party library (iCalendarAgenda) to the project (Pull request [\#114](https://github.com/AY2021S1-CS2103T-W11-1/tp/pull/114))

