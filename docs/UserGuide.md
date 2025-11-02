---
layout: page
title: User Guide
---
Mate is a desktop app that helps users quickly find the right hackathon teammates by surfacing peers based on skills and proficiency. With a simple CLI-first interface and color-coded skill tags, students can instantly discover, connect, and form balanced teams without wasting time on endless networking.

Mate is designed for hackathon participants across the technical spectrum — from developers, designers, and data scientists to product managers, domain experts, and non-technical contributors (e.g., UX, marketing, business). It serves students and professionals who value fast, pragmatic workflows.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from [here](https://github.com/AY2526S1-CS2103T-F08b-2/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for Mate.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar mate.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `listTeam` : Lists all teams.

   * `add n/John Doe e/johnd@example.com t/JohnTG g/John` : Adds a contact named `John Doe` to Mate.

   * `delete p/3` : Deletes the 3rd contact shown in the current list.

   * `createTeam tn/Development Team h/Hackathon 2024 p/1 p/2` : Creates a team with the first two contacts as members.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [h/HACKATHON]` can be used as `n/John Doe h/NUSHacks` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[h/HACKATHON]…​` can be used as ` ` (i.e. 0 times), `h/NUSHacks`, `h/NUSHacks h/NTUHacks` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME t/TELEGRAM`, `t/TELEGRAM  n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.

* Commands are case-insensitive. e.g. `ADD n/John` is equivalent to `add n/John`

</div>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

### Listing all teams : `listTeam`

Shows a list of all teams in the address book.

* Displays all teams with their team names, hackathon names, and member counts.
* No parameters are required for this command.

Format: `listTeam`

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Adding a person: `add`

Adds a person to the address book.

Format: `add n/NAME e/EMAIL t/TELEGRAM_NAME g/GITHUB_NAME [s/SKILL[:LEVEL]]…​ [h/HACKATHON]…​`

* `LEVEL` can be: `Beginner`, `Intermediate`, or `Advanced` (case-insensitive)
* If no level is specified for a skill, it defaults to `Beginner`
* `h/HACKATHON` specifies hackathons the person is interested in. Can be used multiple times to add multiple hackathons.
* **Duplicate skills or hackathons are not allowed.**
* **Hackathon names are case-insensitive** - "NUSHack", "nushack", and "NUSHACK" are treated as the same hackathon
* Skills are displayed with color-coded backgrounds in the UI:
  * **Beginner** - Green background
  * **Intermediate** - Blue background  
  * **Advanced** - Purple background
* Skills are sorted by experience level (Advanced → Intermediate → Beginner), then alphabetically within each level
* Telegram handle constraints (must meet these or `add` will fail):
  * 5–32 characters long
  * contain only letters, numbers, and underscores
  * cannot start with '@'
  * cannot contain spaces
  * cannot start or end with an underscore
  * cannot contain consecutive underscores

  (This matches the app's validation: "Telegram handles should be 5–32 characters long, contain only letters, numbers, and underscores, cannot start with '@', cannot contain spaces, cannot start or end with an underscore, and cannot contain consecutive underscores.")
* Email constraints (must meet these or `add` will fail):
  * Format: local-part@domain
  * Local-part may contain alphanumeric characters and the special characters "+ _ . -" (but may not start or end with one of them)
  * Domain is made of labels separated by periods; each label must start and end with an alphanumeric character and may contain hyphens
  * The final domain label must be at least 2 characters long

  (This matches the app's validation: "Emails should be of the format local-part@domain and adhere to the constraints described in the app.")
* GitHub username constraints (must meet these or `add` will fail):
  * 1–39 characters long
  * contain only letters, numbers, or hyphens
  * cannot start or end with a hyphen
  * cannot have 2 consecutive hyphens
  * cannot contain underscores, spaces, or other symbols

  (This matches the app's validation: "GitHub usernames should be 1–39 characters long, contain only letters, numbers, or hyphens, cannot start or end with a hyphen or have 2 consecutive hyphens, and cannot contain underscores, spaces, or symbols.")
* Examples:
* `add n/John Doe e/johnd@example.com t/JohnTG g/JohnGH`
* `add n/Alice e/alice@example.com t/alice_tg g/alice123 s/Docker h/TechChallenge`
* `add n/Betsy Crowe e/betsycrowe@example.com t/Betsygram g/Betsy03 s/C#:Intermediate s/Java:Advanced h/NUSHack h/iNTUition`

### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete p/INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete p/2` deletes the 2nd person in the list.
* `find k/Betsy` followed by `delete p/1` deletes the 1st person in the results of the `find` command.

### Creating a team : `createTeam`

Creates a new team in Mate with the specified team members.

Format: `createTeam tn/TEAM_NAME h/HACKATHON_NAME p/INDEX [p/INDEX]…​`

* Creates a team with the specified `TEAM_NAME` and `HACKATHON_NAME`.
* Team members are specified by their `INDEX` numbers from the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​
* You can add multiple team members by specifying multiple `p/INDEX` parameters.
* Team names and hackathon names should only contain alphanumeric characters and spaces, and should not be blank.
* Duplicate teams (same team name) are not allowed.

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
Make sure to use the `list` command first to see the current index numbers of persons before creating a team.
</div>

Examples:
* `createTeam tn/Alpha Squad h/AI Challenge p/2 p/4 p/5` creates a team called "Alpha Squad" for "AI Challenge" hackathon with the 2nd, 4th, and 5th persons as members.

### Deleting a team : `deleteTeam`

Deletes a team from Mate and removes all members from that team.

Format: `deleteTeam p/INDEX`

* Deletes the team at the specified `INDEX`.
* The index refers to the index number shown in the displayed team list.
* The index **must be a positive integer** 1, 2, 3, …
* **Hackathon Management**: When a team is deleted:
    * All team members are automatically removed from the team
    * The team's hackathon is removed from all members' **participating hackathons**
    * The hackathon is **NOT** added back to any member's **interested hackathons**
* After successfully deleting a team, the team list will be updated automatically.

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
Use the `listTeam` command first to see the current index numbers of teams before deleting one.
</div>

Examples:
* `listTeam` followed by `deleteTeam p/2` deletes the 2nd team in the team list.
* `deleteTeam p/1` deletes the 1st team in the displayed team list.

### Adding a person to a team : `addToTeam`

Adds an existing person to an existing team in the address book.

Format: `addToTeam tn/TEAM_NAME p/INDEX`

* Adds the person at the specified `INDEX` to the team with the specified `TEAM_NAME`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …
* The team must already exist in Mate.
* A person can be a member of multiple teams.
* If the person is already a member of the specified team, an error message will be shown.
* **Hackathon Management**: When a person is added to a team:
    * If the team's hackathon is in the person's **interested hackathons**, it will be automatically removed.
    * The team's hackathon is automatically added to the person's **participating hackathons.**
    * A hackathon cannot be in both the interested and participating lists simultaneously.
* After successfully adding a person to a team, the teams list will be displayed automatically.

Examples:
* `addToTeam tn/Alpha Squad p/1` adds the 1st person to the "Alpha Squad" team.

### Removing a person from a team : `removeFromTeam`

Removes an existing person from an existing team in the address book.

Format: `removeFromTeam tn/TEAM_NAME p/INDEX`

* Removes the person at the specified `INDEX` from the team with the specified `TEAM_NAME`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …
* The team must already exist in Mate.
* If the person is not a member of the specified team, an error message will be shown.
* **Hackathon Management**: When a person is removed from a team:
    * The team's hackathon is automatically removed from the person's **participating hackathons.**
    * The hackathon is automatically **added back** to the person's **interested hackathons** (since they were participating, they must have been interested.)
* After successfully removing a person from a team, the person's record and the team's membership are both updated, and the teams list will be displayed automatically.

Examples:
* `removeFromTeam tn/Tech Innovators p/2` removes the 2nd person in the displayed list from the "Tech Innovators" team.
* `removeFromTeam tn/Alpha Squad p/1` removes the 1st person from the "Alpha Squad" team.

### Editing a person : `edit`

Edits an existing person's basic contact details in the address book.

./Format: `edit p/INDEX [n/NAME] [e/EMAIL] [t/TELEGRAM_NAME] [g/GITHUB_NAME]`

* Edits the person at the specified `p/INDEX`.
  * The index refers to the index number shown in the displayed person list.
  * The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* Note: The `edit` command does NOT modify a person's skills or interested/participating hackathon lists. To manage those use the dedicated commands: `addSkill`, `addHackathon`, and `removeHackathon`.

Examples:
* `edit p/1 e/johndoe@example.com t/johndoe_tg` — Edits the email address and Telegram name of the 1st person.
* `edit p/2 n/Betsy Crower` — Edits the name of the 2nd person to `Betsy Crower`.
* `edit p/3 gh/alice-gh` — Edits GitHub username of the 3rd person.

### Removing a skill from a person : `removeSkill`

Removes a skill from a person in the address book.

Format: `removeSkill p/INDEX s/SKILL [s/SKILL]...`

* You may remove multiple skills in a single command by repeating the `s/` prefix (e.g. `s/java s/python`).
* Removes each specified `SKILL` from the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …
* If the person does not have one of the specified skills, an error message will be shown and the command will fail (no partial removal).

Examples:
* `removeSkill p/2 s/Java s/Python` removes both Java and Python from the 2nd person in the displayed list.
* `removeSkill p/1 s/Python` removes the skill "Python" from the 1st person in the displayed list.


### Adding skills to a person : `addSkill`

Adds one or more skills (with optional levels) to a person identified by index.

Format: `addSkill p/INDEX s/SKILL[:LEVEL] [s/SKILL[:LEVEL]]...`

* You may add multiple skills in a single command (e.g. `s/java s/python:Intermediate`).
* Adds each specified `SKILL` to the person at the specified `p/INDEX`.
* `LEVEL` can be: `Beginner`, `Intermediate`, or `Advanced` (default: `Beginner`).
* Behavior when the skill already exists on the person:
  * If you add the same skill with a higher level than the existing one, the skill will be upgraded to the higher level.
  * If you add the same skill with a lower level than the existing one, the skill will be downgraded to the lower level.
  * If you add the same skill with the same level, nothing will change for that skill (no error).
* Duplicate skill names (case-insensitive) are treated as the same skill.
* The index **must be a positive integer** 1, 2, 3, …

Examples:
* `addSkill p/1 s/java:Advanced` adds Java at Advanced level to person 1 (or upgrades it if a lower level existed).
* `addSkill p/2 s/python s/docker:Intermediate` adds Python (Beginner) and Docker (Intermediate) to person 2.
* `addSkill p/3 s/java:Beginner s/java:Advanced` in the same command will result in the higher level (Advanced) being kept for Java.

### Adding an interested hackathon to a person : `addHackathon`

Adds one or more hackathons to a person's interested list.

Format: `addHackathon p/INDEX h/HACKATHON_NAME [h/HACKATHON_NAME]...`

* Adds each specified hackathon to the person's interested hackathons (replaces nothing — it only adds).
* Hackathon names are case-insensitive (e.g., `NUSHack`, `nushack` are treated the same).
* If the person is already participating in a hackathon (via a team), that hackathon cannot be added to the interested list — the command will fail with an error.
* The index **must be a positive integer** 1, 2, 3, …

Examples:
* `addHackathon p/1 h/NUSHack` adds NUSHack to person 1's interested list.
* `addHackathon p/3 h/NUSHack h/iNTUition` adds both hackathons to person 3's interested list.


### Removing an interested hackathon from a person : `removeHackathon`

Removes one or more hackathons from a person's interested hackathon list.

Format: `removeHackathon p/INDEX h/HACKATHON_NAME [h/HACKATHON_NAME]...`

* Removes each specified hackathon from the person's interested list.
* You cannot remove a hackathon that the person is currently participating in — attempting to do so will fail and suggest removing the person from the team (or deleting the team) first.
* At least one `h/HACKATHON_NAME` must be provided.
* Hackathon names are case-insensitive.
* The index **must be a positive integer** 1, 2, 3, …

Examples:
* `removeHackathon p/2 h/TechChallenge` removes TechChallenge from person 2's interested list.
* `removeHackathon p/4 h/NUSHack h/iNTUition` removes both hackathons from person 4.

### Locating persons : `find`

Finds persons who match **all** the given keywords.

Format: `find k/KEYWORD [k/MORE_KEYWORDS]...`

* Searches across multiple fields: name, email, Telegram username, GitHub username, skills, interested hackathons, and participating hackathons.
  * Persons matching **all** keywords in any of the searchable fields will be returned (i.e. `AND` search).
    e.g. `find k/John k/NUSHacks` will return persons who have both `John` AND `NUSHacks`.
* The search is case-insensitive. e.g `k/hans` will match `Hans`.
* The order of the keywords does not matter. e.g. `find k/Hans k/NUSHacks` will match persons with both `Hans` and `NUSHacks`.
* Partial matching is supported e.g. `k/NUS` will match `NUSHack`
* Keywords can contain spaces e.g. `k/AI Hackathon 2024` is a single keyword

Examples:
* `find k/John` returns persons with name `john` or `John Doe`, or email `john@example.com`, or Telegram `john123`
* `find k/Java` returns persons who have `Java` or `JavaScript` as a skill
* `find k/alice k/python` returns persons that have both `alice` AND `python` in their fields (e.g., Alice with Python skill)
* `find k/AI Hackathon 2024` returns persons interested in or participating in hackathons containing `AI Hackathon 2024`

### Saving the data

Mate's data is saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

Mate's data is saved automatically as a JSON file `[JAR file location]/data/mate.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, Mate will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause Mate to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous Mate home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action                      | Format, Examples                                                                                                                                                                 |
|-----------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Help**                    | `help`                                                                                                                                                                           |
| **List**                    | `list`                                                                                                                                                                           |
| **List Team**               | `listTeam`                                                                                                                                                                       |
| **Clear**                   | `clear`                                                                                                                                                                          |
| **Exit**                    | `exit`                                                                                                                                                                           |
| **Add Person**              | `add n/NAME e/EMAIL t/TELEGRAM_NAME g/GITHUB_NAME [s/SKILL[:LEVEL]]…​ [h/HACKATHON]…​` <br> e.g., `add n/John Doe e/johnd@example.com t/JohnTG g/John s/Python:Beginner h/NUSHack` |
| **Delete Person**           | `delete p/INDEX`<br> e.g., `delete p/3`                                                                                                                                          |
| **Create Team**             | `createTeam tn/TEAM_NAME h/HACKATHON_NAME p/INDEX [p/INDEX]…​` <br> e.g., `createTeam tn/Development Team h/Tech Innovation 2024 p/1 p/3`                                        |
| **Delete Team**             | `deleteTeam p/INDEX`<br> e.g., `deleteTeam p/1`                                                                                                                                  |
| **Add Person to Team**      | `addToTeam tn/TEAM_NAME p/INDEX` <br> e.g., `addToTeam tn/Development Team p/3`                                                                                                  |
| **Remove Person from Team** | `removeFromTeam tn/TEAM_NAME p/INDEX` <br> e.g., `removeFromTeam tn/Tech Innovators p/2`                                                                                         |
| **Edit**                    | `edit p/INDEX [n/NAME] [e/EMAIL] [t/TELEGRAM_NAME] [g/GITHUB_NAME]`<br> e.g., `edit p/2 n/James Lee`          |
| **Remove Skill**            | `removeSkill p/INDEX s/SKILL [s/SKILL]...`<br> e.g., `removeSkill p/2 s/Java s/Python`                                                                                     |
| **Add Skill**              | `addSkill p/INDEX s/SKILL[:LEVEL] [s/SKILL[:LEVEL]]...`<br> e.g., `addSkill p/1 s/java:Advanced`, `addSkill p/2 s/python s/docker:Intermediate`                                   |
| **Add Hackathon**          | `addHackathon p/INDEX h/HACKATHON_NAME [h/HACKATHON_NAME]...`<br> e.g., `addHackathon p/1 h/NUSHack`, `addHackathon p/3 h/NUSHack h/iNTUition`                                     |
| **Remove Hackathon**       | `removeHackathon p/INDEX h/HACKATHON_NAME [h/HACKATHON_NAME]...`<br> e.g., `removeHackathon p/2 h/TechChallenge`, `removeHackathon p/4 h/NUSHack h/iNTUition`                       |
| **Find**                    | `find k/KEYWORD [k/MORE_KEYWORDS]…​`<br> e.g., `find k/James k/Python`, `find k/AI Hackathon 2024`                                                                               |
