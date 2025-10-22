---
layout: page
title: User Guide
---

Mate is a **desktop app for managing contacts, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, AB3 can get your contact management tasks done faster than traditional GUI apps.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from [here](https://github.com/se-edu/addressbook-level3/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar addressbook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe e/johnd@example.com tg/John gh/John` : Adds a contact named `John Doe` to the Address Book.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `listTeam` : Lists all teams.

   * `createTeam tn/Development Team hn/Hackathon 2024 p/1 p/2` : Creates a team with the first two contacts as members.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[s/Skill]…​` can be used as ` ` (i.e. 0 times), `s/Python`, `s/C++ s/Java` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME tg/TELEGRAM`, `tg/TELEGRAM  n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a person: `add`

Adds a person to the address book. 

Format: `add n/NAME e/EMAIL tg/TELEGRAM_NAME gh/GITHUB_NAME [s/SKILL[:LEVEL]]…​ [l/BOOLEAN] [h/HACKATHON]…​`

* `LEVEL` can be: `Beginner`, `Intermediate`, or `Advanced` (case-insensitive)
* If no level is specified for a skill, it defaults to `Beginner`
* `BOOLEAN` must be: `true` or `false`
  * `l/true` marks the person as looking for a team
  * `l/false` marks the person as not looking for a team
  * If not specified, the person is not looking for a team (default: false)
* `h/HACKATHON` specifies hackathons the person is interested in. Can be used multiple times to add multiple hackathons.
* Skills are displayed with color-coded backgrounds in the UI:
  * **Beginner** - Light green background
  * **Intermediate** - Light yellow background  
  * **Advanced** - Light red background

<div class="alert alert-primary">:bulb: **Tip:**
A person can have any number of skills and hackathons (including 0).
</div>

Examples:
* `add n/John Doe e/johnd@example.com tg/John gh/John s/Python:Beginner`
* `add n/Betsy Crowe e/betsycrowe@example.com tg/Betsy gh/Betsy03 s/C#:Intermediate s/Java:Advanced l/true h/NUSHack h/iNTUition`
* `add n/Alice e/alice@example.com tg/alice_tg gh/alice123 s/Docker l/true` (person is looking for a team)
* `add n/Bob e/bob@example.com tg/bobbygram gh/bobhub l/false h/HackNRoll` (person explicitly not looking for a team but interested in HackNRoll)

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [e/EMAIL] [tg/TELEGRAM_NAME] [gh/GITHUB_NAME] [s/SKILL[:LEVEL]]…​ [l/BOOLEAN] [h/HACKATHON]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing skills, new skills are added to the existing skills (i.e., adding of skills is cumulative).
* If you edit a skill that already exists (same skill name), the experience level will be updated to the new level specified.
* `LEVEL` can be: `Beginner`, `Intermediate`, or `Advanced` (case-insensitive)
* If no level is specified for a skill, it defaults to `Beginner`
* `BOOLEAN` must be: `true` or `false`
  * `l/true` marks the person as looking for a team
  * `l/false` marks the person as not looking for a team
* `h/HACKATHON` specifies hackathons the person is interested in. Can be used multiple times to replace all hackathons.

Examples:
*  `edit 1 e/johndoe@example.com tg/johndoe_tg` Edits the email address and Telegram name of the 1st person to be `johndoe@example.com` and `johndoe_tg` respectively.
*  `edit 2 n/Betsy Crower s/Python:Advanced` Edits the name of the 2nd person to be `Betsy Crower` and adds/updates the Python skill to Advanced level.
*  `edit 3 s/Docker:Intermediate` Updates the Docker skill of the 3rd person to Intermediate level (or adds it if it doesn't exist).
*  `edit 1 l/true` Marks the 1st person as looking for a team.
*  `edit 2 l/false` Marks the 2nd person as not looking for a team.
*  `edit 3 h/NUSHack h/iNTUition` Sets the 3rd person's interested hackathons to NUSHack and iNTUition.
*  `edit 4 l/true h/HackNRoll` Marks the 4th person as looking for a team and sets their interested hackathon to HackNRoll.

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

### Filtering persons : `filter`

Filters persons based on their team-looking status and/or interested hackathons.

Format: `filter [l/BOOLEAN] [h/HACKATHON [MORE_HACKATHONS]...]`

* At least one of the optional parameters must be provided.
* The `l/BOOLEAN` flag filters for persons based on their team-looking status:
  * `l/true` filters for persons who are looking for a team.
  * `l/false` filters for persons who are not looking for a team.
* `h/HACKATHON` filters for persons interested in the specified hackathon(s).
* Both parameters can be used together to find persons who match the team-looking status AND are interested in specific hackathons.
* The hackathon search is case-insensitive. e.g. `nushack` will match `NUSHack`
* Only full words will be matched e.g. `NUS` will not match `NUSHack`
* Persons matching at least one hackathon keyword will be returned (i.e. `OR` search for hackathons).

Examples:
* `filter l/true` returns all persons who are looking for a team
* `filter l/false` returns all persons who are not looking for a team
* `filter h/NUSHack` returns all persons interested in NUSHack
* `filter l/true h/NUSHack` returns all persons who are looking for a team AND interested in NUSHack
* `filter h/NUSHack iNTUition` returns all persons interested in NUSHack or iNTUition

### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Creating a team : `createTeam`

Creates a new team in the address book with specified team members.

Format: `createTeam tn/TEAM_NAME hn/HACKATHON_NAME p/INDEX [p/INDEX]…​`

* Creates a team with the specified `TEAM_NAME` and `HACKATHON_NAME`.
* Team members are specified by their `INDEX` numbers from the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​
* You can add multiple team members by specifying multiple `p/INDEX` parameters.
* Team names and hackathon names should only contain alphanumeric characters and spaces, and should not be blank.
* Duplicate teams (same team name) are not allowed.

<div class="alert alert-primary">:bulb: **Tip:**
Make sure to use the `list` command first to see the current index numbers of persons before creating a team.
</div>

Examples:
* `createTeam tn/Development Team hn/Tech Innovation 2024 p/1 p/3` creates a team called "Development Team" for "Tech Innovation 2024" hackathon with the 1st and 3rd persons as members.
* `createTeam tn/Alpha Squad hn/AI Challenge p/2 p/4 p/5` creates a team called "Alpha Squad" for "AI Challenge" hackathon with the 2nd, 4th, and 5th persons as members.

### Listing all teams : `listTeam`

Shows a list of all teams in the address book.

Format: `listTeam`

* Displays all teams with their team names, hackathon names, and member counts.
* No parameters are required for this command.

Examples:
* `listTeam` shows all teams currently stored in the address book.

### Adding a person to a team : `addPersonToTeam`

Adds an existing person to an existing team in the address book.

Format: `addPersonToTeam tn/TEAM_NAME p/INDEX`

* Adds the person at the specified `INDEX` to the team with the specified `TEAM_NAME`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …
* The team must already exist in the address book.
* A person can be a member of multiple teams.
* If the person is already a member of the specified team, an error message will be shown.
* After successfully adding a person to a team, the teams list will be displayed automatically.

<div class="alert alert-primary">:bulb: **Tip:**
Use the `listTeam` command first to see available teams, and `list` command to see the current index numbers of persons.
</div>

Examples:
* `addPersonToTeam tn/Development Team p/3` adds the 3rd person in the displayed list to the "Development Team".
* `addPersonToTeam tn/Alpha Squad p/1` adds the 1st person to the "Alpha Squad" team.

### Removing a person from a team : `removePersonFromTeam`

Removes an existing person from an existing team in the address book.

Format: `removePersonFromTeam tn/TEAM_NAME p/INDEX`

* Removes the person at the specified `INDEX` from the team with the specified `TEAM_NAME`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …
* The team must already exist in the address book.
* If the person is not a member of the specified team, an error message will be shown.
* After successfully removing a person from a team, the person's record and the team's membership are both updated, and the teams list will be displayed automatically.

Examples:
* `removePersonFromTeam tn/Tech Innovators p/2` removes the 2nd person in the displayed list from the "Tech Innovators" team.
* `removePersonFromTeam tn/Alpha Squad p/1` removes the 1st person from the "Alpha Squad" team.

### Removing a skill from a person : `removeSkill`

Removes a skill from a person in the address book.

Format: `removeSkill INDEX SKILL`

* Removes the specified `SKILL` from the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …
* The skill name is case-sensitive and must match the skill name exactly.
* If the person does not have the specified skill, an error message will be shown.

Examples:
* `removeSkill 2 Java` removes the skill "Java" from the 2nd person in the displayed list.
* `removeSkill 1 Python` removes the skill "Python" from the 1st person in the displayed list.

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action | Format, Examples |
|--------|------------------|
| **Add** | `add n/NAME e/EMAIL tg/TELEGRAM_NAME gh/GITHUB_NAME [s/SKILL[:LEVEL]]…​ [l/BOOLEAN] [h/HACKATHON]…​` <br> e.g., `add n/John Doe e/johnd@example.com tg/John gh/John s/Python:Beginner l/true h/NUSHack` |
| **Add Person to Team** | `addPersonToTeam tn/TEAM_NAME p/INDEX` <br> e.g., `addPersonToTeam tn/Development Team p/3` |
| **Remove Person from Team** | `removePersonFromTeam tn/TEAM_NAME p/INDEX` <br> e.g., `removePersonFromTeam tn/Tech Innovators p/2` |
| **Clear** | `clear` |
| **Create Team** | `createTeam tn/TEAM_NAME hn/HACKATHON_NAME p/INDEX [p/INDEX]…​` <br> e.g., `createTeam tn/Development Team hn/Tech Innovation 2024 p/1 p/3` |
| **Delete** | `delete INDEX`<br> e.g., `delete 3` |
| **Edit** | `edit INDEX [n/NAME] [e/EMAIL] [tg/TELEGRAM_NAME] [gh/GITHUB_NAME] [s/SKILL[:LEVEL]]…​ [l/BOOLEAN] [h/HACKATHON]…​`<br> e.g.,`edit 2 n/James Lee s/Docker:Intermediate l/true h/NUSHack` |
| **Filter** | `filter [l/BOOLEAN] [h/HACKATHON [MORE_HACKATHONS]...]`<br> e.g., `filter l/true`, `filter l/false`, `filter h/NUSHack`, `filter l/true h/NUSHack` |
| **Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`, `find Python Java` |
| **List** | `list` |
| **List Team** | `listTeam` |
| **Remove Skill** | `removeSkill INDEX SKILL`<br> e.g., `removeSkill 2 Java` |
| **Help** | `help` |
| **Exit** | `exit` |
