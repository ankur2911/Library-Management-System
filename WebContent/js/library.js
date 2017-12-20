/**
 * 
 */

function openTab(evt, action) {
    var i, tabcontent, tablinks;
    hidelements();
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
        document.getElementById("searchtable").style.visibility = "hidden";
        document.getElementById("searchtable").style.display = "none";
        document.getElementById("insertresultdiv").style.display = "none";
        document.getElementById("searchtablecheckin").style.visibility = "hidden";
        document.getElementById("searchtablecheckin").style.display = "none";
//      
        

    }
    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }
    document.getElementById(action).style.display = "block";
    evt.currentTarget.className += " active";
}

function hidelements(){
	 if( document.getElementById("searchtable")){

		    document.getElementById("searchtable").style.display = "none";

	}
	 if( document.getElementById("searchfinetable")){

		    document.getElementById("searchfinetable").style.display = "none";

	}if( document.getElementById("showfinetable")){

	    document.getElementById("showfinetable").style.display = "none";

	} if( document.getElementById("usershowfinetable")){
	
	    document.getElementById("usershowfinetable").style.display = "none";
	
	}
	 
}

function validationcheckin(){
	if(document.getElementById('searchfieldcheckin').value == "" || document.getElementById('searchfieldcheckin').value == null){
		alert("Please enter a search criteria!");
		return false;
	}
	return true;
}

function validationsearch(){
	if(document.getElementById('searchfield').value == "" || document.getElementById('searchfield').value == null){
		alert("Please enter a search criteria!");
		return false;
	}
	return true;
}

function validateadd(){
	if(document.getElementById('fn').value == "" || document.getElementById('fn').value == null){
		alert("Please enter First Name!");
		return false;
	}
	if(document.getElementById('ln').value == "" || document.getElementById('ln').value == null){
		alert("Please enter Last Name!");
		return false;
	}
	if(document.getElementById('ad1').value == "" || document.getElementById('ad1').value == null){
		alert("Please enter address!");
		return false;
	}
	
	 var regex = /[0-9]|\./;
	if( document.getElementById('ssn1').value.length!=9 || document.getElementById('ssn1').value == "" || document.getElementById('ssn1').value == null || !regex.test(document.getElementById('ssn1').value)){
		alert("Please enter valid SSN!");
		return false;
	}
	
	if(document.getElementById('phone1').value != "" &&  !regex.test(document.getElementById('phone1').value)){
		alert("Please enter valid Phone number!");
		return false;
	}
	return true;
	
}


function checkoutbook(isbn){
	var cardid = prompt("Please enter a valid card number for book check out", "");
	if(cardid){
	var xmlhttp;
    if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    } else {// code for IE6, IE5
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange = function() {
    	if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            //document.getElementById("myDiv").innerHTML=xmlhttp.responseText;
    		alert(xmlhttp.responseText);
    		location.reload();
        }
    }
    xmlhttp.open('GET', 'http://localhost:8080/LibraryManagement/CheckOutServlet?isbn='+isbn+'&cardid='+cardid, true);
    xmlhttp.send(null);
}

}

function checkinbook(isbnandid){
	var xmlhttp;
	var param=isbnandid.split('+');
    if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    } else {// code for IE6, IE5
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange = function() {
    	if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            //document.getElementById("myDiv").innerHTML=xmlhttp.responseText;
    		alert(xmlhttp.responseText);
    		location.reload();

        }
    }
    xmlhttp.open('GET', 'http://localhost:8080/LibraryManagement/CheckInServlet?isbn='+param[0]+'&id='+param[1], true);
    xmlhttp.send(null);

}

function payamount(loanid, amount){
	var payment = prompt("Please enter valid amount:", "");
	if(parseFloat(amount) != parseFloat(payment)){
		alert("Please enter exact due amount!");
	}else{

	var xmlhttp;
    if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    } else {// code for IE6, IE5
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange = function() {
    	if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

    		alert("Payment Successfull!");
    		location.reload();


        }
    }
    xmlhttp.open('GET', 'http://localhost:8080/LibraryManagement/PayFine?loanid='+loanid+'&payment='+payment, true);
    xmlhttp.send(null);
	}
	
}




