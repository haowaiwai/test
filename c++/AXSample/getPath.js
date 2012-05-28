function getPath(type){
 		var fileRoute="D:/share/ResourcePath.xml"
 		xmlDoc = new ActiveXObject('Msxml2.DOMDocument');   
    xmlDoc.async=false;   
    xmlDoc.load(fileRoute);
    var path="";
    var sufix = "";
    var state = xmlDoc.readyState;
    if (state == 4){      
        var oNodePath = xmlDoc.selectNodes("//resource/path");    
        path = oNodePath[0].childNodes[0].text;
        if(type=="sms" ){
        	sufix ="sms.xml";
        }else if(type=="pic"){
        	sufix ="pic\\";
        }
       path = path+sufix;
       return path;
    }
}