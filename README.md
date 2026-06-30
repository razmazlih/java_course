# Stage 3 - College Staff Management System

This project is a beginner-level Java OOP college staff management system.
It manages lecturers, departments, and committees using classes, inheritance,
interfaces, enums, arrays, custom exceptions, `toString`, and `equals`.

All user input and screen output are handled only in `BenFisherRazMazliah.java`.
The other classes contain the system logic and do not use `Scanner` or print to
the screen.

## Compile

```bash
javac BenFisherRazMazliah/*.java
```

## Run

```bash
java BenFisherRazMazliah.BenFisherRazMazliah
```

## Main Part 3 Features

- All Java files are in the `BenFisherRazMazliah` package.
- `CollegeActionException` is used for normal operation failures.
- `Lecturer` is the base class.
- `DoctorLecturer` stores article names and implements `ArticleWriter`.
- `ProfessorLecturer` extends `DoctorLecturer` and stores the professorship body.
- Committee chairmen must be doctors or professors.
- The menu supports comparing doctor/professor article counts.
- The menu supports comparing committees by staff count or total article count.
- The menu supports cloning a committee as `new-ORIGINAL_NAME`.
- All dynamic storage uses regular arrays that expand by exactly x2.

## File Structure

```text
BenFisherRazMazliah/
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

## Notes

The project intentionally avoids `ArrayList`, `HashMap`, collections, streams,
generics, reflection, and advanced libraries, according to the course rules.
