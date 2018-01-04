var ajaxUrl = "ajax/admin/users/";
var datatableApi;

// $(document).ready(function () {
$(function () {
    datatableApi = $("#datatable").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "name"
            },
            {
                "data": "email"
            },
            {
                "data": "roles"
            },
            {
                "data": "enabled"
            },
            {
                "data": "registered"
            },
            {
                "defaultContent": "Edit",
                "orderable": false
            },
            {
                "defaultContent": "Delete",
                "orderable": false
            }
        ],
        "order": [
            [
                0,
                "asc"
            ]
        ]
    });
    makeEditable();
});

changeUserState = function (elem) {
    var userId = $(elem).parent().parent().attr("id");
    var state = $(elem).is(':checked');
    var data = "state=" + state;
    $.ajax({
        url: ajaxUrl + userId
        , type: "PUT"
        , data: data
        , success: function () {
            $(elem).parent().parent().toggleClass("disabled");
            successNoty("changed");
        }
    });
}

function updateTable() {
    $.get(ajaxUrl, function (data) {
        datatableApi.clear().rows.add(data).draw();
    });
}