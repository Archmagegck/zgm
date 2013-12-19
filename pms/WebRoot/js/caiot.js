function iniSelect(url, selectId){
	$("#"+selectId).empty();
	$("#"+selectId).html("<option value=\"\">---请选择---</option>");
	$.getJSON(url + "?d="+Math.random(),
		function(data){
		var html = "";
			$.each(data, function(i, type){
				html += ("<option value='"+ type['id'] +"'>" + type['name'] +"</option>");
			});
			$("#"+selectId).append(html);
		}
	);
}

function iniSelectName(url, selectId){
	$("#"+selectId).empty();
	$("#"+selectId).html("<option value=\"\">---请选择---</option>");
	$.getJSON(url + "?d="+Math.random(),
		function(data){
		var html = "";
			$.each(data, function(i, type){
				html += ("<option value='"+ type['name'] +"'>" + type['name'] +"</option>");
			});
			$("#"+selectId).append(html);
		}
	);
}

/**
 * 根据url获得的json生成select的option 用在edit的ready中
 */
function iniList(url, selectId, value){
	$("#"+selectId).empty();
	var html = "<option value=''>---请选择---</option>";
	$.getJSON(url + "?d="+Math.random(),
		function(data){
			$.each(data, function(i, type){
				if(type['id'] == value)
				{
					html += ("<option value='"+ type['id'] +"' selected>" + type['name'] +"</option>");
				}
				else
				{
					html += ("<option value='"+ type['id'] +"'>" + type['name'] +"</option>");
				}
			});
			$("#"+selectId).append(html);
		}
	);
}

function getISOYearWeek(date){   
    var commericalyear=getCommerialYear(date);   
    var date2=getYearFirstWeekDate(commericalyear);      
    var day1=date.getDay();      
    if(day1==0) day1=7;      
    var day2=date2.getDay();      
    if(day2==0) day2=7;      
    var d = Math.round((date.getTime() - date2.getTime()+(day2-day1)*(24*60*60*1000)) / 86400000);        
    return Math.ceil(d / 7)+1;    
}   
   
function getYearFirstWeekDate(commericalyear){   
    var yearfirstdaydate=new Date(commericalyear, 0, 1);      
    var daynum=yearfirstdaydate.getDay();    
    var monthday=yearfirstdaydate.getDate();   
    if(daynum==0) daynum=7;   
    if(daynum<=4){   
        return new Date(yearfirstdaydate.getFullYear(),yearfirstdaydate.getMonth(),monthday+1-daynum);   
    }else{   
        return new Date(yearfirstdaydate.getFullYear(),yearfirstdaydate.getMonth(),monthday+8-daynum)   
    }    
}   
   
function getCommerialYear(date){   
    var daynum=date.getDay();    
    var monthday=date.getDate();   
    if(daynum==0) daynum=7;   
    var thisthurdaydate=new Date(date.getFullYear(),date.getMonth(),monthday+4-daynum);   
    return thisthurdaydate.getFullYear();   
}   
