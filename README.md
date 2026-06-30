# Part 3 – College Staff Management System in Java

## 1. Project purpose

This project is a simple Java OOP system for managing college staff, departments, and committees. The main file handles the menu, user input, and printing. The other classes hold the system data and actions.

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
- Committee chairman must be a doctor or professor.
- Compare doctors/professors by article count.
- Compare committees by staff count.
- Compare committees by total article count.
- Clone committee using the `new-` prefix.
- `toString` and `equals` in all classes.
- Required `MarathonClassDiagram.pdf`.

## 7. File structure

```text
java_course/
├── README.md
├── MarathonClassDiagram.pdf
├── MarathonClassDiagram.puml
└── BenFisherRazMazliah/
    ├── ArticleWriter.java
    ├── BenFisherRazMazliah.java
    ├── College.java
    ├── CollegeActionException.java
    ├── Committee.java
    ├── Degree.java
    ├── Department.java
    ├── DoctorLecturer.java
    ├── Lecturer.java
    └── ProfessorLecturer.java
```

## 8. Final submission notes

- Submit Java files only, not `.class` files.
- Include `MarathonClassDiagram.pdf`.
- Code must compile before submission.
