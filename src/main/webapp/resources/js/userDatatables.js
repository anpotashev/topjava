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
        url: ajaxUrl + userId//+ "?" + data
        , type: "PUT"
        , data: data
        , success: function () {
            updateTable();
            successNoty("changed");
        }
    });
}