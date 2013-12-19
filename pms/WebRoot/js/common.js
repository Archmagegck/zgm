function _go(url){
	window.location.href=url;
}

function form_search_submit(){
	document.form_search.submit();
}

function form_search_reset(){
	document.form_search.searchKeyword="";
	document.form_search.searchMethod="";
}

function _pageSubmit(url){
	document.pageForm.action=url;
	document.pageForm.submit();
}

function image_open(url,name){
	 var left = 100;
	 var top = 60;
	 var width = 600;
	 var height = 450; 
	var newwin = window.open(url,name,'location=0,resizable=1,status=0,titlebar=0,directories=0,toolbar=0,menubar=0,scrollbars=yes,status=0,width='+width+',height='+height+',top='+top+',left='+left);
    newwin.focus();
    return;
}


//弹出新窗口
function window_open(url,name,width,height,top,left) 
{
     var docBody=parent.leftFrame.document.body;
	 var menuHeight = screen.height - parent.top.document.body.clientHeight;
	 //var left = parent.leftFrame.document.body.clientWidth+docBody.scrollLeft;
	 //var top = menuHeight - 64;
	 //var width = parent.mainFrame.document.body.clientWidth;
	 //var height = parent.mainFrame.document.body.clientHeight -50; 
	 var left = 100;
	 var top = 60;
	 var width = 840;
	 var height = 550; 

var newwin = window.open(url,name,
'location=0,resizable=0,status=0,titlebar=1,directories=0,toolbar=0,menubar=0,scrollbars=no,status=0,width='+width+',height='+height+',top='+top+',left='+left);
    newwin.focus();
    return;
}


//新闻弹出新窗口
function news_open(url,name,width,height,top,left) 
{
     //var docBody=parent.leftFrame.document.body;
	 //var menuHeight = screen.height - parent.top.document.body.clientHeight;
	 //var left = parent.leftFrame.document.body.clientWidth+docBody.scrollLeft;
	 //var top = menuHeight - 64;
	 //var width = parent.mainFrame.document.body.clientWidth;
	 //var height = parent.mainFrame.document.body.clientHeight -50; 
	 //var left = 100;
	 //var top = 60;
	 //var width = 600;
	 //var height = 450; 

var newwin = window.open(url,name,
'location=0,resizable=0,status=0,titlebar=0,directories=0,toolbar=0,menubar=0,scrollbars=no,status=0,width='+width+',height='+height+',top='+top+',left='+left);
    newwin.focus();
    return;
}

//产品弹出新窗口
function product_open(url,name,width,height,top,left) 
{    
	 var left = 100;
	 var top = 60;
	 var width = 500;
	 var height = 400; 

	var newwin = window.open(url,name,'location=0,resizable=1,status=0,titlebar=1,directories=0,toolbar=0,menubar=0,scrollbars=yes,status=0,width='+width+',height='+height+',top='+top+',left='+left);
    newwin.focus();
    return;
}

//应聘简历新窗口
function job_open(url,name,width,height,top,left) 
{    
	 var left = 100;
	 var top = 60;
	 var width = 600;
	 var height = 400; 

	var newwin = window.open(url,name,'location=0,resizable=1,status=0,titlebar=1,directories=0,toolbar=0,menubar=0,scrollbars=yes,status=0,width='+width+',height='+height+',top='+top+',left='+left);
    newwin.focus();
    return;
}

//咨询弹出新窗口
function consultation_open(url,name,width,height,top,left) 
{
     //var docBody=parent.leftFrame.document.body;
	 //var menuHeight = screen.height - parent.top.document.body.clientHeight;
	 //var left = parent.leftFrame.document.body.clientWidth+docBody.scrollLeft;
	 //var top = menuHeight - 64;
	 //var width = parent.mainFrame.document.body.clientWidth;
	 //var height = parent.mainFrame.document.body.clientHeight -50; 
	 //var left = 100;
	 //var top = 60;
	 //var width = 600;
	 //var height = 450; 

var consultationwin = window.open(url,name,
'location=0,resizable=0,status=0,titlebar=0,directories=0,toolbar=0,menubar=0,scrollbars=yes,status=0,width='+width+',height='+height+',top='+top+',left='+left);
    consultationwin.focus();
    return;
}



var myImage= new Image();

function openpic(imageURL) {
	myImage.src= imageURL; 
	testsize('myImage');
}

function testsize(img) {
	var imgobj = eval(img);
	if (imgobj.width) {
		window.open(imgobj.src,"picwin","width="+imgobj.width + ",height=" +imgobj.height);
	} else { 
		setTimeout("testsize('"+img+"')", 100 );
	}
}


















