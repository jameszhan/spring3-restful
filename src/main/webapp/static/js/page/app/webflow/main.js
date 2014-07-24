jQuery(document).ready(function ($) {

    $.ajaxPrefilter(function(options, originalOptions, xhr){
        var token = $('meta[name="csrf-token"]').attr('content');
        if (token) xhr.setRequestHeader('X-CSRF-Token', token);
    });

    $(document)
        .on('click', '#webflowExample .params-options input[type=checkbox]', function(e){
            var textarea = $(this).closest("form").find('textarea[name=webflow]'),
                obj = textarea.val() ? JSON.parse(textarea.val()) : {},
                name = $(this).attr('name'),
                checked = $(this).prop('checked'),
                value = $(this).data('value'),
                kv = {};
            kv[name] = checked ? (value || true) : null;
            $.extend(obj, kv);
            textarea.val(JSON.stringify(obj));
        })
        .on('click', '#webflowExample button.ajax', function(e){
            var form = $(this).closest('form'),
                params = form.find('textarea[name=webflow]').val(),
                url = form.attr('action'),
                preview = form.closest('form').find('pre');
            $.post(url, {webflow: params})
                .done(function(data, statusText, xhr){
                    preview.text(JSON.stringify(data));
                })
                .fail(function(xhr, statusText, error){
                    var win = window.open('', '_blank', "width=1000,height=500");
                    win.document.write(xhr.responseText);
                });
        })
        .on('click', '#modifyCsrfToken', function(e){
            var checked = $(this).prop('checked'),
                meta = $('meta[name="csrf-token"]'),
                token = meta.attr('content'),
                original = $(this).data('original');
            if (!original) {
                $(this).data('original', token);
            }
            if (checked) {
                meta.attr('content', 'invalid_csrf_token');
            } else {
                meta.attr('content', $(this).data('original'));
            }
        });
});

/*
$(function(){
    var csrfToken = $('meta[name=csrf-token]').attr('content');
    var csrfParam = $('meta[name=csrf-param]').attr('content');
    $('form input[name="' + csrfParam + '"]').val(csrfToken);
});
*/
