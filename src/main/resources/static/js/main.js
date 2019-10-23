$(document).ready(function(){
	displayTable(0);
});

function displayTableWithTheSamePage(){
	var activePage = 0;
	activePage = $("[class='page-item active']").text();
	if(activePage>1){
		displayTable(activePage-1);		
	}else{
		displayTable(0);
	}

}
		
function displayTable(pageNumber) {
	
	$('#dataTable tbody').empty();
	$('#pages').empty();
	$('#summary').empty();
	
	var jsonEP = '/peopleInfo?page='+ pageNumber;
    var data = $.getJSON(jsonEP, function(json) {
    var people = json.sortedListAccordingToAge.content;
    var oldestPeople=json.theOldestPeopleWithPhone;
    var numberOfPeople=json.numberOfPeople;
    var personId=0;
    var tbody = "";
    
    var pagesNumber = json.sortedListAccordingToAge.totalPages;
    var currentPageNumber = json.sortedListAccordingToAge.pageable.pageNumber;
    var pagesLinks = "";
    
    $.each(people, function(key, value) { 
        tbody += '<tr>';
        $.each(value, function(k, v) {
           tbody += '<td>' + v + '</td>';
        });
        tbody += '<td><button id='+this.id+' type="button" class="btn btn-danger" onclick="deleteClick(this)">Delete</button></td>';
        tbody += "</tr>";
    });
    $('#dataTable tbody').append(tbody);
    
    for (var n = 0; n < pagesNumber; ++ n)
    	if(n==currentPageNumber){
    		pagesLinks +=  ' <li class="page-item active"><a class="page-link" onclick="displayTable(' +n+ ')" id="p'+n+'">' + (n+1) + '</a></li>';	
    	}else{
        	pagesLinks +=  ' <li class="page-item"><a class="page-link" onclick="displayTable(' +n+ ')" id="p'+n+'">' + (n+1) + '</a></li>';
    	}
    $('#pages').append(pagesLinks);
    
    $('#summary').append('<h2>Summary</h2><p>Number of all people in database: <strong>'+numberOfPeople+'</strong></p>');
    	 $('#summary').append('<p><strong>The oldest person/people with phone number</strong></p>');
    	 $.each(oldestPeople, function(k, v) {
    		 $('#summary').append('<p> First name: ' + this.firstName + ', Last name: ' + this.lastName + ', Age: ' + this.age + ', Phone: ' + this.phoneNumber + '</p>');
    	 });
    });
};


$('#uploadForm').submit(function(event) {
    var formElement = this;
    var formData = new FormData(formElement);
    $('#pNumberOfRecords').empty();
    $('#pNumberOfFailures').empty();
    $('#pFailures').empty();
    $('#pError').empty();
    
    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "/uploadFile",
        data: formData,
        dataType: "json",
        processData: false,
        contentType: false,
        success: function (response) {
            console.log(response);
            var failures = response.failures;
            var filuresNumber = failures.length;
            var peopleImpoerted = response.numberOfPeople;
            $('#pNumberOfRecords').append('Number of people successfully imported to database from file <strong>'+peopleImpoerted+'</strong>');         
            $('#pNumberOfFailures').append('Number of failures (empty rows not included): ' + filuresNumber);
        
            $('#uploadSuccess').show();
            if(filuresNumber>0){
                $.each(failures, function(k,v) { 
               	 $('#pFailures').append('<br>'+v);
                }); 
                $('#uploadWarning').show();
            }
            failures=0;
            displayTableWithTheSamePage();
        },
        error: function (response){
            var r = jQuery.parseJSON(response.responseText);
            $('#pError').append("Error occured " + r.message);
            $('#uploadError').show();
        }
    });

    event.preventDefault();
});

function deleteClick(obj) {
    var rowID = $(obj).attr('id');
    $('#pError').empty();
    $.ajax({
        url: '/deletePerson?id='+rowID,
        type: 'DELETE',
        success: function(result) {
        	$('#pError').append(result.message);
        	$('#uploadError').show(); 
            displayTableWithTheSamePage();
        }
    });

}

function deleteAll(){
	$('#pError').empty();
    $.ajax({
        url: '/deleteAll',
        type: 'DELETE',
        success: function(result) {
        	$('#pError').append(result.message);
        	$('#uploadError').show(); 
            displayTable(0);
        }
    });

}

function searchPeople(lastName){
	$('#dataTable tbody').empty();
	$('#pages').empty();
	
	if(!lastName){
		displayTable(0);
		return;
	}
	
	var jsonEP = '/peopleByName?lastName='+ lastName;
    var data = $.getJSON(jsonEP, function(data) {
    	console.log(data);
        var personId=0;
        var tbody = "";
        
        
        $.each(data, function(key, value) { 
            tbody += '<tr>';
            $.each(value, function(k, v) {
               tbody += '<td>' + v + '</td>';
            });
            tbody += '<td><button id='+this.id+' type="button" class="btn btn-danger" onclick="deleteClick(this)">Delete</button></td>';
            tbody += "</tr>";
        });
        $('#dataTable tbody').append(tbody);
    });
    
}

$(function(){
    $("[data-hide]").on("click", function(){
        $(this).closest("." + $(this).attr("data-hide")).hide();
    });
});
