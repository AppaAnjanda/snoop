package com.appa.snoop.data.model.product.response

import com.appa.snoop.data.model.category.response.ProductResponse

data class RecommendProductResponse(
    val list: List<ProductResponse>
)
