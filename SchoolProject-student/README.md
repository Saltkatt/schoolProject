# Schoolproject

Small project for teaching purposes.

* Wildfly
* JEE 8 
* Java 11
* Git
* Maven
* MySQL

## Wildfly configuration

Install any Wildfly release you want. I use 18.

Add a user, and place the school.cli script under the bin folder.<br>
Create database school. The script will need a mysql connector under `C:\Users`
to work. 

The script is predefined for `mysql.connector-java-8.0.12.jar`. Change location and version for your own liking.

Start Wildfly, and once running, open a new prompt, and go to the bin folder.<br>
Write `jboss-cli -c --file=school.cli`

It should say outcome success. Write `jboss-cli -c --command=:reload` to restart the server.


## Port 8095
http://localhost:8095/school/

## Endpoints
 
list all students:
GET /student

list student by name:
GET /student/{firstname}

list student by email:
GET /student/find/{email}

add student:
POST /student/add

{
	"firstname":"", 
	"lastname":"",
	"email": ""
}

delete student:
DELETE /student/{email}

delete teacher:
DELETE /teacher/{email}

delete subject:
DELETE /subject/{title}

update student:
PUT /student?firstname={firstname}&lastname={lastname}&email={email}

partial update of firstname:
PATCH /student/{email}

{
	"firstname":"input new name", 
	"lastname":"lastname",
	"email": "email"
}

updateJoinTableStudent
PATCH /subject/title

{
	"email": "email"
}


updateJoinTableTeacher
PATCH /subject/teacher/title

{
	"email": "email"
}



## Pair programming
Worked with Filip Christofferson on some of the solutions.
There are comments in the code indicating where.

## Problems
Noticed too late that you cannot update the email as the SQL-statement uses WHERE email to update.
This could be fixed by changing from query parameter to path parameter in update.

