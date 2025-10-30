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

   * `add n/John Doe e/johnd@example.com t/John g/John` : Adds a contact named `John Doe` to Mate.

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
  e.g `n/NAME [h/HACKATHON]` can be used as `n/John Doe t/NUSHacks` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[h/HACKATHON]…​` can be used as ` ` (i.e. 0 times), `h/NUSHacks`, `s/NUSHacks s/NTUHacks` etc.

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

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person can have any number of skills and hackathons (including 0).
</div>

Examples:
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

Edits an existing person in the address book.

Format: `edit p/INDEX [n/NAME] [e/EMAIL] [t/TELEGRAM_NAME] [g/GITHUB_NAME] [s/SKILL[:LEVEL]]…​ [h/HACKATHON]…​`

* Edits the person at the specified `INDEX`. 
  * The index refers to the index number shown in the displayed person list.
  * The index **must be a positive integer** 1, 2, 3, …
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing skills, new skills are added to the existing skills (i.e., adding of skills is cumulative).
  * If you edit a skill that already exists (same skill name), the experience level will be updated to the new level specified.
  * `LEVEL` can be: `Beginner`, `Intermediate`, or `Advanced` (case-insensitive)
  * If no level is specified for a skill, it defaults to `Beginner`
* `h/HACKATHON` specifies the hackathon that the person is interested in. Can be used multiple times to replace all interested hackathons.
  * **Hackathon names are case-insensitive** - "NUSHack", "nushack", and "NUSHACK" are treated as the same hackathon
  * **Important**: You cannot add a hackathon to the interested list if the person is already participating in that hackathon. Doing this will show an error message.
  * **Participating hackathons are preserved** - editing other fields will not affect hackathons the person is currently participating in through their teams.
* **Duplicate skills or hackathons are not allowed** - each skill/hackathon can only be added once

Examples:
*  `edit p/1 e/johndoe@example.com t/johndoe_tg` Edits the email address and Telegram name of the 1st person to be `johndoe@example.com` and `johndoe_tg` respectively.
*  `edit p/3 s/Docker:Intermediate` Updates the Docker skill of the 3rd person to Intermediate level (or adds it if it doesn't exist).
*  `edit p/3 h/NUSHack h/iNTUition` Sets the 3rd person's interested hackathons to NUSHack and iNTUition (replaces all previous interested hackathons).

### Removing a skill from a person : `removeSkill`

Removes a skill from a person in the address book.

Format: `removeSkill p/INDEX s/SKILL`

* Removes the specified `SKILL` from the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …
* If the person does not have the specified skill, an error message will be shown.

Examples:
* `removeSkill p/2 s/Java` removes the skill "Java" from the 2nd person in the displayed list.
* `removeSkill p/1 s/Python` removes the skill "Python" from the 1st person in the displayed list.

### Locating persons : `find`

Finds persons whose name, skills, Telegram username, or GitHub username contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Searches across multiple fields: name, skills, Telegram username, and GitHub username
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword in any of the searchable fields will be returned (i.e. `OR` search).
  e.g. `find John Python` will return persons with name `John`, or skill `Python`, or Telegram username `John`, or GitHub username `Python`

Examples:
* `find John` returns persons with name `john` or `John Doe`, or Telegram `john123`, or GitHub `johnny`
* `find Java` returns persons who have `Java` as a skill
* `find alex david` returns `Alex Yeoh`, `David Li`, or anyone with Telegram username `alex` or GitHub username `david`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

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
| **Add Person**              | `add n/NAME e/EMAIL t/TELEGRAM_NAME g/GITHUB_NAME [s/SKILL[:LEVEL]]…​ [h/HACKATHON]…​` <br> e.g., `add n/John Doe e/johnd@example.com t/John g/John s/Python:Beginner h/NUSHack` |
| **Delete Person**           | `delete p/INDEX`<br> e.g., `delete p/3`                                                                                                                                          |
| **Create Team**             | `createTeam tn/TEAM_NAME h/HACKATHON_NAME p/INDEX [p/INDEX]…​` <br> e.g., `createTeam tn/Development Team h/Tech Innovation 2024 p/1 p/3`                                        |
| **Delete Team**             | `deleteTeam p/INDEX`<br> e.g., `deleteTeam p/1`                                                                                                                                  |
| **Add Person to Team**      | `addToTeam tn/TEAM_NAME p/INDEX` <br> e.g., `addToTeam tn/Development Team p/3`                                                                                                  |
| **Remove Person from Team** | `removeFromTeam tn/TEAM_NAME p/INDEX` <br> e.g., `removeFromTeam tn/Tech Innovators p/2`                                                                                         |
| **Edit**                    | `edit p/INDEX [n/NAME] [e/EMAIL] [t/TELEGRAM_NAME] [g/GITHUB_NAME] [s/SKILL[:LEVEL]]…​ [h/HACKATHON]…​`<br> e.g.,`edit p/2 n/James Lee s/Docker:Intermediate h/NUSHack`          |
| **Remove Skill**            | `removeSkill p/INDEX s/SKILL`<br> e.g., `removeSkill p/2 s/Java`                                                                                                                 |
| **Find**                    | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`, `find Python Java`                                                                                                   |

