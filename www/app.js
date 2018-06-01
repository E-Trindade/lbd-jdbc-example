'use strict';
//Ref for chart
// https://bl.ocks.org/nanu146/f48ffc5ec10270f55c9e1fb3da8b38f0
$(function () {

    function _dayOfWeek(i){
        return [
            'Domingo',
            'Segunda',
            'Terça',
            'Quarta',
            'Quinta',
            'Sexta',
            'Sábado'
        ][i - 1]
    }
    function showMonthlyGraph(month, year) {
        $.getJSON('http://localhost:8000/api/monthly-report', {
                month: month,
                year: year
            })
            .then(function (res) {
                var table = $('<table class="table table-striped table-hover"></table>')
                table.append(
                    $('<tr></tr>').append([
                        $('<th>Semana</th>'),
                        $('<th>Dia</th>'),
                        $('<th>Data</th>'),
                        $('<th>Venda</th>'),
                        $('<th>Part%</th>'),
                        $('<th>Status dia</th>'),
                    ])
                )

                var rows = []
                res.daySales.forEach(function (s) {
                    rows.push(
                        $('<tr></tr>')
                        .append([
                            $('<td>' + s.weekOfMonth + '</td>'),
                            $('<td>' + _dayOfWeek(s.dayOfWeek) + '</td>'),
                            $('<td>' + s.day + '</td>'),
                            $('<td>' + s.revenue.toFixed(2) + '</td>'),
                            $('<td>' + (s.fractionInMonth * 100).toFixed(2) + '</td>'),
                            $('<td>' + s.classification + '</td>')
                        ])
                    )
                })
                table.append($('<tbody></tbody>')
                    .append(rows))
                $('#application')
                    .empty()
                    .append(table)
            })
    }

    window.onByMonthClick = function () {
        var month = $('#month').val()
        var year = $('#year').val()
        showMonthlyGraph(month, year)
    }

    window.onRankingClick = function () {

    }

    window.onGraphClick = function () {

    }
})