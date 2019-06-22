package com.example.currency
import com.example.currency.converters.FloatConverter
import com.example.currency.converters.IntConverter
import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "Valute")
data class Currency(
    @Attribute(name = "ID")
    val id: String,

    @PropertyElement(name = "Name")
    val name: String,

    @PropertyElement(name = "Value", converter = FloatConverter::class)
    val value: Float,

    @PropertyElement(name = "Nominal", converter = IntConverter::class)
    val nominal: Int,

    @PropertyElement(name = "CharCode")
    val charCode: String,

    @PropertyElement(name = "NumCode")
    val numCode: String
)

@Xml
data class ValCurs(
    @Attribute(name = "Date")
    val date: String,

    @Attribute(name = "name")
    val name: String,

    @Element
    val currs: List<Currency>
)