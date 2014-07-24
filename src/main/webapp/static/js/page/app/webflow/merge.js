(function(){
    ImportJavscript = {
        url:function(url){
            document.write('<script type="text/javascript" src="' + url + '"></scr' + 'ipt>');
        }
    };
})();

ImportJavscript.url('/vendor/javascripts/jquery-1.11.1.js');
ImportJavscript.url('/static/js/page/app/main.js');