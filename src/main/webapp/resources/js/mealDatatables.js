var ajaxUrl = "ajax/profile/meals/";
var datatableApi;

var allowTimes = [
    '00:00', '01:00', '02:00',  '03:00', '04:00', '05:00', '06:00',  '07:00', '08:00',  '09:00',
    '10:00', '11:00', '12:00',  '13:00', '14:00', '15:00', '16:00',  '17:00', '18:00',  '19:00',
    '20:00', '21:00', '22:00',  '23:00'
];

function updateTable() {
    $.ajax({
        type: "POST",
        url: ajaxUrl + "filter",
        data: $("#filter").serialize(),
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(ajaxUrl, updateTableByData);
}

$(function () {
    datatableApi = $("#datatable").DataTable({
        "paging": false,
        "info": true,
        "ajax": {
            "url": ajaxUrl,
            "dataSrc": ""
        },
        "columns": [
            {
                "data": "dateTime"
                , "render": function (data, type, row) {
                // debugger;
                if (type === "display") {
                    return formatdt(data);
                }
                return data;
            }
            },
            {
                "data": "description"

            },
            {
                "data": "calories"
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderEditBtn
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderDeleteBtn
            }
        ],
        "order": [
            [
                0,
                "desc"
            ]
        ]
        , "createdRow": function (row, data, dataIndex) {
            $(row).addClass(data.exceed ? "exceeded" : "normal")
        }
        , "initComplete": makeEditable
    });

    $.datetimepicker.setLocale(i18n["lang"]);

    $("#startDate,#endDate").datetimepicker({
        format: 'Y-m-d', timepicker: false
    });

    $("#startTime,#endTime").datetimepicker({
        format: 'H:i', datepicker: false
        , allowTimes: allowTimes
    });

    $("#dateTime").datetimepicker({
        format: 'Y-m-d H:i'
        , allowTimes: allowTimes
    })
});

