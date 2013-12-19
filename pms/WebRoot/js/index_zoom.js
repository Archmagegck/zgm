function doZoom(size){
	document.getElementById('doZoom').style.fontSize=size+'px'
	}

	function setimagesize(){
		var arEls = document.getElementsByTagName("img");
		for(var i=0;i<arEls.length;i++){
			
			if(arEls[i].width >700 )
			{
				arEls[i].height = (arEls[i].height * (700/arEls[i].width) );
				arEls[i].width=700 ;
			}
		}		
	}