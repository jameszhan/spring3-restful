jQuery(document).ready(function ($) {

    $(document)
        .on('api:success', '[data-remote]', function (event, data, status, xhr) {
            if (data.success) {
                $(this).closest('.modal').modal('hide');
                window.setTimeout(function(){
                    window.location.reload();
                }, 3000);
            } else {
                alert(data.errorCode + ": " + data.errorMessage);
            }
        })
        .on('api:complete', '[data-remote]', function (event, xhr, status) {
            //console.log(this);
            //$(this).closest('.modal').modal('hide');
        })
        .on('hidden.bs.modal', '.dynamic.modal', function (e) {//Fix the bugs of Bootstrap Modal
            $(e.target).removeData('bs.modal');
            $(this).find('[data-toggle=popover]').popover('hide');
        })
        .on('shown.bs.modal', '#lgModal.dynamic.modal', function(e){
            $(this).find('[data-toggle=popover]').popover('hide');
        })
        .on('click', '[data-preview]', function(e){
            var url = $(this).data('preview');
            $.get(url)
                .done(function(data){
                    $('#previewModel').modal('show').find('.modal-body pre').html(JSON.stringify(data.data));
                })
                .fail(function(){
                    console.log(arguments);
                });
        });
});
