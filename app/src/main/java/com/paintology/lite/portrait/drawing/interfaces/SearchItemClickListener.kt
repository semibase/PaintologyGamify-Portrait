package com.paintology.lite.portrait.drawing.interfaces

import com.paintology.lite.portrait.drawing.Enums.SearchResultType

interface SearchItemClickListener {
    fun selectItem(pos: Int, type: SearchResultType)
}