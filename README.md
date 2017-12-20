# Library-Management-System

USER MANUAL 


This Library management web application is a single page web application, i.e. all the functionalities can be accessed on a single page without having to navigate to any other page.

Home Page:

On the home screen, there are four options for the user for each functionality that is offered:
1.	Search for books/ Checkout
2.	Add a borrower
3.	Check in a book
4.	Refresh/View fines

Search Book/ Checkout

When the user clicks on search a book, a single search field appears with a search button. User can enter a keyword, on the basis of which the search results will be rendered on the screen. The keyword can be the following: Book ISBN, Book Title or book author.

When the search result is rendered, the available books has a checkout button along with them, which can be used to check out a book

Check-out functionality:

The user which have less than 3 books currently issued, can check out a new book using the button along with each row in search book table. The librarian can enter the card number, when prompted, of the user and the book will be issued on his name.

Add a borrower

When user clicks on Add a borrower, he/she can create a new borrower via a form that is displayed. The user will enter the following for new borrower:
1.	Name -> First name and last name
2.	 address
3.	ssn
4.	optional phone number

If the borrower does not already exist, a new borrower will be added, and his card id will be displayed. The check for already existing borrower is implemented using ssn.



 Fines module 

When the user clicks on Show fines button on the screen, he is shown three options:
1.	Show all fines
2.	Show fine for each user
3.	Refresh fine

When the user clicks on ‘Show all fines’, a table containing the fine details for each book is rendered. Along with the book ISBN, Loan id, borrower name, borrower card id and loan amount is shown. Also, there is a column informing if the fine has been paid by the borrower for that book or not.
The borrower can only pay the fine, if the book has been checked in using the check in module.

When the user clicks on ‘Show fine for each user’, a table containing the combined sum amount for each user is rendered.

When the user clicks on ‘Refresh fine’, depending upon the late dates, the fine amount for each book is updated and the table containing all the fines is updated.
