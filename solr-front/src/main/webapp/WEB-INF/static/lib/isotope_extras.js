/*! create by 2015-11-26*/ 
$.Isotope.prototype._getCenteredMasonryColumns=function(){this.width=this.element.width();var a=this.element.parent().width(),b=this.options.masonry&&this.options.masonry.columnWidth||this.$filteredAtoms.outerWidth(!0)||a,c=Math.floor(a/b);c=Math.max(c,1),this.masonry.cols=c,this.masonry.columnWidth=b},$.Isotope.prototype._masonryReset=function(){this.masonry={},this._getCenteredMasonryColumns();var a=this.masonry.cols;for(this.masonry.colYs=[];a--;)this.masonry.colYs.push(0)},$.Isotope.prototype._masonryResizeChanged=function(){var a=this.masonry.cols;return this._getCenteredMasonryColumns(),this.masonry.cols!==a},$.Isotope.prototype._masonryGetContainerSize=function(){for(var a=0,b=this.masonry.cols;--b&&0===this.masonry.colYs[b];)a++;return{height:Math.max.apply(Math,this.masonry.colYs),width:(this.masonry.cols-a)*this.masonry.columnWidth}},$(function(){var a=$("#container");a.find(".element").each(function(){var a=$(this),b=parseInt(a.find(".number").text(),10);b%7%2===1&&a.addClass("width2"),b%3===0&&a.addClass("height2")}),a.isotope({itemSelector:".element",masonry:{columnWidth:120},getSortData:{symbol:function(a){return a.attr("data-symbol")},category:function(a){return a.attr("data-category")},number:function(a){return parseInt(a.find(".number").text(),10)},weight:function(a){return parseFloat(a.find(".weight").text().replace(/[\(\)]/g,""))},name:function(a){return a.find(".name").text()}}});var b=$("#options .option-set"),c=b.find("a");c.click(function(){var b=$(this);if(b.hasClass("selected"))return!1;var c=b.parents(".option-set");c.find(".selected").removeClass("selected"),b.addClass("selected");var d={},e=c.attr("data-option-key"),f=b.attr("data-option-value");return f="false"===f?!1:f,d[e]=f,"layoutMode"===e&&"function"==typeof changeLayoutMode?changeLayoutMode(b,d):a.isotope(d),!1}),$("#insert a").click(function(){var b=$(fakeElement.getGroup());return a.isotope("insert",b),!1}),$("#append a").click(function(){var b=$(fakeElement.getGroup());return a.append(b).isotope("appended",b),!1}),a.delegate(".element","click",function(){$(this).toggleClass("large"),a.isotope("reLayout")}),$("#toggle-sizes").find("a").click(function(){return a.toggleClass("variable-sizes").isotope("reLayout"),!1});var d=$("#sort-by");$("#shuffle a").click(function(){return a.isotope("shuffle"),d.find(".selected").removeClass("selected"),d.find('[data-option-value="random"]').addClass("selected"),!1})});