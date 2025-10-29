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

<div markdown="span" class="alert alert-info">:information_source: **Notes about the command format:**<br>

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

Format: `add n/NAME e/EMAIL tg/TELEGRAM_NAME gh/GITHUB_NAME [s/SKILL[:LEVEL]]…​ [h/HACKATHON]…​`

* `LEVEL` can be: `Beginner`, `Intermediate`, or `Advanced` (case-insensitive)
* If no level is specified for a skill, it defaults to `Beginner`
* `h/HACKATHON` specifies hackathons the person is interested in. Can be used multiple times to add multiple hackathons.
* **Duplicate skills or hackathons are not allowed** - each skill/hackathon can only be added once
* **Hackathon names are case-insensitive** - "NUSHack", "nushack", and "NUSHACK" are treated as the same hackathon
* Skills are displayed with color-coded backgrounds in the UI:
  * **Beginner** - Light green background
  * **Intermediate** - Light yellow background  
  * **Advanced** - Light red background
* Skills are sorted by experience level (Advanced → Intermediate → Beginner), then alphabetically within each level

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person can have any number of skills and hackathons (including 0).
</div>

Examples:
* `add n/John Doe e/johnd@example.com tg/John gh/John s/Python:Beginner`
* `add n/Betsy Crowe e/betsycrowe@example.com tg/Betsy gh/Betsy03 s/C#:Intermediate s/Java:Advanced h/NUSHack h/iNTUition`
* `add n/Alice e/alice@example.com tg/alice_tg gh/alice123 s/Docker h/TechChallenge`
* `add n/Bob e/bob@example.com tg/bobbygram gh/bobhub h/HackNRoll h/AIContest`

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [e/EMAIL] [tg/TELEGRAM_NAME] [gh/GITHUB_NAME]`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* **Skills and hackathons are not affected by the edit command** - use `addSkill`/`removeSkill` for skills (both support multiple skills at once) and `addHackathon`/`removeHackathon` for hackathons.

Examples:
*  `edit 1 e/johndoe@example.com tg/johndoe_tg` Edits the email address and Telegram name of the 1st person to be `johndoe@example.com` and `johndoe_tg` respectively.
*  `edit 2 n/Betsy Crower` Edits the name of the 2nd person to be `Betsy Crower`.

### Adding skills to a person : `addSkill`

Adds one or more skills to an existing person in the address book.

Format: `addSkill p/INDEX s/SKILL[:LEVEL] [s/MORE_SKILLS[:LEVEL]]…​`

* **Command is case-insensitive**: `addSkill`, `addskill`, `ADDSKILL` all work the same way
* Adds skills to the person at the specified `INDEX` using the `p/` prefix
* The index refers to the index number shown in the displayed person list
* The index **must be a positive integer** 1, 2, 3, …​
* At least one skill must be provided using the `s/` prefix
* `LEVEL` can be: `Beginner`, `Intermediate`, or `Advanced` (case-insensitive)
* If no level is specified for a skill, it defaults to `Beginner`
* **Smart skill upgrade**: If you add a skill that already exists with a **higher** experience level, the skill will be automatically upgraded to the higher level
* If you add a skill with the **same or lower** level than what already exists, the existing skill level is kept (no downgrade)
* **Skill names are case-insensitive**: `Java`, `java`, and `JAVA` are all treated as the same skill and will be stored as lowercase
* Skill names must be lowercase alphanumeric and may include '+' or '#' symbols, but cannot start with '#' and must be at least 1 character long
* **Duplicate skills are not allowed** - each skill can only be added once per command

Examples:
*  `addSkill p/1 s/java:Advanced` Adds the Java skill with Advanced level to the 1st person. If Java already exists at Beginner or Intermediate, it will be upgraded to Advanced.
*  `addSkill p/2 s/python:Intermediate s/docker` Adds Python at Intermediate level and Docker at Beginner level (default) to the 2nd person.
*  `addSkill p/3 s/c++ s/react.js` Adds C++ and React.js skills at Beginner level to the 3rd person.
*  `ADDSKILL p/1 s/Python:Advanced` Case-insensitive command - if person 1 has python at Beginner level, it will be upgraded to Advanced.

### Adding interested hackathons to a person : `addHackathon`

Adds one or more interested hackathons to an existing person in the address book.

Format: `addHackathon p/INDEX h/HACKATHON [h/MORE_HACKATHONS]…​`

* **Command is case-insensitive**: `addHackathon`, `addhackathon`, `ADDHACKATHON` all work the same way
* Adds interested hackathons to the person at the specified `INDEX` using the `p/` prefix
* The index refers to the index number shown in the displayed person list
* The index **must be a positive integer** 1, 2, 3, …​
* At least one hackathon must be provided using the `h/` prefix
* Multiple hackathons can be added at once by specifying multiple `h/` prefixes
* **Hackathon names are case-insensitive** - "NUSHack", "nushack", and "NUSHACK" are treated as the same hackathon
* **Cannot add if already participating**: You cannot add a hackathon to the interested list if the person is already participating in that hackathon (as part of a team). An error will be shown if you try to do this.
* **Ignores duplicates**: If you try to add a hackathon that is already in the interested list, it will be silently ignored (no error).
* **Duplicate hackathons are not allowed** - each hackathon can only be added once per command
* Interested hackathons are displayed in light blue boxes in the UI under the "Interested" label.

Examples:
*  `addHackathon p/1 h/NUSHack` Adds NUSHack to the 1st person's interested hackathons list.
*  `addHackathon p/2 h/iNTUition h/HackNRoll` Adds iNTUition and HackNRoll to the 2nd person's interested hackathons.
*  `ADDHACKATHON p/3 h/TechChallenge` Case-insensitive command - adds TechChallenge to the 3rd person's interested hackathons.

### Removing interested hackathons from a person : `removeHackathon`

Removes one or more interested hackathons from an existing person in the address book.

Format: `removeHackathon p/INDEX h/HACKATHON [h/MORE_HACKATHONS]…​`

* **Command is case-insensitive**: `removeHackathon`, `removehackathon`, `REMOVEHACKATHON` all work the same way
* Removes interested hackathons from the person at the specified `INDEX` using the `p/` prefix
* The index refers to the index number shown in the displayed person list
* The index **must be a positive integer** 1, 2, 3, …​
* At least one hackathon must be provided using the `h/` prefix
* Multiple hackathons can be removed at once by specifying multiple `h/` prefixes
* **Hackathon names are case-insensitive** - "NUSHack", "nushack", and "NUSHACK" are treated as the same hackathon
* **Cannot remove if participating**: You cannot remove a hackathon from the interested list if the person is currently participating in that hackathon (as part of a team). You must first use `removePersonFromTeam` or `deleteTeam` to stop participating. An error will be shown if you try to remove a participating hackathon.
* If the hackathon is not in the interested list, an error message will be shown.

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
Only hackathons in the "Interested" list (shown in light blue boxes) can be removed with this command. Participating hackathons (shown in light orange boxes) must be removed by leaving the team first.
</div>

Examples:
*  `removeHackathon p/1 h/NUSHack` Removes NUSHack from the 1st person's interested hackathons list.
*  `removeHackathon p/2 h/iNTUition h/HackNRoll` Removes iNTUition and HackNRoll from the 2nd person's interested hackathons.
*  `REMOVEHACKATHON p/3 h/TechChallenge` Case-insensitive command - removes TechChallenge from the 3rd person's interested hackathons.

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

Format: `filter [h/HACKATHON [MORE_HACKATHONS]...]`

* At least one of the optional parameters must be provided.
* `h/HACKATHON` filters for persons interested in the specified hackathon(s).
* The hackathon search is case-insensitive. e.g. `nushack` will match `NUSHack`
* As long Hackathon contains the parameter, it will be included e.g. `filter h/AI` will match `Dev AI` and `Tech AI`
* Only full words will be matched e.g. `NUS` will not match `NUSHack`
* Persons matching at least one hackathon keyword will be returned (i.e. `OR` search for hackathons).

Examples:
* `filter h/NUSHack` returns all persons interested in NUSHack
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

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
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
* **Hackathon Management**: When a person is added to a team:
  * If the team's hackathon is in the person's **interested hackathons**, it will be automatically removed from interested
  * The team's hackathon is automatically added to the person's **participating hackathons**
  * This ensures a hackathon cannot be in both interested and participating lists simultaneously
* After successfully adding a person to a team, the teams list will be displayed automatically.

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
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
* **Hackathon Management**: When a person is removed from a team:
  * The team's hackathon is automatically removed from the person's **participating hackathons**
  * The hackathon is automatically **added back** to the person's **interested hackathons** (since they were participating, they must have been interested)
* After successfully removing a person from a team, the person's record and the team's membership are both updated, and the teams list will be displayed automatically.

Examples:
* `removePersonFromTeam tn/Tech Innovators p/2` removes the 2nd person in the displayed list from the "Tech Innovators" team.
* `removePersonFromTeam tn/Alpha Squad p/1` removes the 1st person from the "Alpha Squad" team.

### Deleting a team : `deleteTeam`

Deletes a team from the address book and removes all members from that team.

Format: `deleteTeam INDEX`

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
* `listTeam` followed by `deleteTeam 2` deletes the 2nd team in the team list.
* `deleteTeam 1` deletes the 1st team in the displayed team list.

### Removing skills from a person : `removeSkill`

Removes one or more skills from a person in the address book.

Format: `removeSkill p/INDEX s/SKILL [s/MORE_SKILLS]...`

* **Command is case-insensitive**: `removeSkill`, `removeskill`, `REMOVESKILL` all work the same way
* Removes the specified skill(s) from the person at the specified `INDEX` using the `p/` prefix
* The index refers to the index number shown in the displayed person list
* The index **must be a positive integer** 1, 2, 3, …
* At least one skill must be provided using the `s/` prefix
* Multiple skills can be removed at once by specifying multiple `s/` prefixes
* **Skill names are case-insensitive**: `Java`, `java`, and `JAVA` are all treated as the same skill
* Skill names must be lowercase alphanumeric and may include '+' or '#' symbols
* If the person does not have any of the specified skills, an error message will be shown

Examples:
* `removeSkill p/2 s/java` removes the skill "java" from the 2nd person in the displayed list.
* `removeSkill p/1 s/python s/docker` removes the skills "python" and "docker" from the 1st person in the displayed list.
* `REMOVESKILL p/3 s/java s/python s/c++` removes the skills "java", "python", and "c++" from the 3rd person in the displayed list.

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/mate.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
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
| **Add** | `add n/NAME e/EMAIL tg/TELEGRAM_NAME gh/GITHUB_NAME [s/SKILL[:LEVEL]]…​ [h/HACKATHON]…​` <br> e.g., `add n/John Doe e/johnd@example.com tg/John gh/John s/Python:Beginner h/NUSHack` |
| **Add Hackathon** | `addHackathon p/INDEX h/HACKATHON [h/MORE_HACKATHONS]…​` <br> e.g., `addHackathon p/1 h/NUSHack h/iNTUition` |
| **Add Person to Team** | `addPersonToTeam tn/TEAM_NAME p/INDEX` <br> e.g., `addPersonToTeam tn/Development Team p/3` |
| **Add Skill** | `addSkill p/INDEX s/SKILL[:LEVEL] [s/MORE_SKILLS[:LEVEL]]…​` <br> e.g., `addSkill p/1 s/java:Advanced s/python:Intermediate` |
| **Clear** | `clear` |
| **Create Team** | `createTeam tn/TEAM_NAME hn/HACKATHON_NAME p/INDEX [p/INDEX]…​` <br> e.g., `createTeam tn/Development Team hn/Tech Innovation 2024 p/1 p/3` |
| **Delete** | `delete INDEX`<br> e.g., `delete 3` |
| **Delete Team** | `deleteTeam INDEX`<br> e.g., `deleteTeam 1` |
| **Edit** | `edit INDEX [n/NAME] [e/EMAIL] [tg/TELEGRAM_NAME] [gh/GITHUB_NAME]`<br> e.g.,`edit 2 n/James Lee e/james@example.com` |
| **Filter** | `filter [h/HACKATHON [MORE_HACKATHONS]...]`<br> e.g., `filter h/NUSHack` |
| **Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`, `find Python Java` |
| **List** | `list` |
| **List Team** | `listTeam` |
| **Remove Hackathon** | `removeHackathon p/INDEX h/HACKATHON [h/MORE_HACKATHONS]…​` <br> e.g., `removeHackathon p/1 h/NUSHack h/iNTUition` |
| **Remove Person from Team** | `removePersonFromTeam tn/TEAM_NAME p/INDEX` <br> e.g., `removePersonFromTeam tn/Tech Innovators p/2` |
| **Remove Skill** | `removeSkill p/INDEX s/SKILL [s/MORE_SKILLS]...`<br> e.g., `removeSkill p/2 s/java s/python` |
| **Help** | `help` |
| **Exit** | `exit` |
