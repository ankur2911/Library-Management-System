<%@page import="VO.FineTableVO"%>
<%@page import="VO.SearchCheckinVO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="VO.Searchfields"%>
<%@page import="javax.swing.text.Document"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Library Management System</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

  <script type="text/javascript" src="../js/library.js"></script>
  
  <link rel="stylesheet" href="../css/library.css"></head>

 
<body>


<div class="jumbotron text-center">
  <h1>Library Management System</h1>
</div>

<div class="container">

 
  
  <div class="row">
  <div class="tab">
     
    <div class="col-sm-3">
      <h3>Search a Book</h3>
      <br/>
        <button type="button" class="btn btn-success btn-lg" onclick="openTab(event, 'Search')">Search </button>
      
    </div>
    <div class="col-sm-3">
      <h3>Add a Borrower</h3>
     <br/>
        <button type="button" class="btn btn-success btn-lg" onclick="openTab(event, 'Add')">Add a Borrower</button>
    
    </div>
     
     <div class="col-sm-3">
  
        <h3>Check-in a book</h3>
  <br/>
          <button type="button" class="btn btn-success btn-lg" onclick="openTab(event, 'Check-in')">Check-in a book</button>
  
  </div>
    
    
    <div class="col-sm-3">
      <h3>Refresh Fine</h3>        
    <br/>
        <button type="button" class="btn btn-success btn-lg" onclick="openTab(event, 'ShowFines')">Show Fines</button>
     
    </div>
  </div>

</div>

<div class="row">

<!-- Search div -->

<div id="Search" class="tabcontent">
<br/><br/>
<form method="GET" action="http://localhost:8080/LibraryManagement/SearchServlet" onsubmit="return validationsearch();">
  <p><input type="text" style="width: 250px; height: 40px;" id="searchfield" name="searchfieldname"/></p>
  <p><button type="submit" class="btn btn-primary" >Search</button>  </p>
  
 </form>
  <br/><br/>
  </div>
 
  <div id="searchtable">
  <br/><br/>
   <% if((String)session.getAttribute("searchval") =="searchval") {
  
  		session.removeAttribute("searchval");
  %>
  <p>Search Results</p>            
  <table class="table table-striped">
    <thead>
      <tr>
        <th>Book ISBN</th>
        <th>Book Title</th>
        <th>Book Author</th>
        <th>Availability</th>
      </tr>
    </thead>
    <tbody>
    
    <%
	Searchfields searchVO = (Searchfields)session.getAttribute("searchvalues");
    ArrayList<String> isbn = new ArrayList<String>();
	ArrayList<String> name = new ArrayList<String>();

	ArrayList<String> title = new ArrayList<String>();
	ArrayList<String> available = new ArrayList<String>();
	
	isbn=searchVO.getIsbn();
	name=searchVO.getName();
	title=searchVO.getTitle();
	available=searchVO.getAvailable();
	
	for(int i=0;i<isbn.size();i++){
    %>
      <tr>
       <td><%= isbn.get(i)%></td>
       
       <td><%= title.get(i)%></td>

       <td><%= name.get(i)%></td>

       <td><%= available.get(i)%></td>
       
       <% if(available.get(i).equals("Yes")){ %>
       
       <td> <input type="button" name="checkout" id="<%= isbn.get(i)%>"  onclick="checkoutbook(this.id);" value="Check-out"></td>
       	
       <%}else if(available.get(i).equals("No")) {%>
 		
 	   <td></td>
       	
       <%}%>
      </tr>
    <%} %>  
    </tbody>
  </table>
  <% }
   
   else if((String)session.getAttribute("searchval") =="searchvalempty"){
 		session.removeAttribute("searchval");

   %>
   
   No Results found for this criteria!
   
   <%} %>
</div>



<!-- add div -->

<div id="Add" class="tabcontent">



 <br/><br/> <h4>Add a Borrower</h4>
  <br/>
 
    
  
  <form method="GET" action="http://localhost:8080/LibraryManagement/AddServlet" style="text-align: left; margin-left: 450px;" onsubmit="return validateadd();">
  <table>
  <tr>
  
<td>    <label for="fname">First Name:</label></td>
<td>
    <input type="text" id="fn" name="fname">
  </td>
  </tr>
  <tr>
