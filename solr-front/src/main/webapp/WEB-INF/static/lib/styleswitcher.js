/*! create by 2015-11-26*/ 
function getCookie(a){var b=new RegExp(a+"=[^;]+","i");return document.cookie.match(b)?document.cookie.match(b)[0].split("=")[1]:null}function setCookie(a,b,c){var d=new Date;"undefined"!=typeof c?d.setDate(d.getDate()+parseInt(c)):d.setDate(d.getDate()-5);document.cookie=a+"="+b+"; expires="+d.toGMTString()+"; path=/"}function deleteCookie(a){setCookie(a,"moot")}function setStylesheet(a,b){var c,d,e=[""];for(c=0;d=document.getElementsByTagName("link")[c];c++)"alternate stylesheet"==d.getAttribute("rel").toLowerCase()&&d.getAttribute("title")&&(d.disabled=!0,e.push(d),d.getAttribute("title")==a&&(d.disabled=!1));if("undefined"!=typeof b){var f=Math.floor(Math.random()*e.length);e[f].disabled=!1}return"undefined"!=typeof b&&""!=e[f]?e[f].getAttribute("title"):""}function chooseStyle(a,b){document.getElementById&&(setStylesheet(a),setCookie("mysheet",a,b))}function indicateSelected(a){if(null!=selectedtitle&&(void 0==a.type||"select-one"==a.type))for(var a="select-one"==a.type?a.options:a,b=0;b<a.length;b++)if(a[b].value==selectedtitle){"OPTION"==a[b].tagName?a[b].selected=!0:a[b].checked=!0;break}}var manual_or_random="manual",randomsetting="3 days";if("manual"==manual_or_random){var selectedtitle=getCookie("mysheet");document.getElementById&&null!=selectedtitle&&setStylesheet(selectedtitle)}else"random"==manual_or_random&&("eachtime"==randomsetting?setStylesheet("","random"):"sessiononly"==randomsetting?null==getCookie("mysheet_s")?document.cookie="mysheet_s="+setStylesheet("","random")+"; path=/":setStylesheet(getCookie("mysheet_s")):-1!=randomsetting.search(/^[1-9]+ days/i)&&(null==getCookie("mysheet_r")||parseInt(getCookie("mysheet_r_days"))!=parseInt(randomsetting)?(setCookie("mysheet_r",setStylesheet("","random"),parseInt(randomsetting)),setCookie("mysheet_r_days",randomsetting,parseInt(randomsetting))):setStylesheet(getCookie("mysheet_r"))));