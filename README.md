# Part 3 - College Staff Management System in Java

## 1. Project purpose

This project is a beginner-level Java OOP system for managing college staff, departments, and committees.
It uses classes, inheritance, interfaces, enums, arrays, custom exceptions, `toString`, and `equals`.

All user input and screen output are handled only in `Main.java`.
The other classes contain the system logic and do not use `Scanner` or print to the screen.

## 2. Submitters

- Ben Fisher - ID: 213160005
- Raz Mazliah - ID: 324965094

## 3. How to compile

```bash
javac -encoding UTF-8 -d out $(find college_exe3 -name "*.java")
```

## 4. How to run

```bash
java -cp out ben_fisher_raz_matzliach.Main
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

- Inheritance for `Doctor` and `Professor`.
- Article list for doctors and professors.
- Professor granting body.
- Custom exception handling with `CollegeActionException`, `InvalidChairmanException`,
  and `AlreadyCommitteeMemberException`.
- Chairman must be doctor/professor.
- Compare doctors/professors by articles.
- Compare committees by staff count.
- Compare committees by total article count.
- Clone committee using `new-` prefix.
- `toString` and `equals` in all classes.
- Dynamic arrays expand by exactly x2.
- `marathon_uml_diagram.pdf` class diagram file.

## 7. File structure

```text
java_course/
|-- README.md
|-- marathon_uml_diagram.pdf
`-- college_exe3/
    `-- ben_fisher_raz_matzliach/
        |-- AlreadyCommitteeMemberException.java
        |-- Article.java
        |-- ArticleWriter.java
        |-- College.java
        |-- CollegeActionException.java
        |-- Committee.java
        |-- Degree.java
        |-- Department.java
        |-- Doctor.java
        |-- InvalidChairmanException.java
        |-- Lecturer.java
        |-- Main.java
        `-- Professor.java
```

## 8. Manual tests performed

The program was compiled cleanly and then tested manually through the menu.
The test included:

- Adding FIRST and SECOND lecturers.
- Adding a doctor with 2 articles.
- Adding a professor with a granting body and 3 articles.
- Rejecting a FIRST lecturer as committee chairman.
- Creating a committee with a doctor chairman.
- Adding a regular member and rejecting the same member a second time.
- Rejecting an invalid chairman update without changing the old chairman.
- Updating the chairman to a professor and removing that professor from regular members.
- Creating two departments and moving a lecturer from the first to the second.
- Checking general average salary and department average salary.
- Comparing doctor/professor article counts.
- Comparing two committees by staff count and total article count.
- Cloning a committee as `new-<name>` and rejecting a duplicate clone.
- Showing all lecturers and all committees.

## 9. Final submission notes

- Submit the package folder with the Java files only:
  `college_exe3/ben_fisher_raz_matzliach`.
- Do not submit `.class`, `out`, `build`, or temporary files.
- Include `marathon_uml_diagram.pdf`.
- Code must compile before submission.
- The project avoids `ArrayList`, `HashMap`, collections, streams, generics, reflection, and advanced libraries.