<td>    <label for="lname">Last Name:</label></td>
 <td>   <input type="text" id="ln" name="lname">
  </td>
  </tr>
    <tr>
<td>    <label for="address">Address:</label></td>
 <td>   <input type="text" id="ad1" name="ad">
  </td>
  </tr>
  
  <tr>
<td>    <label for="email">SSN:</label></td>
  <td>  <input type="text"  id="ssn1" name="ssn">
  </td>
  </tr>
  <tr>
<td>    <label for="phone">Phone Number:</label></td>
   <td> <input type="text"  id="phone1" name="phone">
  </td>
  </tr>
  <tr>
 <td> <button type="submit" class="btn btn-default" style="align: center">Submit</button></td>
  </tr>
  </table>
</form>
</div>
<div id="insertresultdiv">
 <%
System.out.println("value: "+ (String)session.getAttribute("insertflag"));


if((String)session.getAttribute("insertflag") =="success") {
%>

<br/><br/>
Borrower added successfully! <br/>
Card id: <%=(String)session.getAttribute("cardid")%>

<%
session.removeAttribute("insertflag");
}else if((String)session.getAttribute("insertflag") =="fail"){ %>
Borrower was not added due to some constraint, kindly re-check the values you enter!
<%
session.removeAttribute("insertflag");
} %> 
</div>

<!-- Refresh div -->

<div id="ShowFines" class="tabcontent">
<br/><br/>
<form method="GET" action="http://localhost:8080/LibraryManagement/ShowFineServlet" >
  <p><button type="submit" class="btn btn-primary" >Show All fines</button>  </p>
  
 </form> 
 
 
 <form method="GET" action="http://localhost:8080/LibraryManagement/ShowUserServlet" >
  <p><button type="submit" class="btn btn-primary" >Show fines for each user</button>  </p>
  
 </form>
 
 <form method="GET" action="http://localhost:8080/LibraryManagement/RefreshFineServlet" >
  <p><button type="submit" class="btn btn-primary" >Refresh fines</button>  </p>
  
 </form> 
  
  <br/><br/>
  </div>
  
  
  <!-- user fine table -->
  
  <div id="usershowfinetable">
  
  <%if(((String)session.getAttribute("userfinetable")) == "userfinetable"){
	  System.out.println("inside show user table case");
	  session.removeAttribute("userfinetable"); %>
  <p>Search Results</p>            
  <table class="table table-striped">
    <thead>
      <tr>
     
        <th>Borrower Name</th>
        <th>Card ID</th>
        <th>Fine Amount till Date</th>
   
      </tr>
    </thead>
    <tbody>
    
     <%
     FineTableVO finetableVO = (FineTableVO)session.getAttribute("userfinetableresult");

     ArrayList<String> BName = new ArrayList<String>();
     ArrayList<Float> FineAmt = new ArrayList<Float>();
     ArrayList<Integer> cardid = new ArrayList<Integer>();


   
     BName=finetableVO.getBorrower();
     FineAmt=finetableVO.getFineamt();
     cardid=finetableVO.getCardid();
     for(int i=0;i<cardid.size();i++){
    %>
    <tr>

    <td><%=BName.get(i) %></td>
    <td><%=cardid.get(i) %></td>
    <td>$<%=FineAmt.get(i) %></td>
    </tr>
    
    <%} %>
    </tbody>
    </table>
  <%}else if(((String)session.getAttribute("userfinetable")) == "finetableempty"){ session.removeAttribute("userfinetable");%>
  
  No Data to Display!
  
  <%} %>
    </div>
  
  <!-- user fine table ends -->
  
    <div id="showfinetable">
  
  <%if(((String)session.getAttribute("finetable")) == "finetable"){
	  System.out.println("inside show table case");
	  session.removeAttribute("finetable"); %>
  <p>Search Results</p>            
  <table class="table table-striped">
    <thead>
      <tr>
        <th>Book ISBN</th>
        <th>Loan Id</th>
        <th>Borrower Name</th>
        <th>Card ID</th>
        <th>Fine Amount</th>
        <th>Paid</th>
      </tr>
    </thead>
    <tbody>
    
     <%
     FineTableVO finetableVO = (FineTableVO)session.getAttribute("finetableresult");
     ArrayList<String> isbn = new ArrayList<String>();
     ArrayList<String> LoanID = new ArrayList<String>();
     ArrayList<String> BName = new ArrayList<String>();
     ArrayList<Float> FineAmt = new ArrayList<Float>();
     ArrayList<String> Paid = new ArrayList<String>();
     ArrayList<String> datein = new ArrayList<String>();
     ArrayList<Integer> cardid = new ArrayList<Integer>();


     isbn=finetableVO.getIsbn();
     LoanID= finetableVO.getLoanID();
     BName=finetableVO.getBorrower();
     FineAmt=finetableVO.getFineamt();
     Paid=finetableVO.getPaid();
     datein=finetableVO.getDatein();
     cardid=finetableVO.getCardid();
     for(int i=0;i<isbn.size();i++){
    %>
    <tr>
    <td><%=isbn.get(i) %></td>
    <td><%=LoanID.get(i) %></td>
    <td><%=BName.get(i) %></td>
    <td><%=cardid.get(i) %></td>
    <td>$<%=FineAmt.get(i) %></td>
    <td><%=Paid.get(i)%></td>
    <%
    
    System.out.println("in jsp datein.get(i): "+ datein.get(i));
    if(Paid.get(i)=="No" && null!=datein.get(i)){ %>
    <td><button onclick="payamount('<%=LoanID.get(i) %>','<%=FineAmt.get(i) %>');">Pay Amount</button></td>
    <%} %>
    </tr>
    
    <%} %>
    </tbody>
    </table>
  <%}else if(((String)session.getAttribute("finetable")) == "finetableempty"){ 
  session.removeAttribute("finetable");%>
  
  No Data to Display!
  
  <%} %>
    </div>
  
  <%if(((String)session.getAttribute("finetablerefresh")) == "finetablerefresh"){
	  System.out.println("inside refresh table case");
	  session.removeAttribute("finetablerefresh"); %>
  <div id="searchfinetable" > 
  
           
Fine Refresh Successful! 
   
  </div>
  <%} else if (((String)session.getAttribute("finetablerefresh")) == "finetablerefreshempty"){
  session.removeAttribute("finetablerefresh");%>
Fine Refresh encountered a problem!
 
<%} %>
  


