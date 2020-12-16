package com.example.corona19

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root
data class Header(@field:Element var resultCode: String,
                  @field:Element var resultMsg: String
)

@Root
data class Item(@field:Element var accDefRate: String,
                @field:Element var accExamCnt: String,
                @field:Element var accExamCompCnt: String,
                @field:Element var careCnt: String,
                @field:Element var clearCnt: String,
                @field:Element var createDt: String,
                @field:Element var deathCnt: String,
                @field:Element var decideCnt: String,
                @field:Element var examCnt: String,
                @field:Element var resultNegCnt: String,
                @field:Element var seq: String,
                @field:Element var stateDt: String,
                @field:Element var stateTime: String,
                @field:Element var updateDate: String
)

@Root
data class Items(@field:ElementList var item: List<Item>)

@Root
data class Body(@field:Element var items: Items,
                @field:Element var numOfRows: String,
                @field:Element var pageNo: String,
                @field:Element var totalCount: String
)

@Root
data class Response(@field:Element var header: Header,
                    @field:Element var body: Body)
