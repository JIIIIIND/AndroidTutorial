package com.example.corona19

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

data class Header @JvmOverloads constructor(
        @field:Element(name = "resultCode", required = false) var resultCode: Int? = null,
        @field:Element(name = "resultMsg", required = false) var resultMsg: String? = null
)

data class Item @JvmOverloads constructor(
        @field:Element(name = "accDefRate", required = false) var accDefRate: String? = null,
        @field:Element(name = "accExamCnt", required = false) var accExamCnt: String? = null,
        @field:Element(name = "accExamCompCnt", required = false) var accExamCompCnt: String? = null,
        @field:Element(name = "careCnt", required = false) var careCnt: String? = null,
        @field:Element(name = "clearCnt", required = false) var clearCnt: String? = null,
        @field:Element(name = "createDt", required = false) var createDt: String? = null,
        @field:Element(name = "deathCnt", required = false) var deathCnt: String? = null,
        @field:Element(name = "decideCnt", required = false) var decideCnt: String? = null,
        @field:Element(name = "examCnt", required = false) var examCnt: String? = null,
        @field:Element(name = "resutlNegCnt", required = false) var resutlNegCnt: String? = null,
        @field:Element(name = "seq", required = false) var seq: String? = null,
        @field:Element(name = "stateDt", required = false) var stateDt: String? = null,
        @field:Element(name = "stateTime", required = false) var stateTime: String? = null,
        @field:Element(name = "updateDt", required = false) var updateDt: String? = null
)

data class Items @JvmOverloads constructor(@field:ElementList(inline = true, entry = "item") var item: List<Item>? = null)

data class Body @JvmOverloads constructor(
        @field:Element(name = "items", required = false) var items: Items? = null,
        @field:Element(name = "numOfRows", required = false) var numOfRows: String? = null,
        @field:Element(name = "pageNo", required = false) var pageNo: String? = null,
        @field:Element(name = "totalCount", required = false) var totalCount: String? = null
)

data class Response @JvmOverloads constructor(@Root(strict = false, name="header") var header: Header? = null,
                    @Root(strict = false, name="header") var body: Body? = null)