<!-- Checkin div -->

<div id="Check-in" class="tabcontent">
<br/><br/>
<form method="GET" action="http://localhost:8080/LibraryManagement/CheckinSearch" onsubmit="return validationcheckin();">
  <p><input type="text" style="width: 250px; height: 40px;" id="searchfieldcheckin" name="searchfieldnamecheckin"/></p>
  <p><button type="submit" class="btn btn-primary" >Search for book</button>  </p>
  
 </form>
  <br/><br/>
  </div>

 <div id="searchtablecheckin">
  <br/><br/>
   <% if((String)session.getAttribute("searchvalcheckin") =="searchvalcheckin") {
  
  		session.removeAttribute("searchvalcheckin");
  %>
  <p>Search Results</p>            
  <table class="table table-striped">
    <thead>
      <tr>
        <th>Book ISBN</th>
        <th>Borrower Name</th>
        <th>Card ID</th>
      </tr>
    </thead>
    <tbody>
    
    <%
	SearchCheckinVO searchVO = (SearchCheckinVO)session.getAttribute("searchcheckinvalues");
    ArrayList<String> isbn = new ArrayList<String>();
	ArrayList<String> name = new ArrayList<String>();
	ArrayList<String> checkout = new ArrayList<String>();

	
	isbn=searchVO.getIsbn();
	name=searchVO.getName();
	//title=searchVO.getTitle();
	//available=searchVO.getAvailable();
	checkout=searchVO.getCheckoutBy();
	
	for(int i=0;i<isbn.size();i++){
    %>
      <tr>
       <td><%= isbn.get(i)%></td>
       
       <td><%= name.get(i)%></td>

       <td><%= checkout.get(i)%></td>
     
       <td>  <button onclick="checkinbook(this.id)" id="<%=isbn.get(i)%>+<%=checkout.get(i)%>">Check-In</button>  </td>
      </tr>
    <%} %>  
    </tbody>
  </table>
  <% }
   
   else if((String)session.getAttribute("searchvalcheckin") =="searchvalcheckinempty"){
 		session.removeAttribute("searchvalcheckin");

   %>
   
   No Results found for this criteria!
   
   <%} %>
</div>

<!-- Check in div ends -->




</div>
</div>

</body>
</html>