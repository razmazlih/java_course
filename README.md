# Part 3 – College Staff Management System in Java

## 1. Project purpose

This project is a beginner-level Java OOP system for managing college staff, departments, and committees.
It uses classes, inheritance, interfaces, enums, arrays, custom exceptions, `toString`, and `equals`.

All user input and screen output are handled only in `BenFisherRazMazliah.java`.
The other classes contain the system logic and do not use `Scanner` or print to the screen.

## 2. Submitters

- Ben Fisher - ID: 213160005
- Raz Mazliah - ID: 324965094

## 3. How to compile

```bash
javac BenFisherRazMazliah/*.java
```

## 4. How to run

```bash
java BenFisherRazMazliah.BenFisherRazMazliah
```

## 5. Main features from previous parts

- Add lecturers.
- Add committees and departments.
- Assign lecturers to committees.
- Update committee chairman.
- Remove lecturers from committees.
- Assign lecturers to departments.
- Show average salary for all lecturers.
- Show average salary by department.
- Show all lecturers and all committees.

## 6. New Part 3 features

- Inheritance for `DoctorLecturer` and `ProfessorLecturer`.
- Article list for doctors and professors.
- Professor granting body.
- Custom exception handling with `CollegeActionException`.
- Chairman must be doctor/professor.
- Compare doctors/professors by articles.
- Compare committees by staff count.
- Compare committees by total article count.
- Clone committee using `new-` prefix.
- `toString` and `equals` in all classes.
- Dynamic arrays expand by exactly x2.
- `MarathonClassDiagram.pdf`.

## 7. File structure

```text
java_course/
|-- README.md
|-- MarathonClassDiagram.pdf
|-- MarathonClassDiagram.puml
`-- BenFisherRazMazliah/
    |-- ArticleWriter.java
    |-- BenFisherRazMazliah.java
    |-- College.java
    |-- CollegeActionException.java
    |-- Committee.java
    |-- Degree.java
    |-- Department.java
    |-- DoctorLecturer.java
    |-- Lecturer.java
    `-- ProfessorLecturer.java
```

## 8. Final submission notes

- Submit Java files only, not `.class` files.
- Include `MarathonClassDiagram.pdf`.
- Code must compile before submission.
- The project avoids `ArrayList`, `HashMap`, collections, streams, generics, reflection, and advanced libraries.
