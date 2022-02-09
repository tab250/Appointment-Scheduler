# Customer-Relations-Manager

This is a Full-Stack project that uses MySQL on the backend to save information to the database.
Functionality includes:
* Login page that checks if the user's timezone and if they are in a French speaking area, if true,
the login page is changed to French
* Login page keeps track of all login attempts and writes them to a text file. A file is generated 
if one does not exist.
* Login in within work hours, the user cannot login outside of business hours.
* On the Main screen, the user is able to see and sort through all customers and appointments pertaining
to them. 
* The user can create, update and delete both Customers and Appointments as needed.
* Upon logging in, if the user has a meeting within 15 minutes, a pop-up window appears to alert them.

What was learned:
* The Create, Read, Update, and Delete (CRUD) functions of a database. 
* How to write a Java Doc.
* How to write Prepared Statements for SQL database.

What can be improved:
* The Controller files can be refactored to remove the level of control they have. This can lead to 
security issues.
* A more robust UI could be made to have additional features.
