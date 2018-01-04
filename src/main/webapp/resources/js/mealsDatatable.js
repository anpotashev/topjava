var ajaxUrl = "ajax/meals/";
var datatableApi;
var filtered = false;

// $(document).ready(function () {
$(function () {
    datatableApi = $("#dataTable").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime"
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
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
                "desc"
            ]
        ]
    });
    makeEditable();

});

function updateTable() {
    if (filtered) {
        updateTableWithFiter();
    } else {
        updateTableWithoutFilter();
    }
}


clearFilter = function () {
    $("#filterForm").find(":input").val("");
    filtered = false;
    updateTable();
}

setFilter = function () {
    filtered = true;
    updateTable();

}

updateTableWithoutFilter = function () {
    $.get(ajaxUrl, function (data) {
        datatableApi.clear().rows.add(data).draw();
    });
}

updateTableWithFiter = function () {
    var form = $("#filterForm");
    $.ajax({
        type: "GET",
        url: ajaxUrl + "filter",
        data: form.serialize(),
        success: function (data) {
            datatableApi.clear().rows.add(data).draw();
        }
    });
}

