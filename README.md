# Part 4 - College Staff Management System in Java

## 1. Project purpose

This project is a beginner-level Java OOP system for managing college staff, departments, and committees.
It uses classes, inheritance, interfaces, enums, ArrayList, Serializable, custom exceptions,
`toString`, and `equals`.

All user input and screen output are handled only in `Main.java`.
The other classes contain the system logic and do not use `Scanner` or print to the screen.

## 2. Submitters

- Ben Fisher - ID: 213160005
- Raz Mazliah - ID: 324965094

## 3. How to compile

```bash
javac -encoding UTF-8 -d out $(find ben_fisher_raz_matzliach -name "*.java")
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

## 6. Part 3 features (still working)

- Inheritance for `Doctor` and `Professor`.
- Article list for doctors and professors (using `ArrayList<Article>`).
- Professor granting body.
- Custom exception handling with `CollegeActionException`, `InvalidChairmanException`,
  and `AlreadyCommitteeMemberException`.
- Chairman must be doctor/professor.
- Compare doctors/professors by articles.
- Compare committees by staff count.
- Compare committees by total article count.
- Clone committee using `new-` prefix.
- `toString` and `equals` in all classes.

## 7. New Part 4 features

- **ArrayList instead of arrays**: All arrays replaced with `ArrayList` with full Generics,
  including the article lists in `Doctor` and `Professor`. No raw ArrayLists, no count fields,
  no manual array doubling or copying.
- **Binary save and load**: On exit, the system saves all data to `college_data.dat` using
  `ObjectOutputStream`. On startup, if the file exists, data is loaded with `ObjectInputStream`.
  If the file is missing or corrupted, the system starts fresh.
- **Committee homogeneity**: When creating a committee, the user chooses the allowed member type:
  - `REGULAR` — only regular lecturers (not doctors or professors)
  - `DOCTOR` — only doctors (not professors, since `Professor extends Doctor`)
  - `PROFESSOR` — only professors
  - The chairman is exempt from this restriction but must still be a doctor or professor.
  - A new custom exception `InvalidCommitteeMemberTypeException` is thrown when the wrong type
    is added as a regular member.
- **Serializable**: All model classes implement `Serializable` for binary file storage.
  New classes/enums: `CommitteeMemberType`, `InvalidCommitteeMemberTypeException`.

## 8. File structure

```text
BenFisherRazMazliah/
|-- README.md
|-- mermaid-diagram.pdf
`-- ben_fisher_raz_matzliach/
    |-- AlreadyCommitteeMemberException.java
    |-- Article.java
    |-- ArticleWriter.java
    |-- College.java
    |-- CollegeActionException.java
    |-- Committee.java
    |-- CommitteeMemberType.java
    |-- Degree.java
    |-- Department.java
    |-- Doctor.java
    |-- InvalidChairmanException.java
    |-- InvalidCommitteeMemberTypeException.java
    |-- Lecturer.java
    |-- Main.java
    `-- Professor.java
```

## 9. Manual tests performed (Part 4)

The program was compiled cleanly and tested manually through the menu:

1. First open without saved file starts empty system.
2. Adding a FIRST-degree regular lecturer.
3. Adding a doctor with 2 articles.
4. Adding a professor with granting body and 3 articles.
5. Creating a REGULAR committee with doctor chairman — OK.
6. Trying to add doctor as member to REGULAR committee — rejected with clear message.
7. Trying to add professor as member to REGULAR committee — rejected with clear message.
8. Adding regular lecturer as member to REGULAR committee — OK.
9. Creating a DOCTOR committee.
10. Trying to add regular lecturer to DOCTOR committee — rejected.
11. Trying to add professor to DOCTOR committee — rejected.
12. Adding a doctor as member to DOCTOR committee — OK.
13. Creating a PROFESSOR committee.
14. Trying to add regular lecturer to PROFESSOR committee — rejected.
15. Trying to add doctor (non-professor) to PROFESSOR committee — rejected.
16. Adding professor as member to PROFESSOR committee — OK.
17. Chairman can be professor even if committee is REGULAR or DOCTOR type.
18. Regular lecturer cannot be set as chairman (rejected).
19. Compare doctor/professor article counts — works.
20. Compare committees by staff count — works.
21. Compare committees by total articles — works.
22. Clone committee with `new-` prefix — works.
23. Exit saves to `college_data.dat`.
24. Restarting loads saved data automatically.

## 10. Final submission notes

- Submit only the package folder with Java files:
  `ben_fisher_raz_matzliach/` (all `.java` files).
- Do **not** submit `.class`, `out`, `build`, or `college_data.dat` files.
- Include `mermaid-diagram.pdf`.
- Code must compile before submission.
- `college_data.dat` is generated at runtime and should **not** be submitted.
