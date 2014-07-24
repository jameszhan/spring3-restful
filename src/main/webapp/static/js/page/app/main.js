jQuery(function($){

    var ajaxLogger = $('#ajaxLogger');
    ajaxLogger.hide();

    $(document).ajaxComplete(function(e, xhr, options){
        var httpVersion = 'HTTP/1.1',
            headers = xhr.getAllResponseHeaders(),
            str
            name;
        str = requestStartContent(options.type, options.url, httpVersion);
        if (options.allRequestHeaders) {
            for (name in options.allRequestHeaders) {
                str += name + ': ' + options.allRequestHeaders[name] + '\r\n';
            }
        }
        str += '\r\n\r\n';
        str += httpVersion + ' ' + xhr.status + ' ' + xhr.statusText + '\r\n';
        str += headers;
        ajaxLogger.text(str);
        ajaxLogger.show();
    });

    function requestStartContent(method, url, httpVersion){
        var regex = /^https?:\/\/([^/]+)(.+)$/,
            t, str;
        str = method + ' ';
        if (t = regex.exec(url)) {
            str += t[2] + ' ' + httpVersion + '\r\n';
            str += 'Host: ' + t[1] + '\r\n';
        } else {
            str += url + ' ' + httpVersion + '\r\n';
        }
        return str;
    }
});