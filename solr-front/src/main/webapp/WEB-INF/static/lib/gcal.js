/*! create by 2015-11-26*/ 
!function(a){function b(b,c,h){var i=b.success,j=a.extend({},b.data||{},{"start-min":d(c,"u"),"start-max":d(h,"u"),singleevents:!0,"max-results":9999}),k=b.currentTimezone;return k&&(j.ctz=k=k.replace(" ","_")),a.extend({},b,{url:b.url.replace(/\/basic$/,"/full")+"?alt=json-in-script&callback=?",dataType:"jsonp",data:j,startParam:!1,endParam:!1,success:function(b){var c=[];b.feed.entry&&a.each(b.feed.entry,function(b,d){var g,h=d.gd$when[0].startTime,i=e(h,!0),j=e(d.gd$when[0].endTime,!0),l=-1==h.indexOf("T");a.each(d.link,function(a,b){"text/html"==b.type&&(g=b.href,k&&(g+=(-1==g.indexOf("?")?"?":"&")+"ctz="+k))}),l&&f(j,-1),c.push({id:d.gCal$uid.value,title:d.title.$t,url:g,start:i,end:j,allDay:l,location:d.gd$where[0].valueString,description:d.content.$t})});var d=[c].concat(Array.prototype.slice.call(arguments,1)),h=g(i,this,d);return a.isArray(h)?h:c}})}var c=a.fullCalendar,d=c.formatDate,e=c.parseISO8601,f=c.addDays,g=c.applyAll;c.sourceNormalizers.push(function(a){("gcal"==a.dataType||void 0===a.dataType&&(a.url||"").match(/^(http|https):\/\/www.google.com\/calendar\/feeds\//))&&(a.dataType="gcal",void 0===a.editable&&(a.editable=!1))}),c.sourceFetchers.push(function(a,c,d){return"gcal"==a.dataType?b(a,c,d):void 0}),c.gcalFeed=function(b,c){return a.extend({},c,{url:b,dataType:"gcal"})}}(jQuery);