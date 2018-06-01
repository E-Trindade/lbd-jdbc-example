package com.mycompany.app.database;

public abstract class Queries{
    public static final String DAY_ACCUMULATED_SALES_IN_MONTH = "SELECT "+
            "date_part('day', \"data\") as dia," +
            "date_part('month', \"data\") as mes," +
            "date_part('year', \"data\") as ano," +
            "sum(detalhe.valorunidade * detalhe.quantidade) \"receita\" " +
        "from detalhe " +
        "inner join notasfiscais on detalhe.numero = notasfiscais.numero " +
        "where date_part('month', \"data\")=? and date_part('year', \"data\")=? " +
        "group by dia, mes, ano " +
        "order by ano, mes, dia ";
    public static final String RANKING_DAY_ACCUMULATED_SALES_IN_MONTH = "";
}